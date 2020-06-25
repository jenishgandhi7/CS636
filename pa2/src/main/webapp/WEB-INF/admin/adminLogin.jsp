<!DOCTYPE html>
<jsp:include page="/includes/header.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<title>Admin Login Page</title>
</head>
<div class="main">
	<section class="sidebarPlayarea" id="adminLogin">

	<h1>Admin Login </h1>
	
	<c:if test="${error=='Invalid Credentials'}" var="error"
		scope="request">
		<I>Invalid Credentials. Enter Correct Credentials</I>
	</c:if>

	<form action="adminWelcome.html" method="post" id="admin_login">
		Username:<input type="text" name="username" /> <br /> Password:<input
			type="password" name="password" /> <br /> <input type="submit"
			value="login" />
	</form>

	</section>
</div>
<jsp:include page="/includes/footer.jsp" />