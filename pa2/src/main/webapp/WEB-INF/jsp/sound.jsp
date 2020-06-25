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
		<h1>List of ${product.description}</h1>

		<br>
		<table>
			<tr>
			<tr>
				<th>Track Title</th>
				<th>Sample MP3 For Listen</th>
				<th>Link</th>
			</tr>
			</tr>
			<c:forEach var="tracks" items="${track}">
				<tr>
					<td>${tracks.title}</td>
					<td><audio controls>
							<source src='/sound/${product.code}/${tracks.sampleFilename}' />
						</audio></td>
					<td style="background-color: #3cbd75;"><a
						href="<c:url value = '/download.html?id=${tracks.id}'/>" style= "color: #000;" >Download Track &#8681;</a></td>
				</tr>
			</c:forEach>
			<tr></tr>
		</table>
	
		<br> <br>
				<div id="btn"><a href=" <c:url value ='/cart.html?code=${product.code}'/>">Add the album to Cart</a></div>

</div>

<jsp:include page="/includes/footer.jsp" />