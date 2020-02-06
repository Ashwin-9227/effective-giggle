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
		conn = java.sql.DriverManager.getConnection(url, "root", "sysadmin@123");
		System.out.println("conn property..."+conn);
	}
	
	public Map<String, Object> getinitialization(String str) throws Exception
	{
		List <tsquestions> Listtsquestions = new ArrayList<tsquestions>();
		tsquestions tsquestions = new tsquestions();
		List <tsanswers> Listtsanswers = new ArrayList<tsanswers>();
		tsanswers tsanswers = new tsanswers();
		StringBuilder StringBuilder = new StringBuilder();
		
		Map<String, Object> Map = new HashMap<String, Object>();
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery("select a.* from ( " + 
				"select *,FLOOR(RAND()*100 ) as random from tsquestions where active=1 ) a " + 
				"order by a.random");
		while(result.next())
		{
			tsquestions = new tsquestions();
			tsquestions.setQuesid(result.getInt("quesid"));
			tsquestions.setQuestion(result.getString("question"));
			tsquestions.setActive(result.getInt("active"));
			tsquestions.setSortorder(result.getInt("sortorder"));
			
			Listtsquestions.add(tsquestions);
			
			StringBuilder = StringBuilder.append(","+tsquestions.getQuesid());
		}
		
		result = statement.executeQuery("select * from tsanswers order by field(quesid"+StringBuilder+")");
		System.out.println("select * from tsanswers order by field(quesid"+StringBuilder+")");
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
		List <tsvalidateanswers> List = new ArrayList<tsvalidateanswers>();
		tsvalidateanswers tsvalidateanswers = new tsvalidateanswers();
		int totalquestions = 0;
		int totalrightanswers = 0;
		
		String[] StringArray = str.split("quesid:");
		
		for (String string : StringArray) 
		{
			tsvalidateanswers = new tsvalidateanswers();
			if(string.length() > 0)
			{
				tsvalidateanswers.setQuesid( Integer.valueOf( string.substring( 0, string.lastIndexOf("answ")-1 ) ) );
				tsvalidateanswers.setAnswid( Integer.valueOf( string.substring( string.lastIndexOf(":")+1, string.length() ) ) );
				List.add(tsvalidateanswers);
			}
		}
		
		totalquestions = List.size();
		
		for (tsvalidateanswers tsvalidateanswers1 : List) 
		{
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("select * from tsvalidateanswers where quesid="+tsvalidateanswers1.getQuesid()+" "
            		+ "and answid="+tsvalidateanswers1.getAnswid());
            
            while(result.next())
    		{
            	totalrightanswers++;
    		}
		}
		
		Map.put("totalquestions", totalquestions);
		Map.put("totalrightanswers", totalrightanswers);
		
		return Map;
	}

}
