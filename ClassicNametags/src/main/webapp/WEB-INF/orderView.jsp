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
<title>Classic Nametags: New Order</title>
</head>
<body>

	<t:nav>
		<div class="container">
			<div class="row">
				<div class="col-12">
					<h2>Order ${thisOrder.orderNumber}</h2>
					<h3>Promise Time: ${thisOrder.dueDateString }</h3>
					<h3>Order Status: ${thisOrder.orderStatus.description }</h3>
					<c:choose>
						<c:when test="${sessionScope.permissions == 'admin' || sessionScope.permissions == 'employee'}">
							<form action="/orders/${thisOrder.id }/confirm" method="post">
								<input type="datetime-local" value="${htmlDueDate }" name="confirmDue">
								<button>Confirm Order</button>
							</form>
						</c:when>
					</c:choose>
				</div>
			</div>

			<div class="row">
				<div class="col-12">
					<table class="table">
						<thead>
							<tr><th>Item</th><th>Text</th><th>Color</th><th>Quantity</th><th>Price</th><th>Action</th></tr>
						</thead>
						<tbody>
							<c:forEach items="${thisOrder.items }" var="item">
								<tr>
									<td>${item.itemProduct.type }</td>
									<td><p>Line 1: ${item.line1}</p><p>Line 2: ${item.line2}</p></td>
									<td>${item.itemColor.colorName}</td>
									<td>${item.quantity}</td>
									<td>$${item.subtotal }0</td>
									<td>
										<c:choose>
												<c:when test="${item.itemStatus.id < 4 }">
													<form action="/orders/item/${item.id}/complete" method="post">
														<input type="hidden" name="origin" value="${thisOrder.id }">
														<button>Complete Item</button>
													</form>
												</c:when>
											</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<p>Subtotal: $${thisOrder.orderTotal}0</p>
				</div>
			</div>
		</div>
	</t:nav>
</body>
</html>