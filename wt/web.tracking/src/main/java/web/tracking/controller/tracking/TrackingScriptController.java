package web.tracking.controller.tracking;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import web.tracking.db.dao.CompanyConfigRepository;
import web.tracking.db.dto.CompanyConfigDTO;

@Controller
public class TrackingScriptController {
	@Autowired
	private CompanyConfigRepository configRepo;
	private static Map<String, String> predefinedMap = new HashMap<>();

	static {
		predefinedMap.put("", "POST");

	}

	@RequestMapping(value = "/script/{token}/{jsName}", method = RequestMethod.GET,produces = "text/javascript")
	@ResponseBody
	public String js(@PathVariable(name = "token") String token, @PathVariable(name = "jsName") String jsName,
			HttpServletRequest request, HttpServletResponse response) {
		StringBuilder sb = new StringBuilder();
		if (validateToken(request, token)) {
			try {
				setCookie(response);

				System.out.println("jsName:  " + jsName);
				File file = null;
				if(jsName.toLowerCase().endsWith(".js")) {
					file = new File(request.getServletContext().getRealPath("/script/") + jsName);
				}
				else {
					file = new File(request.getServletContext().getRealPath("/script/") + jsName + ".js");
				}
				
				Scanner scanner = new Scanner(file);
				String compId = new String(Base64.getUrlDecoder().decode(token.getBytes()));

				List<CompanyConfigDTO> companyConfigs = configRepo.findByCompanyId(compId);
				while (scanner.hasNextLine()) {
					String nextLine = scanner.nextLine();

					nextLine = replaceVariable(nextLine, compId, companyConfigs);

					sb.append(nextLine + System.getProperty("line.separator"));
					// sb.append(nextLine);
				}
				scanner.close();
				// response.setHeader("Cache-Control", "private, max-age=86400");
				// response.setHeader("Last-Modified", LocalDateTime.now().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private String replaceVariable(String nextLine, String compId, List<CompanyConfigDTO> companyConfigs) {

		if (nextLine.indexOf("$%") != -1 && nextLine.indexOf("%$") != -1) {
			String variable = nextLine.substring(nextLine.indexOf("$%") + 2, nextLine.indexOf("%$"));
			if ("companyId".equalsIgnoreCase(variable)) {
				nextLine = replaceVariableValue(nextLine, variable,
						new String(Base64.getUrlEncoder().encode(compId.getBytes())));
			} else if ("jsonpCallback".equalsIgnoreCase(variable)) {
				List<CompanyConfigDTO> matchedVariables = companyConfigs.stream()
						.filter(dto -> dto.getName().equalsIgnoreCase("dataType")).collect(Collectors.toList());
				if (matchedVariables != null && !matchedVariables.isEmpty()) {
					nextLine = replaceVariableValue(nextLine, variable, "jsonpCallback:'logResults',");
				} else {
					nextLine = replaceVariableValue(nextLine, variable, "");
				}
			} else {
				// jsonpCallback: "logResults"
				List<CompanyConfigDTO> matchedVariables = companyConfigs.stream()
						.filter(dto -> dto.getName().equalsIgnoreCase(variable)).collect(Collectors.toList());

				if (matchedVariables != null && !matchedVariables.isEmpty()) {
					nextLine = replaceVariableValue(nextLine, variable, matchedVariables.get(0).getValue());
				}
			}
		}
		return nextLine;
	}

	private String replaceVariableValue(String nextLine, String variable, String value) {

		String before = nextLine.substring(0, nextLine.indexOf("$%"));
		String after = nextLine.substring(nextLine.indexOf("%$") + 2, nextLine.length());
		return before + value + after;
	}

	private void setCookie(HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private boolean validateToken(HttpServletRequest request, String token) {
		// TODO Auto-generated method stub
		return !StringUtils.isEmpty(token);
	}
}
