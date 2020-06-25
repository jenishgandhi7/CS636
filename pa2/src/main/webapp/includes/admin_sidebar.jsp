<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside id = "sidecol">

<div class="sidenav" id="admin_side"> 
  	<form action="initDB.html" method="get">
		<input type="submit" value="Initialize DB"><br />
	</form>
	<form action="processInvoices.html" method="get">
		<input type="submit" value="Process Invoices"><br />
	</form>
	<form action="displayReports.html" method="get">
		<input type="submit" value="Invoice Reports"><br />
	</form>
	<form action="downloadReports.html" method="get">
		<input type="submit" value="Downloads Reports"><br />
	</form>	
 </div>
</aside>