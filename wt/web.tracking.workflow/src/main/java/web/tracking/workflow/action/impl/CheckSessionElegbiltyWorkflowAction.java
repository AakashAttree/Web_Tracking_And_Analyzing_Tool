package web.tracking.workflow.action.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationContext;
import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.CompanyRepository;
import web.tracking.db.dao.SessionTrackingDataRepository;
import web.tracking.db.dto.CompanyDTO;
import web.tracking.db.dto.SessionTrackingDataDTO;
import web.tracking.workflow.action.WorkflowAction;
import web.tracking.workflow.context.Context;


public class CheckSessionElegbiltyWorkflowAction implements WorkflowAction {

  @Override
  public boolean doAction(Context context) throws Exception {
    System.out.println("CheckSessionElegbiltyWorkflowAction");
    ApplicationContext applicationContext =
        (ApplicationContext) context.getAttribute(TrackingConstants.SPRING_APPLICATION_CONTEXT);
    SessionTrackingDataRepository sessionTrackingDataRepository =
        (SessionTrackingDataRepository) applicationContext
        .getBean(SessionTrackingDataRepository.class);

    CompanyRepository companyRepo = applicationContext.getBean(CompanyRepository.class);

    LocalDateTime dateTime = LocalDateTime.now().minusMinutes(3);
    System.out.println(dateTime);
    Boolean closed = new Boolean(false);
    List<CompanyDTO> companies = companyRepo.findAll();
    for(CompanyDTO company : companies) {
      List<SessionTrackingDataDTO> sessionTrackingDataDTOs = sessionTrackingDataRepository.findByCompanyIdAndClosed(company.getId(), closed);
      if(sessionTrackingDataDTOs != null) {
        sessionTrackingDataDTOs = sessionTrackingDataDTOs.stream().filter(predicate -> {
          return predicate.getModifiedTS().isBefore(dateTime);
        }).collect(Collectors.toList());
        if (sessionTrackingDataDTOs.size() > 0) {
          context.setAttribute(TrackingConstants.COMPLETED_SESSIONS, sessionTrackingDataDTOs);
          return true;
        }
      }
    }
    return false;
  }

}
