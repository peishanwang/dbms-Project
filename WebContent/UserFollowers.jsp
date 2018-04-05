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
<title>Followers</title>
</head>
<body>
	<div class="container theme=showcase" role="main">
		<div class="jumbotron text-center">
			<h1>${messages.title}</h1>
			<h1><small>${messages.title2}</small></h1>
		</div>
		<br/>
	    <div id="followCreate">
	          <a class="btn btn-primary" 
	          href="followcreate?followee=<c:out value="${fn:escapeXml(param.username)}"/>">Add Follower</a>
	    </div>
	    <br/>
	    <table class="table table-striped table-bordered">
	        <tr>
	            <th>FollowId</th>
	            <th>Follower</th>
	            <th>Delete Follow</th>
	        </tr>
	        <c:forEach items="${follows}" var="follow" >
	            <tr>
	                <td><c:out value="${follow.getFollowId()}" /></td>
	                <td><a href="user?username=<c:out value="${follow.getFollower().getUserName()}"/>">${follow.getFollower().getUserName()}</a></td>
	                <td><a href="followdelete?followid=<c:out value="${follow.getFollowId()}"/>">Delete</a></td>
	            </tr>
	        </c:forEach>
	   </table>
   </div>
</body>
</html>
