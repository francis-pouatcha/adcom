'use strict';
    
angular.module('AdInvtry')

.controller('invtryCtrl',['$scope','invtryResource',function($scope, invtryResource){
	
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
	            self.totalItems = entitySearchResult.count;
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
.controller('invtryCreateCtlr',['$scope','$location','$http','invtryResource',function($scope,$location,$http,invtryResource){
	var self = this ;
    $scope.invtryCreateCtlr = self;
    self.invtry = {
        invtryStatus:"ONGOING",
        gapSaleAmtHT:"0.0",
        gapPurchAmtHT:"0.0"
    };
    self.invstkSections = [];
    self.create = create;
    self.error = "";
    self.defaultParam=true;
    self.invModes = ['BY_SECTION','ALPHABETICAL_ORDER_RANGE','FREE_INV'];
    self.selectedAction = selectedAction;
    self.selectedSection=false;
    self.selectedOrderRange=false;
    self.selectedFreeInv=false;
    findAllSections();
    
    function selectedAction(){
    	console.log(self.invtry.invMode);
    	if(self.invtry.invMode=="BY_SECTION") {
    		self.selectedSection=true;
    	}else {
    		self.selectedSection=false;
		}
    	if(self.invtry.invMode=="ALPHABETICAL_ORDER_RANGE"){
    		self.selectedOrderRange=true;
    	}else {
    		self.selectedOrderRange=false;
		}
    	if(self.invtry.invMode=="FREE_INV") {
    		self.selectedFreeInv=true;
    	}else {
    		self.selectedFreeInv=false;
		}
    }
    
    function findAllSections(){
       var responsePromise= $http.get('/adstock.server/rest/stksections')
        .success(function(data, status, headers, config){
            console.log('Success'+data);
            self.invstkSections= data;
        })
        .error(function(data, status, headers, config){
            console.log('Error');
        });
    }
   

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