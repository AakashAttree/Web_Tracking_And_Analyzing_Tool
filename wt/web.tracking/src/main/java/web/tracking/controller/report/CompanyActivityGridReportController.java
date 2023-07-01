package web.tracking.controller.report;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.tracking.db.dao.CompanyActiviesRepository;
import web.tracking.db.dao.CountryRepository;
import web.tracking.db.dao.EndUserActiviesRepository;
import web.tracking.db.dto.CompanyActivies;
import web.tracking.db.dto.EndUserActivies;

public class CompanyActivityGridReportController {
	@Autowired
	CompanyActiviesRepository companyActiviesRepository;

	@Autowired
	EndUserActiviesRepository userActiviesRepository;

	@Autowired
	CountryRepository countryRepository;

	@RequestMapping(value = "/grid/company/{companyId}/report")
	@ResponseBody
	public List<CompanyActivies> getCompanyActivityReport(@PathVariable(name = "companyId") String companyId) {
		return companyActiviesRepository.findByCompanyId(companyId);
	}

	@RequestMapping(value = "/grid/company-user/{companyId}/report")
	@ResponseBody
	public Map<String, Integer> getCompanyEndUserActivityReport(@PathVariable(name = "companyId") String companyId) {
		List<EndUserActivies> userActivies = userActiviesRepository.findByCompanyId(companyId);
		if (userActivies != null) {
			for (EndUserActivies endUserActivies : userActivies) {

			}
		}
		return null;
	}

}
