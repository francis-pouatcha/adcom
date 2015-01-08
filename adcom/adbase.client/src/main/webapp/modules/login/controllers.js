'use strict';

angular.module('AdBase')
.controller('loginController',['$scope','loginService',function ($scope,loginService) {
		$scope.logins = function(){
			return loginService.logins;
		}; 
    }]
);