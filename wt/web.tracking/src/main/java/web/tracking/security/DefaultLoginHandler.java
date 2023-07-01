package web.tracking.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultLoginHandler {

	/*
	 * @RequestMapping(value="/",method=RequestMethod.GET) public String welcome(){
	 * return "login"; }
	 */

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = { "/", "/default" }, method = RequestMethod.GET)
	public String defaultLogin(HttpServletRequest request) {
		String returnValue = "login";
		if (request.isUserInRole("ROLE_ADMIN")) {
			returnValue = "redirect:/admin/home";
		} else if (request.isUserInRole("ROLE_USER")) {
			returnValue = "redirect:/user/home";
		} else if (request.isUserInRole("ROLE_SA")) {
			returnValue = "redirect:/sa/home";
		}

		return returnValue;
	}

}
