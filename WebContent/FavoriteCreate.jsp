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
<title>Create a Favorite</title>
</head>
<body>
	<div class="container theme=showcase" role="main">
		<div class="jumbotron text-center">
			<h1>Create Favorite</h1>
		</div>
		<form action="favoritecreate" method="post">
			<p>
				<label for="username">UserName</label>
				<input id="username" name="username" value="${fn:escapeXml(param.username)}">
			</p>
			<p>
				<label for="productid">ProductId</label>
				<input id="productid" name="productid" value="">
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