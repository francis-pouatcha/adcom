'use strict';

angular.module("adaptmt")

.controller('aptAptmtShowController',['$scope','genericResource', '$translate', 'aptAptmtsService','$location','$routeParams',
                                function($scope,genericResource, $translate, aptAptmtsService,$location,$routeParams){

        var self = this;
        self.aptAptmt = {};
        self.show = show;
        self.previous = previous;
        self.next = next;
	
        function show(){

            var identif = $routeParams.id;

            aptAptmtsService.loadAptAptmt(identif).then(function(result){

            	self.aptAptmt = result;

            })

        };
                              
        function init(){
            show();
        }

    init();
    
    function previous(){
        self.error = "";
        aptAptmtsService.previousAptAptmt(self.aptAptmt.id).then(function(result){
            self.aptAptmt = result;
        },function(error){
            self.error = error;
        })

    }

    function next(){
        self.error = "";
        aptAptmtsService.nextAptAptmt(self.aptAptmt.id).then(function(result){
            self.aptAptmt = result;
        },function(error){
            self.error = error;
        })

    }
    
    
}]);