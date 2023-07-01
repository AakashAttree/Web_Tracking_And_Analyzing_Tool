package web.tracking.controller.tracking;

import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import web.tracking.configuration.ConfigurationUtility;
import web.tracking.controller.request.ClientTrackingPOJO;
import web.tracking.core.StringUtils;
import web.tracking.db.dao.ClientTrackingInfoRepository;
import web.tracking.db.dao.CompanyVariableRepository;
import web.tracking.db.dto.ClientTrackingInfoDTO;

@Controller
public class CreateClientTrackingInfo {

	@Autowired
	ClientTrackingInfoRepository clientTrackingInfoRepository;

	@Autowired
	CompanyVariableRepository companyVariableRepository;

	@Autowired
	ConfigurationUtility configurationUtil;

	@RequestMapping(value = "/saveclient/{companyId}", method = RequestMethod.POST)
	@ResponseBody
	public String post(@PathVariable("companyId") String companyId,
			@ModelAttribute ClientTrackingPOJO clientTrackingPOJO, HttpServletRequest httpServletRequest) {

		if (clientTrackingPOJO != null) {
			clientTrackingPOJO.setCompanyId(companyId);
		}

		ClientTrackingInfoDTO clientTrackingInfo = transform(clientTrackingPOJO);

		if (clientTrackingPOJO != null && clientTrackingPOJO.isAddRequest()) {
			String script = generateScript(clientTrackingPOJO, httpServletRequest);
			clientTrackingInfo.setScript(script);
			clientTrackingInfo = clientTrackingInfoRepository.insert(clientTrackingInfo);
		} else if (clientTrackingPOJO != null && clientTrackingPOJO.isUpdateRequest()) {
			clientTrackingInfo.setScript(HtmlUtils.htmlEscape(clientTrackingPOJO.getScript()));
			clientTrackingInfo = clientTrackingInfoRepository.save(clientTrackingInfo);
		} else {
			clientTrackingInfoRepository.delete(clientTrackingInfo);
		}

		return "refresh";
	}

	private String generateScript(ClientTrackingPOJO clientTrackingPOJO, HttpServletRequest httpServletRequest) {
		String domainName = configurationUtil.get(clientTrackingPOJO.getCompanyId(), "script", "company.domain.name");
		if (StringUtils.isBlank(domainName)) {
			domainName = httpServletRequest.getServerName();
			if (domainName != null && domainName.trim().length() > 0 && domainName.equalsIgnoreCase("localhost")) {
				domainName = "localhost:" + httpServletRequest.getServerPort();
			}
		}
		String compId = new String(Base64.getUrlEncoder().encode(clientTrackingPOJO.getCompanyId().getBytes()));
		StringBuilder stringBuilder = new StringBuilder("<script type=\"text/javascript\" src=\"http://" + domainName
				+ "/script/" + compId + "/tracking.js\"></script>\n");
		stringBuilder.append("<script>\n$(document).ready(function()\n{\n");
		stringBuilder.append("var obj = {};\n");
		stringBuilder.append("obj.optype = \"visit\";\n");

		String page = new String(Base64.getUrlEncoder().encode(clientTrackingPOJO.getPageName().getBytes()));
		stringBuilder.append("obj.page = \"" + page + "\";\n");
		stringBuilder.append("track(obj);\n");
		stringBuilder.append("});\n</script>\n");
		return HtmlUtils.htmlEscape(stringBuilder.toString());
	}

	private ClientTrackingInfoDTO transform(ClientTrackingPOJO clientTrackingPOJO) {
		ClientTrackingInfoDTO clientTrackingInfo = new ClientTrackingInfoDTO();
		clientTrackingInfo.setClientId(clientTrackingPOJO.getCompanyId());
		clientTrackingInfo.setPageName(clientTrackingPOJO.getPageName());
		clientTrackingInfo.setScript(clientTrackingPOJO.getScript());
		clientTrackingInfo.setId(clientTrackingPOJO.getId());
		// clientTrackingInfo.setVariablesIds(clientTrackingPOJO.getVariablesIds());
		return clientTrackingInfo;
	}

}
