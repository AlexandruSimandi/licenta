package ro.asimandi.simsec.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ro.asimandi.simsec.models.Post;
import ro.asimandi.simsec.models.User;

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
	public void addPostsFb(List<com.restfb.types.Post> posts, User user) {
		HashMap<String, Boolean> hash = new HashMap<String, Boolean>();
		for (int i = 0; i < posts.size(); i++) {
			if(hash.containsKey(posts.get(i).getId())){
				posts.remove(i);
				i--;
			} else {
				hash.put(posts.get(i).getId(), true);
			}
		}
		
		List<Post> modelPosts = new ArrayList<Post>();
		for (com.restfb.types.Post post : posts) {
			Post modelPost = new Post();
			
			modelPost.setId(post.getId());
			if(post.getPlace() != null && post.getPlace().getLocation() != null && post.getPlace().getLocation().getLatitude() != null){
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
			modelPost.setCreated_time(post.getCreatedTime());
			modelPost.setUser_id(user.getId());
			modelPosts.add(modelPost);
		}
		this.addPosts(modelPosts);
	}
	

	@Override
	public List<Post> listPost(User user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("user_id").is(user.getId()));
		return mongoTemplate.find(query, Post.class);
	}

	@Override
	public void removePostsByUser(User user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("user_id").is(user.getId()));
		mongoTemplate.remove(query, Post.class);
	}
	
}
