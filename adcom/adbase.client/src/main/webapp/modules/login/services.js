'use strict';

angular.module('Login')
.factory('loginService',
    ['loginResource',
    function (loginResource) {
        var service = {};
        service.logins=[];
        service.loadLogins = function(successCallback, erorCallback){
        	loginResource.listAll(0,100)
    			.success(function(data, status, headers, config){
    				service.logins = data;
    				successCallback(data, status, headers, config);
    			}).error(function(data, status, headers, config){
    				erorCallback(data, status, headers, config);
    			});
    	};
    	return service;
    }]
);
