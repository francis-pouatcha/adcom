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
    		$location.path('/PrcmtDeliverys/show/'+data.identif);
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
            $location.path('/PrcmtDeliverys/show/'+data.identif);
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

}])
.controller('prcmtDeliveryShowCtlr',['$scope','prcmtDeliveryResource','catalArticleResource','$routeParams','$location','$q',function($scope,prcmtDeliveryResource,catalArticleResource,$routeParams,$location,$q){
    var self = this ;
    $scope.prcmtDeliveryShowCtlr = self;
    self.prcmtDelivery = {};
    self.prcmtDeliveryItem = {};
    self.error = "";
    self.loadArticlesByNameLike = loadArticlesByNameLike;
    self.loadArticlesByCipLike = loadArticlesByCipLike;
    self.loading = true;
    self.onSelect = onSelect;

    load();

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
