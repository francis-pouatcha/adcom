(function () {
    'use strict';
    angular.module('AdBase').controller('loginController',loginController);

    loginController.$inject = ['$scope', 'loginService'];

    function loginController($scope,loginService){
        var self = this ;

        self.searchInput = {};
        self.totalItems ;
        self.itemPerPage=25 ;
        self.currentPage = 1;
        self.maxSize = 5 ;
        self.logins = [];
        self.searchEntity = {};
        self.logins = [];
        self.selectedLogin = {} ;
        self.selectedIndex  ;
        self.handleSearchRequestEvent = handleSearchRequestEvent;
        self.handlePrintRequestEvent = handlePrintRequestEvent;
        self.partner = {};

        init();

        function init(){

            self.searchInput = {
                entity:{},
                fieldNames:[],
                start:0,
                max:self.itemPerPage
            }

            loginService.loadLogins().then(function(entitySearchResult) {
                self.logins = entitySearchResult.resultList;
                self.totalItems = entitySearchResult.count ;
            });


        }

        function processSearchInput(searchEntity){
            var fileName = [];

            if(searchEntity.loginName){
                fileName.push('loginName') ;
                self.searchInput.entity.loginName = searchEntity.loginName ;
            }

            if(searchEntity.fullName){
                fileName.push('fullName') ;
                self.searchInput.entity.fullName = searchEntity.fullName ;
            }

            if(searchEntity.ouIdentif){
                fileName.push('ouIdentif') ;
                self.searchInput.entity.fullName = searchEntity.ouIdentif ;
            }

            self.searchInput.fieldNames = fileName ;
            return self.searchInput ;
        };

        function  handleSearchRequestEvent(){

            var searchInput =   processSearchInput(self.searchEntity);
            loginService.findLogins(searchInput).then(function(data){
                self.logins = data.resultList;
            })

        };


        function handlePrintRequestEvent(){

        }
    };



})();