<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/css/stylesheet.css">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<title>Ideas</title>
</head>
<body>
	<div class="container">
		<div class="jumbotron jumbotron-fluid">
			<div class="container">
				<div class="jumboText">
					<h1 class="display-4"><img src="/images/cntLogo.png" alt="Classic Nametags"></h1>
					<c:choose>
						<c:when test="${sessionScope.permissions == 'admin'}">
							<p>Welcome, ${sessionScope.userName } (Admin Permissions)</p>
							<a href="/orders/items/new">New Order</a> | <a href="/orders/my_orders">My Order History</a> | <a href="/orders/all_orders">Order History</a> | <a href="/products">Product Management</a> | <a href="/orders/queue">Open Order Queue</a> | <a href="#">Admin Tools</a> | <a href="/logout">Logout</a>
						</c:when>
						<c:when test="${sessionScope.permissions == 'employee'}">
							<p>Welcome, ${sessionScope.userName } (Employee Permissions)</p>
							<a href="/orders/items/new">New Order</a> | <a href="/orders/my_orders">My Order History</a> | <a href="/orders/all_orders">Order History</a> | <a href="/products">Product Management</a> | <a href="/orders/queue">Open Order Queue</a> | <a href="/logout">Logout</a>
						</c:when>
						<c:when test="${sessionScope.permissions == 'customer' && sessionScope.loggedIn == true}">
							<p>Welcome, ${sessionScope.userName }</p>
							<a href="/orders/items/new">New Order</a> | <a href="/orders/my_orders">My Order History</a> | <a href="/logout">Logout</a>
						</c:when>
						<c:otherwise>
							<form action="/login" method="post">
								<input name="email" type="email" placeholder="email"> <input
									name="password" type="password" placeholder="password">
								<button>Login</button>
							</form>
							<a href="/register">Register</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>


		<hr>


		<jsp:doBody />


		<div id="footer">
			<hr>
			<p>View our Privacy Policy - ©2021 Q.R. Zed Engraving (Other
				Footer stuff here)</p>
		</div>
	</div>
</body>
</html>