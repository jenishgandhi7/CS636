<!DOCTYPE html>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/sidebar.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<head>
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<div class="main">
			<section class ="sidebarPlayarea">
				<h1>List of Music Catalog</h1>
				<table>
				  <tr>
					<th>Product Code</th>
					<th>Product Description</th> 
					<th>Product Price</th>
				  </tr>
				  <c:forEach var="catalog" items="${catalogs}">
					  <tr>
						<td>${catalog.code}</td>
						<td><a href ="<c:url value = '/product.html?productCode=${catalog.code}'/>" style="color: #000; text-decoration: underline;">${catalog.description}</a></td>
						<td>$${catalog.price}</td>
					  </tr>
				  </c:forEach>
				</table>
			</section>
	</div>

<jsp:include page="/includes/footer.jsp" />