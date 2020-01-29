package boot.dao;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import boot.model.UserDetails;

@Repository
public class UserDaoImpl implements UserDao
{

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Object registerUser(UserDetails userDetails) 
	{
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(UserDetails.class);
		cr.add(Restrictions.eq("loginName", userDetails.getLoginName()));
		if(cr.list().isEmpty())
		{
			cr = session.createCriteria(UserDetails.class);
			cr.add(Restrictions.eq("email", userDetails.getEmail()));
			if(cr.list().isEmpty())
			{
				session.save(userDetails);
				return new Gson().toJson(userDetails);
			}
			else
				return new Gson().toJson("Email ID already exist");
		}
		else
			return new Gson().toJson("Login ID already exist");
	}

	public Map<String, Object> loginUser(UserDetails userDetails) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(UserDetails.class);
		cr.add(Restrictions.eq("loginName", userDetails.getLoginName()));
		cr.add(Restrictions.eq("password", userDetails.getPassword()));
		if(cr.list().isEmpty())
		{
			map.put("status", "Login ID does not exist");
		}
		else if (userDetails.getLoginName().equalsIgnoreCase("admin"))
		{
			cr = session.createCriteria(UserDetails.class);
			cr.add(Restrictions.ne("id", -1));
			map.put("userdetails", cr.list());
		}
		else
		{
			map.put("userdetails", cr.list().get(0));
			map.put("status", "Login Successfull!!!");
		}
		
		return map;

	}


}
