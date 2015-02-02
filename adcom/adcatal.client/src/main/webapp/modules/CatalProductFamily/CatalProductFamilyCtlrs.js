'use strict';
    
angular.module('AdCatal')

.controller('catalProductFamiliesCtlr',['$scope','catalProductFamilyResource',function($scope,catalProductFamilyResource){
	
    var self = this ;
    $scope.catalProductFamiliesCtlr = self;

    self.searchInput = {
        entity:{
        	features:{}
        },
        fieldNames:[],
        start:0,
        max:self.itemPerPage
    };
    self.totalItems ;
    self.itemPerPage=25;
    self.currentPage = 1;
    self.maxSize = 5 ;
    self.catalProductFamilies = [];
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
            	features:{}
            },
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        }
        findCustom(self.searchInput);
    }

    function findCustom(searchInput){
    	catalProductFamilyResource.findCustom(searchInput)
    		.success(function(entitySearchResult) {
	            self.catalProductFamilies = entitySearchResult.resultList;
	            self.totalItems = entitySearchResult.count ;
    		});
    }

    function processSearchInput(){
        var fieldNames = [];
        if(self.searchInput.entity.features.familyName){
        	fieldNames.push('features.familyName') ;
        }
        if(self.searchInput.entity.famCode){
        	fieldNames.push('famCode') ;
        }
        if(self.searchInput.entity.features.purpose){
        	fieldNames.push('features.purpose') ;
        }
        self.searchInput.fieldNames = fieldNames ;
        return self.searchInput ;
    };

    function  handleSearchRequestEvent(){
    	processSearchInput();
    	findCustom(self.searchInput);
    };

    function paginate(){
    	self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
    	self.searchInput.max = self.itemPerPage ;
    	findCustom(self.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
    
}])
.controller('catalProductFamilyCreateCtlr',['$scope','catalProductFamilyResource','$location',function($scope,catalProductFamilyResource,$location){
	var self = this ;
    $scope.catalProductFamilyCreateCtlr = self;
    self.catalProductFamily = {};
    self.create = create;
    self.error = "";

    function create(){
    	catalProductFamilyResource.create(self.catalProductFamily)
    	.success(function(data){
    		$location.path('/CatalProductFamilies/show/'+data.identif);
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };
	
}])
.controller('catalProductFamilyEditCtlr',['$scope','catalProductFamilyResource','$routeParams','$location',function($scope,catalProductFamilyResource,$routeParams,$location){
    var self = this ;
    $scope.catalProductFamilyEditCtlr = self;
    self.catalProductFamily = {};
    self.update = update;
    self.error = "";

    function update(){
    	catalProductFamilyResource.update(self.catalProductFamily)
    	.success(function(data){
            $location.path('/CatalProductFamilies/show/'+data.identif);
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
        var famCode = $routeParams.famCode;
        catalProductFamilyResource.findByIdentif(famCode)
        .success(function(data){
            self.catalProductFamily = data;
        })
        .error(function(error){
            self.error = error;
        });
    };

}])
.controller('catalProductFamilyShowCtlr',['$scope','catalProductFamilyResource','$routeParams',
                                          function($scope,catalProductFamilyResource,$routeParams){
    var self = this ;
    $scope.catalProductFamilyShowCtlr = self;
    self.catalProductFamily = {};
    self.error = "";
    self.previousProductFamily = previousProductFamily;
    self.nextProductFamily = nextProductFamily;

    load();

    function load(){
        var famCode = $routeParams.famCode;
        catalProductFamilyResource.findByIdentif(famCode)
        .success(function(data){
            self.catalProductFamily = data;
        })
        .error(function(error){
            self.error = error;
        });
    };

    function previousProductFamily(){

    }
    function nextProductFamily(){
    }
}]);
