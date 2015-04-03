'use strict';

angular.module("adaptmt")

.controller('aptAptmtUpdateController',['$scope','genericResource', '$translate', 'aptAptmtsService','$location','$routeParams',
                                function($scope,genericResource, $translate, aptAptmtsService,$location,$routeParams){

        $scope.aptAptmt = {};
	
        $scope.update = function(){
            aptAptmtsService.updateAptAptmt($scope.aptAptmt).then(function(result){
            	$location.path('/aptaptmt/show/' + result.id);
            })

        };
                              
        function show(){

        	var identif = $routeParams.id;
            aptAptmtsService.loadAptAptmt(identif).then(function(result){
               $scope.aptAptmt = result;
            })

        };
                              
        function init(){
            show();
        }

        init();
}]);