package web.tracking.controller.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class JSPController {

	@RequestMapping(value = "/admin/{jspName}", method = RequestMethod.GET)
	public String admin(@PathVariable(name = "jspName") String jspName) {
		System.out.println("---------------------------------------");
		System.out.println("---------------------------------------" + jspName);
		return "admin-ui/" + jspName;
	}

	@RequestMapping(value = "/sa/{jspName}", method = RequestMethod.GET)
	public String sa(@PathVariable(name = "jspName") String jspName) {
		System.out.println("---------------------------------------");
		System.out.println("---------------------------------------" + jspName);
		return "sa-ui/" + jspName;
	}

	@RequestMapping(value = "/user/{jspName}", method = RequestMethod.GET)
	public String user(@PathVariable(name = "jspName") String jspName) {
		System.out.println("---------------------------------------");
		System.out.println("---------------------------------------" + jspName);
		return "user-ui/" + jspName;
	}

	@RequestMapping(value = "/anonymous/{jspName}", method = RequestMethod.GET)
	public String anonymous(@PathVariable(name = "jspName") String jspName) {
		return "anonymous-ui/" + jspName;
	}
}
