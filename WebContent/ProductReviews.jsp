<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reviews</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>ReviewId</th>
                <th>Summary</th>
                <th>Content</th>
                <th>Created</th>
                <th>User</th>
                <th>Rating</th>
                <th>Comments</th>
                <th>Delete Review</th>
            </tr>
            <c:forEach items="${reviews}" var="review" >
                <tr>
                    <td><c:out value="${review.getReviewId()}" /></td>
                    <td><c:out value="${review.getSummary()}" /></td>
                    <td><c:out value="${review.getContent()}" /></td>
                    <td><fmt:formatDate value="${review.getCreated()}" pattern="MM-dd-yyyy hh:mm:sa"/></td>
                    <td><a href="user?username=<c:out value="${review.getUser().getUsername()}"/>">${review.getUser().getUsername()}</a></td>
                    <td><c:out value="${review.getRating()}" /></td>
                    <td><a href="reviewcomments?reviewid=<c:out value="${review.getReviewId()}"/>">Comments</a></td>
                    <td><a href="reviewdelete?reviewid=<c:out value="${review.getReviewId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
