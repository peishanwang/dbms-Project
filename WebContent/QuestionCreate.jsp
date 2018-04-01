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
<title>QuestionCreate</title>
</head>
<body>
<div class="container theme=showcase" role="main">
<form action="questioncreate" method="post">
    <div class="jumbotron">
		<h1>Create a question</h1>
	</div>
		<p>
			<label for="content">Content</label>
			<input id="content" name="content">
		</p>
		<p>
			<label for="productid">ProductId</label>
			<input id="productid" name="productid" value="${fn:escapeXml(param.productid)}">
		</p>
		
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username">
		</p>
		<p>
			<input type="submit" class="btn btn-primary">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="questionCreate"><a class="btn btn-danger" href="questions">Go Back</a></div>
	</div>

</body>
</html>