package ro.asimandi.simsec.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.WebRequestor;
import com.restfb.types.Post;
import com.restfb.types.User;

public class FacebookService {
	
	private static AccessToken getFacebookUserToken(String code) throws IOException {
	    String appId = "475736505939108";
	    String secretKey = "eb720d5200bf4fed5ae7c205f1d69402";

	    WebRequestor wr = new DefaultWebRequestor();
	    WebRequestor.Response accessTokenResponse = wr.executeGet(
	            "https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=http://localhost/loginSolver" 
	            + "&client_secret=" + secretKey + "&code=" + code);

	    System.out.println(accessTokenResponse.getBody());
	    return DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody());
	}	

	public static void readPosts(String code) throws IOException {
		AccessToken token = getFacebookUserToken(code);
		String accessToken = token.getAccessToken();
		//Date expires = token.getExpires();
		@SuppressWarnings("deprecation")
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		ArrayList<Post> allPosts = new ArrayList<Post>();
		
		Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class, Parameter.with("limit", 999));
		while(myFeed.getData().size() > 0){
			allPosts.addAll(myFeed.getData());			
			myFeed = facebookClient.fetchConnectionPage(myFeed.getNextPageUrl(), Post.class);
		}
		System.out.println(allPosts.size());

	}
	
}
