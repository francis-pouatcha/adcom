(function () {
    'use strict';
    angular.module('AdBase').controller('loginCreateController',loginCreateController);

    loginCreateController.$inject = ['$scope', 'loginService','$location'];

    function loginCreateController($scope,loginService, $location){
        var self = this ;
        self.login = {};
        self.create = create;
        self.error = "";

        function create(){

            loginService.create(self.login).then(function(result){

                $location.path('/login/show/'+result.identif);
            },function(error){
                self.error = error;
            })

        };
    };



})();