package web.tracking.controller.report;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.tracking.controller.response.RequestAndSessionCountResponse;
import web.tracking.db.dao.RequestTrackingDataRepository;
import web.tracking.db.dao.SessionTrackingDataRepository;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.db.dto.SessionTrackingDataDTO;
import web.tracking.utility.DateOption;
import web.tracking.utility.WebUtils;

@Controller
public class RequestAndSessionCountController {

  @Autowired
  private RequestTrackingDataRepository requestTrackingRepo;

  @Autowired
  private SessionTrackingDataRepository sessionTrackingRepo;

  @RequestMapping(value = "/chart/company/{companyId}/report/count/{dateOption}")
  @ResponseBody
  public RequestAndSessionCountResponse getCount(@PathVariable(name = "companyId") String companyId, 
      @PathVariable(name = "dateOption") String dateOption) {
    DateOption dates = WebUtils.getDates(dateOption);
    List<RequestTrackingDataDTO> requestsData 
    = requestTrackingRepo.findByCompanyIdAndCreatedTSBetween(companyId,  
        dates.getFromDate(), dates.getToDate());

    List<SessionTrackingDataDTO> sessionsData = sessionTrackingRepo.findByCompanyIdAndCreatedTSBetween(companyId,  
        dates.getFromDate(), dates.getToDate());

    return new RequestAndSessionCountResponse(Long.valueOf(sessionsData.size()),Long.valueOf(requestsData.size()));
  }

}
