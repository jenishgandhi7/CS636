<!DOCTYPE html>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/sidebar.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<title>DB Error</title>
</head>
<div class="main">
	<section class="sidebarPlayarea" id="admin_menu">

	<h1>DB Error: ${error}</h1>
	<br> 
	<br> 
	<a href="adminWelcome.html"> Back to Admin Menu</a>
	
</section>
</div>
<jsp:include page="/includes/footer.jsp" />