package web.tracking.controller.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.tracking.controller.response.SocialMediaChartResponse;
import web.tracking.core.StringUtils;
import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.RequestTrackingDataRepository;
import web.tracking.db.dao.SocialMediaRepository;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.db.dto.SocialMediaActivityDTO;
import web.tracking.utility.DateOption;
import web.tracking.utility.WebUtils;

@Controller
public class SocialMediaChartController {
  @Autowired
  private SocialMediaRepository socialMediaRepository;

  @Autowired
  private RequestTrackingDataRepository requestTrackingRepo;
  
  @RequestMapping(value = "/chart/company/{companyId}/social-media-activity/{dateOption}")
  @ResponseBody
  public SocialMediaChartResponse getSocialMediaActivityTreands(@PathVariable(name = "companyId") String companyId, 
      @PathVariable(name = "dateOption") String dateOption) {
    DateOption dates = WebUtils.getDates(dateOption);
    List<RequestTrackingDataDTO> requestData = requestTrackingRepo.findByCompanyIdAndCreatedTSBetween(companyId, dates.getFromDate(), dates.getToDate());
    List<List<Object>> socialMediaTrend = new ArrayList<List<Object>>();
    List<Object> arrayHeader = new ArrayList<>();
      arrayHeader.add("Social Media");
    Map<String, List<RequestTrackingDataDTO>> collect = requestData.stream()
        .filter(data->StringUtils.isNotBlank(data.getSocialMediaId()))
        .collect(Collectors.groupingBy(RequestTrackingDataDTO::getSocialMediaId));
    List<SocialMediaActivityDTO> socialMedias = socialMediaRepository.findByCompId(companyId);
    Map<String, List<SocialMediaActivityDTO>> socialMediaGroupedByIds = socialMedias.stream()
        .collect(Collectors.groupingBy(SocialMediaActivityDTO::getId));
    Map<String, Map<String,Long>> dataPerSocialMedia = new HashMap<String, Map<String,Long>>();
    collect.entrySet().forEach(action ->{
      List<SocialMediaActivityDTO> socialMediaActivities = socialMediaGroupedByIds.get(action.getKey());
      SocialMediaActivityDTO socialMediaActivity = socialMediaActivities.get(0);
      Map<String, Long> socialMediaInfoMap = dataPerSocialMedia.getOrDefault(socialMediaActivity.getSocialMediaName(), new HashMap<String, Long>());;
      List<RequestTrackingDataDTO> trackingDataValues = action.getValue();
      for(RequestTrackingDataDTO requestTrackingData : trackingDataValues) {
        if(requestTrackingData.getData().containsKey(TrackingConstants.REQUEST_PARAM)) {
          Map<String, List<String>> requestParam = (Map<String, List<String>>)requestTrackingData.getData().get(TrackingConstants.REQUEST_PARAM);
          String optype = requestParam.get("optype").get(0);
          Long count = socialMediaInfoMap.getOrDefault(optype, 0L);
          socialMediaInfoMap.put(optype, count+1);
          if(!arrayHeader.contains(optype))
          {
            arrayHeader.add(optype);
          }
        }
      }
      dataPerSocialMedia.put(socialMediaActivity.getSocialMediaName(), socialMediaInfoMap);
    });
    dataPerSocialMedia.entrySet().forEach(action->{
      List<Object> values = new ArrayList<>();
      values.add(action.getKey());
      for(int index =1; index<arrayHeader.size();index++) {
        values.add(action.getValue().get(arrayHeader.get(index)));
      }
      socialMediaTrend.add(values);
    });
    socialMediaTrend.add(0, arrayHeader);
    return new SocialMediaChartResponse(socialMediaTrend);
  }
}
