package web.tracking.workflow.action.impl;

import java.util.List;
import org.springframework.context.ApplicationContext;
import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.SessionTrackingDataRepository;
import web.tracking.db.dto.SessionTrackingDataDTO;
import web.tracking.workflow.action.WorkflowAction;
import web.tracking.workflow.context.Context;

public class UpdateSessionInfoWorkflowAction implements WorkflowAction {

	@Override
	public boolean doAction(Context context) throws Exception {
	  try {
      ApplicationContext applicationContext = (ApplicationContext)context.getAttribute(TrackingConstants.SPRING_APPLICATION_CONTEXT);
      List<SessionTrackingDataDTO> sessionTrackingDataDTOs = (List<SessionTrackingDataDTO>)context.getAttribute(TrackingConstants.COMPLETED_SESSIONS);
      SessionTrackingDataRepository sessionDAO = applicationContext.getBean(SessionTrackingDataRepository.class);
      sessionTrackingDataDTOs.stream().filter(session -> session.isClosed()).forEach(session ->{
        sessionDAO.save(session);
      });
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
		return false;
	}

}
