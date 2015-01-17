(function () {
    'use strict';
    angular.module('AdBase').controller('orgUnitCreateController',loginCreateController);

    loginCreateController.$inject = ['$scope', 'orgUnitsService','countryService','ouTypeService','$location'];

    function loginCreateController($scope,orgUnitsService,countryService,ouTypeService,$location){
        var self = this ;
        self.orgUnit = {};
        self.create = create;
        self.error = "";
        self.countrys = [];
        self.outypes = [];
        
        init();
        
        function init(){
            populateSearchBar();//load data required for the view.
        }
        function create(){

            orgUnitsService.create(self.orgUnit).then(function(result){

                $location.path('/orgunits/show/'+result.identif);
            },function(error){
                self.error = error;
            })

        };
        
        function populateSearchBar(){
            countryService.findActifsFromNow().then(function(data){
               self.countrys=data;
            });
            ouTypeService.findActifsFromNow().then(function(data){
                self.outypes = data.resultList;
            });
        }
    };



})();