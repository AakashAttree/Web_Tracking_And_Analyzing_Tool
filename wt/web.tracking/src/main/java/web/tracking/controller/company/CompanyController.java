package web.tracking.controller.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.tracking.configuration.LoadConfigurationInDB;
import web.tracking.controller.request.CompanyPojo;
import web.tracking.db.dao.CompanyRepository;
import web.tracking.db.dto.CompanyDTO;

@Controller
public class CompanyController {
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	LoadConfigurationInDB configurationInDB;

	@RequestMapping(value = "/savecompany", method = RequestMethod.POST)
	@ResponseBody
	public String companyControl(@ModelAttribute CompanyPojo companyPojo) {

		CompanyDTO companyDTO = transform(companyPojo);
		if (companyPojo.isAddRequest() || companyPojo.isUpdateRequest()) {
			CompanyDTO c = companyRepository.save(companyDTO);
			if (c.getId() != null) {
				String companyId = c.getId();
				try {
					configurationInDB.loadConfiguration(companyId);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else {
			companyRepository.delete(companyDTO);
		}
		return "refresh";
	}

	private CompanyDTO transform(CompanyPojo companyPojo) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(companyPojo.getCompanyId());
		companyDTO.setName(companyPojo.getName());
		companyDTO.setDomainName(companyPojo.getDomainName());
		return companyDTO;
	}

	@RequestMapping(value = "/getcompany", method = RequestMethod.GET)
	@ResponseBody
	public List<CompanyPojo> getCompany() {
		List<CompanyDTO> companyDTOs = companyRepository.findAll();
		List<CompanyPojo> companyPojos = new ArrayList<CompanyPojo>();
		if (!companyDTOs.isEmpty()) {
			for (CompanyDTO companyDTO : companyDTOs) {
				CompanyPojo companyPojo = transform(companyDTO);
				companyPojos.add(companyPojo);
			}
		}
		return companyPojos;
	}

	@RequestMapping(value = "/getcompanies", method = RequestMethod.GET)
	@ResponseBody
	public String getCompanies() {
		List<CompanyDTO> companyDTOs = companyRepository.findAll();
		StringBuilder companyPojos = new StringBuilder(
				"<select name=\"compId\" id=\"compId\"  class=\"form-control\" >");
		if (companyDTOs != null && !companyDTOs.isEmpty()) {
			for (CompanyDTO companyDTO : companyDTOs) {
				companyPojos
						.append("<option value=\"" + companyDTO.getId() + "\">" + companyDTO.getName() + "</option>");
			}
		}
		companyPojos.append("</select>");
		return companyPojos.toString();
	}

	private CompanyPojo transform(CompanyDTO companyDTO) {
		CompanyPojo companyPojo = new CompanyPojo();
		companyPojo.setCompanyId(companyDTO.getId());
		companyPojo.setName(companyDTO.getName());
		companyPojo.setDomainName(companyDTO.getDomainName());
		return companyPojo;
	}
}
