package ro.asimandi.simsec.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import ro.asimandi.simsec.models.Post;
import ro.asimandi.simsec.models.User;
import ro.asimandi.simsec.models.results.WorkThreat;

@Repository
public class ResultDAOImpl implements ResultDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void addWorkThreat(List<Post> list, User user) {
		WorkThreat wt = new WorkThreat();
		wt.setList(list);
		wt.setUser_id(user.getId());
		mongoTemplate.insert(wt, "workThreat");
	}

	public List<Post> getWorkThreat(User user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(user.getId()));
		WorkThreat wt = mongoTemplate.find(query, WorkThreat.class).get(0); 
		return wt.getList();
	}

	@Override
	public void removeWorkThreat(User user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(user.getId()));
		mongoTemplate.remove(query, WorkThreat.class);
	}

}
