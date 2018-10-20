<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="teacherUploadDetailApp">
    
    <head>
		<title>Upload Details</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/w3.css">
	    <link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"/>
		<link href="css/bootstrap-glyphicons-3.0.0.css" rel="stylesheet"> 
		<link rel="stylesheet" href="css/table/jquery-ui.css">
		<link rel="stylesheet" href="css/toggle-button.css">
		<link rel="stylesheet" href="css/common.css">
		<link rel="stylesheet" href="css/jquery-confirm/jquery-confirm-3.3.0.min.css">
      
		<script src="js/angular.min.js"></script>
		<script src="js/table/jquery-2.2.4.min.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="js/table/bootbox.min.js"></script>
		<script src="js/table/jquery-ui.js"></script>
		<script src="js/table/datatables-bs-3.3.6.min.js"></script>
		<script src="js/teacher/uploadDetailControllerForTeachers.js"></script>
		<script src="js/teacher/uploadDetailServiceForTeachers.js"></script>
		<script src="js/preview.js"></script>
		<script src="js/avatar/ionic-letter-avatar.js"></script>
		<script src="js/dirPagination-0.11.1.min.js"></script>
		<script src="js/jquery-confirm/jquery-confirm-3.3.0.min.js"></script>
		
	</head>
	
	<body ng-controller="teacherUploadDetailController" ng-init="searchDetails('V','Batch 1','M.Tech (IT)')">
	
		<!-- Navbar -->
		<div class="w3-top">
		  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
		    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" ng-click="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
		    <a href="/home" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
		    <!-- <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Download others Work</a> -->
		    <div class="dropdown">
			    <button class="dropbtn">${loggedInUser}&nbsp;&nbsp;<i class="fa fa-user-circle"></i>
			      <i class="fa fa-caret-down"></i>
			    </button>
			    <div class="dropdown-content">
			      <a href="/user-profile"><i class="fa fa-user-o"> Profile</i></a>
			      <a href="/logout"><i class="fa fa-sign-out"></i> Logout</a>
			    </div>
		    </div> 
		  </div>
		
		  <!-- Navbar on small screens -->
		  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="/tch-upload-pdf-page" class="w3-bar-item w3-button w3-padding-large">Upload Your Work Here</a>
		    <!-- <a href="#" class="w3-bar-item w3-button w3-padding-large">How To Download</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">Profile</a>
		    <a href="/logout" class="w3-bar-item w3-button w3-padding-large">Logout</a> -->
		  </div>
		</div>
		
		<!-- First Grid -->
		<div id = "test" class="w3-row-padding w3-container" style="padding-top:65px!important;">
		  <div>
		      <form name="myForm" class="form-inline">
		        <div class="form-group" style="padding:22px">
		            <div search-details></div>
		        </div>
		        <div style="float:right;margin-right: 30px;">
					<!-- <dir-pagination-controls max-size="5" direction-links="true" boundary-links="true" on-page-change="getData(newPageNumber,6)" /> -->
					<dir-pagination-controls max-size="5" direction-links="true" boundary-links="true" />
		   		</div>
		     </form>
		     <hr>
		     
		     <div class="myradio" style="margin-top: 15px;">
		        <label style="margin-left:20px;" > APPLY FILTER <i style="color:#72c3cc;" class="fa fa-filter" aria-hidden="true"></i> : </label> 
		        <input id="radio-1"  ng-model="filterType.marksExists" type="radio" checked>
				<label for="radio-1" class="myradio-label myradioApplyFilter" >Show All</label>
				<input id="radio-2"  ng-model="filterType.marksExists" type="radio" value="true" >
				<label for="radio-2" class="myradio-label myradioApplyFilter" >Evaluated</label>
				<input id="radio-3"  ng-model="filterType.marksExists" type="radio" value="false">
				<label  for="radio-3" class="myradio-label myradioApplyFilter" >Not Yet Evaluated </label>
			 </div><br>
		     
		     <!-- <div dir-paginate="attachment in attachmentLst|filter:search|itemsPerPage:itemsPerPage"  total-items="{{total_count}}" class="col-md-4"> -->
		     <div dir-paginate="attachment in attachmentLst|filter:filterType|itemsPerPage:6"  class="col-md-4"> 
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