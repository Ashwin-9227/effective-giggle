package boot.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import boot.dao.AptitudeDao;

@Service("aptitudeService")
public class AptitudeService 
{

	@Autowired
	AptitudeDao aptitudeDao;


	@Transactional
	public Map<String, Object> getinitialization() throws Exception 
	{
		return aptitudeDao.getinitialization();
	}

	@Transactional
	public Map<String, Object> validateanswers(Object object, int userid) throws Exception 
	{
		return aptitudeDao.validateanswers(object,userid);
	}

}
