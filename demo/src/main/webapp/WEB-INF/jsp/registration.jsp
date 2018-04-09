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

<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Montserrat">
<!-- <link rel="stylesheet"  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->

<link rel="icon" type="image/png" href="images/icons/favicon.ico" />
<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet" />
<link rel="stylesheet"
	href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="fonts/iconic/css/material-design-iconic-font.min.css">
<link rel="stylesheet" href="vendor/animate/animate.css">
<link rel="stylesheet" href="vendor/css-hamburgers/hamburgers.min.css">
<link rel="stylesheet" href="vendor/animsition/css/animsition.min.css">
<link rel="stylesheet" href="vendor/select2/select2.min.css">
<link rel="stylesheet" href="vendor/daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="css/util.css">
<link rel="stylesheet" href="css/login.css">

<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="vendor/animsition/js/animsition.min.js"></script>
<script src="vendor/bootstrap/js/popper.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="vendor/select2/select2.min.js"></script>
<script src="vendor/daterangepicker/moment.min.js"></script>
<script src="vendor/daterangepicker/daterangepicker.js"></script>
<script src="vendor/countdowntime/countdowntime.js"></script>
<script src="vendor/jquery/main.js"></script>

<script src="js/angular.min.js"></script>
<script src="js/mainController.js"></script>
<script src="js/mainService.js"></script>

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
			<a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red"
				href="javascript:void(0);" ng-click="myFunction()"
				title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a> <a
				href="/home" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
			<a href="#"
				class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">How
				To Upload</a> <a href="#"
				class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">How
				To Download</a> <a href="#"
				class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">About
				Us</a> <a href="#"
				class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Contact
				Us</a>
		</div>

		<!-- Navbar on small screens -->
		<div id="navDemo"
			class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
			<a href="#" class="w3-bar-item w3-button w3-padding-large">How To
				Upload</a> <a href="#" class="w3-bar-item w3-button w3-padding-large">How
				To Download</a> <a href="#"
				class="w3-bar-item w3-button w3-padding-large">About Us</a> <a
				href="#" class="w3-bar-item w3-button w3-padding-large">Contact
				Us</a>
		</div>
	</div>

	<div class="limiter">
		<div class="container-login100" style="background-color: #f44336;">
			<div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
				<form:form action="/registerUser" method="post" modelAttribute="user">

					<span class="login100-form-title p-b-30"> Sign up in seconds </span>
					<c:if test="${confirmationMessage ne null}">
					        <span "class="alert alert-success">${confirmationMessage}</span>
				    </c:if>
				    <c:if test="${alreadyRegisteredMessage ne null}">
					        <span "class="alert alert-success">${alreadyRegisteredMessage}</span>
				    </c:if>
					<div class="wrap-input100 validate-input m-b-23"
						data-validate="Username is reauired">
						<span class="label-input100">Username</span> <input
							class="input100" type="text" name="username"
							placeholder="Type your username" required> <span
							class="focus-input100" data-symbol="&#xf206;"></span>
					</div>
					<div class="wrap-input100 validate-input m-b-23"
						data-validate="Email is required">
						<span class="label-input100">Email Id</span> <input
							class="input100" type="email" name="email"
							placeholder="Type your Email Id" required> <span
							class="focus-input100" data-symbol="&#9993"></span>
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
	<footer class="w3-container w3-padding-64 w3-center w3-opacity">
		<div class="w3-xlarge w3-padding-32">
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
		</p>
	</footer>

</body>
</html>