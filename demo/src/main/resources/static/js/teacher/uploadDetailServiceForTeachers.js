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
    	
    	
    	this.saveRatingAndComment = function(csrf_token,attachmentId,rating,comment,cb){
    		
	       	 $http({
	       		 url : 'http://localhost:9099/std-save-rating-and-comment',
	       	     method : 'POST',
		       	 headers: {
		       		'X-CSRF-Token': csrf_token
		         },
	       	     params: { attachmentId:attachmentId ,rating:rating ,comment:comment }
	       	 }).then(function(response){
	       		 cb(response.data);
	       	 },function(response){
	       		 $log.error("ERROR Occurred"); 
	       	 });
   	     }
        
    }]);
    
})();