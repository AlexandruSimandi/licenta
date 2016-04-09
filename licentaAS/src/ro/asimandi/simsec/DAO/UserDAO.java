package ro.asimandi.simsec.DAO;

import java.util.List;

import ro.asimandi.simsec.models.User;

public interface UserDAO {
	public void addUser(User user);
	public List<User> listUser();
	public void deleteUser(User user);
	public void updateUser(User user);
}
