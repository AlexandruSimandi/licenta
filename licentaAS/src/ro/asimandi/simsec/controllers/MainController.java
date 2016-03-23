package ro.asimandi.simsec.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restfb.types.Post;

import ro.asimandi.simsec.utils.FacebookUtils;
import ro.asimandi.simsec.utils.Pair;
import ro.asimandi.simsec.utils.WordUtils;
 
@Controller
public class MainController{
	
	private String code;
	private String postPrivacy;
	private FacebookUtils facebookUtils;
	private List<Post> allPosts;
	private List<Post> dangerousPostList;
	private List<Post> workThreatList;
	private List<Post> photoPostList;
	private List<Pair<Post, Integer>> postsWithLocation;
	
 
	@RequestMapping(value={"/", "/login"})
	public String login(Model model) throws IOException {
//		model.addAttribute("screenStatus", "login");
		return "login";
	}
	
	@RequestMapping("/loginSolver")	
	public String login(@RequestParam String code) throws IOException {
		this.code = code;
		return "redirect:/loading";
	}
	
	@RequestMapping("/loading")
	public String loading(Model model){
		model.addAttribute("screenStatus", "loading");
		return "main";
	}
	
	//TODO animate that thing only one time, because it would be annoying on refresh everytime
	@RequestMapping("/logged")
	public String scan(Model model) throws IOException {
		facebookUtils = new FacebookUtils();
		facebookUtils.init(code);
		
		allPosts =  facebookUtils.readPosts();
		dangerousPostList = FacebookUtils.getDangerousPosts(allPosts);
		workThreatList = FacebookUtils.getWorkThreatList(allPosts);		
		postPrivacy = FacebookUtils.determinePrivacySettingForPosts(allPosts);
		photoPostList = FacebookUtils.getPostsContainingPhotosWithBadPrivacy(allPosts);
		postsWithLocation =  FacebookUtils.getClusteredLocations(allPosts, 20);
		//TODO not forget about this
		//facebookUtils.readAlbums();
		if(code == null){
			System.out.println("code is null");
			return "redirect:/login";
		} else {
			if(workThreatList.size() > 0){
				model.addAttribute("hasWorkThreats", true);
			}
			System.out.println(code);
			model.addAttribute("dangerousPostList", dangerousPostList);
			model.addAttribute("workThreatList", workThreatList);
			model.addAttribute("postPrivacy", postPrivacy);
			model.addAttribute("screenStatus", "logged");
			model.addAttribute("photoPostList", photoPostList);
			model.addAttribute("postsWithLocation", postsWithLocation);
			return "main";	
		}
	}
	
	@RequestMapping("/test")
	public String test(){
		return "test";
	}
	
}