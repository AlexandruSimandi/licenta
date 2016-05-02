package ro.asimandi.simsec.DAO;

import java.util.List;

import ro.asimandi.simsec.models.Post;
import ro.asimandi.simsec.models.User;


public interface PostDAO {
	public void addPost(Post post);
	public void addPost(com.restfb.types.Post post);
	public void addPosts(List<Post> posts);
	public void addPostsFb(List<com.restfb.types.Post> posts, User user);
	public List<Post> listPost(User user);
	public void removePostsByUser(User user);
}
