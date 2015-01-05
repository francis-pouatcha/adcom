'use strict';

angular.module('AdLogin')
.factory('workspaceService',
    ['$http','sessionData','workspaceResource',
    function ($http,sessionData,workspaceResource) {
        var service = {};
        service.workspaces=[];
        service.loadWorkspaces = function(successCallback, erorCallback){
    		workspaceResource.findUserWorkspaces()
    			.success(function(data, status, headers, config){
    				service.workspaces = data;
    				successCallback(data, status, headers, config);
    			}).error(function(data, status, headers, config){
    				erorCallback(data, status, headers, config);
    			});
    	};
    	service.switchWorkspace = function(identif, successCallback, erorCallback){
        	sessionData.opr='wsout';
        	sessionData.wrk=identif;
    		workspaceResource.switchWorkspace(identif)
			.success(function(data, status, headers, config){
				successCallback(data, status, headers, config);
			}).error(function(data, status, headers, config){
				erorCallback(data, status, headers, config);
			});
    	};
    	return service;
    }]
);
