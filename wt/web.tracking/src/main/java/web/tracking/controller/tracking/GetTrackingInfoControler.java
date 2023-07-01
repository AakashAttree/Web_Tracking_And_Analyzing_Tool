package web.tracking.controller.tracking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import web.tracking.controller.request.ClientTrackingPOJO;
import web.tracking.db.dao.ClientTrackingInfoRepository;
import web.tracking.db.dto.ClientTrackingInfoDTO;

@RestController
public class GetTrackingInfoControler {

	@Autowired
	ClientTrackingInfoRepository clientTrackingInfoRepository;

	@RequestMapping(value = "/gettrackinginfo/{clientId}", method = RequestMethod.GET)
	public List<ClientTrackingPOJO> getTrackingInfo(@PathVariable(name = "clientId") String clientId) {
		List<ClientTrackingInfoDTO> clientTrackingInfos = clientTrackingInfoRepository.findByClientId(clientId);
		List<ClientTrackingPOJO> clientTrackingPOJOs = new ArrayList<ClientTrackingPOJO>();
		if (clientTrackingInfos != null && !clientTrackingInfos.isEmpty()) {
			for (ClientTrackingInfoDTO clientTrackingInfo : clientTrackingInfos) {
				clientTrackingPOJOs.add(transform(clientTrackingInfo));
			}
		}
		return clientTrackingPOJOs;
	}

	private ClientTrackingPOJO transform(ClientTrackingInfoDTO clientTrackingInfo) {
		ClientTrackingPOJO clientTrackingPOJO = new ClientTrackingPOJO();
		clientTrackingPOJO.setCompanyId(clientTrackingInfo.getClientId());
		clientTrackingPOJO.setId(clientTrackingInfo.getId());
		clientTrackingPOJO.setScript(clientTrackingInfo.getScript());
		clientTrackingPOJO.setPageName(clientTrackingInfo.getPageName());
//		clientTrackingPOJO.setVariablesIds(clientTrackingInfo.getVariablesIds());
		return clientTrackingPOJO;
	}
}
