(function () {
    'use strict';
    angular.module('AdBase').controller('converterCurrRateController',converterCurrRateController);

    converterCurrRateController.$inject = ['$scope', 'converterCurrRateService'];

    function converterCurrRateController($scope,converterCurrRateService){
        var self = this ;

        self.searchInput = {};
        self.totalItems ;
        self.itemPerPage=25 ;
        self.currentPage = 1;
        self.maxSize = 5 ;
        self.converterCurrRates = [];
        self.searchEntity = {};
        self.selectedIndex  ;
        self.handleSearchRequestEvent = handleSearchRequestEvent;
        self.handlePrintRequestEvent = handlePrintRequestEvent;
        self.paginate = paginate;

        init();

        function init(){
            self.searchInput = {
                entity:{},
                fieldNames:[],
                start:0,
                max:self.itemPerPage
            }
            findAllActive(self.searchInput);
        }

        function findAllActive(searchInput){
            converterCurrRateService.findAllActive(searchInput).then(function(entitySearchResult) {
                self.converterCurrRates = entitySearchResult.resultList;
                self.totalItems = entitySearchResult.count ;
            });
        }
        function searchRequest(searchInput){
            converterCurrRateService.find(searchInput).then(function(entitySearchResult) {
                self.converterCurrRates = entitySearchResult.resultList;
                self.totalItems = entitySearchResult.count ;
            });
        }

        function processSearchInput(){
            var fileName = [];
            if(self.searchInput.entity.partnerIds){
                fileName.push('partnerIds') ;
            }
            self.searchInput.fieldNames = fileName ;
            return self.searchInput ;
        };

        function  handleSearchRequestEvent(){
            var searchInput =   processSearchInput();
            searchRequest(self.searchInput);
        };

        function paginate(){
            self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
            self.searchInput.max = self.itemPerPage ;
            searchRequest(self.searchInput);
        };


        function handlePrintRequestEvent(){

        }

    };



})();