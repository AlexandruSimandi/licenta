package ro.asimandi.simsec.DAO;

import java.util.List;

import ro.asimandi.simsec.models.Post;
import ro.asimandi.simsec.models.User;

public interface ResultDAO {
	public void addWorkThreat(List<Post> list, User user);
	public List<Post> getWorkThreat(User user);
	public void removeWorkThreat(User user);
}
