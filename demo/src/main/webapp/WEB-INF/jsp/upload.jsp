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
		<link rel="stylesheet" href="css/w3.css">
		<link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"/>
		<link href="css/file-drag-and-drop.css" rel="stylesheet"/>
		<link href="css/pdf-viewer/pdf.css" rel="stylesheet"/>
		<link href="css/custom-checkbox.css" rel="stylesheet"/>
		<link rel="stylesheet" href="css/common.css">
		
		<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
		<script src="vendor/bootstrap/js/popper.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="js/pdf-viewer/pdf.js"></script>
		<script src="js/pdf-viewer/pdf.worker.js"></script>
		<script src="js/pdf-viewer/pdf-script.js"></script>
		<script src="js/angular.min.js" ></script>
		<script src="js/student/uploadControllerForStudents.js" ></script>
		<script src="js/student/uploadServiceForStudents.js" ></script> 
	</head>
	<style>
		body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
		.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
		.fa-anchor,.fa-coffee,.fa-upload {font-size:200px}
	</style>
	
	<body data-ng-controller="uploadController">
	    
	    <c:if test="${!isSubmissionAllowed}">
	        <div class="uploadPageCover"></div>	
		    <div class="bg-text-upload">
		       <p><i class="fa fa-ban" aria-hidden="true"></i> Currently not available</p>
			</div>
	    </c:if>
	    
		<!-- Navbar -->
		<div class="w3-top">
		  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
		    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" ng-click="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
		    <a href="/home" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
		    <a href="/std-upload-details" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">View Uploaded Details</a>
		    <c:if test="${!isSubmissionAllowed}">
		       <a href="/user-profile" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white"> Profile</a>
		       <a href="/logout" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white"> Logout</a>
		    </c:if>
		    <div class="dropdown">
			    <button class="dropbtn">${loggedInUser}&nbsp;&nbsp;<i class="fa fa-user-circle"></i>
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
		    <a href="/std-upload-details" class="w3-bar-item w3-button w3-padding-large">View Uploaded Details</a>
		    <a href="/user-profile" class="w3-bar-item w3-button w3-padding-large">Profile</a>
		    <a href="/logout" class="w3-bar-item w3-button w3-padding-large">Logout</a>
		  </div>
		</div>
		
		<!-- Header -->
		<!-- <header class="w3-container w3-red w3-center" style="padding:40px 0px 0px 0px;">
		  <h5 class="w3-margin w3-jumbo">Make Your Work Count</h5>
		</header> -->
		
		<!-- First Grid -->
		<div class="w3-row-padding w3-container" style="padding-top:60px!important;padding-right: 200px;">
		  <div class="w3-content">
		    <div class="w3-twothird">
		        <h5 class="errorThenShowRed">${missingInformationMessage}</h5>
		        <h5 class="errorThenShowRed">${submissionNotAllowedMessage}</h5>
		        <form action="/std-file-upload" method="post" enctype="multipart/form-data">
		            <div class="row">
		                <div class="col-sm-6 form-group">
		                    <label for="name">
		                        Title:</label>
		                    <input type="text" class="form-control" id="title" name="title"  maxlength="100" required>
		                </div>
		                <div class="col-sm-6 form-group">
		                    <label for="name">
		                        Categories:</label>
		                     <select class="form-control" name="category" id="sel1">
						        <option value="SRS">SRS</option>
						        <option value="Design Document">Design Document</option>
						        <option value="Implementation Document">Implementation Document</option>
						     </select>
		                </div>
	                </div>
		            
		            <div class="row">
		                <div class="col-sm-12 form-group">
		                    <label for="name">
		                        Describe Your Work Here :</label>
		                    <textarea class="form-control" type="textarea" id="description" name="description" placeholder="Description about your work here..." maxlength="500" rows="5" required></textarea>
		                </div>
		            </div>
		            
		            <div class="row">
		                 <div class="col-sm-12 form-group">
		                    <div class="form-group files">
				                <label for="name">
				                    Upload Your Work (only .pdf documents are allowed) : </label>
				                <input type="file" id="file-to-upload" name="myfile" class="form-control" multiple="" accept="application/pdf,application/msword">
			              </div>
		                 </div>
		            </div>
		            <div class = "row">
		                 <div class="col-sm-12 form-group">
		                     <c:set var="profileDetailsDeclarationNotDone" value="${profileDetailsDeclarationNotDone}"/>
		                     <label class="container">
		                        I agree that my submission adhere to my current profile details and I understand that any inconsistency could lead to discrepancies in marks, and shall not be revert back.
							  <input type="checkbox" id="profileDetailsDeclarationFlag" name="profileDetailsDeclarationFlag" value="1">
							  <c:if test="${profileDetailsDeclarationNotDone == 'Yes' }">
							     <span class="checkmark checkBoxError"></span>
							  </c:if>
							  <c:if test="${profileDetailsDeclarationNotDone == 'No' }">
							     <span class="checkmark"></span>
							  </c:if>
							</label>
		                 </div>
		            </div>
	                <div class="row">
		                <div class="col-sm-12 form-group">
		                    <input type="submit" class="btn btn-lg btn-success btn-block" value="Upload Document" >
		                </div>
	                </div>
	                <input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
	           </form>  
			</div>
		    <div class="w3-third w3-center">
		      <!-- <i class="fa fa-upload w3-padding-64 w3-text-red" style="margin-top: 50%;"></i> -->
		      
		      <!-- <button id="upload-button">Select PDF</button> 
			  <input type="file" id="file-to-upload" accept="application/pdf" /> -->
					  <div id="pdf-main-container">
						    <div id="pdf-loader">Loading document ...</div>
						    <div id="nopreview"><img alt="" src="images/No_image_available.jpg"></div>
						    <div id="pdf-contents">
						        <div id="pdf-meta">
						            <!-- <div id="pdf-buttons">
						                <button id="pdf-prev">Previous</button>
						                <button id="pdf-next">Next</button>
						            </div> -->
						            <!-- <div id="page-count-container">Page <div id="pdf-current-page"></div> of <div id="pdf-total-pages"></div></div> -->
						        </div>
						        
					            <canvas id="pdf-canvas" width="400" height="500"></canvas>
						        
						        <div id="page-loader">Loading page ...</div>
						    </div>
					 </div>
		    </div>
		  </div>
		</div>
		
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