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
	    }

		$scope.showLoading = false;
		$scope.isSuccess = undefined;
		$scope.startAndEndTimeResponseMessage = 'Oops! Failed To Update';
		$scope.saveCoordinatorStartAndEndTime = function(){
		   $scope.showLoading = true;
		   var csrf_token = $('input[name="_csrf"]').attr('value');
	       var coordinatorDaterange = $('#coordinatorDaterange').val();
	       coordinatorService.saveCoordinatorStartAndEndTime(csrf_token,coordinatorDaterange,function(result){
		        if(result.startAndEndTimeResponseMessage){
		        	$scope.showLoading = false;
		        	$scope.isSuccess = true;
		        	$scope.coordinatorDaterange = result.coordinatorDaterange;
		        	$scope.startAndEndTimeResponseMessage = result.startAndEndTimeResponseMessage;
		        	$timeout( function(){ $scope.callAtTimeout(); }, 5000);
		        }else{
		        	$scope.showLoading = false;
		        	$scope.isSuccess = false;
		        	$scope.coordinatorDaterange = coordinatorDaterange;
		        } 
		   });
		  
	    }
	    
	}]);
	
})();



