<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script>
	function confirmDelete() {
		return confirm("Are u sure u want to delete?")
	}
</script>

<title>View Contacts</title>
</head>
<body>
	<a href="loadForm"> +Add New Contacts</a>
	<table border="1">
		<thead>
			<tr>
				<th>S.No</th>
				<th>Name</th>
				<th>Email</th>
				<th>Number</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${contacts}" var="c" varStatus="count">
				<tr>
					<td>${count.index+1}</td>
					<td>${c.contactName}</td>
					<td>${c.contactEmail}</td>
					<td>${c.contactNumber}</td>
					<td><a href="editContact?cid=${c.contactId}">Edit</a> <a
						href="deleteContact?cid=${c.contactId}"
						onclick="return confirmDelete()">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${currPno >1}">
		<a href="viewContacts?pno=${currPno -1}">Previous</a>

	</c:if>
	<c:forEach begin="1" end="${tp}" var="pageNo">

		<c:choose>

			<c:when test="${currPno==pageNo}">
			${pageNo}
			</c:when>
			<c:otherwise>
				<a href="viewContacts?pno=${pageNo}">${pageNo}</a>
			</c:otherwise>

		</c:choose>

	</c:forEach>
	<c:if test="${currPno < tp}">
		<a href="viewContacts?pno=${currPno+1}">Next</a>
	</c:if>
</body>
</html>