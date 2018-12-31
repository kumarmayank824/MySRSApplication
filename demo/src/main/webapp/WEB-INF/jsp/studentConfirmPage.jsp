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
   <link rel="stylesheet"  href="fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>
   <link rel="stylesheet"  href="fonts/iconic/css/material-design-iconic-font.min.css"/>
   <link rel="stylesheet"  href="css/util.css"/>
   <link rel="stylesheet"  href="css/login.css"/>
   <link rel="stylesheet" href="css/jquery-confirm/jquery-confirm-3.3.0.min.css"/>
   
   <script src="vendor/jquery/jquery-3.2.1.min.js"></script>
   <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
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
	.password-progress {margin-top: 10px;margin-bottom: 0;}
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
				  	<div class="signin-text">Choose Password</div>
				  </div>
				</div>
				<div class="wrap-login100" style="margin-bottom: 25px;padding: 40px 30px;">
				    <p class="errorMessage" >${errorMessage}</p>
					<form action="/studentConfirm" method="post">
					        
					    <c:if test="${successMessage ne null}">
						   <input type="hidden" id="alertSuccessMessage" value="${successMessage}"/>
					    </c:if>
					    <c:if test="${failureMessage ne null}">
					       <input type="hidden" id="alertFailureMessage" value="${failureMessage}"/>
					    </c:if>
	                   
						<div class="wrap-input100 validate-input" style="margin-bottom:5px;" data-validate="Password is required">
							<span class="${ ( isPasswordNotValidError || isEmptyPasswordError ) ? 'label-input100 error' : 'label-input100' }">Password
							    <span class="showHidePasswordIcon" title="{{titlePassword}}"  ng-click="hideShowPassword()" ><i ng-class="isPasswordDanger ? 'fa fa-eye error' : 'fa fa-eye' " aria-hidden="true"></i></span>
							</span>
							<input class="input100" style="height:55px;" type="{{inputTypePassword}}" name="password" placeholder="Type your password" required>
							<span class="focus-input100" data-symbol="&#xf190;"></span>
						</div>
						
						<div class="wrap-input100 validate-input" style="margin-bottom:5px;" data-validate="Semester is required">
							<span class="${ isSemsterError ? 'label-input100 error' : 'label-input100' }">Semester</span>
							<input class="input100" list="semesterList" style="height:55px;" type="text" name="semester" placeholder="Choose your semester" value="${semester}" required>
							<span class="focus-input100" data-symbol="&#xf11b;"></span>
							<datalist id="semesterList">
							  <select>
								  <option value="V"/>
								  <option value="VI"/>
								  <option value="VII"/>
							  </select>
							</datalist>
						</div>
						
						<div class="wrap-input100 validate-input" style="margin-bottom:5px;" data-validate="Batch is required">
							<span class="${ isBatachError ? 'label-input100 error' : 'label-input100' }">Batch</span>
							<input class="input100" list="batchList" style="height:55px;" type="text" name="batch" placeholder="Choose your batch" value="${batch}" required>
							<span class="focus-input100" data-symbol="&#xf3e9;"></span>
							<datalist id="batchList">
							  <select>
								  <option value="Batch 1"/>
								  <option value="Batch 2"/>
								  <option value="Batch 3"/>
								  <option value="Batch 4"/>
								  <option value="Batch 5"/>
								  <option value="Batch 6"/>
								  <option value="Batch 7"/>
								  <option value="Batch 8"/>
								  <option value="Batch 9"/>
								  <option value="Batch 10"/>
							  </select>
							</datalist>
						</div>
						
						<div class="wrap-input100 validate-input" style="margin-bottom:5px;" data-validate="Semester is required">
							<span class="${ isCourseError ? 'label-input100 error' : 'label-input100' }">Course</span>
							<input class="input100" list="courseList" style="height:55px;" type="text" name="course" placeholder="Choose your course" value="${course}" required>
							<span class="focus-input100" data-symbol="&#xf174;"></span>
							<datalist id="courseList">
							  <select>
								  <option value="M.Tech (IT)"/>
								  <option value="M.Tech (VLSI)"/>
								  <option value="M.Tech (EDS)"/>
							  </select>
							</datalist>
						</div>
												
						<div class="container-login100-form-btn">
							<div class="wrap-login100-form-btn" style="margin: 5px auto;">
								<div class="login100-form-bgbtn"></div>
								<button type="submit" class="login100-form-btn">
									Save
								</button>
							</div>
						</div>
						
						<input type="hidden" name="token" value="${confirmationToken}" />
						
						<input type="hidden" name="signInType" value="${signInType}" />
						
						<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
									
	                </form>
				</div>
			</div>
		</div>
		
		<footer class="w3-container w3-center">  
		 <p> &copy;2018 MyApplication </p>
		</footer>
		
   </body> 
</html>