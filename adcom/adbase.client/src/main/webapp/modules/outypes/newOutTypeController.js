'use strict';

angular.module('AdBase')
    .controller('newOuTypeController', ['$scope', 'ouTypeResource', '$location'
        function ($scope, ouTypeResource,$location) {
            //variables
            $scope.parents = [];
            $scope.ouType = {};
            
            //controllers's methods
            $scope.create = function () {
                ouTypeResource.create($scope.ouType).success(function(data){
                    $location.path('/outypes/show/'+data.id);
                }).error(function(){});
            };
            $scope.update = function () {
                return ouTypeResource.update($scope.ouType);
            };
            
            //others functions
            function loadParentsOrgUnit() {
                ouTypeResource.findActifsFromNow().success(function (data) {
                    $scope.parents = data;
                }).error(function () {});
            }
            
            //init controller
            function init() {
                loadParentsOrgUnit();
            }
            init();
    }]);