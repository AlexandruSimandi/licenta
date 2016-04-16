package ro.asimandi.simsec.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.restfb.experimental.api.Facebook;

import ro.asimandi.simsec.DAO.PostDAO;
import ro.asimandi.simsec.DAO.UserDAO;
import ro.asimandi.simsec.models.Post;
import ro.asimandi.simsec.models.User;
import ro.asimandi.simsec.utils.FacebookUtils;
import ro.asimandi.simsec.utils.Pair;

@Controller
@SessionAttributes({"user", "code", "usedCode", "postPrivacy", "dangerousPostList", "workThreatList", "photoPostList",
		"postsWithLocation", "groupedPostsByMonth", "groupedPostsWithLocationByMonth", "groupedPostsWithPhotoByMonth", "privacyCount"})
public class MainController {

	@Autowired
	private UserDAO userDao;
	@Autowired
	private PostDAO postDao;

	@RequestMapping(value = { "/", "/login" })
	public String login(Model model) throws IOException {
		model.addAttribute("screenStatus", "login");
		return "login";
	}

	@RequestMapping("/loginSolver")
	public String login(@RequestParam String code, Model model) throws IOException {
		model.addAttribute("code", code);
		model.addAttribute("screenStatus", "loading");
		return "loading";
	}

	@RequestMapping("/results")
	public String scan(HttpSession session, Model model) throws IOException {
		if (session.getAttribute("code") == null) {
			System.out.println("code is null");
			return "redirect:/login";
		} else if (session.getAttribute("usedCode") == null) {
			model.addAttribute("usedCode", true);
			FacebookUtils facebookUtils = new FacebookUtils();
			facebookUtils.init(session.getAttribute("code").toString());
			
			User user = facebookUtils.getUser();
			userDao.addUser(user);

			List<com.restfb.types.Post> allPosts = facebookUtils.readPosts();
			postDao.addPostsFb(allPosts, user);

			List<Post> databasePosts = postDao.listPost(user);
			List<Post> workThreatList = FacebookUtils.getWorkThreatList(databasePosts);
			String postPrivacy = FacebookUtils.determinePrivacySettingForPosts(databasePosts);
			List<Post> photoPostList = FacebookUtils.getPostsContainingPhotosWithBadPrivacy(databasePosts);
			List<Pair<Post, Integer>> postsWithLocation = FacebookUtils.getClusteredLocations(databasePosts, 20);
			List<ArrayList<Post>> groupedPostsByMonth = FacebookUtils.groupPostsByMonth(databasePosts);
			List<ArrayList<Post>> groupedPostsWithLocationByMonth = FacebookUtils.groupPostsWithLocationByMonth(databasePosts);
			List<ArrayList<Post>> groupedPostsWithPhotoByMonth = FacebookUtils.groupPostsWithPhotoByMonth(databasePosts);
			List<Pair<String, Integer>> privacyCount = FacebookUtils.countPrivacy(databasePosts);
			
			model.addAttribute("workThreatList", workThreatList);
			model.addAttribute("postPrivacy", postPrivacy);
			model.addAttribute("photoPostList", photoPostList);
			model.addAttribute("postsWithLocation", postsWithLocation);
			model.addAttribute("screenStatus", "results");
			model.addAttribute("postsCount", allPosts.size());
			model.addAttribute("groupedPostsByMonth", groupedPostsByMonth);
			model.addAttribute("groupedPostsWithLocationByMonth", groupedPostsWithLocationByMonth);
			model.addAttribute("groupedPostsWithPhotoByMonth", groupedPostsWithPhotoByMonth);
			model.addAttribute("dangerousPostsCount", workThreatList.size());
			model.addAttribute("user", user);
			model.addAttribute("privacyCount", privacyCount);
			
			if (workThreatList.size() > 0) {
				model.addAttribute("hasWorkThreats", true);
			}
		}

		return "results";

	}

	@RequestMapping("/test")
	public String test() {
		return "view";
	}

}