package boot.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import boot.model.AptitudeAnswers;
import boot.model.AptitudeQuestions;
import boot.model.AptitudeValidateanswers;
import boot.model.UserDetails;

@Repository
@SuppressWarnings("unchecked")
public class AptitiudeDaoImpl implements AptitudeDao
{
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	private String str = "";
	private Query query;

	public Map<String, Object> getinitialization() throws Exception
	{
		List <AptitudeQuestions> Listtsquestions = new ArrayList<AptitudeQuestions>();
		List <AptitudeAnswers> Listtsanswers = new ArrayList<AptitudeAnswers>();

		AptitudeQuestions aptitudeQuestions;
		AptitudeAnswers aptitudeAnswers;

		StringBuilder StringBuilder = new StringBuilder();

		Map<String, Object> Map = new HashMap<String, Object>();
		Session session = this.sessionFactory.getCurrentSession();
		str = "select a.* from ( " + 
				" select b.quesid,b.question,b.active,FLOOR(RAND()*100 ) as sortorder from AptitudeQuestions b where active=1 ) a " + 
				" order by a.sortorder";
		query = session.createSQLQuery(str);
		List<Object> result = (List<Object>) query.list(); 
		if(!result.isEmpty())
		{
			Iterator<Object> itr = result.iterator();
			while(itr.hasNext())
			{
				Object[] questions = (Object[]) itr.next();
				aptitudeQuestions = new AptitudeQuestions();
				aptitudeQuestions.setQuesid(Integer.parseInt(String.valueOf(questions[0])));
				aptitudeQuestions.setQuestion(String.valueOf(questions[1]));
				aptitudeQuestions.setActive(Integer.parseInt(String.valueOf(questions[2])));
				aptitudeQuestions.setSortorder(Integer.parseInt(String.valueOf(questions[3]).substring(0,(String.valueOf(questions[3]).length()-2))));

				Listtsquestions.add(aptitudeQuestions);

				StringBuilder = StringBuilder.append(","+aptitudeQuestions.getQuesid());
			}

			str = "select a.* from ( " +
					" select b.answid,b.quesid,b.answer,FLOOR(RAND()*100 )as sortorder from AptitudeAnswers b) a" +
					" order by field(quesid"+StringBuilder+"),a.sortorder";

			query = session.createSQLQuery(str);
			result = (List<Object>) query.list(); 
			if(!result.isEmpty())
			{
				itr = result.iterator();

				while(itr.hasNext())
				{
					Object[] answers = (Object[]) itr.next();
					aptitudeAnswers = new AptitudeAnswers();
					aptitudeAnswers.setAnswid(Integer.parseInt(String.valueOf(answers[0])));
					aptitudeAnswers.setQuesid(Integer.parseInt(String.valueOf(answers[1])));
					aptitudeAnswers.setAnswer(String.valueOf(answers[2]));
					aptitudeAnswers.setSortorder(Integer.parseInt(String.valueOf(answers[3]).substring(0,(String.valueOf(answers[3]).length()-2))));

					Listtsanswers.add(aptitudeAnswers);
				}
			}

			if(!Listtsquestions.isEmpty())
			{
				Map.put("questions", Listtsquestions);
			}

			if(!Listtsanswers.isEmpty())
			{
				Map.put("answers", Listtsanswers);
			}
		}

		return Map;
	}


	public Map<String, Object> validateanswers(Object object, int userid) throws Exception
	{
		Map<String, Object> Map = new HashMap<String, Object>();
		int totalquestions = 0;
		int totalrightanswers = 0;
		AptitudeAnswers aptitudeAnswers;
		List <AptitudeAnswers> Listsanswers = new ArrayList<AptitudeAnswers>();
		UserDetails userDetails = new UserDetails();
		Session session = this.sessionFactory.getCurrentSession();

		ObjectMapper objectMapper = new ObjectMapper();

		AptitudeAnswers[] answersarray = objectMapper.readValue(object.toString(), AptitudeAnswers[].class);

		for(AptitudeAnswers answers : answersarray)
		{
			aptitudeAnswers = new AptitudeAnswers();
			aptitudeAnswers.setAnswid(Integer.parseInt(String.valueOf(answers.getAnswid())));
			aptitudeAnswers.setQuesid(Integer.parseInt(String.valueOf(answers.getQuesid())));
			aptitudeAnswers.setAnswer(String.valueOf(answers.getAnswer()));
			aptitudeAnswers.setSortorder(Integer.parseInt(String.valueOf(answers.getSortorder())));

			Listsanswers.add(aptitudeAnswers);

			str = "select * from AptitudeValidateanswers where quesid="+answers.getQuesid()+" and answid="+answers.getAnswid();

			query = session.createSQLQuery(str);
			List<AptitudeValidateanswers> result = (List<AptitudeValidateanswers>) query.list(); 

			if(!result.isEmpty())
			{
				totalrightanswers++;
			}
		}

		totalquestions = Listsanswers.size();

		str = "select * from UserDetails where id="+userid;

		query = session.createSQLQuery(str);

		List<Object> result = (List<Object>) query.list(); 
		Iterator<Object> itr = result.iterator();

		while(itr.hasNext())
		{
			Object[] objects = (Object[]) itr.next();
			userDetails = new UserDetails();
			userDetails.setId(Integer.parseInt(String.valueOf(objects[0])));
			userDetails.setLoginName(String.valueOf(objects[1]));
			userDetails.setFullName(String.valueOf(objects[2]));
			userDetails.setPassword(String.valueOf(objects[3]));
			userDetails.setEmail(String.valueOf(objects[4]));
			userDetails.setDob((Date)objects[5]);
			userDetails.setTestresult(String.valueOf(objects[6]));
			userDetails.setLastattemptoftest(String.valueOf(objects[7]));
			userDetails.setTotalattempts(Integer.parseInt(String.valueOf(objects[8])));
		}

		if(!Listsanswers.isEmpty())
		{
			Date date = Calendar.getInstance().getTime();  
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");  
			String strDate = dateFormat.format(date);

			userDetails.setTestresult(totalrightanswers+"/"+totalquestions);
			userDetails.setLastattemptoftest(strDate);
			userDetails.setTotalattempts(userDetails.getTotalattempts()+1);
			session.update(userDetails);
			Map.put("Result", "Success");
			Map.put("userdetails", userDetails);
		}
		else
		{
			Map.put("Result", "Failure");
		}

		return Map;
	}

}
