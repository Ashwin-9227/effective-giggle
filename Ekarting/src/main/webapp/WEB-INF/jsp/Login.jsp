<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body style="text-align: center;">
<h1>Login</h1>
<form:form method="post" action="loginvalidation">
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
			<td></td>
			<td>
			<input type="submit" value="Login" /> 
			<input type="button" value="Clear" onclick="id_login_action_clear()" /></td>
		</tr>
	</table>
</form:form>
</body>
New user? Click <a href="Register">here</a> to register!!!

<script LANGUAGE="JavaScript">

	function id_login_action_clear() 
	{
		id_login_user.value = "";
		id_login_pass.value = "";
	}
</script>