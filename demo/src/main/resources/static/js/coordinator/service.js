(function(){
    
    var coordinatorApp = angular.module("coordinatorApp");  
    
    coordinatorApp.service('coordinatorService', [ '$http', function($http) {
        
    	this.saveCoordinatorStartAndEndTime = function(csrf_token,coordinatorDaterange,cb){
    		 
	       	 $http({
	       		 url : '/coord-save-submission-start-and-end-date',
	       	     method : 'POST',
		       	 headers: {
		       		'X-CSRF-Token': csrf_token
		         },
	       	     params: { coordinatorDaterange:coordinatorDaterange }
	       	 }).then(function(response){
	       		 cb(response.data);
	       	 },function(response){
	       		 $log.error("ERROR Occurred"); 
	       	 });
   	     }
    	
    	this.getNewSecretCode = function(cb){
    		$http({
	       		 url : '/coord-get-new-secret-code',
	       	     method : 'GET'		 
	       	 }).then(function(response){
	       		 cb(response.data);
	       	 },function(response){
	       		 $log.error("ERROR Occurred"); 
	       	 });
  	     }
    	
    	this.saveCoordinatorDocument = function(csrf_token,formdata,cb){
   		 
	       	 $http({
	       		 url : '/coord-save-document',
	       	     method : 'POST',
		       	 headers: {
		       		'X-CSRF-Token': csrf_token,
		       		'Content-Type': undefined
		         },
	       	     data: formdata
	       	 }).then(function(response){
	       		 cb(response.data);
	       	 },function(response){
	       		 $log.error("ERROR Occurred"); 
	       	 });
  	     }
        
    }]);
    
})();