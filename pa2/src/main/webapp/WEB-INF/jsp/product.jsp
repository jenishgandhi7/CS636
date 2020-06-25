<!DOCTYPE html>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/sidebar.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<head>
<title>Murach's Java Servlets and JSP</title>
<link rel="stylesheet" href="styles/main.css" type="text/css" />
</head>
<div class="main">
	<section class="sidebarPlayarea">
		
		<h1>${product.description}</h1>
		<p>Product Code: ${product.code}</p>
		<p>Discounted price &#36<i>${product.price}</i> </p>


		<br>
		<br> 
		<div><div id="btn"><a  href="<c:url value='/listen.html?productId=${product.id}' />">Explore
			the Album!</a> </div><br>	
		<div id="btn"><a href=" <c:url value ='/cart.html?code=${product.code}'/>">Add the album to Cart</a></div>
		</div>
	</section>
		
</div>

<jsp:include page="/includes/footer.jsp" />