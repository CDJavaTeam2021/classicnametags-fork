<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>

<head>
<link href="../../dist/css/bootstrap.min.css" rel="stylesheet">
  	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="signin.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/stylesheet.css">
<meta charset="ISO-8859-1">
<title>Classic Nametags: Register Here!</title>
</head>
<body>
	<div class="container">
		<h1>Classic Nametags</h1>
		<hr>

		<section class="vh-100" style="background-color: #eee;">
  <div class="container h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-lg-12 col-xl-11">
        <div class="card text-black" style="border-radius: 25px;">
          <div class="card-body p-md-5">
            <div class="row justify-content-center">
              <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">New Customer Registration</p>

				<form:form method="POST" action="/register" modelAttribute="newUser" class="mx-1 mx-md-4">
                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                    <div class="form-outline flex-fill mb-0">
                     
					<form:label path="userName" class="form-label">Name:</form:label>
					<p class="error"><form:errors path="userName"/></p>
					<form:input path="userName" id="form3Example1c" class="form-control"/>
                      
                    </div>
                  </div>

                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                    <div class="form-outline flex-fill mb-0">
                    	
                    <form:label path="userEmail" class="form-label">Email:</form:label>
					<p class="error"><form:errors path="userEmail"/></p>
					<form:input path="userEmail" id="form3Example3c" class="form-control"/>
                    
                    

                    </div>
                  </div>

                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
                    <div class="form-outline flex-fill mb-0">
                      
                      	<form:label path="password" class="form-label">Password:</form:label>
						<p class="error"><form:errors path="password"/></p>
						<form:input type="password" path="password" id="form3Example4c" class="form-control"/>
                      
                      
                    </div>
                  </div>

                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                    <div class="form-outline flex-fill mb-0">
                    
                    	<form:label path="confirmPassword" class="form-label">Confirm Password:</form:label>
						<span class="error"><form:errors path="confirmPassword"/></span>
						<form:input type="password" id="form3Example4cd" class="form-control" path="confirmPassword"/>

                    </div>
                  </div>
                  <h3>Shipping and Contact Info</h3>
                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                    <div class="form-outline flex-fill mb-0">
                    
                    	<form:label path="phone" class="form-label">Phone:</form:label>
						<span class="error"><form:errors path="phone"/></span>
						<form:input id="form3Example4cd" class="form-control" path="phone"/>

                    </div>
                  </div>
                  
                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                    <div class="form-outline flex-fill mb-0">
                    
                    	<form:label path="street1" class="form-label">Street 1:</form:label>
						<span class="error"><form:errors path="street1"/></span>
						<form:input id="form3Example4cd" class="form-control" path="street1"/>

                    </div>
                  </div>
                  
                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                    <div class="form-outline flex-fill mb-0">
                    
                    	<form:label path="street2" class="form-label">Street 2:</form:label>
						<span class="error"><form:errors path="street1"/></span>
						<form:input id="form3Example4cd" class="form-control" path="street1"/>

                    </div>
                  </div>
                  
                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                    <div class="form-outline flex-fill mb-0">
                    
                    	<form:label path="city" class="form-label">City:</form:label>
						<span class="error"><form:errors path="city"/></span>
						<form:input id="form3Example4cd" class="form-control" path="city"/>

                    </div>
                  </div>
                  
                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                    <div class="form-outline flex-fill mb-0">
                    
                    	<form:label path="state" class="form-label">State:</form:label>
						<span class="error"><form:errors path="state"/></span>
						<form:input id="form3Example4cd" class="form-control" path="state"/>

                    </div>
                  </div>
                  
                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                    <div class="form-outline flex-fill mb-0">
                    
                    	<form:label path="zip" class="form-label">Zip:</form:label>
						<span class="error"><form:errors path="zip"/></span>
						<form:input id="form3Example4cd" class="form-control" path="zip"/>

                    </div>
                  </div>

                 

                  <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                    <button class="btn btn-primary btn-lg">Register</button>
                  </div>

                </form:form>
             
                
               

              </div>
              <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                <img src="https://mdbootstrap.com/img/Photos/new-templates/bootstrap-registration/draw1.png" class="img-fluid" alt="Sample image">
                

              </div>
              
            </div>
            <p>Already a User? <a href="/login">Login Here!</a></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
	</div>
</body>
</html>