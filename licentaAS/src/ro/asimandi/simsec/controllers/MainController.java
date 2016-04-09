package ro.asimandi.simsec.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.restfb.types.Post;

import ro.asimandi.simsec.DAO.PostDAO;
import ro.asimandi.simsec.DAO.UserDAO;
import ro.asimandi.simsec.models.User;
import ro.asimandi.simsec.utils.FacebookUtils;
import ro.asimandi.simsec.utils.Pair;
import ro.asimandi.simsec.utils.WordUtils;

@Controller
@SessionAttributes({ "code", "usedCode", "postPrivacy", "dangerousPostList", "workThreatList", "photoPostList",
		"postsWithLocation", "groupedPostsByMonth" })
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

			List<Post> allPosts = facebookUtils.readPosts();
			postDao.addPostsFb(allPosts);

			List<Post> dangerousPostList = FacebookUtils.getDangerousPosts(allPosts);
			List<Post> workThreatList = FacebookUtils.getWorkThreatList(allPosts);
			String postPrivacy = FacebookUtils.determinePrivacySettingForPosts(allPosts);
			List<Post> photoPostList = FacebookUtils.getPostsContainingPhotosWithBadPrivacy(allPosts);
			List<Pair<Post, Integer>> postsWithLocation = FacebookUtils.getClusteredLocations(allPosts, 20);
			List<ArrayList<Post>> groupedPostsByMonth = FacebookUtils.groupPostsByMonth(allPosts);
			
			model.addAttribute("dangerousPostList", dangerousPostList);
			model.addAttribute("workThreatList", workThreatList);
			model.addAttribute("postPrivacy", postPrivacy);
			model.addAttribute("photoPostList", photoPostList);
			model.addAttribute("postsWithLocation", postsWithLocation);
			//mby delete this
			model.addAttribute("screenStatus", "results");
			model.addAttribute("postsCount", allPosts.size());
			model.addAttribute("groupedPostsByMonth", groupedPostsByMonth);
			model.addAttribute("dangerousPostsCount", workThreatList.size());
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