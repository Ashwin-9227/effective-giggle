package boot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="aptitudequestions")
public class AptitudeQuestions 
{
	
	@Id
	private int quesid;
	private String question;
	private int active;
	private int sortorder;
	
	public int getQuesid() {
		return quesid;
	}
	public void setQuesid(int quesid) {
		this.quesid = quesid;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getSortorder() {
		return sortorder;
	}
	public void setSortorder(int sortorder) {
		this.sortorder = sortorder;
	}
	
	public String toString()
	{
		return new StringBuilder()
				.append("quesid -> "+String.valueOf(quesid))
				.append(" question -> "+question)
				.append(" active -> "+String.valueOf(active))
				.append(" sortorder -> "+String.valueOf(sortorder))
				.toString();
	}
}
