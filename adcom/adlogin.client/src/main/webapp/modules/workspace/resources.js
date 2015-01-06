﻿'use strict';

angular.module('AdLogin')
.factory('workspaceResource',['$http', function ($http) {
        var service = {};
        service.findUserWorkspaces = function(){
            return $http.get('/adbase.server/rest/userworkspaces/findUserWorkspaces');
        };
        service.switchWorkspace = function(identif){
            return $http.post('/adbase.server/rest/userworkspaces/switchWorkspace', {'content':identif});
        };
        return service;
    }]
);
