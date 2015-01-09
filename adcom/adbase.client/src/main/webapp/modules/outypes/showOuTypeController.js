'use strict';

angular.module('AdBase')
    .controller('listOuTypeController', ['$scope', 'ouTypeResource','$location',
        function ($scope, ouTypeResource,$location) {

            $scope.goToList = function (){
                $location.path("/outypes/list");
            }
            
            function loadOrgUnitType(ouTypeId) {
                ouTypeResource.findById(ouTypeId).success(function(data){
                    
                }).error(function(){});
            }
}]);