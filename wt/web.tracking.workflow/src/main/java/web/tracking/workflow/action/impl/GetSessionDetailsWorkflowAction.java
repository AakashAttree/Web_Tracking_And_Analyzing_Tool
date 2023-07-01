package web.tracking.workflow.action.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.RequestTrackingDataRepository;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.db.dto.SessionTrackingDataDTO;
import web.tracking.workflow.action.WorkflowAction;
import web.tracking.workflow.context.Context;

public class GetSessionDetailsWorkflowAction implements WorkflowAction {

	@Override
	public boolean doAction(Context context) throws Exception {
		System.out.println(this.getClass().getName());
		ApplicationContext applicationContext = (ApplicationContext) context.getAttribute(TrackingConstants.SPRING_APPLICATION_CONTEXT);
		List<SessionTrackingDataDTO> sessionTrackingDataDTOs = (List<SessionTrackingDataDTO>)context.getAttribute(TrackingConstants.COMPLETED_SESSIONS);
		final RequestTrackingDataRepository requestTrackingDataRepository = applicationContext.getBean(RequestTrackingDataRepository.class);
		context.setAttribute("processed", false);
		WebTrackingRequestWorkflowStarter requestWorkflowStarter = new WebTrackingRequestWorkflowStarter(applicationContext);
		sessionTrackingDataDTOs.parallelStream().forEach(value -> {
			List<RequestTrackingDataDTO> requestTrackingDataDTOs = (List<RequestTrackingDataDTO>)requestTrackingDataRepository.findBySessionIdAndIpProcessed(value.getSessionId(), false);
			requestTrackingDataDTOs.stream().forEach(requestDTO -> {
				requestWorkflowStarter.execute(requestDTO);
			});
			value.setClosed(true);
			context.setAttribute("processed", true);
		});
		return (boolean)context.getAttribute("processed");
	}

}
