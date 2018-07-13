(function(){
    
    var uploadDetailApp = angular.module("uploadDetailApp");  
    
    uploadDetailApp.service('uploadDetailService', [ '$http', function($http) {
        
    	//Added for pagination With Server Call Each Time On Each Click
    	/*this.getAttachmentLst = function(pageno,itemsPerPage,cb){
	       	 $http({
	       		 url : 'http://localhost:9099/to-apiAttachment/'+itemsPerPage+'/'+pageno,
	       	     method : 'GET'		 
	       	 }).then(function(response){
	       		 cb(response.data);
	       	 },function(response){
	       		 $log.error("ERROR Occurred"); 
	       	 });
    	}*/
    	
    	this.getAttachmentLst = function(cb){
	       	 $http({
	       		 url : 'http://localhost:9099/to-apiAttachment',
	       	     method : 'GET'		 
	       	 }).then(function(response){
	       		 cb(response.data);
	       	 },function(response){
	       		 $log.error("ERROR Occurred"); 
	       	 });
   	     }
        
    }]);
    
})();