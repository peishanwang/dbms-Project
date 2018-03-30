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
<form action="findquestions" method="post">
		<h1>Search for questions by QuestionId</h1>
		<p>
			<label for="productId">ProductId</label>
			<input id="productId" name="productId" value="${fn:escapeXml(param.productId)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="questionCreate"><a href="questioncreate">Create Question</a></div>
	<br/>
	<h1>Matching Questions</h1>
        <table border="1">
            <tr>
                <th>QuestionId</th>
                <th>Content</th>
                <th>Created</th>
                <th>UserName</th>
                <th>ProductId</th>
            </tr>
            <c:forEach items="${questions}" var="question" >
                <tr>
                    <td><c:out value="${question.getQuestionId()}" /></td>
                    <td><c:out value="${question.getContent()}" /></td>
                    <td><c:out value="${question.getCreated()}" /></td>
                    <td><a href="user?username=<c:out value="${question.getUser().getUserName()}"/>">${question.getUser().getUserName()}</a></td>
                    <td><a href="questions?productid=<c:out value="${question.getProductId()}"/>">Product</a></td>
                    <td><a href="questiondelete?questionid=<c:out value="${question.getQuestionId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>

</body>
</html>