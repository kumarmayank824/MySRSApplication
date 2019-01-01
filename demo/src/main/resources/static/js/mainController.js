(function(){
	 
	var app = angular.module("mainApp",['ionic-letter-avatar']);  
	
	
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
	    
	    //Email Validation For Register Page
	    function isValidEmailAddress(emailAddress,pattern) {
    	   return pattern.test(emailAddress.toLowerCase());  // returns a boolean 
	    }
	    
	    var minLength = 8;
	    $scope.signInTypeModelValue = 'Student';
	    $scope.isEmailIdValidationError = false;
	    $scope.emailIdModelChanged = function(emailIdModel) {
	    	if (emailIdModel && ( emailIdModel.indexOf('@') != -1 || emailIdModel.length >= minLength ) && $scope.signInTypeModelValue == 'Student'){
	    		var wipro_email_regex = /^\w+([\.-]?\w+)*@\wipro(\.\w{2,3})+$/;
	    		console.log("Email  " + emailIdModel + "     isValid " + isValidEmailAddress(emailIdModel,wipro_email_regex));
	    		if(!isValidEmailAddress(emailIdModel,wipro_email_regex)){
	    			$scope.emailErrorMessage = '* Please note, only WIPRO Mail id is allowed for sign in';
	    			$scope.isEmailIdValidationError = true;
	    		}else{
	    			$scope.emailErrorMessage = '';
	    			$scope.isEmailIdValidationError = false;
	    		}
	    		
	    	}else{
    			$scope.emailErrorMessage = '';
    			$scope.isEmailIdValidationError = false;
    		}
	    }
	    
	    $scope.$watch('signInTypeModelValue', function(value) {
	        if( value == 'Teacher'){
	        	$scope.emailErrorMessage = '';
    			$scope.isEmailIdValidationError = false;
	        }else{
	        	$scope.emailIdModelChanged($scope.emailIdModel);
	        }
	    });
	    
	    //To check the forgot password rules
	    $scope.forgotPassCheckClass = 'forgotPassCheck';
	    $scope.condition1 = false;
	    $scope.condition2 = false;
	    $scope.condition3 = false;
	    $scope.isDisabled = true;
	    
	    $scope.checkPasswordRules = function(forgotPassword) {
	    	
	    	var strongRegex = new RegExp("^(?=.*[0-9])(?=.*[@#\$%\^&])(?=\\S+$).{8,}$");
	    	var conditionRegex1 = new RegExp("^(?=\\S+$).{8,}$");
	    	var conditionRegex2 = new RegExp("^(?=.*[0-9])");
	    	var conditionRegex3 = new RegExp("^(?=.*[@#\$%\^&])");
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
                if( !$scope.condition1 || !$scope.condition2 || !$scope.condition3 ){
                	$scope.isDisabled = true;
                }
            }
	    }
	    
	    $scope.showProfileInEditMode = false;
	    $scope.loadProfileInEditMode = function() {
	    	$scope.showProfileInEditMode = true;
	    }
	    
	    $scope.backToReadMode = function() {
	    	$scope.showProfileInEditMode = false;
	    }
	    
	    
	    // Set the default value of inputTypePassword
	    $scope.inputTypePassword = 'password';
	    $scope.titlePassword = 'Show Password';
	    $scope.isPasswordDanger = false;
	    // Hide & show password function
	    $scope.hideShowPassword = function(){
	        if ($scope.inputTypePassword == 'password'){
	            $scope.inputTypePassword = 'text';
	            $scope.titlePassword = 'Hide Password';
	            $scope.isPasswordDanger = true;
		    }else{
		        $scope.inputTypePassword = 'password';
		        $scope.titlePassword = 'Show Password';
		        $scope.isPasswordDanger = false;
		    }    
	    };
	    
	    // Set the default value of inputTypeConfirmPassword
	    $scope.inputTypeConfirmPassword = 'password';
	    $scope.titleConfirmPassword = 'Show Confirm Password';
	    $scope.isConfirmPasswordDanger = false;
	    // Hide & show password function
	    $scope.hideShowConfirmPassword = function(){
	        if ($scope.inputTypeConfirmPassword == 'password'){
	    	    $scope.inputTypeConfirmPassword = 'text';
		        $scope.titleConfirmPassword = 'Hide Confirm Password'; 
		        $scope.isConfirmPasswordDanger = true;
	        }else{
	            $scope.inputTypeConfirmPassword = 'password';
	            $scope.titleConfirmPassword = 'Show Confirm Password';
	            $scope.isConfirmPasswordDanger = false;
	        }  
	    };
	    
	}]);
	
	app.directive('noEmailValidation', function() {
	    return {
	      restrict: 'A',
	      require: 'ngModel',
	      link: function(scope, elm, attr, ctrl) {
	        ctrl.$validators['email'] = function() {
	          return true;
	        };
	      }
	    }
	  });
	
})();