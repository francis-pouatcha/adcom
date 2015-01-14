(function () {
    'use strict';
    angular.module('AdBase').controller('orgUnitShowController',loginShowController);

    loginShowController.$inject = ['$scope', 'orgUnitsService','$location','$routeParams'];

    function loginShowController($scope,orgUnitsService, $location,$routeParams){
        var self = this ;
        self.orgUnit = {};
        self.show = show;
        self.previous = previous;
        self.next = next;

        init();

        function init(){
            show();
        }

        function show(){

            var identif = $routeParams.identif ;
            orgUnitsService.findDtoByIdentif(identif).then(function(result){
                self.orgUnit = result
            })

        };

        function previous(){
            self.error = "";
            loginService.previousLogin(self.login.loginName).then(function(result){
                self.login = result;
            },function(error){
                self.error = error;
            })

        }



        function next(){
            self.error = "";
            loginService.nextLogin(self.login.loginName).then(function(result){
                self.login = result;
            },function(error){
                self.error = error;
            })

        }
    };



})();