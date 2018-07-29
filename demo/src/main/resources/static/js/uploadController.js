(function(){
	
	 var fileUploadApp = angular.module('fileUploadApp', []);

	 fileUploadApp.controller('uploadController', ['$scope', '$q', 'uploadService', function($scope, $q, uploadService){
		 
		 $scope.dataUpload = true;
		 $scope.errVisibility = false;
		 
		 $scope.submitFileForm = function(){
		     var fileObj = $scope.file;
		     console.log('file is ' );
		     console.dir(fileObj);
		      var uploadUrl = "/std-file-Upload";
		      uploadService.uploadFileToUrl(fileObj, uploadUrl).then(function(result){
			       $scope.errors = uploadService.getResponse();
			       console.log($scope.errors);
			       $scope.errVisibility = true;
		       }, function(error) {
		       	  alert('error');
		       })
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
	      
	 }]);
	 
	 fileUploadApp.directive('fileModel', ['$parse', function ($parse) {
		 return {
		 	restrict: 'A',
		 	link: function(scope, element, attrs) {
		 		var model = $parse(attrs.fileModel);
				var modelSetter = model.assign;
				element.bind('change', function(){
			 		scope.$apply(function(){
			 			modelSetter(scope, element[0].files[0]);
			 		});
			    });
		    }
	      };
	 }]);
	
})();

