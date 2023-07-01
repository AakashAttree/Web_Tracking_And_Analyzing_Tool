package web.tracking.workflow.action.referrer.handler.strategy;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.SearchStringRepository;
import web.tracking.db.dao.SearchWordRepository;
import web.tracking.db.dao.SocialMediaRepository;
import web.tracking.db.dao.WebTrackingDAO;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.db.dto.SearchStringDTO;
import web.tracking.db.dto.SearchWordDTO;
import web.tracking.db.dto.SocialMediaActivityDTO;

public abstract class ReferrerURLHandlerStrategy {

  protected String name;
  protected String parameterName;

  public ReferrerURLHandlerStrategy(String name,String parameterName) {
    super();
    this.name = name;
    this.parameterName = parameterName;
  }

  public boolean handleReferrer(ApplicationContext applicationContext, RequestTrackingDataDTO requestDTO) throws Exception {
    SocialMediaRepository socialMediaRepository = (SocialMediaRepository)applicationContext.getBean(SocialMediaRepository.class);
    Map<String, Object> requestData =  requestDTO.getData();
    Object requestObject = requestData.get(TrackingConstants.REQUEST_OBJECT);
    JsonElement jsonElement = new Gson().toJsonTree(requestObject);
    if(jsonElement.isJsonObject()){
      JsonObject jsonObject = jsonElement.getAsJsonObject();
      JsonElement jsonElementReferURL = jsonObject.get(TrackingConstants.REQUEST_OBJECT_REFERRURL);

      String strReferURL =jsonElementReferURL.getAsString();

      URL url = new URL(strReferURL);
      String companyId = requestDTO.getCompanyId();
      String page = requestDTO.getPage();

      SocialMediaActivityDTO activityDTO = saveSocialMediaActivity(socialMediaRepository, jsonObject, companyId, page);
      if(activityDTO != null && activityDTO.getId() != null){
        setSocialMediaIdToRequest(requestDTO, activityDTO);
        markProcessed(requestDTO);
      }
      if(url.getQuery() != null){
        saveSearchContent(applicationContext, requestDTO, url.getQuery(), companyId, page, parameterName);
      }
    }
    return true;
  }


  protected void markProcessed(RequestTrackingDataDTO requestDTO) {
    requestDTO.setReferrerProcessed(true);
  }

  protected void setSocialMediaIdToRequest(RequestTrackingDataDTO requestDTO,
      SocialMediaActivityDTO activityDTO) {
    String id = activityDTO.getId();
    requestDTO.setSocialMediaId(id);
  }

  protected void saveSearchContent(ApplicationContext applicationContext, RequestTrackingDataDTO requestDTO, String urlQuery, String companyId, String page,String parameterName)
      throws UnsupportedEncodingException {
    String[] array = urlQuery.split("&");
    for(String query : array){
      if(query != null && parameterName != null && query.contains(parameterName)){
        SearchWordRepository searchWordRepository = (SearchWordRepository)applicationContext.getBean(SearchWordRepository.class);
        SearchStringRepository searchStringRepository = (SearchStringRepository)applicationContext.getBean(SearchStringRepository.class);
        String words = query.substring(query.indexOf(parameterName)+2, query.length());
        words = URLDecoder.decode(words,"utf-8");
        String[] arr = words.split(" ");
        for(String value : arr){
          saveSearchWord(searchWordRepository,requestDTO, companyId, page, value);
        }
        saveSearchString(searchStringRepository,requestDTO, companyId, page, words);
      }
    }
  }

  public void saveSearchString(SearchStringRepository searchStringRepository, RequestTrackingDataDTO requestDTO, String companyId, String page,
      String words) {
    SearchStringDTO searchStringDTO = initSearchStringDTO(companyId, page, words);
    searchStringDTO = fetchExistingSearchString(searchStringRepository, searchStringDTO);
    if(searchStringDTO != null){
      preSave(searchStringDTO);
      searchStringDTO = searchStringRepository.save(searchStringDTO);
      requestDTO.setSearchStringId(searchStringDTO.getId() );
    }
  }

  private SearchStringDTO fetchExistingSearchString(SearchStringRepository searchStringRepository,
      SearchStringDTO searchStringDTO) {
    WebTrackingDAO<SearchStringDTO> trackingDAO2 = new WebTrackingDAO<SearchStringDTO>();
    SearchStringDTO existingSearchStringDTO = trackingDAO2.findRecord(searchStringRepository, searchStringDTO);
    if(existingSearchStringDTO != null){
      existingSearchStringDTO.setCount(existingSearchStringDTO.getCount());
      return existingSearchStringDTO;
    }
    else{
      searchStringDTO.setCount(1);
      return searchStringDTO;
    }
  }

  protected SearchStringDTO initSearchStringDTO(String companyId, String page, String words) {
    SearchStringDTO searchStringDTO = new SearchStringDTO();
    searchStringDTO.setSearchString(words);
    searchStringDTO.setCompId(companyId);
    searchStringDTO.setPage(page);
    searchStringDTO.setSocialMediaName(name);
    return searchStringDTO;
  }

  public void saveSearchWord(SearchWordRepository searchWordRepository, RequestTrackingDataDTO requestDTO, String companyId, String page,
      String value) {
    SearchWordDTO searchWordDTO = initSearchWord(companyId, page, value);
    searchWordDTO = fetchExistingSearchWord(searchWordRepository, searchWordDTO);
    preSave(searchWordDTO);
    searchWordDTO = searchWordRepository.save(searchWordDTO);
    requestDTO.setSearchWordId(searchWordDTO.getId());
  }

  protected SearchWordDTO fetchExistingSearchWord(SearchWordRepository searchWordRepository, SearchWordDTO searchWordDTO) {
    WebTrackingDAO<SearchWordDTO> trackingDAO1 = new WebTrackingDAO<SearchWordDTO>();
    SearchWordDTO existingSearchWordDTO = trackingDAO1.findRecord(searchWordRepository, searchWordDTO);
    if(existingSearchWordDTO != null){
      existingSearchWordDTO.setCount(existingSearchWordDTO.getCount() + 1);
      return existingSearchWordDTO;
    }
    else{
      searchWordDTO.setCount(1);
      return searchWordDTO;
    }
  }

  protected SearchWordDTO initSearchWord(String companyId, String page, String value) {
    SearchWordDTO searchWordDTO = new SearchWordDTO();
    searchWordDTO.setCompId(companyId);
    searchWordDTO.setPage(page);
    searchWordDTO.setSocialMediaName(name);
    searchWordDTO.setSearchWord(value);
    return searchWordDTO;
  }

  public SocialMediaActivityDTO saveSocialMediaActivity(SocialMediaRepository socialMediaRepository, JsonObject jsonObject,
      String companyId, String page) {
    SocialMediaActivityDTO activityDTO = initSocialMediaActivity(jsonObject, companyId, page);
    activityDTO = fetchSocialMediaActivity(socialMediaRepository, activityDTO);
    preSave(activityDTO);
    return socialMediaRepository.save(activityDTO);
  }

  protected SocialMediaActivityDTO fetchSocialMediaActivity(SocialMediaRepository socialMediaRepository,
      SocialMediaActivityDTO activityDTO) {
    WebTrackingDAO<SocialMediaActivityDTO> trackingDAO = new WebTrackingDAO<SocialMediaActivityDTO>();
    SocialMediaActivityDTO existing = trackingDAO.findRecord(socialMediaRepository, activityDTO);
    if(existing != null){
      existing.setCount(existing.getCount() +1);
      return existing;
    }else{
      activityDTO.setCount(1);
      return activityDTO;

    }
  }

  protected SocialMediaActivityDTO initSocialMediaActivity(JsonObject jsonObject, String companyId, String page) {
    SocialMediaActivityDTO activityDTO = new SocialMediaActivityDTO();
    String optype = jsonObject.get("optype").getAsString();
    activityDTO.setCompId(companyId);
    activityDTO.setActivityType(optype);
    activityDTO.setPage(page);
    activityDTO.setSocialMediaName(name);
    return activityDTO;
  }
  protected abstract void preSave(SearchStringDTO searchStringDTO) ;
  protected abstract void preSave(SearchWordDTO searchWordDTO);
  protected abstract void preSave(SocialMediaActivityDTO activityDTO) ;

}
