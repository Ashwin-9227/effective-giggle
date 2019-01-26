<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body style="text-align: center;">
<h1>Registration</h1>
<form:form method="post" action="register">
	<table style="margin: 0px auto;">
		<tr>
			<td>Login ID :</td>
			<td><form:input path="Loginid" id="id_login_user" /></td>
		</tr>
		<tr>
			<td>Password :</td>
			<td><form:password path="Password" id="id_login_pass" /></td>
		</tr>
		<tr>
			<td>Name :</td>
			<td><form:input path="Name" id="id_login_name" /></td>
		</tr>
		<tr>
			<td></td>
			<td>
			<input type="submit" value="Register" />
			<input type="button" value="Clear" onclick="id_register_action_clear()" />
			<input type="button" value="Cancel" onclick="id_register_action_cancel()" />
			</td>
		</tr>
	</table>
</form:form>
</body>

<script LANGUAGE="JavaScript">

	function id_register_action_clear() 
	{
		id_login_user.value = "";
		id_login_pass.value = "";
		id_login_name.value = "";
	}
	
	function id_register_action_cancel() 
	{
		location.href = "Login";
	}
	
</script>