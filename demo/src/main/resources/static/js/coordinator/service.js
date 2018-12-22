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
        
    }]);
    
})();