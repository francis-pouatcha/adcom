'use strict';

angular.module('AdLogin')

.factory('authService',
    ['sessionData','workspaceService',
    function (sessionData, workspaceService) {
        var service = {};
        service.login = function (username, password, successCallback, erorCallback) {
        	sessionData.opr='login';
        	sessionData.lgn=username;
        	sessionData.pwd=password;
        	sessionData.wrk='login';
        	workspaceService.loadWorkspaces(
    			function(data, status, headers, config){
    				delete sessionData.opr;
    				sessionData.usr=headers("X-USER-SESSION");
    				sessionData.trm=headers("X-TERM-SESSION");
    				successCallback(data, status, headers, config);
    			}, 
    			erorCallback);
        };
        service.clearCredentials = function () {
        	delete sessionData.opr;
        	delete sessionData.lgn;
        	delete sessionData.wrk;
        	delete sessionData.pwd;
        };
        
        return service;
    }]
);
