<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html ng-app="mainApp"> 
  <head>
		<title>Activity History | MyApplication</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="icon" type="image/png" href="images/logo/vit-logo.png"/>
		
		<link rel="stylesheet" href="css/w3.css">
	    <link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"/>
		<link href="css/bootstrap-glyphicons-3.0.0.css" rel="stylesheet"> 
		<link rel="stylesheet" href="css/table/jquery-ui.css">
		<link rel="stylesheet" href="css/ratings.css">
        <link rel="stylesheet" href="css/common.css">
        <link rel="stylesheet" href="css/activity-history.css">
        <link rel="stylesheet" href="css/back-button.css">
        
		<script src="js/angular.min.js"></script>
		<script src="js/table/jquery-2.2.4.min.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="js/table/bootbox.min.js"></script>
		<script src="js/table/jquery-ui.js"></script>
		<script src="js/table/datatables-bs-3.3.6.min.js"></script>
		<script src="js/preview.js"></script>
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
		    <a href="/std-upload-details" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">View Uploaded Details</a>
		    <a href="/std-upload-pdf-page" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Upload Your Work Here</a>
		    <a href="/guideline-documents" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Check Guideline Documents</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">View Statistics</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">About Us</a>
		    <div class="dropdown">
			    <button class="dropbtn">${user.username}&nbsp;&nbsp;<i class="fa fa-user-circle"></i>
			      <i class="fa fa-caret-down"></i>
			    </button>
			    <div class="dropdown-content">
			      <a href="/std-user-profile" ><i class="fa fa-user-o"> Profile</i></a>
			      <a href="/std-activity-history"><i class="fa fa-history"> History</i></a>
			      <a href="/logout"><i class="fa fa-sign-out"></i> Logout</a>
			    </div>
		    </div> 
		  </div>
		
		  <!-- Navbar on small screens -->
		  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="/std-upload-details" class="w3-bar-item w3-button w3-padding-large">View Uploaded Details</a>
		    <a href="/std-upload-pdf-page" class="w3-bar-item w3-button w3-padding-large">Upload Your Work Here</a>
		    <a href="/guideline-documents" class="w3-bar-item w3-button w3-padding-large">Check Guideline Documents</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">View Statistics</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">About Us</a>
		  </div>
		</div>
		
        <div class= "w3-row-padding w3-container">
            
            <div class="row">
	  		      <div class="col-sm-2 cursorPointerClass" onclick="window.history.go(-1); return false;">
	  		          <p><div class="bk-btn"><div class="bk-btn-triangle"></div><div class="bk-btn-bar"></div></div> Go Back</p>
	  		      </div>
	  		      <div class="col-sm-9">
	  		         <h3 class="text-center" style="padding-top:50px!important;"> Activity History </h3>
	  		      </div>
			</div>  		      
	        <div class="container bootstrap snippet" style="padding-top:18px!important;">
	          <c:if test="${not empty attachmentLst}">
	             <c:forEach var="attachment" items="${attachmentLst}" varStatus="loopCounter">
				     <div class="row">
			  		      <div class="col-sm-2"><!--left col-->
			  		        <div class="text-center">
			  		           <ion-content>
									 <div class="list">
										   <ion-item>
												 <ionic-letter-avatar data="${loopCounter.count}" shape="round" charcount="2"></ionic-letter-avatar>
										   </ion-item> 
									 </div>
								</ion-content>
								<h5><fmt:formatDate value="${attachment.uploadedDate}" pattern="MMM d, y h:mm a" /></h5>
			  		        </div>
			              </div><!--/col-2-->
			    	      <div class="col-sm-9">
			    	          <ol class="progtrckr" data-progtrckr-steps="3">
							       <li class="progtrckr-done"><a style="text-decoration:none;" href="#myHistoryModal${attachment.id}" data-toggle="modal" title="Click Here To View Uploaded Details">View Details</a></li><!--
								--><li class="progtrckr-done"><a style="text-decoration:none;" title="Click Here To Preview" class='preview-pdf' href="href='javascript:;" data-pdfurl="/attachment-download/preview/${attachment.id}"><i style="color:yellowgreen" class="fa fa-file-pdf-o" aria-hidden="true"></i> View Document</a></li><c:choose><c:when test="${ not empty marksMap  && not empty marksMap[attachment.id]}"><c:set var="marks" value="${marksMap[attachment.id]}"/><li class="progtrckr-done"><a style="text-decoration:none;" href="#myHistoryMarksModal${marks.attachmentId}" data-toggle="modal" title="Click Here To View The Result"> View Marks</a></li><!-- This is to show the marks detail modal --><div class="modal fade" id="myHistoryMarksModal${marks.attachmentId}" role="dialog"><div class="modal-dialog"><div class="modal-content"><!-- Modal Header --><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i style="color:#f44336" class="fa fa-window-close" aria-hidden="true"></i></button><h3 style="color: #f44336;" class="modal-title">Marks Details</h3></div><!-- Modal Body --><div class="modal-body" ><div class="row"><div class="col-sm-12"><ion-content><div class="list"><ion-item><div style="float:left;padding-right: 10px;"><ionic-letter-avatar data="${marks.author}" shape="round" charcount="2"></ionic-letter-avatar></div></ion-item></div></ion-content><strong class="camelCase">${marks.author}</strong><i> ( ${marks.email} ) evaluated on <b><fmt:formatDate value="${marks.commentTime}" pattern="MMM d, y" /></b> at <b><fmt:formatDate value="${marks.commentTime}" pattern="h:mm a" /></b></i></div></div><div class="row" style="padding-left:40px;padding-top: 15px;"><div class="col-sm-11"><span style="color:#109828;"> Quality Of the content : </span><strong>${marks.markPara1} Marks</strong></div></div><div class="row" style="padding-left:40px;"><div class="col-sm-11"><span style="color:#109828;">Uniqueness of the content : </span><strong>${marks.markPara2} Marks</strong></div></div><div class="row" style="padding-left:40px;"><div class="col-sm-11"><span style="color:#109828;">Information Quality : </span><strong>${marks.markPara3} Marks</strong></div></div><div class="row" style="padding-left:40px;"><div class="col-sm-11"><span style="color:#109828;">Uniqueness of the content4 : </span><strong>${marks.markPara4} Marks</strong></div></div><div class="row" style="padding-left:40px;"><div class="col-sm-11"><span style="color:#109828;">Uniqueness of the content5 : </span><strong>${marks.markPara5} Marks</strong></div></div><br><div class="row" style="padding-left:40px;"><div class="col-sm-4"><span style="color:#0e336d;"> Total Marks </span> : <strong>${marks.marks} Marks</strong></div></div><!-- Modal Footer --><div class="modal-footer"><button type="button" style="background-color:#f44336;color: white "class="btn btn-default " data-dismiss="modal">Close</button></div></div></div></div></div></c:when><c:otherwise><li class="progtrckr-todo">View Marks</li></c:otherwise></c:choose>
						      </ol>
						</div>
		            </div><br><br><br><!--/row-->
		           
		            <!-- This is to show the uploaded detail modal  -->
		            <div class="modal fade" id="myHistoryModal${attachment.id}">
					   <div class="modal-dialog">
					      <div class="modal-content">
						        <div class="modal-header">
							          <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i style="color:#f44336" class="fa fa-window-close" aria-hidden="true"></i></button>
							          <h3 style="color: #f44336;" class="modal-title">Uploaded Details</h3>
						        </div>
					            <div class="modal-body">
					            
					                 <div class="row">
								       <div class="col-sm-4"><strong>FileName</strong></div>
								       <div class="col-sm-7">${attachment.fileName}</div>
								     </div>
								     <br>
								     <div class="row">
								       <div class="col-sm-4"><strong>Author</strong></div>
								       <div class="col-sm-7">${attachment.author}</div>
								     </div>
								     <br>
								     <div class="row">
								       <div class="col-sm-4"><strong>Category</strong></div>
								       <div class="col-sm-7">${attachment.category}</div>
								     </div>
								     <br>
								     <div class="row">
								       <div class="col-sm-4"><strong>Semester</strong></div>
								       <div class="col-sm-7">${attachment.semester}</div>
								     </div>
								     <br>
								     <div class="row">
								       <div class="col-sm-4"><strong>Batch</strong></div>
								       <div class="col-sm-7">${attachment.batch}</div>
								     </div>
								     <br>
								     <div class="row">
								       <div class="col-sm-4"><strong>Course</strong></div>
								       <div class="col-sm-7">${attachment.course}</div>
								     </div>
								     <br>
								     <div class="row">
								       <div class="col-sm-4"><strong>Uploaded Date</strong></div>
								       <div class="col-sm-7"><fmt:formatDate value="${attachment.uploadedDate}" pattern="MMM d, y h:mm a" /></div>
								     </div>
								     <br>
								     <div class="row">
								       <div class="col-sm-4"><strong>Title</strong></div>
								       <div class="col-sm-7" style="word-wrap: break-word;">
			                               ${attachment.title}
			                           </div>  
								     </div>
								     <br>
								     <div class="row">
								       <div class="col-sm-4"><strong>Description</strong></div>
								       <div class="col-sm-7" style="word-wrap: break-word;">
								          ${attachment.description}
								       </div>
								     </div>
							    </div>
						        <div class="modal-footer">
						         	 <button type="button" style="background-color:#f44336;color:white" class="btn btn-default " data-dismiss="modal">Close</button>
						        </div>
					      </div>
					  </div>
			       </div><!-- details modal DIV ends  -->
		           
				</c:forEach> 
	         </c:if>
	          
           </div>
	   </div> 
	   <footer class="w3-container w3-center w3-opacity">  
	       <p> &copy;2018 MyApplication </p>
	   </footer>
		
   </body> 
</html>