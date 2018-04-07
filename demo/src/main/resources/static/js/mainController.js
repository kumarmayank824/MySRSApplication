(function(){
	 
	var app = angular.module("mainApp",[]);  
	
	
	app.controller('mainController',['$scope', 'mainService','$window',function ($scope,mainService,$window) {
	    
		$scope.init = function () {
		};
		
		// Used to toggle the menu on small screens when clicking on the menu button
		$scope.myFunction = function(){
			var x = document.getElementById("navDemo");
		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else { 
		        x.className = x.className.replace(" w3-show", "");
		    }
		}
		
		$scope.submit = function () {
	        console.log('Submitting');
	        if ($scope.user.username && $scope.user.password ) {
	        	mainService.checkUser($scope.user)
	        	.then(function success(response) {
		        	  console.log('request succeded');
		        	  if(response.data.isValidUser){
		        		  $scope.errorMessage = '';
		        		  $window.location.href = '/loginSuccess';
		        	  }else{
		        		  $scope.errorMessage = '**Authentication Failed'; 
		        	  }
		          },
		          function error (errResponse) {
		        	  console.error('Error while checking User');
		        	  $scope.errorMessage = 'Error while checking User: ' + errResponse.errorMessage;
		        	  $scope.successMessage='';
		          });
	        }
	    }
		
		
	}]);
	
	
})();