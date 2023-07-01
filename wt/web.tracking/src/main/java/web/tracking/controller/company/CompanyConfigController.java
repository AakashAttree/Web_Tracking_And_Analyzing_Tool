package web.tracking.controller.company;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.tracking.configuration.ConfigurationUtility;
import web.tracking.configuration.LoadConfigurationInDB;
import web.tracking.core.StringUtils;
import web.tracking.db.dao.CompanyConfigRepository;
import web.tracking.db.dto.CompanyConfigDTO;

@RestController
public class CompanyConfigController {

	@Autowired
	CompanyConfigRepository companyConfigRepository;

	@Autowired
	LoadConfigurationInDB loadConfiguration;

	@RequestMapping(value = "/company/{companyId}/config/get", method = RequestMethod.GET)
	public List<CompanyConfigDTO> get(@PathVariable(name = "companyId", required = true) String companyId,
			@RequestParam(name = "masterConfigId", required = false) String masterConfigId) {
		if (StringUtils.isBlank(masterConfigId)) {
			masterConfigId = "NIL";
		}
		try {
			loadConfiguration.loadConfiguration(companyId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companyConfigRepository.findByCompanyIdAndMasterConfigId(companyId, masterConfigId);
	}

	@RequestMapping(value = "/company/{companyId}/config/post", method = RequestMethod.POST)
	public String post(@PathVariable(name = "companyId", required = true) String companyId,
			@RequestParam(name = "masterConfigId", required = false) String masterConfigId,
			@ModelAttribute CompanyConfigDTO companyConfigDTO) {

		if (StringUtils.isBlank(companyConfigDTO.getId()) || "_empty".equalsIgnoreCase(companyConfigDTO.getId())) {
			companyConfigDTO.setId(null);
		}
		companyConfigRepository.save(companyConfigDTO);
		try {
			ConfigurationUtility.refresh(companyId);
			loadConfiguration.loadConfiguration(companyId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
