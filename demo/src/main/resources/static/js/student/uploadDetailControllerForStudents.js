(function(){

	var uploadDetailApp = angular.module("uploadDetailApp",['ionic-letter-avatar','angularUtils.directives.dirPagination']);  
	
	
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
	
	uploadDetailApp.directive('ratingDetails', function(){
		return { 
			templateUrl : 'js/directives/ratingDetails.htm'
		}
	});
	
	uploadDetailApp.directive('ratingReviews', function(){
		return { 
			templateUrl : 'js/directives/ratingReviews.htm'
		}
	});
	
	uploadDetailApp.directive("starRating", function() {
		  
		return {
			    restrict : "EA",
			    template : "<ul class='rating' ng-class='{readonly: readonly}'>" +
			               "  <li style='font-size: 20px;text-shadow: .05em .05em #aaa;' ng-repeat='star in stars' ng-class='star' ng-click='toggle($index)'>" +
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
	
	uploadDetailApp.filter('range', function() {
		  return function(input, total) {
		    total = parseInt(total);
		    for (var i=0; i<total; i++)
		      input.push(i);
		    return input;
		  };
	});
	
	uploadDetailApp.filter("asDate", function () {
	    return function (input) {
	        return new Date(input);
	    }
	});
	
	uploadDetailApp.filter('camelCase', function (){
        var camelCaseFilter = function ( input ){
            var words = input.split( ' ' );
            for ( var i = 0, len = words.length; i < len; i++ )
                words[i] = words[i].charAt( 0 ).toUpperCase() + words[i].slice( 1 );
            return words.join( ' ' );
        };
        return camelCaseFilter;
    });

	
})();



