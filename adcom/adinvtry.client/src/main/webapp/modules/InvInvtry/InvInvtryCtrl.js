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
    	invtryResource.findByLike(searchInput)
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
        invtryStatus:"ONGOING"
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

}])
.controller('invtryShowCtlr',['$scope','invtryResource','catalArticleResource','$routeParams','$location','$q',function($scope,invtryResource,catalArticleResource,$routeParams,$location,$q){
    var self = this ;
    $scope.invtryShowCtlr = self;
    self.invtry = {};
    self.invtryItem = {};
    self.error = "";
    self.loadArticlesByNameLike = loadArticlesByNameLike;
    self.loadArticlesByCipLike = loadArticlesByCipLike;
    self.loading = true;
    self.onSelect = onSelect;

    load();

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

   function loadArticlesByNameLike(name){
       var searchInput = {
           entity:{
               features:{}
           },
           fieldNames:[],
           start:0,
           max:10
       };
       if(name){
           searchInput.entity.features.artName = name;
           searchInput.fieldNames.push('features.artName') ;
           searchInput.entity.features.langIso2='fr';
       }
       return findCustomArticle(searchInput).then(function(entitySearchResult){
           return entitySearchResult.resultList;
       });
   }

        function loadArticlesByCipLike(pic){
            var searchInput = {
                entity:{
                    features:{}
                },
                fieldNames:[],
                start:0,
                max:10
            };
            if(pic){
                searchInput.entity.pic = pic;
                searchInput.fieldNames.push('pic') ;
            }
            return findCustomArticle(searchInput).then(function(entitySearchResult){
                return entitySearchResult.resultList;
            });
        }

        function findCustomArticle (searchInput) {
            var deferred = $q.defer();
            catalArticleResource.findCustom(searchInput)
                .success(function(entitySearchResult) {
                    deferred.resolve(entitySearchResult);
                })
                .error(function(){
                    deferred.reject("No Article");
                });
            return deferred.promise;;
        }

        function onSelect(item,model,label){
            console.log(item);
        }

}]);