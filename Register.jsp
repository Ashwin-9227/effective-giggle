<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>E-Kart - Register user</title>
<link rel="stylesheet" href="EKart.css">
</head>
<body style="text-align: center;padding: 5px 5px 5px 5px;">
	<div style="padding: 5px 5px 5px 5px;">
		<label>User ID</label> <input id="id_register_login" type="text">
	</div>
	<br>
	<div style="padding: 5px 5px 5px 5px;">
		<label>Password</label> <input id="id_register_pass" type="password">
	</div>
	<br>
	<div style="padding: 5px 5px 5px 5px;">
		<label>Name</label> <input id="id_register_name" type="text">
	</div>
	<br>
	<div style="padding: 5px 5px 5px 5px;">
		<label>Age</label> <input id="id_register_age" type="text">
	</div>
	<br>
	<div style="padding: 5px 5px 5px 5px;">
		<label>Email ID</label> <input id="id_register_email" type="text">
	</div>
	<br>
	<div style="padding: 5px 5px 5px 5px;">
		<label>Address</label> <input id="id_register_address" type="text"
			style="height: 50px;">
	</div>
	<br>
	<form NAME="id_register_form1" METHOD="POST">
		<input type="button" id="id_register_submit" value="Register" onclick="id_register_action_register()" style="width: 80px; height: 20px"> 
		<input type="button" id="id_Register_clear" value="Clear" onclick="id_register_action_clear()" style="width: 80px; height: 20px"> 
		<input type="button" id="id_Register_Cancel" value="Cancel" onclick="id_register_action_cancel()" style="width: 80px; height: 20px">
	</form>

	<script LANGUAGE="JavaScript">
		function id_register_action_register() {
			//document.id_login_form1.buttonName.value = "login";
			//id_login_form1.submit();
			//window.open("/EKart/Register.jsp");
			//location.href="/EKart/Login.jsp";
		}

		function id_register_action_clear() {
			id_register_login.value = "";
			id_register_pass.value = "";
			id_register_name.value = "";
			id_register_age.value = "";
			id_register_email.value = "";
			id_register_address.value = "";
		}

		function id_register_action_cancel() {
			location.href = "/EKart/Login.jsp";
		}
	</script>
</body>
</html>