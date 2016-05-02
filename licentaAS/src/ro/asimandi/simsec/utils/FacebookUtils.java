package ro.asimandi.simsec.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.WebRequestor;

import edu.stanford.nlp.util.CoreMap;
import ro.asimandi.simsec.models.Post;
import ro.asimandi.simsec.models.User;

public class FacebookUtils {

	private FacebookClient facebookClient;

	@SuppressWarnings("deprecation")
	public void init(String code) throws IOException {
		AccessToken token = FacebookUtils.getFacebookUserToken(code);
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
	
	public User getUser(){
		com.restfb.types.User user = facebookClient.fetchObject("me", com.restfb.types.User.class, Parameter.with("fields", "id,first_name,last_name"));
		User userModel = new User();
		userModel.setId(user.getId());
		userModel.setFirst_name(user.getFirstName());
		userModel.setLast_name(user.getLastName());
		return userModel;
	}

	public List<com.restfb.types.Post> readPosts() throws IOException {
		ArrayList<com.restfb.types.Post> allPosts = new ArrayList<com.restfb.types.Post>();

		Connection<com.restfb.types.Post> myFeed = facebookClient.fetchConnection("me/feed", com.restfb.types.Post.class,
				Parameter.with("fields", "privacy,message,story,actions,created_time,object_id,place"),
				Parameter.with("limit", 999));

		while (myFeed.getData().size() > 0) {
			allPosts.addAll(myFeed.getData());
			myFeed = facebookClient.fetchConnectionPage(myFeed.getNextPageUrl(), com.restfb.types.Post.class);
		}

		return allPosts;

	}

	public static List<Pair<List<Post>,Integer>> getClusteredLocations(List<Post> allPosts, double dist) {
		List<Post> postsWithLocation = new ArrayList<Post>();
		HashMap<Post, List<Post>> numberOfGroupedLocations = new HashMap<Post, List<Post>>();
		
		for (Post post : allPosts) {
			if (post.getLatitude() != null) {
				if (postsWithLocation.size() == 0) {
					List<Post> newList = new ArrayList<Post>();
					newList.add(post);
					postsWithLocation.add(post);
					numberOfGroupedLocations.put(post, newList);
					continue;
				}

				boolean existsFlag = false;

				for (Post postWithLocation : postsWithLocation) {
					if (getDistanceBetweenTwoLocations(post.getLatitude(), post.getLongitude(), postWithLocation.getLatitude(), post.getLongitude()) < dist) {
						existsFlag = true;
						List<Post> cluster = numberOfGroupedLocations.get(postWithLocation);
						cluster.add(post);
//						numberOfGroupedLocations.put(postWithLocation, numberOfGroupedLocations.remove(postWithLocation) + 1);
						break;
					}
				}

				if (!existsFlag) {
					List<Post> newList = new ArrayList<Post>();
					newList.add(post);
					postsWithLocation.add(post);
					numberOfGroupedLocations.put(post, newList);
				}

			}
		}
		
		ArrayList<Pair<List<Post>, Integer>> pairedList = new ArrayList<Pair<List<Post>, Integer>>();
		System.out.println("Distance min is " + dist);
		System.out.println(postsWithLocation.size());
		for (Post post : postsWithLocation) {
//			System.out.println(numberOfGroupedLocations.get(post) + " " + post.getId() + " " + post.getPlace().getLocationAsString());
			pairedList.add(new Pair<List<Post>, Integer>(numberOfGroupedLocations.get(post), numberOfGroupedLocations.get(post).size()));
		}
		
		return pairedList;
	}

	public static List<Pair<List<Post>,Integer>> getClusteredLocationsByHoliday(List<Post> allPosts){
		final int AREA_SIZE = 20000;
		List<Pair<List<Post>,Integer>> clusters = getClusteredLocations(allPosts, AREA_SIZE);
		int maxOne = Integer.MIN_VALUE;
		int maxPos = -1;
		for (int i = 0; i < clusters.size(); i++) {
			if(clusters.get(i).getSnd() > maxOne){
				maxOne = clusters.get(i).getSnd();
				maxPos = i;
			}
		}
		clusters.remove(maxPos);
		return clusters;
	}
	
	public static List<Post> getPostsContainingPhotosWithBadPrivacy(List<Post> allPosts) {
		List<Post> photosWithBadPrivacyList = new ArrayList<Post>();

		for (Post post : allPosts) {
			if (post.getObject_id() != null) {
				if (post.getPrivacy().equals("EVERYONE")) {
					photosWithBadPrivacyList.add(post);
				}
			}
		}

		return photosWithBadPrivacyList;
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
			if (messageLowered.contains(workRelatedWord)) {
				List<CoreMap> sentences = NlpUtils.getSentences(messageLowered);
				for (CoreMap sentence : sentences) {
					if (NlpUtils.getSentiment(sentence).equals("Negative")) {
						return true;
					}
				}
			}
		}

		return false;
	}

    private static int getMonthOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }
	
	public static List<ArrayList<Post>> groupPostsByMonth(List<Post> posts){
		ArrayList<Post> clonedPosts = new ArrayList<Post>(posts);
		Collections.reverse(clonedPosts);
		
		List<ArrayList<Post>> groupedPosts = new ArrayList<ArrayList<Post>>();
		
		int currentMonth = -1;
		for(Post post: clonedPosts){
			int postMonth = getMonthOfYear(post.getCreated_time());
			if(currentMonth != postMonth){
				groupedPosts.add(new ArrayList<Post>());
				currentMonth = postMonth;
			}
			groupedPosts.get(groupedPosts.size() - 1).add(post);
		}
		return groupedPosts;
	}
	
	public static List<ArrayList<Post>> groupPostsWithLocationByMonth(List<Post> posts){
		ArrayList<Post> clonedPosts = new ArrayList<Post>(posts);
		Collections.reverse(clonedPosts);
		
		List<ArrayList<Post>> groupedPosts = new ArrayList<ArrayList<Post>>();
		
		int currentMonth = -1;
		for(Post post: clonedPosts){
			if(post.getLongitude() != null){
				int postMonth = getMonthOfYear(post.getCreated_time());
				if(currentMonth != postMonth){
					groupedPosts.add(new ArrayList<Post>());
					currentMonth = postMonth;
				}
				groupedPosts.get(groupedPosts.size() - 1).add(post);
			}
		}
		return groupedPosts;
	}
	
	public static List<ArrayList<Post>> groupPostsWithPhotoByMonth(List<Post> posts){
		ArrayList<Post> clonedPosts = new ArrayList<Post>(posts);
		Collections.reverse(clonedPosts);
		
		List<ArrayList<Post>> groupedPosts = new ArrayList<ArrayList<Post>>();
		
		int currentMonth = -1;
		for(Post post: clonedPosts){
			if(post.getObject_id() != null){
				int postMonth = getMonthOfYear(post.getCreated_time());
				if(currentMonth != postMonth){
					groupedPosts.add(new ArrayList<Post>());
					currentMonth = postMonth;
				}
				groupedPosts.get(groupedPosts.size() - 1).add(post);
			}
		}
		return groupedPosts;
	}
	
	public static List<Pair<String, Integer>> countPrivacy(List<Post> allPosts){
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		for (Post post : allPosts) {
			if(hash.get(post.getPrivacy()) != null){
				hash.put(post.getPrivacy(), hash.remove(post.getPrivacy()) + 1);
			} else hash.put(post.getPrivacy(), 1);
		}
		
		List<Pair<String, Integer>> privacyList = new ArrayList<Pair<String, Integer>>();
		for (String privacy : hash.keySet()) {
			privacyList.add(new Pair<String, Integer>(privacy.equals("")?"OTHER":privacy,hash.get(privacy)));
			
		}
		
		return privacyList;
	}

	public static String determinePrivacySettingForPosts(List<Post> allPosts) {
		final int POST_NUMBER_CHECK_PRIVACY = 10;
		int postCount = allPosts.size();

		int loopCount = Math.min(POST_NUMBER_CHECK_PRIVACY, postCount);
		int privacyArrayCounter[] = new int[6];
		for (int i = 0; i < loopCount; i++) {
			String privacy = allPosts.get(i).getPrivacy();
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
				discoveredSetting = privacyArray[i].name();
				return beautifyPrivacyString(discoveredSetting);
			}
		}

		return null;
	}
	
	// http://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
	public static double distance(double lat1, double lng1, double lat2, double lng2) {
		double a = (lat1 - lat2) * distPerLat(lat1);
		double b = (lng1 - lng2) * distPerLng(lat1);
		return Math.sqrt(a * a + b * b);
	}

	private static double distPerLng(double lat) {
		return 0.0003121092 * Math.pow(lat, 4) + 0.0101182384 * Math.pow(lat, 3) - 17.2385140059 * lat * lat
				+ 5.5485277537 * lat + 111301.967182595;
	}

	private static double distPerLat(double lat) {
		return -0.000000487305676 * Math.pow(lat, 4) - 0.0033668574 * Math.pow(lat, 3) + 0.4601181791 * lat * lat
				- 1.4558127346 * lat + 110579.25662316;
	}

	private static double getDistanceBetweenTwoLocations(String aLatitude, String aLongitude, String bLatitude, String bLongitude) {
		return distance(Double.parseDouble(aLatitude), Double.parseDouble(aLongitude), Double.parseDouble(bLatitude), Double.parseDouble(bLongitude));
	}

	// TODO read custom settings for privacy
	public static String beautifyPrivacyString(String privacy) {
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
