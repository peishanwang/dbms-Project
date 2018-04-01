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
<title>Brand</title>
</head>
<body>
	<div class="container theme=showcase" role="main">
		<div class="jumbotron text-center">
			<h1>${messages.title}</h1>
		</div>
        <table class="table table-striped table-bordered">
            <tr>
                <th>BrandName</th>
                <th>Description</th>
                <th>Update</th>
                <th>Delete</th>
            </tr>
            <tr>
                <td><c:out value="${brand.getBrandName()}" /></td>
                <td><c:out value="${brand.getDescription()}" /></td>
                <td><a href="brandupdate?brandname=<c:out value="${brand.getBrandName()}"/>">Update</a></td>
                <td><a href="branddelete?brandname=<c:out value="${brand.getBrandName()}"/>">Delete</a></td>
            </tr>
       </table>
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>
