<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Input</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>

   <div class="container">
	   <form action="controller" method="post" onsubmit="submitFnc()">
	      <div class="form-group">
		      <label for="category">Category</label><br>
		      <select id="category" name="category" class="form-control">
		         <c:forEach var="category" items="${allCategories}">
					   <option value="${category.id}" ${defaultCategory == category.id ? "selected" : ""}>${category.name}</option>
					</c:forEach>
				</select><br>
				<label for="key">Key</label><br>
				<input id="key" name="key" type="text" class="form-control" maxlength="50"/><br>
		      <label for="value">Value</label><br>
		      <textarea id="value" name="value" class="form-control" maxlength="1000"></textarea><br>
            <label for="captcha">CAPTCHA</label><br>
            <img src="captcha"/>
            <input id="captcha" name="captcha" type="text" class="form-control"></input><br>
	      </div>
	      <input type="submit" class="btn btn-primary"/>
	   </form> 
   </div>

</body>
</html>