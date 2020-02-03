package boot.dao;

import java.util.Map;

public interface AptitudeDao 
{
	public Map<String, Object> getinitialization() throws Exception;
	public Map<String, Object> validateanswers(Object object, int userid) throws Exception;
}
