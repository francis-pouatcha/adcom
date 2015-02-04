﻿'use strict';
    
angular.module('AdBnsptnr')

.factory('bpBnsPtnrUtils',function(){
    var service = {};
    service.isIndividual = function(bpBnsPtnr){
    	return bpBnsPtnr.ptnrType=='INDIVIDUAL';
    }
    service.isInstitution = function(bpBnsPtnr){
    	return bpBnsPtnr.ptnrType=='LEGAL';
    }
    
    service.ptnrTypeI18nMsgTitleKey = function(enumKey){
    	return "BpPtnrType_"+enumKey+"_description.title";
    }
    
    service.bpBnsPtnrTypes = [
      {enumKey:'INDIVIDUAL', translKey:'BpPtnrType_INDIVIDUAL_description.title'},
      {enumKey:'LEGAL', translKey:'BpPtnrType_LEGAL_description.title'}
    ];
    	
    

    return service;
})
.controller('bpBnsPtnrsCtlr',['$scope','bpBnsPtnrResource','bpBnsPtnrUtils',function($scope,bpBnsPtnrResource,bpBnsPtnrUtils){
	
    var self = this ;
    $scope.bpBnsPtnrsCtlr = self;

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
    self.error = "";
    self.bpBnsPtnrUtils=bpBnsPtnrUtils;
    
    init();

    function init(){
        self.searchInput = {
            entity:{},
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        }
        findByLike(self.searchInput);
    }

    function findByLike(searchInput){
    	bpBnsPtnrResource.findByLike(searchInput)
    		.success(function(entitySearchResult) {
	            self.bpBnsPtnrs = entitySearchResult.resultList;
	            self.totalItems = entitySearchResult.count ;
    		});
    }

    function processSearchInput(){
        var fieldNames = [];
        if(self.searchInput.entity.ptnrNbr){
        	fieldNames.push('ptnrNbr') ;
        }
        if(self.searchInput.entity.fullName){
        	fieldNames.push('fullName') ;
        }
        if(self.searchInput.entity.ptnrType && self.searchInput.entity.ptnrType=='')
        	self.searchInput.entity.ptnrType=undefined;
        
        if(self.searchInput.entity.ptnrType){
        	fieldNames.push('ptnrType') ;
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
.controller('bpBnsPtnrCreateCtlr',['$scope','bpBnsPtnrResource','bpBnsPtnrUtils',
                                   function($scope,bpBnsPtnrResource,bpBnsPtnrUtils){
	var self = this ;
    $scope.bpBnsPtnrCreateCtlr = self;
    self.bpBnsPtnr = {};
    self.create = create;
    self.error = "";
    self.isIndividual=bpBnsPtnrUtils.isIndividual;
    self.isInstitution=bpBnsPtnrUtils.isInstitution;

    function create(){
    	bpBnsPtnrResource.create(self.bpBnsPtnr)
    	.success(function(data){
    		$location.path('/BpBnsPtnrs/show/'+data.identif);
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };
	
}])
.controller('bpBnsPtnrEditCtlr',['$scope','bpBnsPtnrResource','$routeParams','$location','bpBnsPtnrUtils',
                                 function($scope,bpBnsPtnrResource,$routeParams,$location,bpBnsPtnrUtils){
    var self = this ;
    $scope.bpBnsPtnrEditCtlr = self;
    self.bpBnsPtnr = {};
    self.update = update;
    self.error = "";
    self.isIndividual=bpBnsPtnrUtils.isIndividual;
    self.isInstitution=bpBnsPtnrUtils.isInstitution;

    function update(){
    	bpBnsPtnrResource.update(self.bpBnsPtnr)
    	.success(function(data){
            $location.path('/BpBnsPtnrs/show/'+data.identif);
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
        var ptnrNbr = $routeParams.ptnrNbr;
        bpBnsPtnrResource.findByIdentif(ptnrNbr)
        .success(function(data){
            self.bpBnsPtnr = data;
        })
        .error(function(error){
            self.error = error;
        });
    };
    

}])
.controller('bpBnsPtnrShowCtlr',['$scope','bpBnsPtnrResource','$routeParams','$location','bpBnsPtnrUtils',
                                 function($scope,bpBnsPtnrResource,$routeParams,$location,bpBnsPtnrUtils){
    var self = this ;
    $scope.bpBnsPtnrShowCtlr = self;
    self.bpBnsPtnr = {};
    self.error = "";
    self.previousBP = previousBP;
    self.nextBP = nextBP;
    self.isIndividual=bpBnsPtnrUtils.isIndividual;
    self.isInstitution=bpBnsPtnrUtils.isInstitution;

    load();

    function load(){
        var ptnrNbr = $routeParams.ptnrNbr;
        bpBnsPtnrResource.findByIdentif(ptnrNbr)
        .success(function(data){
            self.bpBnsPtnr = data;
        })
        .error(function(error){
            self.error = error;
        });
    };

    function previousBP(ptnrNbr){
        bpBnsPtnrResource.previous(ptnrNbr).success(function(data){
            $location.path('/BpBnsPtnrs/show/'+data.identif);
        });
    }

    function nextBP(ptnrNbr){
        bpBnsPtnrResource.next(ptnrNbr).success(function(data){
            $location.path('/BpBnsPtnrs/show/'+data.identif);
        });
    }

}]);