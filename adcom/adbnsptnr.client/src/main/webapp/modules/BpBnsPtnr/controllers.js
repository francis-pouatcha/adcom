'use strict';
    
angular.module('AdBnsptnr')

.controller('bpBnsPtnrCtrl',['$scope','bpBnsPtnrService',function($scope,bpBnsPtnrService){
	
    var self = this ;
    $scope.bpBnsPtnrCtrl = self;

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
    self.bpBnsPtnrs = [];
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
    	bpBnsPtnrService.find(searchInput).then(function(entitySearchResult) {
            self.bpBnsPtnrs = entitySearchResult.resultList;
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
.controller('bpBnsPtnrCreateCtrl',['$scope','bpBnsPtnrService',function($scope,bpBnsPtnrService){
	var self = this ;
    $scope.bpBnsPtnrCreateCtrl = self;
    self.bpBnsPtnr = {};
    self.create = create;
    self.error = "";

    function create(){
    	bpBnsPtnrService.create(self.bpBnsPtnr).then(function(result){

            $location.path('/StkSections/show/'+result.identif);
        },function(error){
            self.error = error;
        })

    };
	
}]);
