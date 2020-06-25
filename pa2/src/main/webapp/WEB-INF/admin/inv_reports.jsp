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

		<h2>Invoice Reports</h2>
		<c:choose>
			<c:when test='${empty Invoice_List}'>
				<h4>
					No Invoices available
					<h4>
						<br /> <br />
			</c:when>
			<c:otherwise>

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
							<td>$ ${Invs.totalAmount}</td>
							<c:choose>
								<c:when test='${Invs.processed eq true}'>
									<td>Y</td>
								</c:when>
								<c:otherwise>
									<td>N</td>
								</c:otherwise>
							</c:choose>

						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
		<br> <br> <br>
		<div></div>

		<div></div>
	</section>
</div>
<jsp:include page="/includes/footer.jsp" />