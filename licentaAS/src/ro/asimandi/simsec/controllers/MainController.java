package ro.asimandi.simsec.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restfb.types.Post;

import ro.asimandi.simsec.DAO.PostDAO;
import ro.asimandi.simsec.DAO.UserDAO;
import ro.asimandi.simsec.models.User;
import ro.asimandi.simsec.utils.FacebookUtils;
import ro.asimandi.simsec.utils.Pair;
import ro.asimandi.simsec.utils.WordUtils;

@Controller
public class MainController {
	
	@Autowired
	private UserDAO userDao;
	@Autowired
	private PostDAO postDao;

	private String code;
	private boolean usedCode = false;
	private String postPrivacy;
	private FacebookUtils facebookUtils;
	private List<Post> allPosts;
	private List<Post> dangerousPostList;
	private List<Post> workThreatList;
	private List<Post> photoPostList;
	private List<Pair<Post, Integer>> postsWithLocation;
	private List<ArrayList<Post>> groupedPostsByMonth;

	@RequestMapping(value = { "/", "/login" })
	public String login(Model model) throws IOException {
		model.addAttribute("screenStatus", "login");
		return "login";
	}

	@RequestMapping("/loginSolver")
	public String login(@RequestParam String code, Model model) throws IOException {
		this.code = code;
		model.addAttribute("screenStatus", "loading");
		return "loading";
	}

	@RequestMapping("/loading")
	public String loading(Model model) {
		return "main";
	}

	// TODO animate that thing only one time, because it would be annoying on
	// refresh everytime
	@RequestMapping("/results")
	public String scan(Model model) throws IOException {
		if (code == null) {
			System.out.println("code is null");
			return "redirect:/login";
		} else if (!usedCode) {
			usedCode = true;
			facebookUtils = new FacebookUtils();
			facebookUtils.init(code);

			allPosts = facebookUtils.readPosts();
			postDao.addPostsFb(allPosts);
			
			dangerousPostList = FacebookUtils.getDangerousPosts(allPosts);
			workThreatList = FacebookUtils.getWorkThreatList(allPosts);
			postPrivacy = FacebookUtils.determinePrivacySettingForPosts(allPosts);
			photoPostList = FacebookUtils.getPostsContainingPhotosWithBadPrivacy(allPosts);
			postsWithLocation = FacebookUtils.getClusteredLocations(allPosts, 20);
			groupedPostsByMonth = FacebookUtils.groupPostsByMonth(allPosts);
//			facebookUtils.testSomething();
//			System.out.println(code);
		}
//		facebookUtils.testSomething();
		if (workThreatList.size() > 0) {
			model.addAttribute("hasWorkThreats", true);
		}
		
		model.addAttribute("dangerousPostList", dangerousPostList);
		model.addAttribute("workThreatList", workThreatList);
		model.addAttribute("postPrivacy", postPrivacy);
		model.addAttribute("photoPostList", photoPostList);
		model.addAttribute("postsWithLocation", postsWithLocation);
		model.addAttribute("screenStatus", "results");
		model.addAttribute("postsCount", allPosts.size());
		model.addAttribute("groupedPostsByMonth", groupedPostsByMonth);
		model.addAttribute("dangerousPostsCount", workThreatList.size());
		return "results";

	}

	@RequestMapping("/test")
	public String test() {
		return "view";
	}

}