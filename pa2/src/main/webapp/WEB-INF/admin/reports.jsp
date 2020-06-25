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

		<h1>Welcome to Admin Page</h1>
		<table>
			<tr>
				<th>Invoice Id</th>
				<th>Customer Name</th>
				<th>Invoice Date</th>
				<th>Total Amount</th>
			<th>Status</th>
			</tr>
			<c:forEach var="Invs" items="${Invoice_List}">
				<tr>
					<td>${Invs.invoiceId}</td>
					<td>${Invs.userFullName}</td>
					<td>${Invs.invoiceDate}</td>
					<td>${Invs.totalAmount}</td>
			<%-- 			<c:choose>
										<c:when test='${Invs.isProcessed eq true}'>
											<td>Y</td>
										</c:when>
										<c:otherwise>
											<td>N</td>
										</c:otherwise>
									</c:choose> --%>
					

				</tr>
			</c:forEach>
</table>
			<br>
			<br>
			<br>
			<div></div>

		
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

	</section>
</div>
<jsp:include page="/includes/footer.jsp" />