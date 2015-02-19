'use strict';
    
angular.module('AdProcmt')

.controller('prcmtOrderCtrl',['$scope','prcmtOrderResource',function($scope,prcmtOrderResource){
	
    var self = this ;
    $scope.prcmtOrderCtrl = self;

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
    self.prcmtOrders = [];
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
    	prcmtOrderResource.findByLike(searchInput)
    		.success(function(entitySearchResult) {
	            self.prcmtOrders = entitySearchResult.resultList;
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
.controller('prcmtOrderCreateCtlr',['$scope','$location','$q','prcmtOrderResource','bplegalptnridsResource','orgUnitsResource',function($scope,$location,$q,prcmtOrderResource,bplegalptnridsResource,orgUnitsResource){
	var self = this ;
    $scope.prcmtOrderCreateCtlr = self;
    self.prcmtOrder = {};
    self.create = create;
    self.error = "";
    self.loadBusinessPartner = loadBusinessPartner;
    self.loadOrgUnit = loadOrgUnit;
    self.currencys = ['XAF','EUR','NGN','USD'];

        function loadBusinessPartner(val){
            return loadBusinessPartnerPromise(val).then(function(entitySearchResult){
                return entitySearchResult.resultList;
            })
        }

        function loadBusinessPartnerPromise(businessPartnerName){
            var searchInput = {
                entity:{},
                fieldNames:[],
                start: 0,
                max: 10
            };
            if(businessPartnerName){
                searchInput.entity.cpnyName = businessPartnerName+'%';
                searchInput.fieldNames.push('cpnyName');
            }
            var deferred = $q.defer();
            bplegalptnridsResource.findByLike(searchInput).success(function (entitySearchResult) {
                deferred.resolve(entitySearchResult);
            }).error(function(){
                deferred.reject("No Manufacturer/Supplier");
            });
            return deferred.promise;
        }

        function loadOrgUnit(val){
            return loadOrgUnitPromise(val).then(function(entitySearchResult){
                return entitySearchResult.resultList;
            })
        }

        function loadOrgUnitPromise(val){
            var searchInput = {
                entity:{},
                fieldNames:[],
                start: 0,
                max: 10
            };
            if(val){
                searchInput.entity.fullName = val+'%';
                searchInput.fieldNames.push('fullName');
            }
            var deferred = $q.defer();
            orgUnitsResource.findByLike(searchInput).success(function (entitySearchResult) {
                deferred.resolve(entitySearchResult);
            }).error(function(){
                deferred.reject("No organisation unit");
            });
            return deferred.promise;
        }

    function create(){
        self.prcmtOrder.supplier = self.prcmtOrder.supplier.ptnrNbr;
        self.prcmtOrder.rcvngOrgUnit = self.prcmtOrder.rcvngOrgUnit.identif;
    	prcmtOrderResource.create(self.prcmtOrder)
    	.success(function(data){
    		$location.path('/PrcmtOrders/show/'+data.id);
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };
	
}])
.controller('prcmtOrderEditCtlr',['$scope','prcmtOrderResource','$routeParams','$location',function($scope,prcmtOrderResource,$routeParams,$location){
    var self = this ;
    $scope.prcmtOrderEditCtlr = self;
    self.prcmtOrder = {};
    self.update = update;
    self.error = "";

    function update(){
    	prcmtOrderResource.update(self.prcmtOrder)
    	.success(function(data){
            $location.path('/PrcmtOrders/show/'+data.id);
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
        prcmtOrderResource.findByIdentif(identif)
        .success(function(data){
            self.prcmtOrder = data;
        })
        .error(function(error){
            self.error = error;
        });
    };

}]);
