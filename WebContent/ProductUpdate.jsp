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
<title>Update a Product</title>
</head>
<body>
<div class="container theme=showcase" role="main">
	<div class="jumbotron">
		<h1>Update Product</h1>
	</div>
	<form action="productupdate" method="post">
		<p>
			<label for="productid">ProductId</label>
			<input id="productid" name="productid" value="${fn:escapeXml(param.productid)}">
		</p>
		<p>
			<label for="productname">New Product Name</label>
			<input id="productname" name="productname" value="${product.getProductName()}">
		</p>
		<p>
			<label for="description">New Description</label>
			<input id="description" name="description" value="${product.getDescription()}">
		</p>
		<p>
			<label for="price">New Price</label>
			<input id="price" name="price" value="${product.getPrice()}">
		</p>
		<p>
			<input type="submit" class="btn btn-primary">
		</p>
	</form>
	<br/><br/>
	<div class="alert alert-info">
		<span id="successMessage"><b>${messages.success}</b></span>
	</div>
</div>
</body>
</html>