(function(){

	var uploadDetailApp = angular.module("uploadDetailApp",['angularUtils.directives.dirPagination']);  
	
	
	uploadDetailApp.controller('uploadDetailController',['$scope', 'uploadDetailService',function ($scope,uploadDetailService) {
	    
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
	    
		uploadDetailService.getAttachmentLst(function(result){
	        $scope.attachmentLst = result.attachmentLst; 
		});
	   	
	}]);
	
	uploadDetailApp.directive('attachmentDetails', function(){
		return { 
			templateUrl : 'js/directives/attachmentDetails.htm'
		}
	});
	
	/*uploadDetailApp.filter('moreOrLessFilter', function () {
		  return function (item) {
			  
			  var newStr = '';
			  if(item.length > 40){
				  for (var i = 0; i < item.length; i++) {
			    	  if(i != 0 && i%40 == 0){
			    		 newStr += item.charAt(i) + '<br>'; 
			    	  }else{
			    		  newStr += item.charAt(i); 
			    	  }
			      }  
			  }
			  return newStr;
		  }	  
	});*/
	
})();



