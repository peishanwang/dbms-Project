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
<title>Update a User</title>
</head>
<body>
<div class="container theme=showcase" role="main">
	<div class="jumbotron">
		<h1>Update User</h1>
	</div>
	<form action="userupdate" method="post">
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.username)}">
		</p>
		<p>
			<label for="firstname">New FirstName</label>
			<input id="firstname" name="firstname" value="${user.getFirstName()}">
		</p>
		<p>
			<label for="lastname">New LastName</label>
			<input id="lastname" name="lastname" value="${user.getLastName()}">
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