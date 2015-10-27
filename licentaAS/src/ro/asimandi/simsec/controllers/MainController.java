package ro.asimandi.simsec.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ro.asimandi.simsec.services.FacebookService;
 
@Controller
public class MainController{
	
	public String code;
 
	@RequestMapping(value={"/", "/login"})
	public String login(Model model) {
		model.addAttribute("screenStatus", "login");
		return "main";
	}
	
	@RequestMapping("/loginSolver")	
	public String login(@RequestParam String code) throws IOException {
		this.code = code;
		FacebookService.readPosts(code);
		return "redirect:/logged";
	}
	
	//TODO animate that thing only one time, because it would be annoying on refresh everytime
	@RequestMapping("/logged")
	public String scan(Model model) {
		if(code == null){
			System.out.println("code is null");
			return "redirect:/login";
		} else {
			System.out.println(code);
			model.addAttribute("screenStatus", "logged");
			return "main";	
		}
	}
	
}