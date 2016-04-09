package ro.asimandi.simsec.DAO;

import java.util.List;

import ro.asimandi.simsec.models.Post;


public interface PostDAO {
	public void addPost(Post post);
	public void addPost(com.restfb.types.Post post);
	public void addPosts(List<Post> posts);
	public void addPostsFb(List<com.restfb.types.Post> posts);
	public List<Post> listPost();
}
