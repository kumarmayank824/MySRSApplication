<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="mainApp">
    <head>
		<title>My App</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/w3.css">
		<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
		<script src="js/chart/highcharts.js"></script>
		<script src="js/angular.min.js" ></script>
		<script src="js/avatar/ionic-letter-avatar.js"></script>
		<script src="js/mainController.js" ></script>
		<script src="js/mainService.js" ></script>
		<script src="js/chart/chart.js"></script>
	</head>
	<style>
		body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
		.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
		.fa-anchor,.fa-coffee {font-size:200px}
		.sign{float:right}
	</style>
	
	<body data-ng-controller="mainController" data-ng-init="init()">
	
		<!-- Navbar -->
		<div class="w3-top">
		  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
		    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" ng-click="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
		    <a href="/home" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
		    <a href="/loginSuccess" class="w3-bar-item w3-button w3-padding-large">Click Here To Start</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">About Us</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Contact Us</a>
		  </div>
		
		  <!-- Navbar on small screens -->
		  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">About Us</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">Contact Us</a>
		  </div>
		</div>
		
		<!-- First Grid -->
		<div class="w3-row-padding w3-container">
			  <c:if test="${isSubmissionAllowed}">
			     <div style="padding-top:75px!important;">
			       <marquee><p class="marquee w3-amber">${marqueeMessage}</p></marquee>
			       <div class="w3-content">
				     <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
				   </div>
			     </div>  
			  </c:if>
			  <c:if test="${!isSubmissionAllowed}">
			     <div class="w3-content" style="padding-top:120px!important;">
			       <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
			     </div>
			  </c:if>
		 </div>
		
		<!-- Footer -->
		<footer class="w3-container w3-center w3-opacity" style="padding-top:64px!important;">  
		   <p> &copy;2018 MyApplication </p>
		</footer>
		
	</body>
	
</html>