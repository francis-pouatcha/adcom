'use strict';

angular.module('AdBase')
    .controller('showOuTypeController', ['$scope', 'ouTypeResource','$location','$routeParams',
        function ($scope, ouTypeResource,$location,$routeParams) {
            $scope.ouType={};
            $scope.goToList = function (){
                $location.path("/outypes/list");
            }
            
            function loadOrgUnitType(ouTypeId) {
                ouTypeResource.findById(ouTypeId).success(function(data){
                    $scope.ouType = data;
                }).error(function(){});
            }
            
            function  init(){
                loadOrgUnitType($routeParams.ouTypeId)
            };
            init();
}]);