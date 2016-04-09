package ro.asimandi.simsec.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import ro.asimandi.simsec.models.Post;

@Repository
public class PostDAOImpl implements PostDAO {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public void addPost(Post post) {
		if(!mongoTemplate.collectionExists(Post.class)){
			mongoTemplate.createCollection(Post.class);
		}
		mongoTemplate.insert(post, "post");
	}
	
	@Override
	public void addPost(com.restfb.types.Post post) {
		Post modelPost = new Post();
		modelPost.setId(post.getId());
		if(post.getPlace() != null){
			modelPost.setLatitude(post.getPlace().getLocation().getLatitude().toString());
			modelPost.setLongitude(post.getPlace().getLocation().getLongitude().toString());	
		}
		if(post.getActions().size() > 0){
			modelPost.setLink(post.getActions().get(0).getLink());
		}
		modelPost.setMessage(post.getMessage());
		modelPost.setObject_id(post.getObjectId());
		if(post.getPrivacy() != null){
			modelPost.setPrivacy(post.getPrivacy().getValue());
		}
		modelPost.setStory(post.getStory());
		this.addPost(modelPost);
	}
	
	@Override
	public void addPosts(List<Post> posts) {
		mongoTemplate.insert(posts, "post");
	}
	
	@Override
	public void addPostsFb(List<com.restfb.types.Post> posts) {
		List<Post> modelPosts = new ArrayList<Post>();
		for (com.restfb.types.Post post : posts) {
			Post modelPost = new Post();
			
			modelPost.setId(post.getId());
			if(post.getPlace() != null){
				modelPost.setLatitude(post.getPlace().getLocation().getLatitude().toString());
				modelPost.setLongitude(post.getPlace().getLocation().getLongitude().toString());	
			}
			if(post.getActions().size() > 0){
				modelPost.setLink(post.getActions().get(0).getLink());
			}
			modelPost.setMessage(post.getMessage());
			modelPost.setObject_id(post.getObjectId());
			if(post.getPrivacy() != null){
				modelPost.setPrivacy(post.getPrivacy().getValue());
			}
			modelPost.setStory(post.getStory());
			modelPosts.add(modelPost);
		}
		for (int i = 0; i < modelPosts.size(); i += 1) {
			this.addPosts(modelPosts.subList(i, Math.min(i + 1, modelPosts.size())));
		}
	}
	

	@Override
	public List<Post> listPost() {
		return mongoTemplate.findAll(Post.class, "post");
	}




}
