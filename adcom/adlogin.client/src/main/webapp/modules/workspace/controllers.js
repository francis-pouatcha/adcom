'use strict';

angular.module('AdLogin')
.controller('workspaceController',
    ['$scope','workspaceService','$location','adUtils',function ($scope,workspaceService,$location, adUtils) {
    	$scope.workspaces=workspaceService.workspaces;
    	$scope.loadWorkspaces = function(){
    		workspaceService.loadWorkspaces(function(){}, function(){});
    	};
    	$scope.switchWorkspace = function(identif){
    		
    		workspaceService.switchWorkspace(identif, function(data, status, headers, config){
    			adUtils.loadApp(data.content);
//    			var content = data.content;
//    			var absUrl = $location.protocol() + '://' + $location.host();
//    			var port = $location.port();
//    			if(port && port!=80 && port!=443){
//    				absUrl += ':'+port;
//    			}
//    			absUrl +=content;
//    			$location.absUrl(absUrl);
//    			window.location.href=absUrl;
    		}, function(data, status, headers, config){
    			$location.absUrl('/login');
    		});
    	};
    }]
);