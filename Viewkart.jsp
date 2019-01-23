<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>E- Kart View Kart</title>
<link rel="stylesheet" href="EKart.css">
</head>
<body style="text-align: center;">
<div style="float: right;">
Welcome user!!!
<a href="/EKart/Login.jsp">Logout</a>
</div>
<br>
<div>
<table style="width:100%">
  <tr>
    <th>Product</th>
    <th>Quantity</th> 
    <th>Price</th>
  </tr>
  <tr>
    <td>Power bank</td>
    <td>2</td> 
    <td>4000</td>
  </tr>
  <tr>
    <td>USB cable</td>
    <td>1</td> 
    <td>400</td>
  </tr>
</table>
</div>
<div style="float: right; font-weight: bold;">
Total amount to be paid:
4400
</div>
<br>
<div style="float: right;">
<input type="button" value="Buy now" id="id_viewkart_addtokart" onclick="id_viewkart_viewkart()">
<input type="button" value="Back to Home screen" id="id_viewkart_back" onclick="id_viewkart_back()">
</div>
<script LANGUAGE="JavaScript">
        
	function id_viewkart_viewkart()
	{
		//location.href="/EKart/Viewkart.jsp";
	} 
	
	function id_viewkart_back()
	{
		location.href="/EKart/Ekart.jsp";
	} 
	
</script>
</body>
</html>