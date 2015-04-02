'use strict';

angular.module("adaptmt")

.controller('aptAptmtShowController',['$scope','genericResource', '$translate', 'aptAptmtsService','$location','$routeParams',
                                function($scope,genericResource, $translate, aptAptmtsService,$location,$routeParams){

        $scope.aptAptmt = {};
	
        function show(){

            var identif = $routeParams.identif;

            aptAptmtsService.loadAptAptmt(identif).then(function(result){

               $scope.aptAptmt = result;

            })

        };
                              
        function init(){
            show();
        }

    init();
}]);