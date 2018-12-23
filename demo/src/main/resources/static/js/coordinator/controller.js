(function(){

	var coordinatorApp = angular.module("coordinatorApp",[]);  
	
	
	coordinatorApp.controller('coordinatorController',['$scope', 'coordinatorService','$timeout',function ($scope,coordinatorService,$timeout) {
	    
		$scope.myFunction = function(){
			var x = document.getElementById("navDemo");
		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else { 
		        x.className = x.className.replace(" w3-show", "");
		    }
		}
		
		$scope.callAtTimeout = function() {
			$scope.startAndEndTimeResponseMessage = '';
			$scope.newCodeResponseMessage = '';
	    }

		$scope.showLoading = false;
		$scope.isStartAndEndTimeResponseSuccess = undefined;
		$scope.saveCoordinatorStartAndEndTime = function(){
		   $scope.showLoading = true;
		   var csrf_token = $('input[name="_csrf"]').attr('value');
	       var coordinatorDaterange = $('#coordinatorDaterange').val();
	       coordinatorService.saveCoordinatorStartAndEndTime(csrf_token,coordinatorDaterange,function(result){
		        if(result.startAndEndTimeResponseMessage){
		        	$scope.showLoading = false;
		        	$scope.isStartAndEndTimeResponseSuccess = true;
		        	$scope.coordinatorDaterange = result.coordinatorDaterange;
		        	$scope.startAndEndTimeResponseMessage = result.startAndEndTimeResponseMessage;
		        	$timeout( function(){ $scope.callAtTimeout(); }, 5000);
		        }else{
		        	$scope.showLoading = false;
		        	$scope.isStartAndEndTimeResponseSuccess = false;
		        	$scope.coordinatorDaterange = coordinatorDaterange;
		        	$scope.startAndEndTimeResponseMessage = "Oops! Failed To Submit Submission Dates";
		        	$timeout( function(){ $scope.callAtTimeout(); }, 5000);
		        } 
		   });
	    }
		
		$scope.isNewSecretCodeResponseSuccess = undefined;
		$scope.newCodeResponseMessage = 'Oops! Failed To Generate Secret Code';
		$scope.getNewSecretCode = function() {
			$scope.showLoading = true;
			coordinatorService.getNewSecretCode(function(result){
				if(result.newSecretCode){
					$scope.showLoading = false;
		        	$scope.isNewSecretCodeResponseSuccess = true;
					$scope.newSecretCode = result.newSecretCode;
					$scope.newCodeResponseMessage = result.newCodeSuccessMessage;
					$timeout( function(){ $scope.callAtTimeout(); }, 5000);
				}else{
		        	$scope.showLoading = false;
		        	$scope.isNewSecretCodeResponseSuccess = false;
		        	$timeout( function(){ $scope.callAtTimeout(); }, 5000);
		        } 
		        
			});
		}
		
	}]);
	
})();



