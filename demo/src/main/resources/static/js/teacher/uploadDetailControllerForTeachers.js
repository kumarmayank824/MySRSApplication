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
		
		$scope.saveMarks = function(attachmentId){
		   var csrf_token = $('input[name="_csrf"]').attr('value');
	       var marks = $('#marks'+attachmentId).val();
	       var remarks = $('#remarks'+attachmentId).val();
	       teacherUploadDetailService.saveMarksAndRemarks(csrf_token,attachmentId,marks,remarks,function(result){
		        /*if(!result.showRatingLink){
		        	$scope.user = {rating:1};
		        	$('#ratingModalBtn'+attachmentId).click(function () {return false;});
		        	$('#ratingModalBtn'+attachmentId).removeAttr("href");
		        }*/ 
		   });
		  
	    }
		 
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
	
	teacherUploadDetailApp.directive('marksInEditMode', function(){
		return { 
			templateUrl : 'js/directives/marksInEditMode.htm'
		}
	});
	
	teacherUploadDetailApp.directive('marksInReadMode', function(){
		return { 
			templateUrl : 'js/directives/marksInReadMode.htm'
		}
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



