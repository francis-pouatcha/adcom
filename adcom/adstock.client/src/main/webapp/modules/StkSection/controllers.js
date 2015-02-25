'use strict';
    
angular.module('AdStock').controller('stkSectionCtrl',['$scope','$location','stkSectionResource',function($scope,$location,stkSectionResource){
	
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
    self.deleteItem = deleteItem;
    self.handleSelectedItem = handleSelectedItem;
    self.handleSearchRequestEvent = handleSearchRequestEvent;
    self.handlePrintRequestEvent = handlePrintRequestEvent;
    self.paginate = paginate;
    init();

    function init(){
        self.searchInput = {
            entity:{},
            fieldNames:[],
            start:0,
            max:self.itemPerPage
        }
      findAll(self.searchInput);  
    }
    
    function findAll(searchInput){
    	stkSectionResource.listAll(searchInput.start, searchInput.max)
    	.success(function(entitySearchResult){
    		self.stkSections = entitySearchResult.resultList;
    	})
    	.error(function(error){
    		self.error = error;
        });
    };

    function findByLike(searchInput){
    	stkSectionResource.findByLike(searchInput).then(function(entitySearchResult) {
            self.stkSections = entitySearchResult.resultList;
            self.totalItems = entitySearchResult.count;
        });
    }
    
    function handleSelectedItem(index){
        index = index ? index : 0 ;
        self.selectedIndex = index ;
        angular.copy(self.stkSections[self.selectedIndex],self.selectedItem);
    };
    
    function deleteItem(index){
        handleSelectedItem(index);
        var deleteConfirm= window.confirm("Voulez-vous vraiment supprimer cette ligne?");
        if (deleteConfirm==true) {
        	stkSectionResource.deleteById(self.selectedItem.id).success(function(){
        		init();
        	});
		}
    };

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
        return self.searchInput;
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
.controller('stkSectionCreateCtrl',['$scope','$location','stkSectionResource',function($scope,$location,stkSectionResource){
	var self = this ;
    $scope.stkSectionCreateCtrl = self;
    self.stkSection = {};
    self.create = create;
    self.error = "";

    function create(){
    	stkSectionResource.create(self.stkSection)
    	.success(function(result){
            $location.path('/StkSections/show/'+result.id);
        })
        .error(function(error){
            self.error = error;
        })
    };
	
}])
.controller('stkSectionEditCtrl',['$scope','$location', '$routeParams', 'stkSectionResource',function($scope,$location,$routeParams,stkSectionResource){
	var self = this ;
    $scope.stkSectionEditCtrl = self;
    var code = $routeParams.StkSectionId;
    self.stkSection = {};
    self.update = update;
    self.error = "";
    load();
    
    function load(){
    	stkSectionResource.findById(code)
    	.success(function(data){
            self.stkSection = data;
        })
        .error(function(error){
            self.error = error;
        });
    }

    function update(){
    	stkSectionResource.update(self.stkSection)
    	.success(function(result){
            $location.path('/StkSections/show/'+result.id);
        })
    	.error(function(error){
            self.error = error;
        })
    };

    
 }])
 .controller('stkSectionShowCtrl',['$scope','$location', '$routeParams', 'stkSectionResource',function($scope,$location,$routeParams,stkSectionResource){
		var self = this;
		var code = $routeParams.StkSectionId;
	    $scope.stkSectionShowCtrl = self;
	    self.stkSection = {};
	    self.error = "";
	    load();
	    
	    function load(){
	    	stkSectionResource.findById(code)
	    	.success(function(data){
	            self.stkSection = data;
	        })
	        .error(function(error){
	            self.error = error;
	        });
	    }
	    
	    
	    
	 }]);
