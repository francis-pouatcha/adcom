'use strict';

angular.module('AdLogin')
.controller('workspaceController',
    ['$scope','workspaceService','$location','adUtils','$cookieStore','sessionData',
     function ($scope,workspaceService,$location, adUtils,$cookieStore,sessionData) {
    	$scope.workspaces=workspaceService.workspaces;
    	$scope.loadWorkspaces = function(){
    		workspaceService.loadWorkspaces(function(){}, function(){});
    	};
    	$scope.switchWorkspace = function(identif){
    		workspaceService.switchWorkspace(identif, function(data, status, headers, config){
    			$cookieStore.put('trm',sessionData.trm);
    			$cookieStore.put('usr',sessionData.usr);
    			adUtils.loadApp(data.content);
    		}, function(data, status, headers, config){
    			$location.absUrl('/login');
    		});
    	};
    }]
);