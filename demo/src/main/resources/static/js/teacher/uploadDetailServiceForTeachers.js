(function(){
    
    var teacherUploadDetailApp = angular.module("teacherUploadDetailApp");  
    
    teacherUploadDetailApp.service('teacherUploadDetailService', [ '$http', function($http) {
        
    	this.searchDetails = function(csrf_token,semester,batch,course,cb){
	       	 $http({
	       		 url : '/tch-search-details',
	       		 method : 'POST',
		       	 headers: {
		       		'X-CSRF-Token': csrf_token
		         },
	       	     params: { semester:semester ,batch:batch ,course:course }	 
	       	 }).then(function(response){
	       		 cb(response.data);
	       	 },function(response){
	       		 $log.error("ERROR Occurred"); 
	       	 });
  	    }
    	
    	
    	this.saveMarksAndRemarks = function(csrf_token,attachmentId,marks,remarks,cb){
    		
	       	 $http({
	       		 url : 'http://localhost:9099/tch-save-marks-and-remarks',
	       	     method : 'POST',
		       	 headers: {
		       		'X-CSRF-Token': csrf_token
		         },
	       	     params: { attachmentId:attachmentId ,marks:marks ,remarks:remarks }
	       	 }).then(function(response){
	       		 cb(response.data);
	       	 },function(response){
	       		 $log.error("ERROR Occurred"); 
	       	 });
   	     }
        
    }]);
    
})();