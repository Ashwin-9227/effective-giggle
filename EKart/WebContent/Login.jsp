<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>E-Kart - Login</title>
<link rel="stylesheet" href="EKart.css">
</head>
<body style="text-align: center;padding: 5px 5px 5px 5px;">
	<div style="padding: 5px 5px 5px 5px;">
		<label>User ID</label> <input id="id_login_user" type="text">
	</div>
	<div style="padding: 0px 5px 5px 0px;">
	<br>
		<label>Password</label> <input id="id_login_pass" type="password">
	</div>
	<div style="padding: 0px 0px 0px 0px;">
	 <script NAME="id_login_form1" METHOD="POST">
		<input type="button" id="id_login_submit" value="Login" onclick="id_login_action_login()" style="width: 80px; height: 20px" >
		<input type="button" id="id_login_clear" value="Clear" onclick="id_login_action_clear()" style="width: 80px; height: 20px" >
	</form>
	</div>
	New user? Click	<a href="/EKart/Register.jsp">here</a> to register!!!
	
	<script LANGUAGE="JavaScript">
        
        function id_login_action_login()
        {
            //document.id_login_form1.buttonName.value = "login";
            //id_login_form1.submit();
        	//window.open("/EKart/Register.jsp");
        	location.href="/EKart/Ekart.jsp";
        } 
        
        function id_login_action_clear()
        {
        	id_login_user.value = "";
        	id_login_pass.value = "";
        } 
        
    </script>

</body>
</html>