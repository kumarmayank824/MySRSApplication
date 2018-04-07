<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="fileUploadApp">
    
    <head>
		<title>Upload Your Work</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"/>
		<link href="css/fileDragAndDrop.css" rel="stylesheet"/>
		<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
		<script src="vendor/bootstrap/js/popper.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="js/angular.min.js" ></script>
		<script src="js/uploadController.js" ></script>
		<script src="js/uploadService.js" ></script>
	</head>
	<style>
		body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
		.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
		.fa-anchor,.fa-coffee,.fa-upload {font-size:200px}
	</style>
	
	<body data-ng-controller="uploadController">
	
		<!-- Navbar -->
		<div class="w3-top">
		  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
		    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" ng-click="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
		    <a href="/home" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">How To Upload</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">How To Download</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">About Us</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Contact Us</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Logout</a>
		  </div>
		
		  <!-- Navbar on small screens -->
		  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">How To Upload</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">How To Download</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">About Us</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">Contact Us</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Logout</a>
		  </div>
		</div>
		
		<!-- Header -->
		<header class="w3-container w3-red w3-center" style="padding:60px">
		  <h5 class="w3-margin w3-jumbo">We are eagerly looking for your contribution</h5>
		  <!-- <i class="fa fa-upload w3-text-black" style="font-size:80px;"></i> -->
		</header>
		
		<!-- First Grid -->
		<div class="w3-row-padding w3-padding-64 w3-container" style="padding-top:50px!important;">
		  <div class="w3-content">
		    <div class="w3-twothird">
		      <h1 style="color: #f44336;margin-top:0px;text-align: center;">Make Your Work Count</h1>
		        <form ng-submit="submitFileForm()" name="fileForm">
		            <div class="row">
		                <div class="col-sm-6 form-group">
		                    <label for="name">
		                        Title:</label>
		                    <input type="text" class="form-control" ng-model="file.title" id="title" name="title"  maxlength="100">
		                </div>
		                <div class="col-sm-6 form-group">
		                    <label for="name">
		                        Categories:</label>
		                     <select class="form-control" ng-model="file.category" name="category" id="sel1">
						        <option value="SRS">SRS</option>
						        <option value="Design Document">Design Document</option>
						        <option value="Implementation Document">Implementation Document</option>
						     </select>
		                </div>
	                </div>
		            
		            <div class="row">
		                <div class="col-sm-12 form-group">
		                    <label for="name">
		                        Describe Your Contribution Here :</label>
		                    <textarea class="form-control" type="textarea" ng-model="file.description" id="description" name="description" placeholder="Description about your contribution here..." maxlength="500" rows="5"></textarea>
		                </div>
		            </div>
		            
		            <div class="row">
		                 <div class="col-sm-12 form-group">
		                    <div class="form-group files">
				                <label for="name">
				                    Upload Your Work : </label>
				                <input type="file" file-model="file.myFile" class="form-control" multiple="">
			              </div>
		                 </div>
		            </div>
	                <div class="row">
		                <div class="col-sm-12 form-group">
		                    <input type="submit" class="btn btn-lg btn-success btn-block" value="Post Your Contribution" >
		                </div>
	                </div>
	           </form>  
			</div>
		    <div class="w3-third w3-center">
		      <i class="fa fa-upload w3-padding-64 w3-text-red" style="margin-top: 50%;"></i>
		    </div>
		  </div>
		</div>
		
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