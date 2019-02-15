package com.Ekarting.dao;  
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Ekarting.pojo.Products;
import com.Ekarting.pojo.Userkart;
import com.Ekarting.pojo.Users;

//@Repository("dao")
@Service("dao")
public class UsersDao // extends HibernateDaoSupport
{  
	JdbcTemplate jdbctemplate;  
	//private HibernateTemplate hibernateTemplate;

	public void setJdbctemplate(JdbcTemplate jdbctemplate) 
	{  
		this.jdbctemplate = jdbctemplate;  
	}  

	public int save(Users u)
	{  
		List<Users> Users = new ArrayList<Users>();

		String sql="select * from users where loginid=?";  
		Users = jdbctemplate.query(sql, new Object[]{u.getLoginid()},new BeanPropertyRowMapper<Users>(Users.class));  

		if(Users.isEmpty())
		{
			sql="insert into Users(loginid,password,name) values('"+u.getLoginid()+"','"+u.getPassword()+"','"+u.getName()+"')";
			jdbctemplate.update(sql);
			return 1;
		}
		else
		{
			return 0;
		}
		  
	} 

	public int loginvalidation(Users u)
	{
		List<Users> Users = new ArrayList<Users>();

		String sql="select * from users where loginid=? and password=?";  
		Users = jdbctemplate.query(sql, new Object[]{u.getLoginid(),u.getPassword()},new BeanPropertyRowMapper<Users>(Users.class));  

		if(Users.isEmpty())
		{
			return 0;
		}
		else
		{
			return Users.get(0).getUserId();
		}

	}

	public List<Products> userkartitems (Users u)
	{
		List<Products> Products = new ArrayList<Products>();

		String sql="select uk.kartid,p.productname,uk.prodquantity,p.price,(p.price * uk.prodquantity) quantityprice from userkart uk, products p "
				+ "where uk.prodid=p.prodid and uk.userid=? ;";  
		Products = jdbctemplate.query(sql, new Object[]{u.getUserId()},new BeanPropertyRowMapper<Products>(Products.class));  

		return Products;
	}
	
	public Products itemandpricekartcount (Users u)
	{
		Products Products = new Products();

		String sql="select count(*) as itemcount,IFNULL(sum(p.price * uk.prodquantity),0) as totalcost"
				+ " from userkart uk, products p where uk.prodid=p.prodid and uk.userid=? ;";  
		Products = jdbctemplate.queryForObject(sql, new Object[]{u.getUserId()},new BeanPropertyRowMapper<Products>(Products.class));  

		return Products;
	}

	public Users getUsers(int Userid)
	{  
		String sql="select *,userid as id from users where userid=?";
		return jdbctemplate.queryForObject(sql, new Object[]{Userid},new BeanPropertyRowMapper<Users>(Users.class));    
	}  
	
	public List<Users> getUsers()
	{  
		String sql="select *,userid as id from users";
		return  jdbctemplate.query(sql, new Object[] {},new BeanPropertyRowMapper<Users>(Users.class)); 	
	} 
	
	public List<Products> getProducts()
	{  
		String sql="select * from products";
		return  jdbctemplate.query(sql, new Object[] {},new BeanPropertyRowMapper<Products>(Products.class)); 		
	}

	public int addtokart(int UserId, int ProdId, int Quantity)
	{
		
		List<Userkart> Userkart = new ArrayList<Userkart>();

		String sql="select * from userkart where userid=? and prodid=?";  
		Userkart = jdbctemplate.query(sql, new Object[]{UserId,ProdId},new BeanPropertyRowMapper<Userkart>(Userkart.class));
		
		if(Userkart.isEmpty())
		{
			sql="insert into userkart(userid,prodid,prodquantity) values("+UserId+","+ProdId+","+Quantity+")";  
			return jdbctemplate.update(sql);
		}
		else
		{
			sql="update userkart set prodquantity=prodquantity+"+Quantity+" where userid="+UserId+" and prodid="+ProdId+" ";  
			return jdbctemplate.update(sql);
		}
	}
	
	public int deletefromkart(int kartid)
	{  
		List<Userkart> Userkart = new ArrayList<Userkart>();
		String sql="select * from userkart where kartid="+kartid+" ";
		Userkart = jdbctemplate.query(sql, new Object[] {},new BeanPropertyRowMapper<Userkart>(Userkart.class)); 
	
		sql="delete from userkart where kartid="+kartid+" ";  
		jdbctemplate.update(sql); 
		
		return Userkart.get(0).getUserid();
	}
	
	
}  