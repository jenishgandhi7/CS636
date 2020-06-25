<!DOCTYPE html>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/admin_sidebar.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<title>Admin Menu</title>
</head>
<div class="main">
	<section class="sidebarPlayarea" id="admin_menu">

		<h2>Downloads Reports</h2>
		<c:choose>
			<c:when test='${empty Downloads_List}'>
				<h4>
					No Downloads available
					<h4>
						<br /> <br />
			</c:when>
			<c:otherwise>

				<table>
					<tr>
						<th>Coustmer Name</th>
						<th>Customer Email</th>
						<th>Product Code</th>
						<th>Track Title</th>
						<th>Download Date</th>
					</tr>
					<c:forEach var="download" items="${Downloads_List}">
						<tr>
							<td>${download.userFullName}</td>
							<td>${download.userEmail}</td>
							<td>${download.productCode}</td>
							<td>${download.trackTitle}</td>
							<td>${download.downloadDate}</td>


						</tr>
					</c:forEach>


				</table>
			</c:otherwise>
		</c:choose>
		<div></div>
	</section>
</div>
<jsp:include page="/includes/footer.jsp" />