(function(){

	var teacherUploadDetailApp = angular.module("teacherUploadDetailApp",['ionic-letter-avatar','angularUtils.directives.dirPagination']);  
	
	
	teacherUploadDetailApp.controller('teacherUploadDetailController',['$scope', 'teacherUploadDetailService',function ($scope,teacherUploadDetailService) {
	    
		$scope.myFunction = function(){
			var x = document.getElementById("navDemo");
		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else { 
		        x.className = x.className.replace(" w3-show", "");
		    }
		}
		
		//Added for pagination With Server Call Each Time On Each Click
	    /*$scope.pageno = 1; // initialize page no to 1
	    $scope.total_count = 0;
	    $scope.itemsPerPage = 6; //this could be a dynamic value from a drop down
	    $scope.getData = function(pageno,itemsPerPage){ // This would fetch the data on page change.
	        //ajax request to fetch data
	        uploadDetailService.getAttachmentLst(pageno,itemsPerPage,function(result){
	        	$scope.attachmentLst = result.attachmentLst; // data to be displayed on current page.
	        	$scope.total_count = result.totalCount; // total data count.
			});
	    };
	    $scope.getData($scope.pageno,$scope.itemsPerPage); // Call the function to fetch initial data on page load.
	   */
		$scope.semester = "V";
		$scope.batch = "Batch 1";
		$scope.course = "M.Tech (IT)";
		$scope.searchDetails = function(semester,batch,course){
			var csrf_token = $('input[name="_csrf"]').attr('value');
			teacherUploadDetailService.searchDetails(csrf_token,semester,batch,course,function(result){
			  $scope.attachmentLst = result.attachmentLst; 
		   });
		}
		
	
		$scope.user = {rating:1}; 
		
	}]);
	
	teacherUploadDetailApp.directive('searchDetails', function(){
		return { 
			templateUrl : 'js/directives/searchDetails.htm'
		}
	});
	
	teacherUploadDetailApp.directive('attachmentDetails', function(){
		return { 
			templateUrl : 'js/directives/attachmentDetails.htm'
		}
	});
	
	teacherUploadDetailApp.directive('marksDetails', function(){
		return { 
			templateUrl : 'js/directives/marksDetails.htm'
		}
	});
	
	teacherUploadDetailApp.directive('marksInEditMode', ['teacherUploadDetailService', function(teacherUploadDetailService){
		return { 
			restrict: "EA",
	        scope: {},
	        controller: function ($scope) {
	        	
	        	$scope.marksError = false;
	        	$scope.markPara1 = 0;
	    		$scope.markPara2 = 0;
	    		$scope.markPara3 = 0;
	    		$scope.markPara4 = 0;
	    		$scope.markPara5 = 0;
	    		$scope.total = 0;
	    		$scope.calculateTotal1 = function(updatedValue,oldValue){
	    			$scope.total = parseInt(updatedValue)+parseInt($scope.markPara2)+parseInt($scope.markPara3)+parseInt($scope.markPara4)+parseInt($scope.markPara5);
	    		}
	    		$scope.calculateTotal2 = function(updatedValue,oldValue){
	    			$scope.total = parseInt($scope.markPara1)+parseInt(updatedValue)+parseInt($scope.markPara3)+parseInt($scope.markPara4)+parseInt($scope.markPara5);
	    		}
	    		$scope.calculateTotal3 = function(updatedValue,oldValue){
	    			$scope.total = parseInt($scope.markPara1)+parseInt($scope.markPara2)+parseInt(updatedValue)+parseInt($scope.markPara4)+parseInt($scope.markPara5);
	    		}
	    		$scope.calculateTotal4 = function(updatedValue,oldValue){
	    			$scope.total = parseInt($scope.markPara1)+parseInt($scope.markPara2)+parseInt($scope.markPara3)+parseInt(updatedValue)+parseInt($scope.markPara5);
	    		}
	    		$scope.calculateTotal5 = function(updatedValue,oldValue){
	    			$scope.total = parseInt($scope.markPara1)+parseInt($scope.markPara2)+parseInt($scope.markPara3)+parseInt($scope.markPara4)+parseInt(updatedValue);
	    		}
	    		
	    		$scope.saveMarks = function(attachmentId){
	    			
		    		if($scope.markPara1 == 0 || $scope.markPara2 == 0 || $scope.markPara3 == 0
		    				|| $scope.markPara4 == 0  || $scope.markPara5 == 0 ){
		    			$('#previousMarksModal'+attachmentId).modal({'backdrop': 'static'});
		    			$.alert({
					    	    title: 'Sorry !',
					    	    icon: 'fa fa-times-circle',
					    	    content: 'Please Provide all neccessary information.',
					    	    type: 'red',
					    	    boxWidth: '35%',
					    	    useBootstrap: false,
					    	    typeAnimated: true
				    	});

		    		}else{
		    		   $scope.marksError = false;
	    			   var csrf_token = $('input[name="_csrf"]').attr('value');
	    		       var marks = $scope.total;
	    		       var markPara1 = $scope.markPara1;
	    		       var markPara2 = $scope.markPara2;
	    		       var markPara3 = $scope.markPara3;
	    		       var markPara4 = $scope.markPara4;
	    		       var markPara5 = $scope.markPara5;
	    		       var semester = $scope.$parent.semester;
	    		       var batch = $scope.$parent.batch;
	    		       var course =  $scope.$parent.course;
	    		       teacherUploadDetailService.saveMarksAndRemarks(csrf_token,attachmentId,marks,semester,batch,course,markPara1,markPara2,markPara3,markPara4,markPara5,function(result){
	    		    	    
	    		    	    $scope.markPara1 = '0';
	    		    	    $scope.markPara2 = '0';
	    		    		$scope.markPara3 = '0';
	    		    		$scope.markPara4 = '0';
	    		    		$scope.markPara5 = '0';
	    		    		$scope.total = 0;
	    		    		$.alert({
					    	    title: 'Success !',
					    	    icon: 'fa fa-check-circle-o',
					    	    content: 'Please Provide all neccessary information.',
					    	    type: 'green',
					    	    boxWidth: '35%',
					    	    useBootstrap: false,
					    	    typeAnimated: true
				    	   });
	    		    		
    		    		   console.log(JSON.stringify(result.attachmentLst));
    		    		   $scope.$parent.attachmentLst = result.attachmentLst;
    		    		   
    		    		   $('#previousMarksModal'+attachmentId).modal( "hide" );
	    			   });
		    		}	
    			   
	    	    }
	        },
			templateUrl : 'js/directives/marksInEditMode.htm'
		}
	}]);
	
	teacherUploadDetailApp.directive('marksInReadMode', function(){
		return { 
			templateUrl : 'js/directives/marksInReadMode.htm'
		}
	});
	
	teacherUploadDetailApp.directive('marksSelectOptions', function(){
		return {
	        templateUrl : 'js/directives/marksSelectOptions.htm'
	    };
	});

	teacherUploadDetailApp.filter('range', function() {
		  return function(input, total) {
		    total = parseInt(total);
		    for (var i=0; i<total; i++)
		      input.push(i);
		    return input;
		  };
	});
	
	teacherUploadDetailApp.filter("asDate", function () {
	    return function (input) {
	        return new Date(input);
	    }
	});
	
	teacherUploadDetailApp.filter('camelCase', function (){
        var camelCaseFilter = function ( input ){
            var words = input.split( ' ' );
            for ( var i = 0, len = words.length; i < len; i++ )
                words[i] = words[i].charAt( 0 ).toUpperCase() + words[i].slice( 1 );
            return words.join( ' ' );
        };
        return camelCaseFilter;
    });

	
})();



