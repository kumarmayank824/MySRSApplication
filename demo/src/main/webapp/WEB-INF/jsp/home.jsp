<!DOCTYPE html>
<html ng-app="mainApp">
    <head>
		<title>My App</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/w3.css">
		<!-- <link rel="stylesheet" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat"> -->
		<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
		<script src="js/chart/highcharts.js"></script>
		<script src="js/chart/chart.js"></script>
		<script src="js/angular.min.js" ></script>
		<script src="js/mainController.js" ></script>
		<script src="js/mainService.js" ></script>
	</head>
	<style>
		body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
		.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
		.fa-anchor,.fa-coffee {font-size:200px}
		.sign{float:right}
	</style>
	
	<body data-ng-controller="mainController" data-ng-init="init()">
	
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
		
		<!-- Header -->
		<!-- <header class="w3-container w3-red w3-center" style="padding:60px  16px">
		  <h1 class="w3-margin w3-jumbo">Hey! Lets Explore</h1>
		  <a href="/loginSuccess" class="w3-button w3-black w3-padding-large w3-large w3-margin-top">You Just A Click Short</a>
		</header> -->
		
		<!-- First Grid -->
		<div class="w3-row-padding w3-padding-64 w3-container">
		  <div class="w3-content">
		    <!-- <div class="w3-twothird">
		      <h1>Lorem Ipsum</h1>
		      <h5 class="w3-padding-32">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</h5>
		
		      <p class="w3-text-grey">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Excepteur sint
		        occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
		        laboris nisi ut aliquip ex ea commodo consequat.</p>
		    </div>
		
		    <div class="w3-third w3-center">
		      <i class="fa fa-anchor w3-padding-64 w3-text-red"></i>
		    </div> -->
		    <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
		  </div>
		</div>
		
		<!-- Second Grid -->
		<!-- <div class="w3-row-padding w3-light-grey w3-padding-64 w3-container">
		  <div class="w3-content">
		    <div class="w3-third w3-center">
		      <i class="fa fa-coffee w3-padding-64 w3-text-red w3-margin-right"></i>
		    </div>
		
		    <div class="w3-twothird">
		      <h1>Lorem Ipsum</h1>
		      <h5 class="w3-padding-32">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</h5>
		
		      <p class="w3-text-grey">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Excepteur sint
		        occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
		        laboris nisi ut aliquip ex ea commodo consequat.</p>
		    </div>
		  </div>
		</div> -->
		
		<!-- <div class="w3-container w3-black w3-center w3-opacity w3-padding-64">
		    <h1 class="w3-margin w3-xlarge">Quote of the day:you are amazing remember that</h1>
		</div> -->
		
		<!-- Footer -->
		<footer class="w3-container w3-padding-64 w3-center w3-opacity">  
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