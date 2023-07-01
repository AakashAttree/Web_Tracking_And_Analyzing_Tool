package web.tracking.controller.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.tracking.controller.request.CompanyReportPOJO;
import web.tracking.core.StringUtils;
import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.EndUserActiviesRepository;
import web.tracking.db.dao.IpAddressDetailRepository;
import web.tracking.db.dao.RequestTrackingDataRepository;
import web.tracking.db.dao.WebTrackingDAO;
import web.tracking.db.dto.CompanyActivies;
import web.tracking.db.dto.EndUserActivies;
import web.tracking.db.dto.IpAddressDetail;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.utility.DateOption;
import web.tracking.utility.WebUtils;

@Controller
public class CompanyActvityChartReportController {
  
  @Autowired
  private RequestTrackingDataRepository requestTrackingRepo;

  @Autowired
  private EndUserActiviesRepository userActiviesRepository;

  @Autowired
  private IpAddressDetailRepository ipAddressDetailRepository;

  @RequestMapping(value = "/chart/company/{companyId}/report/activity/{dateOption}")
  @ResponseBody
  public CompanyReportPOJO getReportCompanyActivity(@PathVariable(name = "companyId") String companyId, 
      @PathVariable(name = "dateOption") String dateOption) {
    CompanyReportPOJO companyReportPOJO = new CompanyReportPOJO();
    DateOption dates = WebUtils.getDates(dateOption);
    List<RequestTrackingDataDTO> findByCompanyIdAndCreatedTSBetween 
    = requestTrackingRepo.findByCompanyIdAndCreatedTSBetween(companyId,  
        dates.getFromDate(), dates.getToDate());
    setCompanyActivities2(companyReportPOJO, findByCompanyIdAndCreatedTSBetween);
    return companyReportPOJO;
  }

  private void setCompanyActivities2(CompanyReportPOJO companyReportPOJO,
      List<RequestTrackingDataDTO> requestData) {
    List<Object[]> list = new ArrayList<Object[]>();
    Object[] arrayHeader = new Object[2];
    arrayHeader[0] = "Activities";
    arrayHeader[1] = "Numbers";
    list.add(arrayHeader);
    Map<String, Long> activities = requestData.stream().map(value ->{
      Map<String, Object> data = value.getData();
      if(data.containsKey(TrackingConstants.REQUEST_PARAM)) {
        Map<String, List<String>> requestParam = (Map<String, List<String>>)data.get(TrackingConstants.REQUEST_PARAM);
        if(requestParam.containsKey("optype")) {
          return requestParam.get("optype").get(0);
        }
      }
      return "";
    }).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    Set<String> keys = activities.keySet();
    for (String key : keys) {
      Object[] array = new Object[2];
      long count = activities.get(key);
      if (StringUtils.isBlank(key)) {
        key = "Unknown";
      }
      array[0] = key;
      array[1] = count;
      list.add(array);
    }
    companyReportPOJO.setCompanyActivies(list);
  }

  @RequestMapping(value = "/chart/company/{companyId}/report/activity-trend/{dateOption}")
  @ResponseBody
  public CompanyReportPOJO getReportCompanyActivityTrend(@PathVariable(name = "companyId") String companyId, 
      @PathVariable(name = "dateOption") String dateOption) {
    CompanyReportPOJO companyReportPOJO = new CompanyReportPOJO();
    DateOption dates = WebUtils.getDates(dateOption);
    List<RequestTrackingDataDTO> findByCompanyIdAndCreatedTSBetween 
    = requestTrackingRepo.findByCompanyIdAndCreatedTSBetween(companyId,  
        dates.getFromDate(), dates.getToDate());
    List<List<Object>> list = new ArrayList<List<Object>>();
    List<Object> arrayHeader = new ArrayList<>();
    Comparator<List<Object>> comparator = null;
    if("today".equalsIgnoreCase(dateOption)) {
      arrayHeader.add("Hours");
    }
    else  if("currentweek".equalsIgnoreCase(dateOption)) {
      arrayHeader.add("Week");
    }
    else if(dateOption.contains("daterange")) {
      arrayHeader.add("Dates");
    }
    comparator = WebUtils.getDateOptionComparator(dateOption);
    Function<RequestTrackingDataDTO, String> condition = WebUtils.getDateOptionKey(dateOption);
    Map<String, List<RequestTrackingDataDTO>> collect = findByCompanyIdAndCreatedTSBetween.stream()
        .collect(Collectors.groupingBy(condition));
    Map<String, Map<String,Long>> dataPerTime = new HashMap<String, Map<String,Long>>();
    collect.entrySet().forEach(action ->{
      Map<String, Long> map = dataPerTime.getOrDefault(action.getKey(), new HashMap<String, Long>());;
      List<RequestTrackingDataDTO> trackingDataValues = action.getValue();
      for(RequestTrackingDataDTO data: trackingDataValues) {
        if(data.getData().containsKey(TrackingConstants.REQUEST_PARAM)) {
          Map<String, List<String>> requestParam = (Map<String, List<String>>)data.getData().get(TrackingConstants.REQUEST_PARAM);
          String optype = requestParam.get("optype").get(0);
          Long count = map.getOrDefault(optype,0L);
          map.put(optype, count+1);
          if(!arrayHeader.contains(optype))
          {
            arrayHeader.add(optype);
          }
        }
      }
      dataPerTime.put(action.getKey(), map);
    });
    dataPerTime.entrySet().forEach(action->{
      List<Object> values = new ArrayList<>();
      values.add(action.getKey());
      for(int index =1; index<arrayHeader.size();index++) {
        values.add(action.getValue().get(arrayHeader.get(index)));
      }
      list.add(values);
    });
    Collections.sort(list, comparator);
    list.add(0, arrayHeader);
    companyReportPOJO.setCompanyActiviesTrend(list);
    return companyReportPOJO;
  }

  @RequestMapping(value = "/chart/company/{companyId}/report/user-activity/{dateOption}")
  @ResponseBody
  public CompanyReportPOJO getReportEndUserActivity(@PathVariable(name = "companyId") String companyId, 
      @PathVariable(name = "dateOption") String dateOption) {
    CompanyReportPOJO companyReportPOJO = new CompanyReportPOJO();
    DateOption dates = WebUtils.getDates(dateOption);
    setEndUserActivities(companyId, companyReportPOJO, dates);
    return companyReportPOJO;
  }

  @RequestMapping(value = "/chart/company/{companyId}/report/user-activity-trend-unknown/{dateOption}")
  @ResponseBody
  public CompanyReportPOJO getReportEndUserActivityTrendUnknown(@PathVariable(name = "companyId") String companyId, 
      @PathVariable(name = "dateOption") String dateOption) {
    CompanyReportPOJO companyReportPOJO = new CompanyReportPOJO();
    DateOption dates = WebUtils.getDates(dateOption);
    setEndUserActivitiesTrend(companyId, companyReportPOJO, dates, dateOption,false);
    return companyReportPOJO;
  }
  
  @RequestMapping(value = "/chart/company/{companyId}/report/user-activity-trend-known/{dateOption}")
  @ResponseBody
  public CompanyReportPOJO getReportEndUserActivityTrendKnown(@PathVariable(name = "companyId") String companyId, 
      @PathVariable(name = "dateOption") String dateOption) {
    CompanyReportPOJO companyReportPOJO = new CompanyReportPOJO();
    DateOption dates = WebUtils.getDates(dateOption);
    setEndUserActivitiesTrend(companyId, companyReportPOJO, dates, dateOption,true);
    return companyReportPOJO;
  }

  
  @RequestMapping(value = "/chart/company/{companyId}/report/geo/{dateoption}")
  @ResponseBody
  public CompanyReportPOJO getReportGeoActivity(@PathVariable(name = "companyId") String companyId,
      @PathVariable(name = "dateoption") String dateOption) {
    CompanyReportPOJO companyReportPOJO = new CompanyReportPOJO();
    DateOption dates = WebUtils.getDates(dateOption);
    setGeoActiviesData(companyId, companyReportPOJO, dates);
    return companyReportPOJO;
  }

  private void setGeoActiviesData(String companyId, CompanyReportPOJO companyReportPOJO, DateOption dates) {
    List<RequestTrackingDataDTO> findByCompanyIdAndCreatedTSBetween 
    = requestTrackingRepo.findByCompanyIdAndCreatedTSBetween(companyId,  
        dates.getFromDate(), dates.getToDate());
    Map<String, Long> activities = findByCompanyIdAndCreatedTSBetween.stream().map(value ->{
      Map<String, Object> data = value.getData();
      if(data.containsKey(TrackingConstants.IP_ADDRESS)) {
        return String.valueOf(data.get(TrackingConstants.IP_ADDRESS));
      }
      return "";
    }).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    List<Object[]> list = new ArrayList<Object[]>();
    Object[] arrayHeader = new Object[2];
    arrayHeader[0] = "Country";
    arrayHeader[1] = "Activities";
    list.add(arrayHeader);
    activities.forEach((ipAddress,count)->{
      IpAddressDetail findByIp = ipAddressDetailRepository.findByIp(ipAddress);
      if(findByIp != null) {
        Object[] array = new Object[2];
        array[0] = findByIp.getCountryCode();
        array[1] = count;
        list.add(array);
      }
    });
    companyReportPOJO.setGeoActivities(list);
  }

  private void setCompanyActivities(CompanyReportPOJO companyReportPOJO, List<CompanyActivies> companyActivies) {
    List<Object[]> list = new ArrayList<Object[]>();
    Object[] arrayHeader = new Object[2];
    arrayHeader[0] = "Activities";
    arrayHeader[1] = "Numbers";
    list.add(arrayHeader);
    Map<String, List<CompanyActivies>> activities = companyActivies.stream()
        .collect(Collectors.groupingBy(CompanyActivies::getActivityType));
    Set<String> keys = activities.keySet();
    for (String key : keys) {
      Object[] array = new Object[2];
      int count = activities.get(key).size();
      if (StringUtils.isBlank(key)) {
        key = "Unknown";
      }
      array[0] = key;
      array[1] = count;
      list.add(array);
    }
    companyReportPOJO.setCompanyActivies(list);
  }

  private void setEndUserActivities(String companyId, CompanyReportPOJO companyReportPOJO, DateOption dates) {
    WebTrackingDAO<EndUserActivies> trackingDAO = new WebTrackingDAO<EndUserActivies>();
    EndUserActivies endUserActivityExample = new EndUserActivies();
    endUserActivityExample.setCompanyId(companyId);
    List<EndUserActivies> endUserActivies = userActiviesRepository.findByCompanyIdAndCreatedTSBetween(companyId, dates.getFromDate(), dates.getToDate());
    List<Object[]> list2 = new ArrayList<Object[]>();
    Object[] arrayHeader2 = new Object[2];
    arrayHeader2[0] = "Activities";
    arrayHeader2[1] = "Numbers";
    list2.add(arrayHeader2);
    long unknownUserActivityCount = endUserActivies.stream().filter(activity->activity.getEndUserId().equalsIgnoreCase("unknown")).count();
    long knownUserActivityCount = endUserActivies.stream().filter(activity->!activity.getEndUserId().equalsIgnoreCase("unknown")).count();
    Object[] unknownUserInfo = new Object[2];
    unknownUserInfo[0] = "Unknown User Activities";
    unknownUserInfo[1] = unknownUserActivityCount;
    list2.add(unknownUserInfo);
    Object[] knownUserInfo = new Object[2];
    knownUserInfo[0] = "Known User Activities";
    knownUserInfo[1] = knownUserActivityCount;
    list2.add(knownUserInfo);
    companyReportPOJO.setEndUserActivies(list2);
  }
  private void setEndUserActivitiesTrend(String companyId, CompanyReportPOJO companyReportPOJO, DateOption dates, String dateOption, boolean knowUserFalg) {
    List<EndUserActivies> endUserActivies = userActiviesRepository.findByCompanyIdAndCreatedTSBetween(companyId, dates.getFromDate(), dates.getToDate());
    List<List<Object>> endUserActiviesTrend = new ArrayList<List<Object>>();
    List<Object> arrayHeader = new ArrayList<>();
    if("today".equalsIgnoreCase(dateOption)) {
      arrayHeader.add("Hours");
    }
    else  if("currentweek".equalsIgnoreCase(dateOption)) {
      arrayHeader.add("Week");
    }
    else if(dateOption.contains("daterange")) {
      arrayHeader.add("Dates");
    }
    Comparator<List<Object>> comparator = WebUtils.getDateOptionComparator(dateOption);
    Function<EndUserActivies, String> condition = WebUtils.getEndUserDateOptionKey(dateOption);
    Map<String, List<EndUserActivies>> data = null;
    if(knowUserFalg) {
      data = endUserActivies.stream().filter(activity->!activity.getEndUserId().equalsIgnoreCase("unknown")).collect(Collectors.groupingBy(condition));
    }
    else {
      data = endUserActivies.stream().filter(activity->activity.getEndUserId().equalsIgnoreCase("unknown")).collect(Collectors.groupingBy(condition));
    }
    Map<String, Map<String,Long>> dataPerTime = new HashMap<>();
    data.entrySet().forEach(action ->{
      Map<String, Long> map = dataPerTime.getOrDefault(action.getKey(), new HashMap<>());;
      List<EndUserActivies> trackingDataValues = action.getValue();
      for(EndUserActivies trackingData: trackingDataValues) {
        if(trackingData.getActivityType() != null) {
          Long count = map.getOrDefault(trackingData.getActivityType(),0L);
          map.put(trackingData.getActivityType(), count+1);
          if(!arrayHeader.contains(trackingData.getActivityType()))
          {
            arrayHeader.add(trackingData.getActivityType());
          }
        }
      }
      dataPerTime.put(action.getKey(), map);
    });
    dataPerTime.entrySet().forEach(action->{
      List<Object> values = new ArrayList<>();
      values.add(action.getKey());
      for(int index =1; index<arrayHeader.size();index++) {
        values.add(action.getValue().get(arrayHeader.get(index)));
      }
      endUserActiviesTrend.add(values);
    });
    Collections.sort(endUserActiviesTrend, comparator);
    endUserActiviesTrend.add(0, arrayHeader);
    List<Object[]> list2 = new ArrayList<Object[]>();
    Object[] arrayHeader2 = new Object[2];
    arrayHeader2[0] = "Activities";
    arrayHeader2[1] = "Numbers";
    list2.add(arrayHeader2);
    companyReportPOJO.setEndUserActiviesTrend(endUserActiviesTrend);
  }
}
