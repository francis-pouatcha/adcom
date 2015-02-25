'use strict';
    
angular.module('AdProcmt')
.factory('ProcmtUtils',['genericResource','$q',function(genericResource,$q){
        var service = {};

        service.urlBase='/adprocmt.server/rest/prcmtdeliverys';
        service.urlManagerDelivery='/adprocmt.server/rest/prcmtdeliverys';
        service.adbnsptnr='/adbnsptnr.server/rest/bplegalptnrids';
        service.catalarticles='/adcatal.server/rest/catalarticles';
        service.orgunits='/adbase.server/rest/orgunits';

        return service;
}])

.controller('prcmtDeliveryCtrl',['$scope','ProcmtUtils','genericResource',function($scope,ProcmtUtils,genericResource){
	
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
        genericResource.findByLike(ProcmtUtils.urlBase,searchInput)
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
.controller('prcmtDeliveryCreateCtlr',['$scope','$location','$q','ProcmtUtils','genericResource',function($scope,$location,$q,ProcmtUtils,genericResource){
	var self = this ;
    $scope.prcmtDeliveryCreateCtlr = self;
    self.prcmtDelivery = {};
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
        self.prcmtDelivery.dlvryStatus = 'ONGOING';
        genericResource.create(ProcmtUtils.urlBase ,self.prcmtDelivery)
    	.success(function(data){
    		$location.path('/PrcmtDeliverys/show/'+data.id);
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };
	
}])
.controller('prcmtDeliveryEditCtlr',['$scope','ProcmtUtils','genericResource','$routeParams','$location',function($scope ,ProcmtUtils,genericResource,$routeParams,$location){
    var self = this ;
    $scope.prcmtDeliveryEditCtlr = self;
    self.prcmtDelivery = {};
    self.update = update;
    self.error = "";

    function update(){
        genericResource.update(ProcmtUtils.urlBase,self.prcmtDelivery)
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
        genericResource.findByIdentif(ProcmtUtils.urlBase,identif)
        .success(function(data){
            self.prcmtDelivery = data;
        })
        .error(function(error){
            self.error = error;
        });
    };

}]);
