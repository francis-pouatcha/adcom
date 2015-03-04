'use strict';
    
angular.module('AdProcmt')

.controller('prcmtOrderCtrl',['$scope','ProcmtUtils','genericResource',function($scope,ProcmtUtils,genericResource){
	
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
        genericResource.findByLike(ProcmtUtils.urlPrcmtOrder,searchInput)
    		.success(function(entitySearchResult) {
	            self.prcmtOrders = entitySearchResult.resultList;
	            self.totalItems = entitySearchResult.count ;
    		});
    }


    function  handleSearchRequestEvent(){
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
.controller('prcmtOrderCreateCtlr',['$scope','$location','$q','ProcmtUtils','genericResource',function($scope,$location,$q,ProcmtUtils,genericResource){
	var self = this ;
    $scope.prcmtOrderCreateCtlr = self;
    self.prcmtOrder = {};
    self.create = create;
    self.error = "";
    self.loadBusinessPartner = loadBusinessPartner;
    self.loadOrgUnit = loadOrgUnit;
    self.currencys = ['XAF','EUR','NGN','USD'];
    self.triggerMode = [];
    self.orderType = [];



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
            genericResource.findByLike(ProcmtUtils.adbnsptnr,searchInput).success(function (entitySearchResult) {
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
                searchInput.entity.identif='hs';
                searchInput.entity.fullName = val+'%';
                searchInput.fieldNames.push('fullName');
            }
            var deferred = $q.defer();
            genericResource.findByLike(ProcmtUtils.orgunits,searchInput).success(function (entitySearchResult) {
                deferred.resolve(entitySearchResult);
            }).error(function(){
                deferred.reject("No organisation unit");
            });
            return deferred.promise;
        }

    function create(){
        self.prcmtOrder.supplier = self.prcmtOrder.supplier.ptnrNbr;
        self.prcmtOrder.ordrngOrgUnit = self.prcmtOrder.ordrngOrgUnit.identif;
        genericResource.create(ProcmtUtils.urlPrcmtOrder,self.prcmtOrder)
    	.success(function(data){
    		$location.path('/PrcmtOrders/show/'+data.id);
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };
	
}])
.controller('prcmtOrderEditCtlr',['$scope','$routeParams','$location','ProcmtUtils','genericResource',function($scope,$routeParams,$location,ProcmtUtils,genericResource){
    var self = this ;
    $scope.prcmtOrderEditCtlr = self;
    self.prcmtOrder = {};
    self.update = update;
    self.error = "";

    function update(){
        genericResource.update(ProcmtUtils.urlPrcmtOrder,self.prcmtOrder)
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
        genericResource.findByIdentif(ProcmtUtils.urlPrcmtOrder,identif)
        .success(function(data){
            self.prcmtOrder = data;
        })
        .error(function(error){
            self.error = error;
        });
    };

}]);
