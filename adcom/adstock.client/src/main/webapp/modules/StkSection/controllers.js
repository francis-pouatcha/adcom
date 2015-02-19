'use strict';
    
angular.module('AdStock').controller('stkSectionCtrl',['$scope','$location','stkSectionService',function($scope,$location,stkSectionService){
	
    var self = this ;
    $scope.stkSectionCtrl = self;

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
    self.stkSections = [];
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
    	stkSectionService.find(searchInput).then(function(entitySearchResult) {
            self.stkSections = entitySearchResult.resultList;
            self.totalItems = entitySearchResult.count ;
        });
    }

    function processSearchInput(){
        var fieldNames = [];
        if(self.searchInput.entity.articleName){
        	fieldNames.push('sectionCode') ;
        }
        if(self.searchInput.entity.pic){
        	fieldNames.push('name') ;
        }
        if(self.searchInput.entity.familiesIdentif){
        	fieldNames.push('wharehouse') ;
        }
        if(self.searchInput.entity.familiesIdentif){
        	fieldNames.push('parentCode') ;
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
.controller('stkSectionCreateCtrl',['$scope','$location','stkSectionService',function($scope,$location,stkSectionService){
	var self = this ;
    $scope.stkSectionCreateCtrl = self;
    self.stkSection = {};
    self.create = create;
    self.error = "";

    function create(){
    	stkSectionService.create(self.stkSection).then(function(result){
            $location.path('/StkSections/show/'+result.identif);
        },function(error){
            self.error = error;
        })

    };
	
}]);
