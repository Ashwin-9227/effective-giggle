<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>E- Kart</title>
<link rel="stylesheet" href="EKart.css">
</head>
<body>
<div style="float: right;">
Welcome user!!!
<a href="/EKart/Login.jsp">Logout</a>
</div>
<div style="float: right; width: 200px;">
Items added in cart : 1
<a href="Viewkart.jsp">View</a>
</div>
<br>
<br>
<div>
<label>Select product(s) you want to buy</label>
<br>
<br>
<select>
  <option value="Power bank">Power bank</option>
  <option value="Mobile charger">Mobile charger</option>
  <option value="Usb cable">USB cable</option>
</select>
&nbsp;
<label>Quantity :</label>
<input type="number" style="width: 40px">
&nbsp;
<label>Price:</label>
<label>1000</label>
</div>
<div>
<br>
<input type="button" value="Add to Cart" id="id_ekart_addtokart" onclick="id_ekart_addkart()">
</div>

<script LANGUAGE="JavaScript">
        
	function id_ekart_addkart()
	{
		location.href="/EKart/Viewkart.jsp";
	} 
</script>
        
</body>
</html>