'use strict';

angular.module("adaptmt")

.controller('aptAptmtLoginUpdateController',['$scope','genericResource', '$translate', 'aptAptmtLoginsService','$location','$routeParams',
                                function($scope,genericResource, $translate, aptAptmtLoginsService,$location,$routeParams){

        $scope.aptAptmtLogin = {};
        
        var self = this;
        self.aptAptmtLogins = {};
    	self.searchInput = {};
    	self.error = {};
	
        $scope.update = function(){
            aptAptmtLoginsService.updateAptAptmtLogin($scope.aptAptmtLogin).then(function(result){
            	$location.path('/aptaptmtLogin/show/' + result.id);
            })

        };
                              
        function show(){

        	var identif = $routeParams.id;
            aptAptmtLoginsService.loadAptAptmtLogin(identif).then(function(result){
               $scope.aptAptmtLogin = result;
            })

        };
                              
        function init(){
        	
            show();
            
            self.searchInput = {
                    entity:{},
                    fieldNames:[],
                    start:0,
                    max:$scope.itemPerPage
                }
              
           aptAptmtLoginsService.loadAptAptmtLogins(self.searchInput).then(function(entitySearchResult) {
        	   self.aptAptmtLogins = entitySearchResult.resultList;
                });
            
        }

        init();
}]);