<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AnswerCreate</title>
</head>
<body>
<form action="answercreate" method="post">
		<h1>Create a question</h1>
		<p>
			<label for="content">Content</label>
			<input id="content" name="content">
		</p>
		<p>
			<label for="questionid">QuestionId</label>
			<input id="questionid" name="questionid" value="${fn:escapeXml(param.questionid)}">
		</p>
		
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>

</body>
</html>