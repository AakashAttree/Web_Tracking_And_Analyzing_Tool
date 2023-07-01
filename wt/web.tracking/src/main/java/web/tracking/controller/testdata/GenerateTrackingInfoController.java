package web.tracking.controller.testdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import web.tracking.db.dao.ClientTrackingInfoRepository;
import web.tracking.db.dto.ClientTrackingInfoDTO;

@RestController
public class GenerateTrackingInfoController {

	@Autowired
	ClientTrackingInfoRepository clientTrackingInfoRepository;

	@RequestMapping(value = "/generatetestdata/{clientId}", method = RequestMethod.GET)
	public String generateData(@PathVariable String clientId) {

		ClientTrackingInfoDTO clientTrackingInfo = new ClientTrackingInfoDTO();
		clientTrackingInfo.setClientId(clientId);
		clientTrackingInfo.setPageName("test");
		clientTrackingInfo.setScript("<script></script>");
		clientTrackingInfoRepository.save(clientTrackingInfo);

		return null;
	}

}
