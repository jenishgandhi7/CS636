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
	<section class="sidebarPlayarea" id="cart">
		<h1>Your cart</h1>
		<c:choose>
			<c:when test='${empty cart_Detail}'>
				<h4>
					Your Cart is empty.We are here to make it full!
					<h4>
						<br /> <br />
						<div id="btn">
							<a href="/catalog.html">Continue Shopping</a>
						</div>
			</c:when>
			<c:otherwise>
				<c:set var="Total_amount" value="${0}" />

				<table>
					<tr>
						<th>Product Code</th>
						<th>Product Description</th>
						<th>Qty</th>
						<th>Price</th>
						<th>Total Price</th>
					</tr>
					<c:forEach var="cart" items="${cart_Detail}">
						<tr>
							<td>${cart.code}</td>
							<td>${cart.description}</td>
							<td id="quantity_id" align="justify">
								<form action="<c:url value='/cart_update.html'  />" method="get">
									<input id="input_qty" type="text" value="${cart.quantity}"
										name="quantity"> <input type="hidden" name="id"
										value="${cart.productId}"> <input id="update_btn"
										type="submit" value="Update" name="update">
								</form>
							</td>

							<td>$ ${cart.price}</td>

							<td style="text-align: right;"><c:set var="Total_amount"
									value="${Total_amount+(cart.price * cart.quantity)}" />$
								${cart.price * cart.quantity}</td>

							<td id="quantity_id" align="justify">
								<form action="<c:url value='/cart_remove.html'  />" method="get" >
									<input type="hidden" name="removeId" value="${cart.productId}">
									<input id="update_btn" type="submit" value="Remove (&#215;)"
										name="remove" style="background-color: #bd3c3c;">
								</form>
							</td>
							<%-- <td style="background-color: #bd3c3c;"><a
								href="<c:url value = '/cart_remove.html?removeId=${cart.productId}'/>"
								style="color: #000;">Remove </a></td>
 --%>
						</tr>

					</c:forEach>

				</table>
				</br>
				</br>

				<table id="amount" style="float: right;">
					<tr>
						<th>Total Amount</th>
						<th>$ ${Total_amount}</th>
					</tr>
				</table>


				</br>
				</br>

				<div>
					<div id="btn">
						<a href="/catalog.html">Continue Shopping</a>
					</div>

					<div id="btn" class="right">
						<a href="<c:url value='/beginCheckout.html' />">Checkout</a>
					</div>
				</div>
			</c:otherwise>
		</c:choose>


	</section>
</div>

<jsp:include page="/includes/footer.jsp" />