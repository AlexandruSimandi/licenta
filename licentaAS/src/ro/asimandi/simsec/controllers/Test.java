package ro.asimandi.simsec.controllers;

import java.io.IOException;
import com.restfb.types.Post;

import ro.asimandi.simsec.utils.FacebookUtils;

public class Test {

	public static void main(String[] args) throws ClassCastException, ClassNotFoundException, IOException {

		Post post = new Post();
		post.setStory("Ewing Oil is back in business Bobby!");
			
		System.out.println(FacebookUtils.containsWorkThreats(post));
		
	}

}
