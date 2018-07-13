<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="uploadDetailApp">
    
    <head>
		<title>Upload Details</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/w3.css">
	    <link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet"> 
		<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"/>
		<link rel="stylesheet" href="css/table/jquery-ui.css">
		
		<script src="js/angular.min.js"></script>
		<script src="js/table/jquery-2.2.4.min.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="js/table/bootbox.min.js"></script>
		<script src="js/table/jquery-ui.js"></script>
		<script src="js/table/datatables-bs-3.3.6.min.js"></script>
		<script src="js/dirPagination-0.11.1.min.js"></script>
		<script src="js/uploadDetailController.js"></script>
		<script src="js/uploadDetailService.js"></script>
		<script src="js/preview.js"></script>
		
		
	</head>
	<style>
		body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
		.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
		.fa-anchor,.fa-coffee,.fa-upload {font-size:200px}
		.text-wrap{
		    white-space:normal;
		    word-break: break-all;
		}
		.width-200{
		    width:360px;
		}
		.navbar {
		    overflow: hidden;
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
		    position: inherit !important;
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
		
		.modal-dialog{
			width: 700px;
	    } 
		
	</style>
	
	<body ng-controller="uploadDetailController">
	
		<!-- Navbar -->
		<div class="w3-top">
		  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
		    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" ng-click="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
		    <a href="/home" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
		    <a href="/to-upload-pdf-page" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Upload Your Work Here</a>
		    <!-- <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Download others Work</a> -->
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
		    <a href="/to-upload-pdf-page" class="w3-bar-item w3-button w3-padding-large">Upload Your Work Here</a>
		    <!-- <a href="#" class="w3-bar-item w3-button w3-padding-large">How To Download</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">Profile</a>
		    <a href="/tologout" class="w3-bar-item w3-button w3-padding-large">Logout</a> -->
		  </div>
		</div>
		
		<!-- Header -->
		<header class="w3-container w3-red w3-center" style="padding:40px 0px 0px 0px;">
		  <h5 class="w3-margin w3-jumbo">We really appreciate these efforts</h5>
		  <!-- <a href="/to-upload-pdf-page" class="w3-button w3-black w3-padding-large w3-large w3-margin-top">Click To Contribute</a> -->
		</header>
		
		<!-- First Grid -->
		<!-- <div id = "test" class="w3-row-padding w3-padding-64 w3-container" style="padding-top:10px!important;"> -->
		<div id = "test" class="w3-row-padding w3-container" style="padding-top:10px!important;">
		  <!--<div class="w3-content">  -->
		  <div>
		      <form class="form-inline">
		        <div class="form-group" style="padding:22px">
		            <label >Search</label>
		            <input type="text" ng-model="search" class="form-control" placeholder="Search">
		        </div>
		        <div style="float:right;margin-right: 15px;">
					<!-- <dir-pagination-controls max-size="5" direction-links="true" boundary-links="true" on-page-change="getData(newPageNumber,6)" /> -->
					<dir-pagination-controls max-size="5" direction-links="true" boundary-links="true" />
		   		</div>
		     </form>
		     <!-- <div dir-paginate="attachment in attachmentLst|filter:search|itemsPerPage:itemsPerPage"  total-items="{{total_count}}" class="col-md-4"> -->
		     <div dir-paginate="attachment in attachmentLst|filter:search|itemsPerPage:6"  class="col-md-4"> 
		         <div attachment-details></div>
		     </div>  
		  </div>
		</div>
		<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
									
		<!-- Footer -->
		<!-- <footer class="w3-container w3-padding-64 w3-center w3-opacity">   -->
		<footer class="w3-container w3-center w3-opacity">
		  <!-- <div class="w3-xlarge w3-padding-32">
		    <i class="fa fa-facebook-official w3-hover-opacity"></i>
		    <i class="fa fa-instagram w3-hover-opacity"></i>
		    <i class="fa fa-snapchat w3-hover-opacity"></i>
		    <i class="fa fa-pinterest-p w3-hover-opacity"></i>
		    <i class="fa fa-twitter w3-hover-opacity"></i>
		    <i class="fa fa-linkedin w3-hover-opacity"></i>
		 </div> -->
		 <!-- <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p> -->
		 <p> &copy;2018 MyApplication </p>
		</footer>
		
	</body>
	
</html>