package com.Ekarting.controllers;   
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Ekarting.dao.UsersDao;
import com.Ekarting.pojo.Products;
import com.Ekarting.pojo.Users;

@Controller  
public class EkartController {  
    @Autowired  
    UsersDao dao;//will inject dao from xml file  
      
    Users Users = new Users();
    
    /*It displays a form to input data, here "command" is a reserved request attribute 
     *which is used to display object data into form 
     */  
    @RequestMapping("/Register")  
    public String getregister(Model m){
    	m.addAttribute("command", new Users());
    	return "Register"; 
    }
    
    @RequestMapping("/Login")  
    public String gethome(Model m){
    	m.addAttribute("command", new Users());
    	return "Login"; 
    }
    
    @RequestMapping("/Success")  
    public String getsuccess(){
    	return "Success"; 
    }
    
    @RequestMapping("/Exists")  
    public String getexists(){
    	return "Exists"; 
    }
    
    @RequestMapping("/Validfailure")  
    public String getvalidfailure(){
    	return "ValidFail"; 
    }
    
    @RequestMapping("/AddedSuccess")  
    public String getaddedsuccess(Model m)
    {
    	m.addAttribute("Users", dao.getUsers(Users.getUserId()));
    	return "AddedSuccess"; 
    }
    
    @RequestMapping("/Viewkart/Logout")  
    public String Logout(){
    	return "redirect:/Login";
    }
    
    @RequestMapping("/Ekart")  
    public String getekart(Model m)
    {
    	m.addAttribute("Users", dao.getUsers(Users.getUserId()));
    	m.addAttribute("kartitem", dao.itemandpricekartcount(Users));
    	m.addAttribute("Products", dao.getProducts());
    	return "Ekart"; 
    	 
    }
    
    @RequestMapping(value="/Ekart/{UserId}")    
    public String getekartafteradd(@PathVariable int UserId,Model m)
    {
    	Users.setUserId(UserId);
    	return "redirect:/Ekart"; 
    	 
    }
    
    @RequestMapping(value="/Viewkart/Ekart/{UserId}")    
    public String getekart(@PathVariable int UserId,Model m)
    {
    	Users.setUserId(UserId);
    	return "redirect:/Ekart"; 
    	 
    }
    
    @RequestMapping(value="/Viewkart/{UserId}")  
    public String getviewkart(@PathVariable int UserId, Model m)
    {
    	Users.setUserId(UserId);
    	m.addAttribute("list",dao.userkartitems(Users));
    	m.addAttribute("Users", dao.getUsers(UserId));
    	m.addAttribute("total", dao.itemandpricekartcount(Users));
    	return "Viewkart"; 
    }
    
    @RequestMapping(value="/register",method = RequestMethod.POST)  
    public String register(@ModelAttribute("users") Users users)
    {
    	try
    	{
    		dao.save(users);  
    		return "redirect:/Success";//will redirect to Login request mapping
    	}
    	catch (Exception e)
    	{
    		return "redirect:/Exists";//will redirect to Login request mapping
    	}
    }  
    
    @RequestMapping(value="/loginvalidation",method = RequestMethod.POST)  
    public String loginvalidation(@ModelAttribute("users") Users users)
    {
    	int i = dao.loginvalidation(users);
    	
    	if(i == 0)
    	{
    		return "redirect:/Validfailure"; 
    	}
    	else
    	{
    		Users.setUserId(i);
    		return "redirect:/Ekart"; 
    	}
		 
    }
    
    @RequestMapping(value="/addtokart",method = RequestMethod.POST)  
    public String addtokart(@RequestParam("categoryId") String prodid , @RequestParam("quantity") String quantity , @ModelAttribute("users") Users users)
    {
    	Users.setUserId(users.getId());
    	dao.addtokart(users.getId(), Integer.parseInt(prodid), Integer.parseInt(quantity));
    	return "redirect:/AddedSuccess";
    }
    
    @RequestMapping(value="/Viewkart/deletefromkart/{kartid}",method = RequestMethod.GET)  
    public String deletefromkart(@PathVariable int kartid){  
    	Users.setUserId(dao.deletefromkart(kartid));  
        return "redirect:/viewkartafterdelete";  
    } 
    
    @RequestMapping(value="/deletefromkart/{kartid}",method = RequestMethod.GET)  
    public String deletingfromkart(@PathVariable int kartid){  
    	Users.setUserId(dao.deletefromkart(kartid));  
        return "redirect:/viewkartafterdelete";  
    } 
    
    @RequestMapping("/viewkartafterdelete")  
    public String viewkartafterdelete(Model m)
    {
    	m.addAttribute("list",dao.userkartitems(Users));
    	m.addAttribute("Users", dao.getUsers(Users.getUserId()));
    	m.addAttribute("total", dao.itemandpricekartcount(Users));
    	return "Viewkart"; 
    }
    
    
    /*
    @RequestMapping("/userform")  
    public String showform(Model m){  
    	m.addAttribute("command", new Users());
    	return "userform"; 
    }  
    It saves object into database. The @ModelAttribute puts request data 
     *  into model object. You need to mention RequestMethod.POST method  
     *  because default request is GET  
    @RequestMapping(value="/save",method = RequestMethod.POST)  
    public String save(@ModelAttribute("user") Users user){  
        dao.save(user);  
        return "redirect:/viewuser";//will redirect to viewuser request mapping  
    }  
     It provides list of userloyees in model object   
    @RequestMapping("/viewuser")  
    public String viewuser(Model m){  
        List<Users> list=dao.getUsersloyees();  
        m.addAttribute("list",list);
        return "viewuser";  
    }  
     It displays object data into form for the given id.  
     * The @PathVariable puts URL data into variable.  
    @RequestMapping(value="/edituser/{id}")  
    public String edit(@PathVariable int id, Model m){  
       // Users user=dao.getUsersById(id);  
       // m.addAttribute("command",user);
        return "usereditform";  
    }  
     It updates model object.   
    @RequestMapping(value="/editsave",method = RequestMethod.POST)  
    public String editsave(@ModelAttribute("user") Users user){  
        //dao.update(user);  
        return "redirect:/viewuser";  
    }  
     It deletes record for the given id in URL and redirects to /viewuser   
    @RequestMapping(value="/deleteuser/{id}",method = RequestMethod.GET)  
    public String delete(@PathVariable int id){  
        //dao.delete(id);  
        return "redirect:/viewuser";  
    } */  
}  