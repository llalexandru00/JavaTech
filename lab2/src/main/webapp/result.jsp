<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Result</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
   <div class="container">
      <table class="table">
		   <thead>
		     <tr>
		       <th scope="col">#</th>
		       <th scope="col">Category</th>
		       <th scope="col">Key</th>
		       <th scope="col">Value</th>
		     </tr>
		   </thead>
		   <tbody>
	         <c:forEach var="record" items="${allRecords}">
	           <tr>
			       <th scope="row">${record.id}</th>
			       <td>${record.category.name}</td>
			       <td>${record.key}</td>
			       <td>${record.value}</td>
	           </tr>
	         </c:forEach>
		   </tbody>
      </table>
   </div>
</body>
</html>