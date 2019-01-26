<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
<div style="float: right;">
Welcome ${Users.name}!!!
<a href="Login">Logout</a>
</div>
<div style="float: right; width: 200px;">
Items added in cart : ${kartitem.itemcount}
 <%-- <c:forEach var="Users" items="${lists}"> 
<td>${Users.password}</td>
</c:forEach> --%> 
<a href="Viewkart/${Users.id}">View</a>
</div>
<br>
<br>
<div>
<label>Select product(s) you want to buy</label>
<br>
<br>
		<form:form action="addtokart" method="post">
			Select a Product:&nbsp; 
			<input id="id" name="id" type="hidden" value="${Users.id}" /> 
			<select id="id_ekart_selecter" name=categoryId> <!-- onchange="displayStuff()" -->
				<c:forEach items="${Products}" var="Products">
					<option value="${Products.prodid}">${Products.productname}</option>
				</c:forEach>
			</select> 
			&nbsp; 
			<label> Quantity :</label> <input id="id" name="quantity" value="1" /> 
			<br/> <br /> 
			<input type="submit" value="Add to Kart" />
		</form:form>
		<!-- &nbsp;
<label>Price:</label>
<label>1000</label> -->
</div>
<!-- <div>
<br>
<input type="button" value="Add to Cart" id="id_ekart_addtokart" onclick="id_ekart_addkart()">
</div> -->

<script LANGUAGE="JavaScript">
        
	/* function id_ekart_addkart()
	{
		location.href="/EKart/Viewkart.jsp";
	}  */
	
	function displayStuff() 
	{
	    var e = document.getElementById('id_ekart_selecter');
	    var val = e.options[e.selectedIndex].value;

	    switch (val) {
	        case "1":
	            document.getElementById('one').style.display = 'block';
	            break;
	        case "2":
	            document.getElementById('two').style.display = 'block';
	            break;
	    }
	    
	}
	
</script>
        
</body>