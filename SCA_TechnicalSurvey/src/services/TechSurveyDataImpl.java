package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osoa.sca.annotations.Init;
import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Service;

import com.sun.xml.internal.fastinfoset.util.StringArray;

@Service(TechSurveyData.class)
public class TechSurveyDataImpl implements TechSurveyData
{

	private Connection conn = null;
	
	@Property
	public String test = "binding";
	
	private String string ;

	@Init
	public void init() throws Exception 
	{
		string= test;
		getconnection();
	}

	public void getconnection() throws Exception
	{
		System.out.println("Inside jdbc conn....");
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/world";
		System.out.println("Connecting to database: " + url);
		conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "sysadmin@123");
		System.out.println("conn property..."+conn);
	}
	
	public Map<String, Object> getinitialization(String str) throws Exception
	{
		List <tsquestions> Listtsquestions = new ArrayList<tsquestions>();
		tsquestions tsquestions = new tsquestions();
		List <tsanswers> Listtsanswers = new ArrayList<tsanswers>();
		tsanswers tsanswers = new tsanswers();
		
		Map<String, Object> Map = new HashMap<String, Object>();
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery("select * from tsquestions where active=1 order by sortorder");
		while(result.next())
		{
			tsquestions = new tsquestions();
			tsquestions.setQuesid(result.getInt("quesid"));
			tsquestions.setQuestion(result.getString("question"));
			tsquestions.setActive(result.getInt("active"));
			tsquestions.setSortorder(result.getInt("sortorder"));
			
			Listtsquestions.add(tsquestions);
		}
		
		result = statement.executeQuery("select * from tsanswers");
		while(result.next())
		{
			tsanswers = new tsanswers();
			tsanswers.setAnswid(result.getInt("answid"));
			tsanswers.setQuesid(result.getInt("quesid"));
			tsanswers.setAnswer(result.getString("answer"));
			
			Listtsanswers.add(tsanswers);
		}
		
		Map.put("questions", Listtsquestions);
		Map.put("answers", Listtsanswers);
		
		return Map;
	}
	
	public Map<String, Object> validateanswers(String str) throws Exception
	{
		Map<String, Object> Map = new HashMap<String, Object>();
		List <tsanswers> List = new ArrayList<tsanswers>();
		tsanswers tsanswers = new tsanswers();
		
		String[] StringArray = str.split("quesid:");
		
		for (String string : StringArray) 
		{
			tsanswers = new tsanswers();
			if(string.length() > 0)
			{
				tsanswers.setQuesid( Integer.valueOf( string.substring( 0, string.lastIndexOf("answ")-1 ) ) );
				tsanswers.setAnswid( Integer.valueOf( string.substring( string.lastIndexOf(":")+1, string.length() ) ) );
				List.add(tsanswers);
			}
		}
		
		return Map;
	}

}
