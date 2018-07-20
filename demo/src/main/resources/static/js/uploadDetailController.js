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
		
		$scope.user = {rating:1}; 
		
		$scope.saveRatings = function(attachmentId){
		   var csrf_token = $('input[name="_csrf"]').attr('value');
	       var rating = $('#rating'+attachmentId).text();
	       var comment = $('#comment'+attachmentId).val();
	       uploadDetailService.saveRatingAndComment(csrf_token,attachmentId,rating,comment,function(result){
		        if(!result.showRatingLink){
		        	$scope.user = {rating:1};
		        	$('#ratingModalBtn'+attachmentId).click(function () {return false;});
		        	$('#ratingModalBtn'+attachmentId).removeAttr("href");
		        } 
		   });
		  
	    }
	    
	}]);
	
	uploadDetailApp.directive('attachmentDetails', function(){
		return { 
			templateUrl : 'js/directives/attachmentDetails.htm'
		}
	});
	
	
	uploadDetailApp.directive("starRating", function() {
		  
		return {
			    restrict : "EA",
			    template : "<ul class='rating' ng-class='{readonly: readonly}'>" +
			               "  <li ng-repeat='star in stars' ng-class='star' ng-click='toggle($index)'>" +
			               "    <i class='fa fa-star'></i>" + //&#9733
			               "  </li>" +
			               "</ul>",
			    scope : {
				      ratingValue : "=ngModel",
				      max : "=?", //optional: default is 5
				      onRatingSelected : "&?",
				      readonly: "=?"
			    },
			    link : function(scope, elem, attrs) {
				      if (scope.max == undefined) { scope.max = 5; }
				      function updateStars() {
				        scope.stars = [];
				        for (var i = 0; i < scope.max; i++) {
				          scope.stars.push({
				            filled : (i < scope.ratingValue.rating)
				          });
				        }
				      };
				      scope.toggle = function(index) {
				        if (scope.readonly == undefined || scope.readonly == false){
				          scope.ratingValue.rating = index + 1;
				        }
				      };
				      scope.$watch("ratingValue.rating", function(oldVal, newVal) {
				        if (newVal) { updateStars(); }
				      });
		         }
		  };  
	});
	
})();



