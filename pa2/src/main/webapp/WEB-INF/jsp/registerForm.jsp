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
<h1>Register</h1>
<h2>Create a user account or sign in</h2>

		<form method="get" action="register.html" id="register" >
			First name: <input type="text" name="firstName" required> 
			Last name: <input type="text" name="lastName" required> 
			Email Address: <input type="text" name="email" required>
			<input type="submit" value="Register">
		</form>
				
</section>
</div>

<jsp:include page="/includes/footer.jsp" />