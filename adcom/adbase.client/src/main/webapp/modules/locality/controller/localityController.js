﻿(function () {
    'use strict';
    angular.module('AdBase').controller('localityController',localityController);

    localityController.$inject = ['$scope', 'localityService','countryService'];

    function localityController($scope,localityService,countryService){
        var self = this ;

        self.searchInput = {};
        self.totalItems ;
        self.itemPerPage=25 ;
        self.currentPage = 1;
        self.maxSize = 5 ;
        self.localitys = [];
        self.countrys = [];
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
            loadCountry();
            findAllActive(self.searchInput);
        }

        function findAllActive(searchInput){
            localityService.findAllActive(searchInput).then(function(entitySearchResult) {
                self.localitys = entitySearchResult.resultList;
                self.totalItems = entitySearchResult.count ;
            });
        }
        function searchRequest(searchInput){
            localityService.find(searchInput).then(function(entitySearchResult) {
                self.localitys = entitySearchResult.resultList;
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
            find(self.searchInput);
        };

        function loadCountry(){
            countryService.findActifsFromNow().then(function(data){
                self.countrys=data;
            });
        }

        function handlePrintRequestEvent(){

        }

    };



})();