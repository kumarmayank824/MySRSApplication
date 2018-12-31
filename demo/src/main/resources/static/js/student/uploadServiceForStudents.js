(function(){
	  
	    var fileUploadApp = angular.module('fileUploadApp');   
	    fileUploadApp.service('uploadService', ['$q','$http', function ($q,$http) {
		   
	       var deffered = $q.defer();
		   var responseData;
		   this.uploadFileToUrl = function(fileObj, uploadUrl){
		      var fd = new FormData();
		      fd.append('file', fileObj.myFile);
		      fd.append('title', fileObj.title);
		      fd.append('category', fileObj.category);
		      fd.append('description', fileObj.description);
		       return $http.post(uploadUrl, fd, {
		          transformRequest: angular.identity,
		          headers: { 'Content-Type' : undefined}
		       })
			   .success(function(response){
				   console.log(response);
				   responseData = response;
				   deffered.resolve(response);
				   return deffered.promise;
			   })
			   .error(function(error){
				   deffered.reject(error);
				   return deffered.promise;
			   });
		  }
		   
		  this.getResponse = function() {
		 	return responseData;
		  }
		  
		}]);
})();