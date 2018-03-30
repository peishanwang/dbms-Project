<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>ProductId</th>
                <th>ProductName</th>
                <th>Description</th>
                <th>Brand</th>
                <th>Price</th>
                <th>Questions</th>
                <th>Reviews</th>
                <th>Recommendations</th>
                <th>Delete Product</th>
                <th>Update Product</th>
            </tr>
            <tr>
                <td><c:out value="${product.getProductId()}" /></td>
                <td><c:out value="${product.getProductName()}" /></td>
                <td><c:out value="${product.getDescription()}" /></td>
                <td><a href="brand?brandname=<c:out value="${product.getBrand().getBrandName()}"/>">${product.getBrand().getBrandName()}</a></td>
                <td><c:out value="${product.getPrice()}" /></td>
                <td><a href="questions?productid=<c:out value="${product.getProductId()}"/>">Questions</a></td>
                <td><a href="reviews?productid=<c:out value="${product.getProductId()}"/>">Reviews</a></td>
                <td><a href="recommendations?productid=<c:out value="${product.getProductId()}"/>">Recommendations</a></td>
                <td><a href="productdelete?productid=<c:out value="${product.getProductId()}"/>">Delete</a></td>
                <td><a href="productupdate?productid=<c:out value="${product.getProductId()}"/>">Update</a></td>
            </tr>

       </table>
</body>
</html>
