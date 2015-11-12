package ro.asimandi.simsec.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restfb.types.Post;

import ro.asimandi.simsec.services.FacebookUtils;
 
@Controller
public class MainController{
	
	private String code;
	private String postPrivacy;
	private List<Post> allPosts;
	private List<Post> dangerousPostList;
	private List<Post> workThreatList;
	
 
	@RequestMapping(value={"/", "/login"})
	public String login(Model model) {
		model.addAttribute("screenStatus", "login");
		return "main";
	}
	
	@RequestMapping("/loginSolver")	
	public String login(@RequestParam String code) throws IOException {
		this.code = code;
		allPosts =  FacebookUtils.readPosts(code);
		dangerousPostList = FacebookUtils.getDangerousPosts(allPosts);
		workThreatList = FacebookUtils.getWorkThreatList(allPosts);		
		postPrivacy = FacebookUtils.determinePrivacySettingForPosts(allPosts);
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
			model.addAttribute("dangerousPostList", dangerousPostList);
			model.addAttribute("workThreatList", workThreatList);
			model.addAttribute("postPrivacy", postPrivacy);
			model.addAttribute("screenStatus", "logged");
			return "main";	
		}
	}
	
	@RequestMapping("/test")
	public String test(){
		return "test";
	}
	
}