<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ProductQuestions</title>
</head>
<body>
    <h1>Answers</h1>

	<div id="answers"><a href="answercreate">Create Answer</a></div>
	<br/>
	<h1>Matching Answers</h1>
        <table border="1">
            <tr>
                <th>AnswerId</th>
                <th>Content</th>
                <th>Created</th>
                <th>UserName</th>

            </tr>
            <c:forEach items="${answers}" var="answer" >
                <tr>
                    <td><c:out value="${answer.getAnswerId()}" /></td>
                    <td><c:out value="${answer.getContent()}" /></td>
                    <td><c:out value="${answer.getCreated()}" /></td>
                    <td><a href="user?username=<c:out value="${answer.getUser().getUserName()}"/>">${answer.getUser().getUserName()}</a></td>
                </tr>
            </c:forEach>
       </table>

</body>
</html>