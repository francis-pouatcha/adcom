'use strict';

angular.module('AdBase')
    .controller('listOuTypeController', ['$scope', 'ouTypeResource',
        function ($scope, ouTypeResource) {

            $scope.delete = function (ouId) {
                return ouTypeResource.delete(ouId);
            };
            $scope.search = function () {
                return ouTypeResource.search(organisationUnit);
            };
}]);