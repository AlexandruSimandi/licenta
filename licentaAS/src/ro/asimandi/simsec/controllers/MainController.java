package ro.asimandi.simsec.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.restfb.experimental.api.Facebook;

import ro.asimandi.simsec.DAO.PostDAO;
import ro.asimandi.simsec.DAO.ResultDAO;
import ro.asimandi.simsec.DAO.UserDAO;
import ro.asimandi.simsec.models.Post;
import ro.asimandi.simsec.models.User;
import ro.asimandi.simsec.models.results.WorkThreat;
import ro.asimandi.simsec.utils.FacebookUtils;
import ro.asimandi.simsec.utils.Pair;

@Controller
@SessionAttributes({"user", "code", "usedCode", "facebookUtils", "postPrivacy", "dangerousPostList", "workThreatList", "photoPostList",
		"postsWithLocation", "groupedPostsByMonth", "groupedPostsWithLocationByMonth", "groupedPostsWithPhotoByMonth", "privacyCount", "newAnalysis", "groupedPostsByHoliday"})
public class MainController {

	@Autowired
	private UserDAO userDao;
	@Autowired
	private PostDAO postDao;
	@Autowired
	private ResultDAO resultDao;

	//TODO if logged in redirect to home
	@RequestMapping(value = { "/", "/login" })
	public String login(HttpSession session, Model model) throws IOException {
		if(session.getAttribute("user") != null){
			return "redirect:home";
		}
		model.addAttribute("screenStatus", "login");
		return "login";
	}
	
	@RequestMapping("/home")
	public String home(HttpSession session, Model model){
		if(session.getAttribute("user") == null){
			return "redirect:/login";
		}
		model.addAttribute("screenStatus", "home");
		return "home";
	}

	@RequestMapping("/loginSolver")
	public RedirectView login(@RequestParam String code, Model model) throws IOException {
		model.addAttribute("code", code);
		model.addAttribute("screenStatus", "loading");
		
		FacebookUtils facebookUtils = new FacebookUtils();
		facebookUtils.init(code);
		User user = facebookUtils.getUser();
		user.setAnalyzed(false);
		User dbUser = userDao.getUserById(user.getId());
		if(dbUser != null){
			user = dbUser;
		}
		userDao.addUser(user);
		model.addAttribute("user", user);
		model.addAttribute("facebookUtils", facebookUtils);
	    RedirectView redirect = new RedirectView("/home");
	    redirect.setExposeModelAttributes(false);
	    return redirect;
	}
	
	@RequestMapping("/analyse")
	public String analyze(Model model){
		model.addAttribute("newAnalysis", true);
		return "redirect:/loading";
	}
	
	@RequestMapping("/loading")
	public String loading(Model model){
		model.addAttribute("screenStatus", "results");
		return "loading";
	}


	@RequestMapping("/results")
	public String analyze(HttpSession session, Model model) throws IOException {
		if(session.getAttribute("facebookUtils") == null){
			return "redirect:/login";
		}
		User user = (User) session.getAttribute("user");
		FacebookUtils facebookUtils = (FacebookUtils) session.getAttribute("facebookUtils");
		List<Post> databasePosts;
		if(user.getAnalyzed() == null || user.getAnalyzed() == false || (session != null && session.getAttribute("newAnalysis") != null && (Boolean)session.getAttribute("newAnalysis") == true)){
			model.addAttribute("newAnalysis", false);
			List<com.restfb.types.Post> allPosts = facebookUtils.readPosts();
			postDao.removePostsByUser(user);
			postDao.addPostsFb(allPosts, user);
			user.setAnalyzed(true);
			user.setLast_analysis(new Date(System.currentTimeMillis()));
			userDao.deleteUser(user);
			userDao.addUser(user);
			databasePosts = postDao.listPost(user);
			resultDao.removeWorkThreat(user);
			resultDao.addWorkThreat(FacebookUtils.getWorkThreatList(databasePosts), user);
		}
		
		if(!model.containsAttribute("privacyCount")){
			databasePosts = postDao.listPost(user);
			List<Post> workThreatList = resultDao.getWorkThreat(user);
			String postPrivacy = FacebookUtils.determinePrivacySettingForPosts(databasePosts);
			List<Post> photoPostList = FacebookUtils.getPostsContainingPhotosWithBadPrivacy(databasePosts);
			List<Pair<List<Post>, Integer>> postsWithLocation = FacebookUtils.getClusteredLocations(databasePosts, 1);
			List<ArrayList<Post>> groupedPostsByMonth = FacebookUtils.groupPostsByMonth(databasePosts);
			List<ArrayList<Post>> groupedPostsWithLocationByMonth = FacebookUtils.groupPostsWithLocationByMonth(databasePosts);
			List<ArrayList<Post>> groupedPostsWithPhotoByMonth = FacebookUtils.groupPostsWithPhotoByMonth(databasePosts);
			List<Pair<String, Integer>> privacyCount = FacebookUtils.countPrivacy(databasePosts);
			List<Pair<List<Post>, Integer>> groupedPostsByHoliday = FacebookUtils.getClusteredLocationsByHoliday(databasePosts);
			
			model.addAttribute("workThreatList", workThreatList);
			model.addAttribute("postPrivacy", postPrivacy);
			model.addAttribute("photoPostList", photoPostList);
			model.addAttribute("postsWithLocation", postsWithLocation);
			model.addAttribute("screenStatus", "results");
			model.addAttribute("postsCount", databasePosts.size());
			model.addAttribute("groupedPostsByMonth", groupedPostsByMonth);
			model.addAttribute("groupedPostsWithLocationByMonth", groupedPostsWithLocationByMonth);
			model.addAttribute("groupedPostsWithPhotoByMonth", groupedPostsWithPhotoByMonth);
			model.addAttribute("dangerousPostsCount", workThreatList.size());
			model.addAttribute("user", user);
			model.addAttribute("privacyCount", privacyCount);
			model.addAttribute("groupedPostsByHoliday", groupedPostsByHoliday);
		}
		
		model.addAttribute("screenStatus", "results");
		return "results";

	}

	@RequestMapping("/test")
	public String test() {
		return "view";
	}

}