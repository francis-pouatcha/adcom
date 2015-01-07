'use strict';

angular.module('AdLogin')
.factory('workspaceService',
    ['$http','sessionManager','workspaceResource',
    function ($http,sessionManager,workspaceResource) {
        var service = {};
        service.workspaces=[];
        service.loadWorkspaces = function(successCallback, errorCallback){
    		workspaceResource.findUserWorkspaces()
    			.success(function(data, status, headers, config){
    				service.workspaces = data;
    				successCallback(data, status, headers, config);
    			}).error(function(data, status, headers, config){
    				errorCallback(data, status, headers, config);
    			});
    	};
    	return service;
    }]
);
