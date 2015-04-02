'use strict';

angular.module("adaptmt")

.controller('AptAptmtShowController',['$scope','genericResource', '$translate', 'aptAptmtsService','$location','$routeParams',
                                function($scope,genericResource, $translate, aptAptmtsService,$location,$routeParams){


	
	function loginShowController($scope, aptAptmtsService, $location, $routeParams){
        var self = this ;
        self.aptAptmt = {};
        self.show = show;
        self.previous = previous;
        self.next = next;

        init();

        function init(){
            show();
        }

        function show(){

            var identif = $routeParams.identif ;

            aptAptmtsService.loadAptAptmt(identif).then(function(result){

               self.aptAptmt = result;

            })

        };

        function previous(){
            self.error = "";
            loginService.previousAptAptmt(self.aptAptmt.identif).then(function(result){
                self.aptAptmt = result;
            },function(error){
                self.error = error;
            })

        }



        function next(){
            self.error = "";
            loginService.nextAptAptmt(self.aptAptmt.identif).then(function(result){
                self.aptAptmt = result;
            },function(error){
                self.error = error;
            })

        }
    };


	



}]);