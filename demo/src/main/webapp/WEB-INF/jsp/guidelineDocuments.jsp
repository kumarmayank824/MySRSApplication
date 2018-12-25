<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html ng-app="coordinatorApp"> 
  <head>
		<title>Guideline Documents</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/w3.css">
	    <link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"/>
		<link rel="stylesheet" href="css/table/jquery-ui.css">
        <link rel="stylesheet" href="css/common.css">
        <link rel="stylesheet" href="css/image-overlay.css">
        
		<script src="js/angular.min.js"></script>
		<script src="js/table/jquery-2.2.4.min.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="js/table/bootbox.min.js"></script>
		<script src="js/table/jquery-ui.js"></script>
		<script src="js/table/datatables-bs-3.3.6.min.js"></script>
	    <script src="js/coordinator/controller.js" ></script>
	    <script src="js/coordinator/service.js" ></script>
		<script src="js/preview.js"></script>
		
	</head>
	
  <style>
	body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif;}
	.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
	.fa-anchor,.fa-coffee {font-size:200px}
   </style>
   
   <body data-ng-controller="coordinatorController"> 
        
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
		
        <div class="w3-row-padding w3-container">
            
            <div class="row">
	  		      <div class="col-sm-2"></div>
	  		      <div class="col-sm-9">
	  		         <h3 class="text-center" style="padding-top:70px!important;">Guideline Documents</h3>
	  		      </div>
			</div>
			
			<c:forEach var="coordinatorAttachment" items="${coordinatorAttachmentList}" varStatus ="row">
	            <c:if test="${(row.count-1) % 3 == 0}">
	                <div style="padding-top:40px!important;">
		                 <div class="row"> 
		                    <div class="col-sm-2"></div>
	            </c:if>
						       <div class="col-sm-3">
							       <div class="zoom">
							          <img src="/coordinator-attachment-download/preview/image/${coordinatorAttachment.id}" alt="Avatar" class="image">
							          <div class="overlay">
										     <a href="/coordinator-attachment-download/download/pdf/${coordinatorAttachment.id}" class="icon" title="Click Here To Download Document">
										       <i class="fa fa-cloud-download"></i>
										     </a>
										     <a class='preview-pdf icon' href="href='javascript:;" data-pdfurl="/coordinator-attachment-download/preview/pdf/${coordinatorAttachment.id}" class="icon" title="Click Here To View Document" style="margin-left:115px">
										       <i class="fa fa-file-pdf-o"></i>
										     </a>
										     <p class="coordinatorFileName">${fn:substring(coordinatorAttachment.fileName, 0, 25)}</p>
							          </div>
						           </div>
						      </div>
				<c:if test="${row.count % 3 == 0}">
		                 </div>     
			        </div>
	            </c:if>	
            </c:forEach> 
                        </div>     
		            </div>  	      
	   </div>	
	   <footer class="w3-container w3-center w3-opacity" style="padding-top: 64px!important;">  
	       <p> &copy;2018 MyApplication </p>
	   </footer>
   </body> 
</html>