package ro.asimandi.simsec.models.results;

import java.util.List;

import org.springframework.data.annotation.Id;

import ro.asimandi.simsec.models.Post;

public class WorkThreat {
	@Id
	String user_id;
	List<Post> list;
	
	public List<Post> getList() {
		return list;
	}
	public void setList(List<Post> list) {
		this.list = list;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
