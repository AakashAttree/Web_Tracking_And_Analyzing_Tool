package web.tracking.workflow.action.referrer.handler;

import java.net.URL;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import web.tracking.core.StringUtils;
import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.CompanyRepository;
import web.tracking.db.dao.WebTrackingDAO;
import web.tracking.db.dto.CompanyDTO;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.workflow.action.WorkflowAction;
import web.tracking.workflow.action.referrer.handler.factory.ReferrerURLHandlerStrategyFactory;
import web.tracking.workflow.action.referrer.handler.strategy.ReferrerURLHandlerStrategy;
import web.tracking.workflow.context.Context;

public class ReferrerTrakingWorkflowAction implements WorkflowAction {

	ReferrerURLHandlerStrategyFactory factory = new ReferrerURLHandlerStrategyFactory();
	@Override
	public boolean doAction(Context context) throws Exception {
		RequestTrackingDataDTO requestDTO = (RequestTrackingDataDTO) context.getAttribute(TrackingConstants.REQUEST_DTO);
		ApplicationContext applicationContext = (ApplicationContext)context.getAttribute(TrackingConstants.SPRING_APPLICATION_CONTEXT);
		CompanyRepository companyRepository = (CompanyRepository)applicationContext.getBean(CompanyRepository.class);
		WebTrackingDAO<CompanyDTO> companyDAO = new WebTrackingDAO<>();
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(requestDTO.getCompanyId());
		companyDTO = companyDAO.findRecord(companyRepository, companyDTO);
		if(!companyDTO.isReferrerTrackingEnabled()){
		  return true;
		}
		Map<String, Object> requestData =  requestDTO.getData();
		Object requestObject = requestData.get(TrackingConstants.REQUEST_OBJECT);
		if(requestObject == null)
			return true;
		JsonElement jsonElement = new Gson().toJsonTree(requestObject);
		if(jsonElement.isJsonObject()){
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			JsonElement jsonElementReferURL = jsonObject.get(TrackingConstants.REQUEST_OBJECT_REFERRURL);
			String strReferURL =jsonElementReferURL.getAsString();
			if(StringUtils.isBlank(strReferURL))
				return true;
			URL url = new URL(strReferURL);
			String domainName = url.getHost();
			if(domainName == null)
				return true;
			ReferrerURLHandlerStrategy handlerStrategy =  factory.getReferrerHandler(domainName);
			if(handlerStrategy == null)
				return true;
			return handlerStrategy.handleReferrer(applicationContext, requestDTO);
		}
		return false;
	}
}
