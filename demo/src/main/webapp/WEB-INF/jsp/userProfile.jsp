<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="mainApp"> 
  <head>
		<title>User Profile</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/w3.css">
	    <link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"/>
		<link rel="stylesheet" href="css/ratings.css">
        <link rel="stylesheet" href="css/common.css">
        
		<script src="js/angular.min.js"></script>
		<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
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
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">About Us</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Contact Us</a>
		    <div class="dropdown">
			    <button class="dropbtn">${user.username}&nbsp;&nbsp;<i class="fa fa-user-circle"></i>
			      <i class="fa fa-caret-down"></i>
			    </button>
			    <div class="dropdown-content">
			      <a href="/user-profile" ><i class="fa fa-user-o"> Profile</i></a>
			      <a href="/logout"><i class="fa fa-sign-out"></i> Logout</a>
			    </div>
		    </div> 
		  </div>
		
		  <!-- Navbar on small screens -->
		  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">About Us</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">Contact Us</a>
		    <a href="/user-profile" class="w3-bar-item w3-button w3-padding-large">Profile</a>
		    <a href="/logout" class="w3-bar-item w3-button w3-padding-large">Logout</a>
		  </div>
		</div>
        <div id = "test" class="w3-row-padding w3-container" style="padding-top:110px!important;">
	        <div class="container bootstrap snippet">
	          <div class="row">
	  		     <div class="col-sm-3"><!--left col-->
		             <div class="text-center">
		                <ion-content>
							 <div class="list">
								   <ion-item>
									  <div style="padding-top: 6px;zoom:6.0;">
										 <ionic-letter-avatar data="${user.username}" shape="round" charcount="2"></ionic-letter-avatar>
									  </div>
								   </ion-item> 
							 </div>
						</ion-content>
						<h5 class="camelCase">Welcome, ${user.username} </h5>
				        <!-- <img src="http://ssl.gstatic.com/accounts/ui/avatar_2x.png" class="avatar img-circle img-thumbnail" alt="avatar"> -->
				        <!-- <h6>Upload a different photo...</h6>
				        <input type="file" class="text-center center-block file-upload"> -->
				     </div><br>
	              </div><!--/col-3-->
	    	      
	    	      <div ng-if="!showProfileInEditMode">
	    	         <jsp:include page="profileInReadMode.jsp"/>
	    	      </div>
	    	      
	    	      <div ng-if="showProfileInEditMode">
	    	         <jsp:include page="profileInEditMode.jsp"/>
	    	      </div>
	    	      
	           </div><!--/row-->
	       </div>
       </div>
        
	   <footer class="w3-container w3-padding-64 w3-center w3-opacity">  
	   <p> &copy;2018 MyApplication </p>
	   </footer>
		
   </body> 
</html>