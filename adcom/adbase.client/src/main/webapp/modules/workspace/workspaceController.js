(function () {
    'use strict';
    angular.module('AdBase').controller('workspaceController',workspaceController);

    workspaceController.$inject = ['$scope', 'workspaceService','$location','$routeParams'];

    function workspaceController($scope,workspaceService, $location, $routeParams){
        var self = this ;
        self.workspacesDto = [];
        self.selectedWorkspace = {};
        self.saveWorkspace = saveWorkspace;
        self.error = "";


        init();

        function init(){
            var loginName = $routeParams.loginName;
            loadWorkspace(loginName);
        }


        function saveWorkspace(){


        };

        function loadWorkspace(loginName){


        };
    };



})();