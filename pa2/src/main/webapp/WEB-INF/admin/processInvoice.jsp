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

	
	<table>
				  <tr>
					<th>Invoice Id</th>
					<th>Customer Name</th> 
					<th>Invoice Date</th>
					<th>Total Amount</th>
					<th>Status</th>
				  </tr>
				  <c:forEach var="Invs" items="${pendingInvoice}">
					  <tr>
						<td>${Invs.invoiceId}</td>
						<td>${Invs.userFullName}</td>
						<td>${Invs.invoiceDate}</td>
						<td>$ ${Invs.totalAmount}</td>
										
					  </tr>
				  </c:forEach>
								 
				  	</table>
				
	

</section>
</div>
<jsp:include page="/includes/footer.jsp" />