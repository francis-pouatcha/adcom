﻿'use strict';
    
angular.module('AdCatal')

.controller('catalArticleCtrl',['$scope','catalArticleService',function($scope,catalArticleService){
	
    var self = this ;
    $scope.catalArticleCtrl = self;

    self.searchInput = {
        entity:{
        	features:{},
    		familyFeatures:{}
        },
        fieldNames:[],
        start:0,
        max:self.itemPerPage
    };
    self.totalItems ;
    self.itemPerPage=25;
    self.currentPage = 1;
    self.maxSize = 5 ;
    self.catalArticles = [];
    self.searchEntity = {};
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.handleSearchRequestEvent = handleSearchRequestEvent;
    self.handlePrintRequestEvent = handlePrintRequestEvent;
    self.paginate = paginate;

        init();

    function init(){
        self.searchInput = {
            entity:{
            	features:{},
        		familyFeatures:{}
            },
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        }
        findCustom(self.searchInput);
    }

    function findCustom(searchInput){
    	catalArticleService.findCustom(searchInput).then(function(entitySearchResult) {
            self.catalArticles = entitySearchResult.resultList;
            self.totalItems = entitySearchResult.count ;
        });
    }
    

    function processSearchInput(){
        var fieldNames = [];
        if(self.searchInput.entity.features.artName){
        	fieldNames.push('features.artName') ;
        	self.searchInput.entity.features.langIso2='fr';
        }
        if(self.searchInput.entity.pic){
        	fieldNames.push('pic') ;
        }
        if(self.searchInput.entity.familyFeatures.familyName){
        	fieldNames.push('familyFeatures.familyName') ;
        	self.searchInput.entity.familyFeatures.langIso2='fr';
        }
        self.searchInput.fieldNames = fieldNames ;
        return self.searchInput ;
    };

    function  handleSearchRequestEvent(){
    	processSearchInput();
    	findCustom(self.searchInput);
    };

    function paginate(){
    	self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
    	self.searchInput.max = self.itemPerPage ;
    	findCustom(self.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
    
}])
.controller('catalArticleCreateCtrl',['$scope','catalArticleService',function($scope,catalArticleService){
	var self = this ;
    $scope.catalArticleCreateCtrl = self;
    self.catalArticle = {};
    self.create = create;
    self.error = "";

    function create(){
    	catalArticleService.create(self.catalArticle).then(function(result){

            $location.path('/login/show/'+result.identif);
        },function(error){
            self.error = error;
        })

    };
	
}]);
