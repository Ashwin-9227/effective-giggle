package boot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="aptitudeanswers")
public class AptitudeAnswers 
{

	@Id
	private int answid;
	private int quesid;
	private String answer;
	private int sortorder;

	public int getAnswid() {
		return answid;
	}
	public void setAnswid(int answid) {
		this.answid = answid;
	}
	public int getQuesid() {
		return quesid;
	}
	public void setQuesid(int quesid) {
		this.quesid = quesid;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
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
				.append("answid -> "+String.valueOf(answid))
				.append(" quesid -> "+String.valueOf(quesid))
				.append(" answer -> "+answer)
				.append(" sortorder -> "+String.valueOf(sortorder))
				.toString();
	}
}
