package com.Ekarting.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userkart")
public class Userkart 
{
	@Id
	@Column(name="kartid")private int Kartid;	
	@Column(name="userid")private int Userid;  
	@Column(name="prodid")private int Prodid;
	@Column(name="prodquantity")private String Prodquantity;

	public int getKartid() {
		return Kartid;
	}
	public void setKartid(int kartid) {
		Kartid = kartid;
	}
	public int getUserid() {
		return Userid;
	}
	public void setUserid(int userid) {
		Userid = userid;
	}
	public int getProdid() {
		return Prodid;
	}
	public void setProdid(int prodid) {
		Prodid = prodid;
	}
	public String getProdquantity() {
		return Prodquantity;
	}
	public void setProdquantity(String prodquantity) {
		Prodquantity = prodquantity;
	}



}
