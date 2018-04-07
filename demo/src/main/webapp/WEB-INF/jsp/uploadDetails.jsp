<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    
    <head>
		<title>Upload Details</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/w3.css">
		<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">
		<link href="css/table/datatables.min.css" rel="stylesheet"/>
		<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"/>
		<link rel="stylesheet" href="css/table/jquery-ui.css">
		
		<script src="js/table/jquery-2.2.4.min.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="js/table/bootbox.min.js"></script>
		<script src="js/table/jquery-ui.js"></script>
		<script src="js/table/datatables.min.js"></script>
		<script src="js/table/datatables-bs-3.3.6.min.js"></script>
		<script src="js/table/table.js"></script>
		
		
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
	</style>
	
	<body>
	
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
		  <h5 class="w3-margin w3-jumbo">We really appreciate these efforts</h5>
		  <a href="/to-upload-pdf-page" class="w3-button w3-black w3-padding-large w3-large w3-margin-top">Click To Contribute</a>
		</header>
		
		<!-- First Grid -->
		<div id = "test" class="w3-row-padding w3-padding-64 w3-container" style="padding-top:50px!important;">
		  <div class="w3-content">
		    <div>
		         <table id="example" class="table table-bordered table-striped" cellspacing="0" width="100%">
		            <thead>
			            <tr>
			               <th>SNO.</th>
			               <th>Document Name</th>
			               <th>Author</th>
			               <th>Title</th>
			               <th>Description</th>
			               <th>Category</th>
			               <th>Size</th>
			               <th>Uploaded Date</th>
			               <!-- <th>Document Type</th> -->
			               <th>Preview</th>
			               <th class="none"><b>NOTE</b></th>
			            </tr>
			        </thead>
			        <tbody>
						        
					</tbody>
		        </table>
		    </div>  
		  </div>
		</div>
		
		<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
									
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
		
		<div id="modelDiv" style="display: none">
			
			 <div class="container text-center"> 
                <div class="costumModal">
					<a href="#costumModal9" id="costumModal2" role="button" class="btn btn-default" data-toggle="modal">
			            expandIn
			        </a>
			        <div id="costumModal9" class="modal" data-easein="expandIn"  tabindex="-1" role="dialog" aria-labelledby="costumModalLabel" aria-hidden="true">
			            <div class="modal-dialog">
			                <div class="modal-content">
			                    <div class="modal-header">
			                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                            ×
			                        </button>
			                        <h4 class="modal-title">
			                            Modal Header
			                        </h4>
			                    </div>
			                    <div class="modal-body">
			                        <!-- <p>
			                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
			                            tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
			                            quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
			                            consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
			                            cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
			                            proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
			                        </p> -->
			                    </div>
			                    <div class="modal-footer">
			                        <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">
			                            Close
			                        </button>
			                        
			                    </div>
			                </div>
			            </div>
			        </div>
				</div>
		      </div>
		 </div> 
		
	</body>
	
</html>