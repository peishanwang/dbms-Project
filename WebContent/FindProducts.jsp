<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a Product</title>
</head>
<body>
	<form action="findproducts" method="get">
		<h1>Search for a Product by ProductName</h1>
		<p>
			<label for="productname">ProductName</label>
			<input id="productname" name="productname" value="${fn:escapeXml(param.productname)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="userCreate"><a href="usercreate">Create User</a></div>
	<div id="productCreate"><a href="productcreate">Create Product</a></div>
	<div id="brandCreate"><a href="brandcreate">Create Brand</a></div>
	<br/>
	<h1>Matching Products</h1>
        <table border="1">
            <tr>
                <th>ProductId</th>
                <th>ProductName</th>
            </tr>
            <c:forEach items="${products}" var="product" >
                <tr>
                    <td><a href="product?productid=<c:out value="${product.getProductId()}" />">${product.getProductId()}</a></td>
                    <td><c:out value="${product.getProductName()}"/></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
