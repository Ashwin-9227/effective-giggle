package boot.dao;

import java.util.Map;

import boot.model.UserDetails;

public interface UserDao 
{
 
	public Object registerUser(UserDetails userDetails);

	public Map<String, Object> loginUser(UserDetails userDetails);
 
}
