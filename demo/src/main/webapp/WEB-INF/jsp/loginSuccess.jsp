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
		<link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<script src="js/angular.min.js" ></script>
		<script src="js/mainController.js" ></script>
		<script src="js/mainService.js" ></script>
	</head>
	<style>
		body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
		.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
		.fa-anchor,.fa-coffee {font-size:200px}
		.navbar {
		    overflow: hidden;
		    /* background-color: #333; */
		    font-family: Arial, Helvetica, sans-serif;
          }

		.navbar a {
		    float: left;
		    font-size: 16px;
		    color: white;
		    text-align: center;
		    padding: 14px 16px;
		    text-decoration: none;
		}
		
		.dropdown {
		    float: right;
		    overflow: hidden;
		}
		
		.dropdown .dropbtn {
		    font-size: 18px;    
		    border: none;
		    outline: none;
		    color: white;
		    padding: 12px 20px;
		    background-color: inherit;
		    font-family: inherit;
		    margin: 0;
		}
		
		.navbar a:hover, .dropdown:hover .dropbtn {
		    background-color: red;
		}
		
		.dropdown-content {
		    display: none;
		    position: absolute;
		    background-color: #f9f9f9;
		    min-width: 210px;
		    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
		    z-index: 1;
		}
		
		.dropdown-content a {
		    float: none;
		    color: black;
		    padding: 12px 16px;
		    text-decoration: none;
		    display: block;
		    text-align: left;
		}
		
		.dropdown-content a:hover {
		    background-color: #ddd;
		}
		
		.dropdown:hover .dropdown-content {
		    display: block;
		}
		
		
	</style>
	
	<body data-ng-controller="mainController">
	
		<!-- Navbar -->
		<div class="w3-top">
		  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
		    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" ng-click="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
		    <a href="/home" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">How To Upload</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">How To Download</a>
		    <a href="/to-upload-details" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">View Uploaded Details</a>
		    <div class="dropdown">
			    <button class="dropbtn">${loggedInUser}&nbsp;&nbsp;<i class="fa fa-user-circle"></i>
			      <i class="fa fa-caret-down"></i>
			    </button>
			    <div class="dropdown-content">
			      <a href="#"><i class="fa fa-user-o"> Profile</i></a>
			      <a href="/tologout"><i class="fa fa-sign-out"></i> Logout</a>
			    </div>
		    </div> 
			
		  </div>
		
		  <!-- Navbar on small screens -->
		  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">How To Upload</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">How To Download</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">Profile</a>
		    <a href="/tologout" class="w3-bar-item w3-button w3-padding-large">Logout</a>
		  </div>
		</div>
		
		<!-- Header -->
		<header class="w3-container w3-red w3-center" style="padding:128px 16px">
		  <h1 class="w3-margin w3-jumbo">Show the world your great work</h1>
		  <a href="/to-upload-pdf-page" class="w3-button w3-black w3-padding-large w3-large w3-margin-top">UPLOAD YOUR WORK HERE</a>
		  <a href="/to-upload-details" class="w3-button w3-black w3-padding-large w3-large w3-margin-top">DOWNLOAD OTHERS WORK</a>
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
		 <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p>
		</footer>
		
	</body>
	
</html>