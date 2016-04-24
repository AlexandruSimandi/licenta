package ro.asimandi.simsec.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LogoutController {

	@RequestMapping("/logout")
	public RedirectView logout(HttpSession session, Model model) {
		session.invalidate();
		if(model.containsAttribute("counter")){
			model.asMap().clear();
		}
		
		RedirectView redirect = new RedirectView("/login");
	    redirect.setExposeModelAttributes(false);
	    return redirect;
	}

}