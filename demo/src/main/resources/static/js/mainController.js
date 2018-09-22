(function(){
	 
	var app = angular.module("mainApp",[]);  
	
	
	app.controller('mainController',['$scope', 'mainService','$window',function ($scope,mainService,$window) {
	    
		$scope.init = function () {
			mainService.getGraphDetails(function(result){
				createChart(result);
			});
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
		
		$scope.signInType = "Student";
		$scope.isChecked = true;
	    $scope.checkSignInType = function(isChecked) {
	      if (isChecked) {
	    	  $scope.signInType = "Student";
	      }else{
	    	  $scope.signInType = "Teacher";
	      }
	    };
	    
	    
	    function createChart(result) {
	    	
	    	Highcharts.chart('container', {
	    	    title: {
	    	        text: 'Proportion chart'
	    	    },
	    	    xAxis: {
	    	        categories: result.categories
	    	    },
	    	    labels: {
	    	        items: [{
	    	            html: 'Total Upload ' + '<b>' + result.totalCount + '</b>',
	    	            style: {
	    	                left: '50px',
	    	                top: '-42px',
	    	                color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
	    	            }
	    	        }]
	    	    },
	    	    series: [{
	    	        type: 'column',
	    	        name: 'Upload Count',
	    	        data: result.graphDetails,
	    	        showInLegend: false,
	    	        dataLabels: {
	    	            enabled: false
	    	        }
	    	    },{
	    	        type: 'spline',
	    	        name: 'Upload Count',
	    	        data: result.graphDetails,
	    	        marker: {
	    	            lineWidth: 2,
	    	            lineColor: Highcharts.getOptions().colors[3],
	    	            fillColor: 'white'
	    	        },
	    	        showInLegend: false,
	    	        dataLabels: {
	    	            enabled: false
	    	        }
	    	    }, {
	    	        type: 'pie',
	    	        name: 'Upload Count',
	    	        data: result.graphDetails,
	    	        center: [815, 50],
	    	        size: 120,
	    	        showInLegend: false,
	    	        dataLabels: {
	    	            enabled: false
	    	        }
	    	    }]
	    	});
	    	
	    	
	    }
	    
	    //To check the forgot password rules
	    $scope.forgotPassCheckClass = 'forgotPassCheck';
	    $scope.condition1 = false;
	    $scope.condition2 = false;
	    $scope.condition3 = false;
	    $scope.isDisabled = true;
	    
	    $scope.checkPasswordRules = function(forgotPassword) {
	    	
	    	var strongRegex = new RegExp("^(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})");
	    	var conditionRegex1 = new RegExp("^(?=.{8,})");
	    	var conditionRegex2 = new RegExp("^(?=.*[0-9])");
	    	var conditionRegex3 = new RegExp("^(?=.*[!@#\$%\^&\*])");
	    	if(strongRegex.test(forgotPassword)) {
	    		$scope.forgotPassCheckClass = 'forgotPassCheck2';
	    		$scope.condition1 = true;
	    	    $scope.condition2 = true;
	    	    $scope.condition3 = true;
	    	    $scope.isDisabled = false;
            } else if(!forgotPassword && forgotPassword === ''){
            	$scope.forgotPassCheckClass = 'forgotPassCheck';
            	$scope.condition1 = false;
        	    $scope.condition2 = false;
        	    $scope.condition3 = false;
        	    $scope.isDisabled = true;
            } else {
                if(conditionRegex1.test(forgotPassword)){
                	$scope.forgotPassCheckClass = 'forgotPassCheck2';
                	$scope.condition1 = true;
                }else{
                	$scope.condition1 = false;
                }
                if(conditionRegex2.test(forgotPassword)){
                	$scope.forgotPassCheckClass = 'forgotPassCheck2';
                	$scope.condition2 = true;
                }else{
                	$scope.condition2 = false;
                }
                if(conditionRegex3.test(forgotPassword)){
                	$scope.forgotPassCheckClass = 'forgotPassCheck2';
                	$scope.condition3 = true;
                }else{
                	$scope.condition3 = false;
                }
            }
	    }
	    
	}]);
	
	
	
	
})();