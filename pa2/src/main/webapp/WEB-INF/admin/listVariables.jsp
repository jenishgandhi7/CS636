<!DOCTYPE html>
<jsp:include page="/includes/header.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<title>List Variables Page</title>
</head>
<div class="main">
	<section class="sidebarPlayarea" id="admin_menu">

<h1>Cookies in this request: </h1>
<!-- cookie is an implicit object of JSP, see pg. 217 -->
<c:forEach items="${cookie}" var="cook">
<!-- cook is a map entry, with key (cookie name) and value (cookie object) -->
	  <br>Cookie:	${cook.key} = ${cook.value.value}
</c:forEach>


<h1>Session vars in this session:</h1>
<!-- sessionScope is an implicit object of JSP, a map of session vars -->
<c:forEach items="${sessionScope}" var="v">
<!-- v is a map entry, with key (var name) and value (var object) -->
	  <br>Session var:	${v.key} = ${v.value} 
</c:forEach>
</section>
</div>
<jsp:include page="/includes/footer.jsp" />