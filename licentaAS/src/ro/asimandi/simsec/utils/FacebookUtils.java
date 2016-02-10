package ro.asimandi.simsec.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.WebRequestor;
import com.restfb.types.Album;
import com.restfb.types.Post;

import edu.stanford.nlp.util.CoreMap;

public class FacebookUtils {
	
	private FacebookClient facebookClient;
	
	@SuppressWarnings("deprecation")
	public void init(String code) throws IOException{
		AccessToken token =  FacebookUtils.getFacebookUserToken(code);
		String accessToken = token.getAccessToken();
		facebookClient = new DefaultFacebookClient(accessToken);
	}

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

	public List<Post> readPosts() throws IOException {
		ArrayList<Post> allPosts = new ArrayList<Post>();

		Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class,
				Parameter.with("fields", "privacy,message,story,actions,created_time,object_id"), Parameter.with("limit", 999));

		while (myFeed.getData().size() > 0) {
			allPosts.addAll(myFeed.getData());
			myFeed = facebookClient.fetchConnectionPage(myFeed.getNextPageUrl(), Post.class);
		}

		return allPosts;

	}
	
//	public List<Album> readAlbums() throws IOException {
//		ArrayList<Album> allAlbums = new ArrayList<Album>();
//
//		Connection<Album> albumsConnection = facebookClient.fetchConnection("me/albums", Album.class, Parameter.with("fields", "name,privacy,object_id"), Parameter.with("limit",50));
//		
//		while(albumsConnection.getData().size() > 0){
//			allAlbums.addAll(albumsConnection.getData());
//			albumsConnection = facebookClient.fetchConnectionPage(albumsConnection.getNextPageUrl(), Album.class);
//		}
//		//////////////
//		for(Album album: allAlbums){
//			System.out.println(album.getName() + "  " + album.getPrivacy());
//		}
//		////////////
//		return allAlbums;
//	}

	public static List<Post> getPostContainingPhotosWithBadPrivacy(List<Post> allPosts){
		List<Post> photosWithBadPrivacyList = new ArrayList<Post>();
		
		for (Post post : allPosts) {
			if(post.getObjectId() != null){
				if(post.getPrivacy().getValue().equals("EVERYONE")){
					photosWithBadPrivacyList.add(post);
				}
			}
		}
		
		for (Post post : photosWithBadPrivacyList) {
			System.out.println(post.getStory() + " " + post.getId());
		}
		
		
		return photosWithBadPrivacyList;
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
	
	public static List<Post> getWorkThreatList(List<Post> allPosts) throws IOException {
		List<Post> workThreatList = new ArrayList<Post>();
		
		for (Post post : allPosts) {
			if (containsWorkThreats(post)) {
				workThreatList.add(post);
			}
		}
		return workThreatList;
				
	}
	
	public static boolean containsWorkThreats(Post post) throws IOException {
		
//		String[] workNouns = {"boss", "office", "work", "employer", "chief", "job"};
		//String[] negativeKeywords = {"urasc", "hate", "sleep", "somn", "nebun", "innebunesc", "crazy"};
		
		String message = post.getMessage();
		if (message == null) {
			message = post.getStory();
			if (message == null) {
				return false;
			}
		}
		
		String messageLowered = message.toLowerCase();
		

		List<String> workRelatedList = WordUtils.getWorkRelatedWords();
		
		for (String workRelatedWord : workRelatedList) {
			if(messageLowered.contains(workRelatedWord)) {
				List<CoreMap> sentences = NlpUtils.getSentences(messageLowered);
				for (CoreMap sentence : sentences) {
					if(NlpUtils.getSentiment(sentence).equals("Negative")){
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
		
		String discoveredSetting;

		Privacy[] privacyArray = Privacy.values();
		for (int i = 0; i < privacyArray.length; i++) {
			if (privacyArray[i].value() == maxPoz) {
				discoveredSetting =  privacyArray[i].name();
				return beautifyPrivacyString(discoveredSetting);
			}
		}

		return null;
	}
	
	
	//TODO read custom settings for privacy
	private static String beautifyPrivacyString(String privacy){
		switch (privacy) {
		case "EVERYONE":
			return "everyone";
		case "ALL_FRIENDS":
			return "all your friends";
		case "FRIENDS_OF_FRIENDS":
			return "friends of your friends";
		case "SELF":
			return "only you";
		case "CUSTOM":
			return "custom";
		default:
			return null;
		}
	}

}
