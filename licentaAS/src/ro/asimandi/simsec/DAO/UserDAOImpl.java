package ro.asimandi.simsec.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ro.asimandi.simsec.models.User;

@Repository
public class UserDAOImpl implements UserDAO{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void addUser(User user) {
		mongoTemplate.insert(user, "user");
	}

	@Override
	public List<User> listUser() {
		return mongoTemplate.findAll(User.class, "user");
	}

	@Override
	public void deleteUser(User user) {
		mongoTemplate.remove(user, "user");
	}

	@Override
	public void updateUser(User user) {
		mongoTemplate.insert(user, "user");
	}

	@Override
	public User getUserById(String user_id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("user_id").is(user_id));
		return mongoTemplate.findById(user_id, User.class);
	}

}
