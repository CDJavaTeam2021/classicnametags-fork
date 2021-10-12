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
<title>Classic Nametags: Register Here!</title>
</head>
<body>

	<t:nav>
		<div class="container">
			<h1>Product Management</h1>
			<hr>
			<table>
				<thead>
					<tr>
						<th>New Product</th><th>New Color Combination Laminate</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<form action="/products/new" method="post">
								<input name="type" placeholder="Product Name">
								<input name="price" placeholder="Price" type="number" min="0.01" step="0.01">
								<input name="makeMinutes" placeholder="Time to make in minutes" type="number" step="1">
								<label for="colors">Pick available color(s):</label>
								<select name="colors" multiple id="colors">
									<c:forEach items="${allColors}" var="color">
										<option value="${color.id}">
											${color.colorName }
										</option>
									</c:forEach>
								</select>
								<button>Create New Product</button>
							</form>
						</td>
						<td>
							<form action="products/colors/new" method="post">
								<input name="backgroundColor" placeholder="Body Color">
								<input name="letterColor" placeholder="Letter Color">
								<input name="frameColor" placeholder="Frame Color (from license plate frames only)">
								<button>Create New Stock Color</button>
							</form>
						</td>
					</tr>
				</tbody>
			</table>
			<h2>Products</h2>
			<table>
				<thead>
					<tr>
						<th>Description</th><th>Price</th><th>Time to Make in Minutes</th><th>Available Colors</th><th>Edit/Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${products }" var="product">
						<tr>
							<td>${product.type}</td><td>$${product.price }0</td><td>${product.makeMinutes }</td>
							<td>
								<c:forEach items="${product.colors }" var="itemColor">
								${itemColor.colorName}
								</c:forEach>
							</td>
							<td><a href="/products/${product.id}/edit">Edit</a> | <a href="/products/${product.id }/delete">Delete</a>
							
						</tr>
					</c:forEach>
				</tbody>
			
			</table>


		</div>
	</t:nav>

</body>
</html>