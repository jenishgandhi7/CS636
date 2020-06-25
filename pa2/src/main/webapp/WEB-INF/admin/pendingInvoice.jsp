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

		<h2>Pending Reports</h2>

		<c:choose>
			<c:when test='${empty pending_Inv}'>
				<h4>
					No Pending Invoices
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
						<th>Process Invoice</th>
					</tr>
					<c:forEach var="Invs" items="${pending_Inv}">
						<tr>
							<td>${Invs.invoiceId}</td>
							<td>${Invs.userFullName}</td>
							<td>${Invs.invoiceDate}</td>
							<td>$ ${Invs.totalAmount}</td>
							<td style="background-color: #e62525;"><a
								href="<c:url value = '/adminController/processInvoice.html?invoiceId=${Invs.invoiceId}'/>"
								style="color: #000; text-decoration: underline;">Click Here for Approve</a></td>

						</tr>
					</c:forEach>

				</table>

			</c:otherwise>
		</c:choose>

	</section>
</div>
<jsp:include page="/includes/footer.jsp" />