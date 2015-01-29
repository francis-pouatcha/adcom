'use strict';
    
angular.module('AdCatal')

.controller('catalArticleCtrl',['$scope','catalArticleService',function($scope,catalArticleService){
	
    var self = this ;
    $scope.catalArticleCtrl = self;

    self.searchInput = {
        entity:{},
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

    function init(){
        self.searchInput = {
            entity:{},
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        }
    }

    function findByLike(searchInput){
    	catalArticleService.find(searchInput).then(function(entitySearchResult) {
            self.catalArticles = entitySearchResult.resultList;
            self.totalItems = entitySearchResult.count ;
        });
    }

    function processSearchInput(){
        var fieldNames = [];
        if(self.searchInput.entity.articleName){
        	fieldNames.push('articleName') ;
        }
        if(self.searchInput.entity.pic){
        	fieldNames.push('pic') ;
        }
        if(self.searchInput.entity.familiesIdentif){
        	fieldNames.push('familiesIdentif') ;
        }
        self.searchInput.fieldNames = fieldNames ;
        return self.searchInput ;
    };

    function  handleSearchRequestEvent(){
    	processSearchInput();
    	findByLike(self.searchInput);
    };

    function paginate(){
    	self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
    	self.searchInput.max = self.itemPerPage ;
    	findByLike(self.searchInput);
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
