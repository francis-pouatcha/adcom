'use strict';
    
angular.module('AdInvtry')

.controller('invtryCtrl',['$scope','invtryResource',function($scope,invtryResource){
	
    var self = this ;
    $scope.invtryCtrl = self;

    self.searchInput = {
        entity:{
        },
        fieldNames:[],
        start:0,
        max:self.itemPerPage
    };
    self.totalItems ;
    self.itemPerPage=25;
    self.currentPage = 1;
    self.maxSize = 5 ;
    self.invtrys = [];
    self.searchEntity = {};
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.handleSearchRequestEvent = handleSearchRequestEvent;
    self.handlePrintRequestEvent = handlePrintRequestEvent;
    self.paginate = paginate;
    self.error = "";
    
    init();

    function init(){
        self.searchInput = {
            entity:{
            },
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        }
        findByLike(self.searchInput);
    }

    function findByLike(searchInput){
    	invtryResource.findInvInvtrys(searchInput)
    		.success(function(entitySearchResult) {
	            self.invtrys = entitySearchResult.resultList;
	            self.totalItems = entitySearchResult.count ;
    		});
    }

    function processSearchInput(){
        var fieldNames = [];
        if(self.searchInput.entity.dateMin){
        	//fieldNames.push('dateMin') ;
        	//self.searchInput.entity.dateMin='fr';
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
.controller('invtryCreateCtlr',['$scope','$location','invtryResource',function($scope,$location,invtryResource){
	var self = this ;
    $scope.invtryCreateCtlr = self;
    self.invtry = {
        invtryStatus:"ONGOING",
        gapSaleAmtHT:"0.0",
        gapPurchAmtHT:"0.0"
    };
    self.create = create;
    self.error = "";

    function create(){
    	invtryResource.create(self.invtry)
    	.success(function(data){
    		$location.path('/InvInvtrys/show/'+data.identif);
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };
	
}])
.controller('invtryEditCtlr',['$scope','invtryResource','$routeParams','$location',function($scope,invtryResource,$routeParams,$location){
    var self = this ;
    $scope.invtryEditCtlr = self;
    self.invtry = {};
    self.update = update;
    self.error = "";

    function update(){
    	invtryResource.update(self.invtry)
    	.success(function(data){
            $location.path('/InvInvtrys/show/'+data.identif);
        })
    	.error(function(error){
            self.error = error;
        });
    };

    init();

    function init(){
        load();
    }

    function load(){
        var identif = $routeParams.identif;
        invtryResource.findByIdentif(identif)
        .success(function(data){
            self.invtry = data;
        })
        .error(function(error){
            self.error = error;
        });
    };

}]);