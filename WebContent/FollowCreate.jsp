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
<title>Create a Follow Relationship</title>
</head>
<body>
<div class="container theme=showcase" role="main">
	<div class="jumbotron text-center">
	    <h1>Create Follow</h1>
	</div>
	<form action="followcreate" method="post">
		<p>
			<label for="follower">Follower</label>
			<input id="follower" name="follower" value="${fn:escapeXml(param.follower)}">
		</p>
		<p>
			<label for="followee">Followee</label>
			<input id="followee" name="followee" value="${fn:escapeXml(param.followee)}">
		</p>
		<p>
			<input type="submit" class="btn btn-primary">
		</p>
	</form>
	<br/><br/>
	<p>
	<div class="alert alert-info" role="alert">
		<span id="successMessage"><b>${messages.success}</b></span>
		</div>
	</p>
	</div>
</body>
</html>