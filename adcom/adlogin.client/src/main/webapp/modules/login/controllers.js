'use strict';

angular.module('AdLogin')

.controller('loginController',
    ['$scope', '$rootScope', '$location', 'authService','sessionData','workspaceService',
    function ($scope, $rootScope, $location, authService,sessionData,workspaceService) {
        $scope.login = function () {
        	authService.login($scope.username, $scope.password, successCallback, erorCallback);
        };
        function successCallback(data, status, headers, config){
            $location.path('/workspaces');
        }
        function erorCallback (data, status, headers, config){
			authService.clearCredentials();
			$scope.password = '';
        };
    }]
);