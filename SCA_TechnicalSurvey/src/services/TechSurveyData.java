package services;

import java.util.List;
import java.util.Map;

import org.osoa.sca.annotations.Remotable;

@Remotable
public interface TechSurveyData 
{
	public Map<String, Object> getinitialization(String str) throws Exception;
	public void getconnection() throws Exception;
	public Map<String, Object> validateanswers(String str) throws Exception;
}
