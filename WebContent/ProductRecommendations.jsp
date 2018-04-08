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
<title>Recommendations</title>
</head>
<body>
<div class="container theme=showcase" role="main">
     <div class="jumbotron">
      <h1>Recommendations for Product</h1>
    </div>

	<div id="recommendationCreate">
		<a class="btn btn-primary" href="recommendationcreate?productid=<c:out value="${fn:escapeXml(param.productid)}"/>">Create Recommendation</a></div>
	<br/>
	<h1>Matching Recommendation</h1>
        <table class="table table-striped table-bordered">
            <tr>
                <th>RecommendationId</th>
                <th>UserName</th>
                <th>ProductName</th>
          
            </tr>
            <c:forEach items="${recommendations}" var="recommendation" >
                <tr>
                    <td><c:out value="${recommendation.getRecommendationId()}" /></td>
                    <td><c:out value="${recommendation.getUser().getUserName()}" /></td>
                    <td><c:out value="${recommendation.getProduct().getProductName()}" /></td>
                </tr>
            </c:forEach>
       </table>
</div>
</body>
</html>