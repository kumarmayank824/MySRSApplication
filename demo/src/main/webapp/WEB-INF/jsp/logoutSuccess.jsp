<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="mainApp">
    <head>
		<title>Logout Success</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/w3.css">
		<link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<script src="js/angular.min.js" ></script>
		<script src="js/mainController.js" ></script>
		<script src="js/mainService.js" ></script>
	</head>
	<style>
		body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
		.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
		.fa-anchor,.fa-coffee {font-size:200px}
	</style>
	
	<body data-ng-controller="myController" data-ng-init="init()">
	
		<!-- Navbar -->
		<div class="w3-top">
		  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
		    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" ng-click="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
		    <a href="/home" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">How To Upload</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">How To Download</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">About Us</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Contact Us</a>
		  </div>
		
		  <!-- Navbar on small screens -->
		  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">How To Upload</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">How To Download</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">About Us</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">Contact Us</a>
		  </div>
		</div>
		
		<!-- Header -->
		<header class="w3-container w3-red w3-center" style="padding:128px 16px">
		  <h1 class="w3-margin w3-jumbo">You have been successfully logged out</h1>
		  <a href="/login" class="w3-button w3-black w3-padding-large w3-large w3-margin-top">Click! To Start Again</a>
		</header>
		
		<!-- Footer -->
		<footer class="w3-container w3-padding-64 w3-center w3-opacity">  
		  <div class="w3-xlarge w3-padding-32">
		    <i class="fa fa-facebook-official w3-hover-opacity"></i>
		    <i class="fa fa-instagram w3-hover-opacity"></i>
		    <i class="fa fa-snapchat w3-hover-opacity"></i>
		    <i class="fa fa-pinterest-p w3-hover-opacity"></i>
		    <i class="fa fa-twitter w3-hover-opacity"></i>
		    <i class="fa fa-linkedin w3-hover-opacity"></i>
		 </div>
		 <p>Powered by <a href="#" target="_blank">BootStrap+Spring Boot</a></p>
		</footer>
		
	</body>
	
</html>