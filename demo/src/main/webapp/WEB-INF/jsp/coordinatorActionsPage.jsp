<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="coordinatorApp"> 
  <head>
		<title>Coordinator Actions Page | MyApplication</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="icon" type="image/png" href="images/logo/vit-logo.png"/>
		
		<link rel="stylesheet" href="css/w3.css">
	    <link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"/>
        <link rel="stylesheet" href="css/common.css">
        <link rel="stylesheet" href="vendor/daterangepicker/daterangepicker.css">
        
		<script src="js/angular.min.js"></script>
		<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="vendor/daterangepicker/moment.min.js"></script>
		<script src="vendor/daterangepicker/daterangepicker.js"></script>
	    <script src="js/coordinator/controller.js" ></script>
	    <script src="js/coordinator/service.js" ></script>
		
	</head>
	
  <style>
	body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif;}
	.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
	.fa-anchor,.fa-coffee {font-size:200px}
   </style>
   
   <body data-ng-controller="coordinatorController"> 
        
        <div ng-if="showLoading">
	        <div class="cover"></div>	
		    <div class="bg-text">
			  <div class="loader"></div>
			</div>
		</div>
		   
        <!-- Navbar -->
		<div class="w3-top">
		  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
		    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" ng-click="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
		    <a href="/home" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
		    <a href="/guideline-documents" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Check Guideline Documents</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">View Statistics</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">About Us</a>
		    <div class="dropdown">
			    <button class="dropbtn">${loggedInUser}&nbsp;&nbsp;<i class="fa fa-user-circle"></i>
			      <i class="fa fa-caret-down"></i>
			    </button>
			    <div class="dropdown-content">
			      <a href="/coord-user-profile" ><i class="fa fa-user-o"> Profile</i></a>
			      <a href="/logout"><i class="fa fa-sign-out"></i> Logout</a>
			    </div>
		    </div> 
		  </div>
		
		  <!-- Navbar on small screens -->
		  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="/guideline-documents" class="w3-bar-item w3-button w3-padding-large">Check Guideline Documents</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">View Statistics</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">About Us</a>
		  </div>
		  
		</div>
		
        <div class="w3-row-padding w3-container">
            
            <div class="row">
	  		      <div class="col-sm-2"></div>
	  		      <div class="col-sm-9">
	  		         <h3 class="text-center" style="padding-top:70px!important;">Coordinator Actions</h3>
	  		      </div>
			</div> 
			
			<div style="padding-top:40px!important;">
			  ` <div class="row">
	  		      <div class="col-sm-2"></div>
	  		      <div class="col-sm-2">
	  		         <label for="Select submission start date and end date">Choose Submission Start And End Date</label>
	  		      </div>
	  		      <div class="col-sm-3">
                        <input id="coordinatorDaterange" type="text" class="form-control fa" placeholder="&#xf133 Click To Select Date (MM/DD/YYYY)" name="daterange" ng-model="coordinatorDaterange" readonly/>
	  		      </div>
	  		      <div class="col-sm-1">
                        <button style="margin-left:20px;background-color:#222323c4;color: white" type="button" class="btn btn-default" ng-click="saveCoordinatorStartAndEndTime()" >Submit</button>
	  		      </div>
	  		      <div class="coordinatorActionErrorMessage col-sm-2" ng-if="isStartAndEndTimeResponseSuccess == false">{{startAndEndTimeResponseMessage}}</div>
	  		      <div class="coordinatorActionSuccessMessage col-sm-2" ng-if="isStartAndEndTimeResponseSuccess">{{startAndEndTimeResponseMessage}}</div>
	  		   </div>  
		    </div>
		    <hr/>
		    
		    <div style="padding-top:30px!important;">
			     <div class="row">
		  		      <div class="col-sm-2"></div>
		  		      <div class="col-sm-2">
		  		         <label for="Generate Secret Code">Generate Secret Code</label>
		  		      </div>
		  		      <div class="col-sm-3">
                            <a href="#" class="w3-button w3-amber w3-large" ng-click="getNewSecretCode()">New Secret Code</a>
                            <c:if test="${existingSecretCode ne null}">
						        <div ng-if="!newSecretCode">
							       <p><b><h6 class="profileLabelOnlyColor"><i>Existing Secret Code : </i></h6></b></p>
							       <p><h6>${existingSecretCode}</h6></p>
						        </div>
						    </c:if>
						    <div ng-if="newSecretCode">
						       <p><b><h6 class="profileLabelOnlyColor"><i>New Secret Code : </i></h6></b></p>
						       <p><h6>{{newSecretCode}}</h6></p>
					        </div>
		  		      </div>
		  		      <div class="coordinatorActionErrorMessage col-sm-2" ng-if="isNewSecretCodeResponseSuccess == false">{{newCodeResponseMessage}}</div>
  		              <div class="coordinatorActionSuccessMessage col-sm-2" ng-if="isNewSecretCodeResponseSuccess">{{newCodeResponseMessage}}</div>
	  		     </div>  
		    </div>
		    <hr/>
		    
		    <div style="padding-top:30px!important;">
			    <div class="row">
			       <form id="coordinatorDocumentUploadForm" method="post" enctype="multipart/form-data">
			  		      <div class="col-sm-2"></div>
			  		      <div class="col-sm-2">
			  		         <label for="Upload a document">Upload Document</label>
			  		      </div>
			  		      <div class="col-sm-3">
						      <input type="file" id="coordinatorFileContent" class="w3-button w3-amber form-control-file" name="coordinatorFile" accept="application/pdf">
			  		      </div>
			  		      <div class="col-sm-1">
	                          <button style="margin-left:20px;background-color:#222323c4;color: white" type="submit" class="btn btn-default" ng-click="uploadFiles()" id=saveCoordinatorDocumentBtn >Upload</button>
			  		      </div>
			  		      <div class="coordinatorActionErrorMessage col-sm-2" ng-if="isDocumentUploadResponseSuccess == false">{{documentUploadResponseMessage}}</div>
  		                  <div class="coordinatorActionSuccessMessage col-sm-2" ng-if="isDocumentUploadResponseSuccess">{{documentUploadResponseMessage}}</div>
	  		       </form> 
	  		    </div>  
		    </div>
		    
       </div>        
       <input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
	   <footer class="w3-container w3-center w3-opacity" style="padding-top: 64px!important;">  
	       <p> &copy;2018 MyApplication </p>
	   </footer>
   </body> 
</html>