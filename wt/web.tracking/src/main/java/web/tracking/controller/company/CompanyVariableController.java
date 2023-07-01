package web.tracking.controller.company;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.tracking.controller.request.CompanyVariableRequestObject;
import web.tracking.db.dao.CompanyVariableRepository;
import web.tracking.db.dto.CompanyVariableDTO;

@Controller
public class CompanyVariableController {

	@Autowired
	CompanyVariableRepository companyVariableRepository;

	@RequestMapping(value = "/company/{companyId}/get/variables", method = RequestMethod.GET)
	@ResponseBody
	public List<CompanyVariableRequestObject> get(@PathVariable("companyId") String companyId) {
		List<CompanyVariableDTO> companyVariables = companyVariableRepository.findByCompanyId(companyId);
		List<CompanyVariableRequestObject> companyVariableRequestObjects = new ArrayList<CompanyVariableRequestObject>();
		if (companyVariables != null && !companyVariables.isEmpty()) {
			for (CompanyVariableDTO companyVariable : companyVariables) {
				companyVariableRequestObjects.add(transform(companyVariable));
			}
		}
		return companyVariableRequestObjects;
	}

	@RequestMapping(value = "/company/{companyId}/get/variables-select", method = RequestMethod.GET)
	@ResponseBody
	public String getSelect(@PathVariable("companyId") String companyId) {
		StringBuilder result = new StringBuilder();
		List<CompanyVariableDTO> companyVariables = companyVariableRepository.findByCompanyId(companyId);
		if (companyVariables != null && !companyVariables.isEmpty()) {
			result.append("<select name='variablesIds'>");

			for (CompanyVariableDTO companyVariable : companyVariables) {
				result.append(
						"<option value='" + companyVariable.getId() + "'>" + companyVariable.getName() + "</option>");
			}
			result.append("</select>");
		}
		return result.toString();
	}

	private CompanyVariableRequestObject transform(CompanyVariableDTO companyVariable) {
		CompanyVariableRequestObject variableRequestObject = null;
		if (companyVariable != null) {
			variableRequestObject = new CompanyVariableRequestObject();
			variableRequestObject.setCompId(companyVariable.getCompanyId());
			variableRequestObject.setDefaultValue(companyVariable.getDefaultValue());
			variableRequestObject.setDescription(companyVariable.getDescription());
			variableRequestObject.setId(companyVariable.getId());
			variableRequestObject.setIncludeInReport(companyVariable.getIncludeInReport());
			variableRequestObject.setName(companyVariable.getName());
			variableRequestObject.setRequestParameter(companyVariable.getRequestParameter());
			variableRequestObject.setUniqueId(companyVariable.getUniqueId());
			variableRequestObject.setType(companyVariable.getType());
		}
		return variableRequestObject;
	}

	@RequestMapping(value = "/company/{companyId}/edit/variables", method = RequestMethod.POST)
	@ResponseBody
	public String post(@PathVariable("companyId") String companyId,
			@ModelAttribute CompanyVariableRequestObject companyVariableRequestObject) {

		if (companyVariableRequestObject != null) {
			companyVariableRequestObject.setCompId(companyId);
		}

		CompanyVariableDTO companyVariable = transform(companyVariableRequestObject);
		if ("add".equals(companyVariableRequestObject.getOper())) {
			companyVariable.setId(null);
			companyVariableRepository.insert(companyVariable);
		} else if ("edit".equalsIgnoreCase(companyVariableRequestObject.getOper())) {
			companyVariableRepository.save(companyVariable);
		} else {
			companyVariableRepository.delete(companyVariable);
		}
		return "refresh";
	}

	private CompanyVariableDTO transform(CompanyVariableRequestObject companyVariableRequestObject) {
		CompanyVariableDTO companyVariable = null;
		if (companyVariableRequestObject != null) {
			companyVariable = new CompanyVariableDTO();
			companyVariable.setCompanyId(companyVariableRequestObject.getCompId());
			companyVariable.setDefaultValue(companyVariableRequestObject.getDefaultValue());
			companyVariable.setDescription(companyVariableRequestObject.getDescription());
			companyVariable.setId(companyVariableRequestObject.getId());
			companyVariable.setIncludeInReport(companyVariableRequestObject.getIncludeInReport());
			companyVariable.setName(companyVariableRequestObject.getName());
			companyVariable.setRequestParameter(companyVariableRequestObject.getRequestParameter());
			companyVariable.setUniqueId(companyVariableRequestObject.getUniqueId());
			companyVariable.setType(companyVariableRequestObject.getType());
		}
		return companyVariable;
	}

}
