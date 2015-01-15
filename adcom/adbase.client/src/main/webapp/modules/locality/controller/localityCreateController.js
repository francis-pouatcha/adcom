(function () {
    'use strict';
    angular.module('AdBase').controller('localityCreateController',localityCreateController);

    localityCreateController.$inject = ['$scope', 'localityService','$location'];

    function localityCreateController($scope,localityService, $location){
        var self = this ;
        self.locality = {};
        self.create = create;
        self.error = "";

        function create(){
            localityService.create(self.locality).then(function(result){
                $location.path('/locality/show/'+result.id);
            },function(error){
                self.error = error;
            })
        };
    };



})();