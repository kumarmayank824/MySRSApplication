<!DOCTYPE html>
<html ng-app="mainApp"> 
  <head> 
   <title>Login</title> 
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   
   <link rel="stylesheet"  href="https://www.w3schools.com/w3css/4/w3.css">
   <link rel="stylesheet"  href="https://fonts.googleapis.com/css?family=Lato">
   <link rel="stylesheet"  href="https://fonts.googleapis.com/css?family=Montserrat">
   <!-- <link rel="stylesheet"  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
   
   <link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
   <link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"/>
   <link rel="stylesheet"  href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
   <link rel="stylesheet"  href="fonts/iconic/css/material-design-iconic-font.min.css">
   <link rel="stylesheet"  href="vendor/animate/animate.css">
   <link rel="stylesheet"  href="vendor/css-hamburgers/hamburgers.min.css">
   <link rel="stylesheet"  href="vendor/animsition/css/animsition.min.css">
   <link rel="stylesheet"  href="vendor/select2/select2.min.css">
   <link rel="stylesheet"  href="vendor/daterangepicker/daterangepicker.css">
   <link rel="stylesheet"  href="css/util.css">
   <link rel="stylesheet"  href="css/login.css">
   
   <script src="vendor/jquery/jquery-3.2.1.min.js"></script>
   <script src="vendor/animsition/js/animsition.min.js"></script>
   <script src="vendor/bootstrap/js/popper.js"></script>
   <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
   <script src="vendor/select2/select2.min.js"></script>
   <script src="vendor/daterangepicker/moment.min.js"></script>
   <script src="vendor/daterangepicker/daterangepicker.js"></script>
   <script src="vendor/countdowntime/countdowntime.js"></script>	
   <script src="vendor/jquery/main.js"></script>
   
   <script src="js/angular.min.js" ></script>
   <script src="js/mainController.js" ></script>
   <script src="js/mainService.js" ></script>		
   
  </head>
  <style>
	body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
	.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
	.fa-anchor,.fa-coffee {font-size:200px}
   </style>
   <body data-ng-controller="myController"> 
   
        <!-- Navbar -->
		<div class="w3-top">
		  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
		    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" ng-click="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
		    <a href="/home" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
		    <!-- <a href="/to-upload-pdf-page" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Upload SRS</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Download SRS</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Link 3</a>
		    <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Link 4</a> 
		    <a href="javascript:void(0)" class="w3-padding-large w3-hover-red w3-hide-small w3-right"><i class="fa fa-search"></i></a> -->
		  </div>
		
		  <!-- Navbar on small screens -->
		  <!-- <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
		    <a href="/to-upload-pdf-page" class="w3-bar-item w3-button w3-padding-large">Upload SRS</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">Download SRS</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 3</a>
		    <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 4</a>
		  </div> -->
		</div>
        
		<div class="limiter">
			<div class="container-login100" style="background-color: #f44336;">
				<div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
					<form class="login100-form validate-form">
						<span class="login100-form-title p-b-30">
							Login
						</span>
	                    <span style="color:red">{{errorMessage}}</span>
						<div class="wrap-input100 validate-input m-b-23" data-validate = "Username is reauired">
							<span class="label-input100">Username</span>
							<input class="input100" type="text" ng-model="user.username" placeholder="Type your username">
							<span class="focus-input100" data-symbol="&#xf206;"></span>
						</div>
	
						<div class="wrap-input100 validate-input" data-validate="Password is required">
							<span class="label-input100">Password</span>
							<input class="input100" type="password" ng-model="user.password" placeholder="Type your password">
							<span class="focus-input100" data-symbol="&#xf190;"></span>
						</div>
						
						<div class="text-right p-t-8 p-b-31">
							<a href="#">
								Forgot password?
							</a>
						</div>
						
						<div class="container-login100-form-btn">
							<div class="wrap-login100-form-btn">
								<div class="login100-form-bgbtn"></div>
								<button ng-click="submit()" class="login100-form-btn">
									Login
								</button>
							</div>
						</div>
	
						<div class="txt1 text-center p-t-54 p-b-20">
							<span>
								Or Sign Up Using
							</span>
						</div>
	
						<div class="flex-c-m">
							<a href="#" class="login100-social-item bg1">
								<i class="fa fa-facebook"></i>
							</a>
	
							<a href="#" class="login100-social-item bg2">
								<i class="fa fa-twitter"></i>
							</a>
	
							<a href="#" class="login100-social-item bg3">
								<i class="fa fa-google"></i>
							</a>
						</div>
	
						<div class="flex-col-c p-t-15">
							<span class="txt1 p-b-17">
								Not a member?<a href="#" class="txt2">Sign up now</a>
							</span>
						</div>
					</form>
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