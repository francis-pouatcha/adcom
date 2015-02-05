'use strict';
    
angular.module('AdProcmt')

.controller('prcmtDeliveryCtrl',['$scope','prcmtDeliveryResource',function($scope,prcmtDeliveryResource){
	
    var self = this ;
    $scope.prcmtDeliveryCtrl = self;

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
    self.prcmtDeliverys = [];
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
    	prcmtDeliveryResource.findByLike(searchInput)
    		.success(function(entitySearchResult) {
	            self.prcmtDeliverys = entitySearchResult.resultList;
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
.controller('prcmtDeliveryCreateCtlr',['$scope','$location','prcmtDeliveryResource',function($scope,$location,prcmtDeliveryResource){
	var self = this ;
    $scope.prcmtDeliveryCreateCtlr = self;
    self.prcmtDelivery = {};
    self.create = create;
    self.error = "";

    function create(){
    	prcmtDeliveryResource.create(self.prcmtDelivery)
    	.success(function(data){
    		$location.path('/PrcmtDeliverys/show/'+data.id);
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };
	
}])
.controller('prcmtDeliveryEditCtlr',['$scope','prcmtDeliveryResource','$routeParams','$location',function($scope,prcmtDeliveryResource,$routeParams,$location){
    var self = this ;
    $scope.prcmtDeliveryEditCtlr = self;
    self.prcmtDelivery = {};
    self.update = update;
    self.error = "";

    function update(){
    	prcmtDeliveryResource.update(self.prcmtDelivery)
    	.success(function(data){
            $location.path('/PrcmtDeliverys/show/'+data.id);
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
        prcmtDeliveryResource.findByIdentif(identif)
        .success(function(data){
            self.prcmtDelivery = data;
        })
        .error(function(error){
            self.error = error;
        });
    };

}]);
