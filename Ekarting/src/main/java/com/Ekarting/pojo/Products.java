package com.Ekarting.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="products")
public class Products implements Serializable
{

	@Id
	@Column(name="prodid")private int Prodid;
	@Column(name="productname")private String Productname;
	@Column(name="price")private int Price;
	
	transient private int Prodquantity;
	transient private int Quantityprice;
	transient private int itemcount;
	transient private int totalcost;
	transient private int kartid;
	
	
	public int getProdid() {
		return Prodid;
	}
	public void setProdid(int prodid) {
		Prodid = prodid;
	}
	public String getProductname() {
		return Productname;
	}
	public void setProductname(String productname) {
		Productname = productname;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public int getProdquantity() {
		return Prodquantity;
	}
	public void setProdquantity(int prodquantity) {
		Prodquantity = prodquantity;
	}
	public int getQuantityprice() {
		return Quantityprice;
	}
	public void setQuantityprice(int quantityprice) {
		Quantityprice = quantityprice;
	}
	public int getItemcount() {
		return itemcount;
	}
	public void setItemcount(int itemcount) {
		this.itemcount = itemcount;
	}
	public int getTotalcost() {
		return totalcost;
	}
	public void setTotalcost(int totalcost) {
		this.totalcost = totalcost;
	}
	public int getKartid() {
		return kartid;
	}
	public void setKartid(int kartid) {
		this.kartid = kartid;
	}


}
