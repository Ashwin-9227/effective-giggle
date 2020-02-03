package boot.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userdetails")
public class UserDetails 
{
	
	@Id
	private int id;
	private String loginName;
	private String fullName;
	private String password;
	private String email;
	private Date dob;
	private String testresult;
	private String lastattemptoftest;
	private int totalattempts;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getTestresult() {
		return testresult;
	}
	public void setTestresult(String testresult) {
		this.testresult = testresult;
	}
	public String getLastattemptoftest() {
		return lastattemptoftest;
	}
	public void setLastattemptoftest(String lastattemptoftest) {
		this.lastattemptoftest = lastattemptoftest;
	}
	public int getTotalattempts() {
		return totalattempts;
	}
	public void setTotalattempts(int totalattempts) {
		this.totalattempts = totalattempts;
	}
	public String toString()
	{
		return new StringBuilder()
				.append("id -> "+String.valueOf(id))
				.append(" loginName -> "+loginName)
				.append(" fullName -> "+fullName)
				.append(" password -> "+password)
				.append(" email -> "+email)
				.append(" dob -> "+dob.toString())
				.append(" testresult -> "+testresult)
				.append(" lastattemptoftest -> "+lastattemptoftest)
				.toString();
	}
	
	

}
