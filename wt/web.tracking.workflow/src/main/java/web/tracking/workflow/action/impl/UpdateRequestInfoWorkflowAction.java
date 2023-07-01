package web.tracking.workflow.action.impl;

import org.springframework.context.ApplicationContext;

import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.RequestTrackingDataRepository;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.workflow.action.WorkflowAction;
import web.tracking.workflow.context.Context;

public class UpdateRequestInfoWorkflowAction implements WorkflowAction {

  @Override
  public boolean doAction(Context context) throws Exception {
    System.out.println(this.getClass().getName());
    RequestTrackingDataDTO requestDTO = (RequestTrackingDataDTO) context.getAttribute(TrackingConstants.REQUEST_DTO);

    ApplicationContext applicationContext = (ApplicationContext)context.getAttribute(TrackingConstants.SPRING_APPLICATION_CONTEXT);
    RequestTrackingDataRepository requestTrackingDataRepository = applicationContext.getBean(RequestTrackingDataRepository.class);
    requestTrackingDataRepository.save(requestDTO);
    return true;
  }
 
}
