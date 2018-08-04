<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="mainApp">
	<head>
	<title>Login</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="css/w3.css">
	<link rel="icon" type="image/png" href="images/icons/favicon.ico" />
	<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet" />
	<!-- <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"> -->
	<link rel="stylesheet" href="fonts/iconic/css/material-design-iconic-font.min.css">
	<link rel="stylesheet" href="css/util.css">
	<link rel="stylesheet" href="css/login.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
	
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	
	<script src="js/angular.min.js"></script>
	<script src="js/mainController.js"></script>
	<script src="js/mainService.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
	
	</head>
	<style>
	body, h1, h2, h3, h4, h5, h6 {
		font-family: "Lato", sans-serif
	}
	
	.w3-bar, h1, button {
		font-family: "Montserrat", sans-serif
	}
	
	.fa-anchor, .fa-coffee {
		font-size: 200px
	}
	</style>
	<body data-ng-controller="mainController">

	<!-- Navbar -->
	<div class="w3-top">
		<div class="w3-bar w3-red w3-card w3-left-align w3-large">
			<a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" ng-click="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a> 
			<a href="/home" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
			<a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">About Us</a>
			<a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Contact Us</a>
		</div>

		<!-- Navbar on small screens -->
		<div id="navDemo"
			class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">About Us</a> 
			<a href="#" class="w3-bar-item w3-button w3-padding-large">Contact Us</a>
		</div>
	</div>

	<div class="limiter">
		<div class="container-login100" style="padding:75px">
	        <div class="wrap-logo">
			  <div class="login-upper-div">
			  	<!-- <img class="app-logo" alt="" src="images/logo/logo.png"> -->
			  	<div class="signin-text">Sign up to app</div>
			  </div>
			</div>
			<div class="wrap-login100">
				<form:form action="/registerUser" method="post" modelAttribute="user">

					<c:if test="${confirmationMessage ne null}">
					        <span "class="alert alert-success">${confirmationMessage}</span>
				    </c:if>
				    <c:if test="${alreadyRegisteredMessage ne null}">
				       <script>
					       $.alert({
					    	    title: 'Sorry !',
					    	    icon: 'fas fa-ban',
					    	    content: 'There is already a user registered with the email provided.Please try with a different email.',
					    	    type: 'red',
					    	    boxWidth: '35%',
					    	    useBootstrap: false,
					    	    typeAnimated: true
					    	});
				       </script>
					        <%-- <span "class="alert alert-success">${alreadyRegisteredMessage}</span> --%>
				    </c:if>
					<div class="wrap-input100 validate-input m-b-23"
						data-validate="Username is reauired">
						<span class="label-input100">Username</span> <input
							class="input100" type="text" name="username"
							placeholder="Type your username" required> <span
							class="focus-input100" data-symbol="&#xf206;"></span>
					</div>
					<div class="wrap-input100 validate-input m-b-23" style="margin-bottom: 10px;"
						data-validate="Email is required">
						<span class="label-input100">Email Id</span> <input
							class="input100" type="email" name="email"
							placeholder="Type your Email Id" required> <span
							class="focus-input100" data-symbol="&#9993"></span>
					</div>
					
					<div class="checkbox" style="margin-bottom:15px;margin-top: 15px;">
						<input id="signInType" name="signInType" ng-model="isChecked" ng-change="checkSignInType(isChecked)" value="{{signInType}}" type="checkbox" style="margin-left: 10px;">
					    <span class="mySignUpCheckbox">{{signInType}} Sign In</span> 					  
					</div>
					
					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>
							<button type="submit" class="login100-form-btn">Sign up
								Now</button>
						</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form:form>
				<div class="flex-col-c p-t-15">
					<span class="txt1 p-b-17"> Already a member? <a
						href="/login" class="txt2">Login now</a>
					</span>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<footer class="w3-container w3-center">
		<!-- <div class="w3-xlarge w3-padding-32">
			<i class="fa fa-facebook-official w3-hover-opacity"></i> <i
				class="fa fa-instagram w3-hover-opacity"></i> <i
				class="fa fa-snapchat w3-hover-opacity"></i> <i
				class="fa fa-pinterest-p w3-hover-opacity"></i> <i
				class="fa fa-twitter w3-hover-opacity"></i> <i
				class="fa fa-linkedin w3-hover-opacity"></i>
		</div>
		<p>
			Powered by <a href="https://www.w3schools.com/w3css/default.asp"
				target="_blank">w3.css</a>
		</p> -->
		<p> &copy;2018 MyApplication </p>
	</footer>

</body>
</html>