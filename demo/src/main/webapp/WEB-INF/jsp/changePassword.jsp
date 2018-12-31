<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="mainApp">
	<head>
	<title>Change Password</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="css/w3.css">
	<link rel="icon" type="image/png" href="images/icons/favicon.ico" />
	<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="fonts/iconic/css/material-design-iconic-font.min.css">
	<link rel="stylesheet" href="css/util.css">
	<link rel="stylesheet" href="css/login.css">
	<link rel="stylesheet" href="css/jquery-confirm/jquery-confirm-3.3.0.min.css">
	
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	
	<script src="js/angular.min.js"></script>
	<script src="js/avatar/ionic-letter-avatar.js"></script>
	<script src="js/mainController.js"></script>
	<script src="js/mainService.js"></script>
    <script src="js/jquery-confirm/jquery-confirm-3.3.0.min.js"></script>
	<script src="js/alert.js"></script>
	
	</head>
	<style>
		body, h1, h2, h3, h4, h5, h6 {font-family: "Lato", sans-serif}
		.w3-bar, h1, button {font-family: "Montserrat", sans-serif}
		.fa-anchor, .fa-coffee {font-size: 200px}
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
			<div class="container-login100">
		        <div class="wrap-logo">
				  <div class="login-upper-div">
				  	<div class="signin-text">Change Password</div>
				  </div>
				</div>
				<div class="wrap-login100" style="width:430px;">
				    
				        <h2 class="forgotPassTitle">Please choose a new password</h2>
				        
				        <form action="/forgot-password-reset" method="post">
						    
						    <c:if test="${successMessage ne null}">
						        <input type="hidden" id="alertSuccessMessage" value="${successMessage}"/>
					        </c:if>
					        <c:if test="${failureMessage ne null}">
					           <input type="hidden" id="alertFailureMessage" value="${failureMessage}"/>
					        </c:if>
						    
							<div class="wrap-input100 validate-input" data-validate="Password is required">
								<span class="${ isNewPasswordError ? 'label-input100 error' : 'label-input100' }">New Password
								   <span class="showHidePasswordIcon" title="{{titlePassword}}"  ng-click="hideShowPassword()" ><i ng-class="isPasswordDanger ? 'fa fa-eye error' : 'fa fa-eye' " aria-hidden="true"></i></span>
								</span>
								<input class="input100" type="{{inputTypePassword}}" ng-model="forgotPassword" ng-change="checkPasswordRules(forgotPassword)" name="newPassword" placeholder="Type your password">
								<input type="hidden" name="email" value="${email}">
								<input type="hidden" name="signInType" value="${signInType}">
								<input type="hidden" name="token" value="${token}">
								<span class="focus-input100" data-symbol="&#xf190;"></span>
						    </div> 
							
							<p style="text-align:left;margin:1em 0;font-family:unset;margin-top: 25px;">Password requirements:</p>
					        
					        <ul style="text-align:left;margin:0.6em">
					           <li ng-class="forgotPassCheckClass">
					             <i  class="{{ condition1 ? 'fa fa-check forgotPassSuccessFontAwesome' : 'fa fa-times forgotPassErrorFontAwesome' }}" aria-hidden="true"></i> must be at least 8 characters
					           </li>
					           <li ng-class="forgotPassCheckClass">
					             <i class="{{ condition2 ? 'fa fa-check forgotPassSuccessFontAwesome' : 'fa fa-times forgotPassErrorFontAwesome' }}" aria-hidden="true"></i> must contains a minimum of 1 numeric character [0-9]
					           </li>
					           <li ng-class="forgotPassCheckClass">
					             <i class="{{ condition3 ? 'fa fa-check forgotPassSuccessFontAwesome' : 'fa fa-times forgotPassErrorFontAwesome' }}" aria-hidden="true"></i> must contains a minimum of 1 special character [@#$%^& only.]
					           </li>
					        </ul>
							
							<div class="container-login100-form-btn">
								<div class="wrap-login100-form-btn">
									<div class="login100-form-bgbtn"></div>
									<button type="submit" ng-disabled="isDisabled" class="login100-form-btn">Change password</button>
								</div>
							</div>
							
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
								
						</form>
						
						<h5 ng-if="isDisabled" class="buttonDisabledText" > * Button is disabled </h5>
						
						<div class="flex-col-c p-t-15">
							<span class="txt1 p-b-17" style="padding-bottom:4px"> Already a member? <a
								href="/login" class="txt2">Login now</a>
							</span>
						</div>
							
				</div>
			</div>
		</div>
	
		<!-- Footer -->
		<footer class="w3-container w3-center">
			<p> &copy;2018 MyApplication </p>
		</footer>

	</body>
</html>