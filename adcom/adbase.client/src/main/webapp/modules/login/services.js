'use strict';

angular.module('Login')
.factory('loginService',
    ['loginResource','$cookieStore','sessionManager',
    function (loginResource,$cookieStore,sessionManager) {
        var service = {};
        service.logins=[];
        service.loadLogins = function(successCallback, errorCallback){
        	loginResource.listAll(0,100)
    			.success(function(data, status, headers, config){
    				service.logins = data;
    				successCallback(data, status, headers, config);
    			}).error(function(data, status, headers, config){
    				if(typeof errorCallback !== 'undefined')
    					errorCallback(data, status, headers, config);
    			});
    	};
    	return service;
    }]
);
