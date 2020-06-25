<!DOCTYPE html>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/sidebar.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/includes/footer.jsp" />
<head>
<title>Music Store :: Invoice</title>
</head>
<div class="main">
	<section class="sidebarPlayarea">
		<h1>Invoice</h1>
		<h2>Thanks for your order!</h2>
		<table>
			<tr>
				<th>Invoice number:</th>
				<th>${invoice.invoiceId}</th>
			</tr>
					
						
			<tr>
				<th>Total Date:</th>
				<th> ${invoice.invoiceDate}</th>
			</tr>
			<tr>
			<th>Customer Name:</th>
				<th> ${invoice.userFullName}</th>
			
			</tr>
			<tr>
				<th>Total price:</th>
				<th>$ ${invoice.totalAmount}</th>
			</tr>

			
			
		</table>
		
		
	</section>
</div>
<jsp:include page="/includes/footer.jsp" />
