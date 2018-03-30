<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ReviewComments</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>CommentId</th>
                <th>ReviewId</th>
                <th>User</th>
                <th>Helpful</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${reviewComments}" var="reviewComment" >
                <tr>
                    <td><c:out value="${reviewComment.getCommentId()}" /></td>
                    <td><c:out value="${reviewComment.getReview().getReviewId()}" /></td>
                    <td><a href="user?username=<c:out value="${reviewComment.getUser().getUserName()}"/>">${reviewComment.getUser().getUserName()}</a></td>
                    <td><c:out value="${reviewComment.isHelpful()}" /></td>
                    <td><a href="reviewcommentdelete?commentid=<c:out value="${reviewComment.getCommentId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
