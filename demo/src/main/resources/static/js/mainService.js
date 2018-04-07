(function(){
    
    var app = angular.module("mainApp");  
    app.service('mainService', [ '$http', function($http) {
        
    	this.checkUser = function checkUser(user){
    		return $http({
                method : 'POST',
                url : '/checkUser',
                data : user
            });
    	}
    	
    	
        /*//used for update and create
        this.addUser = function addUser(user) {
                return $http({
                    method : 'POST',
                    url : '/demo/addUser',
                    data : user
                });
        } 
        
        //used to get all data
        this.getAllUsers = function getAllUsers() {
            return $http({
                method : 'GET',
                url : '/demo/users',
            });
        } 
        
        this.editUser = function editUser(id){
            return $http({
                method : 'POST',
                url : '/demo/editUser',
                data : user
            });
        }
        
        //remove user
        this.removeUser = function removeUser(id) {
            return $http({
                method : 'DELETE',
                url : '/demo/deleteUser/'+id,
            });
        } 
        
        this.getUserById = function getUserById(id){
            return $http({
                method : 'GET',
                url : '/demo/users/'+id,
            });
        }
        
        //used for update and create
        this.updateUser = function updateUser(user) {
            return $http({
                method : 'PUT',
                url : '/demo/updateUser/'+user.id,
                data : user
            });
        } */
        
    } ]);
    
})();