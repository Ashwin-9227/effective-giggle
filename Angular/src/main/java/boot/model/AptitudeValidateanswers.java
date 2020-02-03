package boot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="aptitudevalidateanswers")
public class AptitudeValidateanswers 
{
	
	@Id
	private int valid;
	private int answid;
	private int quesid;
	
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
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

	
	public String toString()
	{
		return new StringBuilder()
				.append(" valid -> "+String.valueOf(valid))
				.append(" answid -> "+String.valueOf(answid))
				.append(" quesid -> "+String.valueOf(quesid))
				.toString();
	}
}
