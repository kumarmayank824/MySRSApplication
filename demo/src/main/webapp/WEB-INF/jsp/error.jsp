<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="mainApp">
    <head>
		<title>Error Page | MyApplication</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="icon" type="image/png" href="images/logo/vit-logo.png"/>
		
		<link rel="stylesheet" href="css/w3.css">
		<link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<script src="js/angular.min.js" ></script>
		<script src="js/avatar/ionic-letter-avatar.js"></script>
		<script src="js/mainController.js" ></script>
		<script src="js/mainService.js" ></script>
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
		    <a href="/loginSuccess" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Click Here To Start</a>
		    <a href="/guideline-documents" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Check Guideline Documents</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">View Statistics</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">About Us</a>
		  </div>
		
		  <!-- Navbar on small screens -->
		  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="/loginSuccess" class="w3-bar-item w3-button w3-padding-large">Click Here To Start</a>
		    <a href="/guideline-documents" class="w3-bar-item w3-button w3-padding-large">Check Guideline Documents</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">View Statistics</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">About Us</a>
		  </div>
		</div>
		
		<div class="w3-row-padding w3-container" style="padding-top: 200px!important;">
		  <div style="text-align: center;">
		       <h1 class="w3-margin w3-jumbo">Oops! Something went wrong</h1>
		       <a href="/home" class="w3-button w3-black w3-large">Go Home</a>
		  </div>
		</div>
		
		<!-- Footer -->
		<footer class="w3-container w3-padding-64 w3-center w3-opacity">  
		  <p> &copy;2018 MyApplication </p>
		</footer>
		
	</body>
	
</html>