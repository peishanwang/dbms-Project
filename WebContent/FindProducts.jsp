<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>Find a Product</title>
</head>
<body>
	<div class="container theme=showcase" role="main">
		<form action="findproducts" method="post">
			<div class="jumbotron text-center">
				<h1>Search for a Product</h1> 
				<h1><small>by ProductName</small></h1>
			</div>
			<br/>
				<p id="userCreate"><a class="btn btn-primary" href="usercreate">Create User</a></p>
				<p id="productCreate"><a class="btn btn-primary" href="productcreate">Create Product</a></p>
				<p id="brandCreate"><a class="btn btn-primary" href="brandcreate">Create Brand</a></p>
			<br/>
			<p>
				<h3><label for="productname">ProductName</label></h3>
				<input id="productname" name="productname" value="${fn:escapeXml(param.productname)}">
			</p>
			<p>
				<input type="submit" class="btn btn-primary">
				<br/><br/><br/>
			</p>
		</form>
		<div class="alert alert-info" role="alert">
			<span id="successMessage"><b>${messages.success}</b></span>
		</div>
		
		<h1>Matching Products</h1>
        <table class="table table-striped table-bordered">
            <tr>
                <th>ProductId</th>
                <th>ProductName</th>
                <th>Rating</th>
            </tr>
            <c:forEach items="${products}" var="product" >
                <tr>
                    <td><a href="product?productid=<c:out value="${product.getProductId()}" />">${product.getProductId()}</a></td>
                    <td><c:out value="${product.getProductName()}"/></td>
                    <td><c:out value="${product.getAverageRating()}"/></td>
                </tr>
            </c:forEach>
       </table>
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>
