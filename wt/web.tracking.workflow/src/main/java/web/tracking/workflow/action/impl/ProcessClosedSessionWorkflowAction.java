package web.tracking.workflow.action.impl;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.context.ApplicationContext;
import web.tracking.core.StringUtils;
import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.EndUserActiviesRepository;
import web.tracking.db.dao.RequestTrackingDataRepository;
import web.tracking.db.dao.SessionClusterRepository;
import web.tracking.db.dto.EndUserActivies;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.db.dto.SessionClusterDTO;
import web.tracking.db.dto.SessionTrackingDataDTO;
import web.tracking.workflow.action.WorkflowAction;
import web.tracking.workflow.context.Context;

public class ProcessClosedSessionWorkflowAction implements WorkflowAction {

  @Override
  public boolean doAction(Context context) throws Exception {
    System.out.println(ProcessClosedSessionWorkflowAction.class.getName());
    ApplicationContext applicationContext = (ApplicationContext)context.getAttribute(TrackingConstants.SPRING_APPLICATION_CONTEXT);
    List<SessionTrackingDataDTO> sessionTrackingDataDTOs = (List<SessionTrackingDataDTO>)context.getAttribute(TrackingConstants.COMPLETED_SESSIONS);
    final RequestTrackingDataRepository requestTrackingDataRepository = applicationContext.getBean(RequestTrackingDataRepository.class);
    SessionClusterRepository sessionClusterRepository = applicationContext.getBean(SessionClusterRepository.class);
    EndUserActiviesRepository endUserActiviesRepository =  applicationContext.getBean(EndUserActiviesRepository.class);
    sessionTrackingDataDTOs.stream()
    .filter(session -> {
      List<SessionClusterDTO> sessionClusters = sessionClusterRepository.findBySessionId(session.getSessionId());
      boolean exists = (sessionClusters == null || sessionClusters.isEmpty());
      return exists;
    })
    .forEach(session ->{
      List<RequestTrackingDataDTO> requestTrackingDataDTOs = requestTrackingDataRepository.findBySessionId(session.getSessionId());
      if(requestTrackingDataDTOs != null && !requestTrackingDataDTOs.isEmpty()) {
        Boolean hasSocialMediaId = false;
        Boolean isKnownContact = false;
        Set<String> activities = new HashSet<>();
        Set<String> pages = new HashSet<>();
        for(RequestTrackingDataDTO requestDTO: requestTrackingDataDTOs) {
          hasSocialMediaId = StringUtils.isNotBlank(requestDTO.getSocialMediaId());
          EndUserActivies endUserActivity = endUserActiviesRepository.findByRequestId(requestDTO.getRequestId());
          isKnownContact = endUserActivity != null && !"unknown".equalsIgnoreCase(endUserActivity.getEndUserId());
          pages.add(requestDTO.getPage());
          Map<String,String> requestObject = (Map<String,String>)requestDTO.getData().get("REQUEST_OBJECT");
          activities.add(requestObject.get("optype"));
        }
        Duration sessionDuration = Duration.between(session.getCreatedTS(), session.getModifiedTS());
        Integer averageRequestTime = sessionDuration.getNano()/requestTrackingDataDTOs.size();
        SessionClusterDTO sessionClusterDTO = new SessionClusterDTO(); 
        sessionClusterDTO.setSessionId(session.getSessionId());
        sessionClusterDTO.setNumberOfRequests(requestTrackingDataDTOs.size());
        sessionClusterDTO.setAverageRequestTime(averageRequestTime);
        sessionClusterDTO.setHasSocialMediaId(hasSocialMediaId);
        sessionClusterDTO.setKnownContact(isKnownContact);
        sessionClusterDTO.setNumberOfDistinctActivities(activities.size());
        sessionClusterDTO.setNumberOfDistinctPageVisit(pages.size());
        sessionClusterDTO.setTotalDurationOfSession(sessionDuration.getNano());
        sessionClusterDTO.setCustomer(false);
        sessionClusterRepository.save(sessionClusterDTO);
      }
    });
    return true;
  }

}
