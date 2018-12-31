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
   
   <link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
   <link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"/>
   <link rel="stylesheet"  href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
   <link rel="stylesheet"  href="fonts/iconic/css/material-design-iconic-font.min.css">
   <link rel="stylesheet"  href="css/util.css">
   <link rel="stylesheet"  href="css/login.css">
   <link rel="stylesheet" href="css/jquery-confirm/jquery-confirm-3.3.0.min.css">
   
   <script src="vendor/jquery/jquery-3.2.1.min.js"></script>
   <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
   <script src="vendor/select2/select2.min.js"></script>
   
   <script src="js/angular.min.js" ></script>
   <script src="js/avatar/ionic-letter-avatar.js"></script>
   <script src="js/mainController.js" ></script>
   <script src="js/mainService.js" ></script>
   <script src="js/jquery-confirm/jquery-confirm-3.3.0.min.js"></script>
   <script src="js/alert.js"></script>
   
   
  </head>
  <style>
	body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
	.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
	.fa-anchor,.fa-coffee {font-size:200px}
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
		  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">About Us</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">Contact Us</a>
		  </div>
		</div>
        
		<div class="limiter">
			<div class="container-login100">
				<div class="wrap-logo">
				  <div class="login-upper-div">
				  	<!-- <img class="app-logo" alt="" src="images/logo/logo.png"> -->
				  	<div class="signin-text">Sign in to app</div>
				  </div>
				</div>
				
				<div class="wrap-login100">
					<form action="/login" method="post">
					    
					    <p class="errorMessage" >${errorMessage}</p>    
					    <c:if test="${param.error ne null}">
					        <span style="color:red;margin-left:31%;font-size: 15px;">*Authentication Failed</span>
						</c:if>
						
					    <c:if test="${successMessage ne null}">
						   <input type="hidden" id="alertSuccessMessage" value="${successMessage}"/>
					    </c:if>
					    <c:if test="${failureMessage ne null}">
					       <input type="hidden" id="alertFailureMessage" value="${failureMessage}"/>
					    </c:if>
						
						<div class="wrap-input100 validate-input m-b-15 " data-validate = "Email is reauired">
							<span class="label-input100">Email</span>
							<input class="input100" type="text" name="email" placeholder="Type your email">
							<span class="focus-input100" data-symbol="&#9993"></span>
						</div>
	
						<div class="wrap-input100 validate-input" data-validate="Password is required">
							<span class="label-input100">Password
							    <span class="showHidePasswordIcon" title="{{titlePassword}}"  ng-click="hideShowPassword()" ><i ng-class="isPasswordDanger ? 'fa fa-eye error' : 'fa fa-eye' " aria-hidden="true"></i></span>
							</span>
							<input class="input100" type="{{inputTypePassword}}" name="password" placeholder="Type your password">
							<span class="focus-input100" data-symbol="&#xf190;"></span>
						</div>
						
						<div class="text-right p-t-8 p-b-15">
							<a href="/forgotPassword">
								Forgot password?
							</a>
						</div>
						
						<div class="container-login100-form-btn">
							<div class="wrap-login100-form-btn">
								<div class="login100-form-bgbtn"></div>
								<button type="submit" class="login100-form-btn">
									Login
								</button>
							</div>
						</div>
						
						<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
									
	                </form>
						<div class="txt1 text-center p-t-54 p-b-20">
							<span>
								Or Sign Up Using
							</span>
						</div>
	
						<div class="flex-c-m">
							<a href="#" class="login100-social-item bg1">
								<i class="fa fa-facebook"></i>
							</a>
	
							<a href="#" class="login100-social-item bg2">
								<i class="fa fa-twitter"></i>
							</a>
	
							<a href="#" class="login100-social-item bg3">
								<i class="fa fa-google"></i>
							</a>
						</div>
	
						<div class="flex-col-c ">
							<span class="txt1 p-b-17">
								Not a member?<a href="/registerUser" class="txt2">Sign up now</a>
							</span>
						</div>
				</div>
			</div>
		</div>
		
		<footer class="w3-container w3-center">  
		  <!-- <div class="w3-xlarge " style="    color: white;">
		    <i class="fa fa-facebook-official w3-hover-opacity"></i>
		    <i class="fa fa-instagram w3-hover-opacity"></i>
		    <i class="fa fa-snapchat w3-hover-opacity"></i>
		    <i class="fa fa-pinterest-p w3-hover-opacity"></i>
		    <i class="fa fa-twitter w3-hover-opacity"></i>
		    <i class="fa fa-linkedin w3-hover-opacity"></i>
		    <ul class="list-inline" style="font-size:16px ">
		    	<li>&copy; abc</li>
		    	<li>&copy; abc</li>
		    </ul>
		 </div>
		 <p style="color:white">&copy; abc</p> -->
		 <p> &copy;2018 MyApplication </p>
		</footer>
		
   </body> 
</html>