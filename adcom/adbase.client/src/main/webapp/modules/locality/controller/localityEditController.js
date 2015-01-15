(function () {
    'use strict';
    angular.module('AdBase').controller('localityEditController',localityEditController);

    localityEditController.$inject = ['$scope', 'localityService','$location','$routeParams'];

    function localityEditController($scope,localityService, $location,$routeParams){
        var self = this ;
        self.locality = {};
        self.edit = edit;
        self.error = "";

        load();
        
        function edit(){
            localityService.update(self.locality).then(function(result){
                $location.path('/locality/show/'+result.id);
            },function(error){
                self.error = error;
            })
        };

        function load(){
            var identif = $routeParams.identif ;
            localityService.loadOne(identif).then(function(result){
                self.locality = result;
            },function(error){
                self.error = error;
            })
        };
    };



})();