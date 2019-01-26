<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>E- Kart View Kart</title>
</head>
<body style="text-align: center;">
	<div style="float: right;">
		Welcome ${Users.name}!!! <a href="Logout">Logout</a>
	</div>
	<br>
	<div>
		<table border="2" width="70%" cellpadding="2" style="margin: 0px auto;" >
			<tr>
				<th>Product</th>
				<th>Quantity</th>
				<th>Price of One</th>
				<th>Price as per quantity</th>
				<th>Remove item from the kart</th>
			</tr>
			<c:forEach var="kart" items="${list}">
				<tr>
					<td>${kart.productname}</td>
					<td>${kart.prodquantity}</td>
					<td>${kart.price}</td>
					<td>${kart.quantityprice}</td>
					<td><a href="deletefromkart/${kart.kartid}">Remove</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div style="float: right; font-weight: bold;">Total amount to be
		paid: ${total.totalcost}</div>
	<br>
	<div style="float: right;">
		<input type="button" value="Buy now" id="id_viewkart_addtokart" onclick="id_viewkart_viewkart()"> <input type="button"
			value="Back to Home screen" id="id_viewkart_back" onclick="id_viewkart_back()">
	</div>
	<script LANGUAGE="JavaScript">
		function id_viewkart_viewkart() {
			//location.href="/EKart/Viewkart.jsp";
		}

		function id_viewkart_back() {
			location.href = "Ekart/${Users.id}";
		}
	</script>
</body>
</html>