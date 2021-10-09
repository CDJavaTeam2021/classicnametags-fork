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
					<h2>New Item</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<form action="/orders/items/new" method="post">
						<label for="product">Choose a Product:</label>
						<select name="itemProduct" id="product">
							<c:forEach items="${allProducts}" var="product">
								<option value="${product.id}">${product.type} $${product.price}0</option>
							</c:forEach>
						</select>
						<input name="line1" placeholder="Line 1">
						<input name="line2" placeholder="line 2">
						<select name="itemColor"><!-- TODO build asynchronous function to pull only available colors -->
							<c:forEach items="${allColors }" var="color">
								<option value="${color.id }">${color.colorName }</option>
							</c:forEach>
						</select>
						<label for="quantity">Quantity</label>
						<input type="number" min="1" value="1" step="1" name="quantity" id="quantity">
						<button>Add to Cart</button> 
					</form>
				</div>
				<div class="col-6">
				<h3>Preview</h3>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<table class="table">
						<thead>
							<tr><th>Item</th><th>Text</th><th>Color</th><th>Quantity</th><th>Price</th></tr>
						</thead>
						<tbody>
							<c:forEach items="${sessionScope.myCart }" var="item">
								<tr>
									<td>${item.itemProduct.type }</td>
									<td><p>Line 1: ${item.line1}</p><p>Line 2: ${item.line2}</p></td>
									<td>${item.itemColor.colorName}</td>
									<td>${item.quantity}</td>
									<td>$${item.subtotal }0</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<p>Subtotal: $${sessionScope.cartTotal }0</p>
					<form action="/orders/items/checkout" method="post">
						<button>Checkout</button>
					</form>
				</div>
			</div>
		</div>
	</t:nav>
</body>
</html>