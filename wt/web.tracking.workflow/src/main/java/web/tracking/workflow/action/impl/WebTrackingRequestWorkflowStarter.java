package web.tracking.workflow.action.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import web.tracking.core.TrackingConstants;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.workflow.impl.StandardWorkflow;

public class WebTrackingRequestWorkflowStarter{

  private ApplicationContext applicationContext;

  public WebTrackingRequestWorkflowStarter(ApplicationContext applicationContext) {
    super();
    this.applicationContext = applicationContext;
  }

  public void execute(RequestTrackingDataDTO requestTrackingDataDTO) {
    try {
      StandardWorkflow standardWorkflow =(StandardWorkflow) applicationContext.getBean(TrackingConstants.REQUSET_WEB_TRACKING_WORKFLOW);
      Map<String, Object> parameters = new HashMap<>();
      parameters.put(TrackingConstants.SPRING_APPLICATION_CONTEXT, applicationContext);
      parameters.put(TrackingConstants.REQUEST_DTO, requestTrackingDataDTO);
      standardWorkflow.processWorkflow(TrackingConstants.REQUSET_WEB_TRACKING_WORKFLOW, parameters);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
