package ro.asimandi.simsec.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.WebRequestor;
import com.restfb.types.Post;

public class FacebookUtils {

	private enum Privacy {
		EVERYONE(1), ALL_FRIENDS(2), FRIENDS_OF_FRIENDS(3), SELF(4), CUSTOM(5);

		private final int value;

		private Privacy(int v) {
			value = v;
		}

		public int value() {
			return value;
		}
	}

	private static AccessToken getFacebookUserToken(String code) throws IOException {
		String appId = "475736505939108";
		String secretKey = "eb720d5200bf4fed5ae7c205f1d69402";

		WebRequestor wr = new DefaultWebRequestor();
		WebRequestor.Response accessTokenResponse = wr
				.executeGet("https://graph.facebook.com/oauth/access_token?client_id=" + appId
						+ "&redirect_uri=http://localhost/loginSolver" + "&client_secret=" + secretKey + "&code="
						+ code);

		return DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody());
	}

	public static List<Post> readPosts(String code) throws IOException {
		AccessToken token = getFacebookUserToken(code);
		String accessToken = token.getAccessToken();
		// Date expires = token.getExpires();
		@SuppressWarnings("deprecation")
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		ArrayList<Post> allPosts = new ArrayList<Post>();

		Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class,
				Parameter.with("fields", "privacy,message,story,actions,created_time"), Parameter.with("limit", 999));

		while (myFeed.getData().size() > 0) {
			allPosts.addAll(myFeed.getData());
			myFeed = facebookClient.fetchConnectionPage(myFeed.getNextPageUrl(), Post.class);
		}

		return allPosts;

	}

	public static List<Post> getDangerousPosts(List<Post> allPosts) {

		List<Post> dangerousPostList = new ArrayList<Post>();

		for (Post post : allPosts) {
			if (isDangerousPost(post)) {
				dangerousPostList.add(post);
			}
		}

		return dangerousPostList;

	}
	
	public static List<Post> getWorkThreatList(List<Post> allPosts) {
		List<Post> workThreatList = new ArrayList<Post>();
		
		for (Post post : allPosts) {
			if (containsWorkThreats(post)) {
				workThreatList.add(post);
			}
		}
		return workThreatList;
				
	}
	
	private static boolean containsWorkThreats(Post post) {
		
		String[] workNouns = {"sef", "boss", "birou", "office", "sefu" , "seful"};
		String[] negativeKeywords = {"urasc", "hate", "sleep", "somn", "nebun", "innebunesc", "crazy"};
		
		String message = post.getMessage();
		if (message == null) {
			message = post.getStory();
			if (message == null) {
				return false;
			}
		}
		
		String messageLowered = message.toLowerCase();
		
		for (int i = 0; i < workNouns.length; i++) {
			if(messageLowered.contains(workNouns[i])){
				for (int j = 0; j < negativeKeywords.length; j++) {
					if(messageLowered.contains(negativeKeywords[j])){
						return true;
					}
				}
			}
		}
		
		return false;
	}

	private static boolean isDangerousPost(Post post) {

		String[] foulWords = {};
		
		String message = post.getMessage();
		if (message == null) {
			message = post.getStory();
			if (message == null) {
				return false;
			}
		}
		String messageLowered = message.toLowerCase();

		// if (post.getPrivacy().getValue().equals("SELF")) {
		for (int i = 0; i < foulWords.length; i++) {
			if(messageLowered.contains(foulWords[i])){
				return true;
			}
		}
		// }
		return false;
	}

	public static String determinePrivacySettingForPosts(List<Post> allPosts) {
		final int POST_NUMBER_CHECK_PRIVACY = 10;
		int postCount = allPosts.size();

		int loopCount = Math.min(POST_NUMBER_CHECK_PRIVACY, postCount);
		int privacyArrayCounter[] = new int[6];

		for (int i = 0; i < loopCount; i++) {
			String privacy = allPosts.get(i).getPrivacy().getValue();
			if (privacy != null && !privacy.equals("")) {
				Privacy currentPrivacy = Privacy.valueOf(privacy);
				privacyArrayCounter[currentPrivacy.value()]++;
			}

		}

		int maxPoz = -1;
		int maxCount = -1;
		for (int i = 1; i < privacyArrayCounter.length; i++) {
			if (maxCount < privacyArrayCounter[i]) {
				maxPoz = i;
				maxCount = privacyArrayCounter[i];
			}
		}

		Privacy[] privacyArray = Privacy.values();
		for (int i = 0; i < privacyArray.length; i++) {
			if (privacyArray[i].value() == maxPoz) {
				return privacyArray[i].name();
			}
		}

		return null;
	}

}
