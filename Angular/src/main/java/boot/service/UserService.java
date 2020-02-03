package boot.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import boot.dao.UserDao;
import boot.model.UserDetails;

@Service("userService")
public class UserService 
{

	@Autowired
	UserDao userDao;


	@Transactional
	public Object registerUser(UserDetails userDetails) 
	{
		return userDao.registerUser(userDetails);
	}

	@Transactional
	public Map<String, Object> loginUser(UserDetails userDetails) 
	{
		return userDao.loginUser(userDetails);
	}

}
