<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "t" tagdir = "/WEB-INF/tags" %>
<!DOCTYPE html>
<html>

<head>
<link href="../../dist/css/bootstrap.min.css" rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<!-- Custom styles for this template -->
<link href="signin.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/css/stylesheet.css">
<meta charset="ISO-8859-1">
<title>Classic Nametags: Open Orders</title>
</head>
<body>

	<t:nav>
		<div class="container">
			<div class="row">
				<div class="col-12">
					<h2>Order History</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<table class="table">
						<thead>
							<tr>
								<th>Order number</th>
								<th>Product</th>
								<th>Color</th>
								<th>Lines</th>
								<th>Quantity</th>
								<th>Item Status</th>
								<th>Order Status</th>
								<th>Due date</th>
								
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${allOrders }" var="order">
								<c:forEach items="${order.items}" var="item">
									<tr>
										<td><a href="/orders/${order.id}/view/${order.orderNumber}">${order.orderNumber}</a></td>
										<td>${item.itemProduct.type }</td>
										<td>${item.itemColor.colorName }</td>
										<td>
											<p>Line 1: ${item.line1 }</p>
											<p>Line 2: ${item.line2 }</p>
										</td>
										<td>${item.quantity }</td>
										<td>${item.itemStatus.description }</td>
										<td>${order.orderStatus.description}</td>
										<td>${order.dueDateString }</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>					



				</div>
			</div>

		</div>
	</t:nav>
</body>
</html>