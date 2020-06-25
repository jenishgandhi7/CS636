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

	<h1>Welcome to Invoice Page</h1>
	
	<table>
				  <tr>
					<th>Invoice Id</th>
					<th>Customer Name</th> 
					<th>Invoice Date</th>
					<th>Total Amount</th>
					<th>Status</th>
				  </tr>
				  <c:forEach var="Inv" items="${pending_Inv}">
					  <tr>
						<td>${Inv.invoiceId}</td>
						<td>${Inv.userFullName}</td>
						<td>${Inv.invoiceDate}</td>
						<td>${Inv.totalAmount}</td>
						
						<td style="background-color: #3cbd75;"><a
						href="<c:url value = '/adminController/processInvoices.html?invoiceId=${Inv.invoiceId}'/>" style= "color: #000;" >Process Invoice</a></td>
							
					  </tr>
				  </c:forEach>
								
								 
				  	</table>
				<div id ="btn"><a href="/displayReports.html">Download & Invoice Report</a></div>
				
	

</section>
</div>
<jsp:include page="/includes/footer.jsp" />