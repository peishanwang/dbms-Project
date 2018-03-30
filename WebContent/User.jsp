<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>UserName</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Followers</th>
                <th>Followings</th>
                <th>Reviews</th>
                <th>Recommendations</th>
                <th>Favorite</th>
                <th>Update</th>
                <th>Delete</th>
            </tr>
            <tr>
                <td><c:out value="${user.getUserName()}" /></td>
                <td><c:out value="${user.getFirstName()}" /></td>
                <td><c:out value="${user.getLastName()}" /></td>
                <td><a href="followers?username=<c:out value="${user.getUserName()}"/>">Followers</a></td>
                <td><a href="followings?username=<c:out value="${user.getUserName()}"/>">Followings</a></td>
                <td><a href="reviews?username=<c:out value="${user.getUserName()}"/>">Reviews</a></td>
                <td><a href="recommendations?username=<c:out value="${user.getUserName()}"/>">Recommendations</a></td>
                <td><a href="brand?brandname=<c:out value="${user.getUserName()}"/>">Favorite</a></td>
                <td><a href="userdelete?username=<c:out value="${user.getUserName()}"/>">Delete</a></td>
                <td><a href="userupdate?username=<c:out value="${user.getUserName()}"/>">Update</a></td>
            </tr>

       </table>
</body>
</html>
