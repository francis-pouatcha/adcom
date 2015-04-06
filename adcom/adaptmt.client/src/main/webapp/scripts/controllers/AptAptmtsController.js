'use strict';

angular.module("adaptmt")

.controller('aptAptmtsController',['$scope','genericResource', '$translate', 'aptAptmtsService','$location','$rootScope',
                                function($scope,genericResource, $translate, aptAptmtsService,$location,$rootScope){
	
   var self = this;
	
   self.error = [];
   self.searchInput = {};
   self.aptAptmts = [];
   self.totalItems = 0;
   self.aptAptmtsearchResults = {};
   self.itemPerPage=25;
   self.currentPage = 1;
   self.maxSize = 5 ;
   self.handleSearchRequestEvent = handleSearchRequestEvent;
   self.handlePrintRequestEvent = handlePrintRequestEvent;
   self.paginate = paginate;
   self.logins = [];
   
   function init(){
       
        self.searchInput = {
                entity:{},
                fieldNames:[],
                start:0,
                max:$scope.itemPerPage
            }
          
       aptAptmtsService.loadAptAptmts(self.searchInput).then(function(entitySearchResult) {
    	   		self.aptAptmts = entitySearchResult.resultList;
    	   		self.totalItems = entitySearchResult.count ;
            });
       
       console.log(self.totalItems);
       
   };
   
   init();

   function processSearchInput(){
       var fileName = [];
       if(self.searchInput.entity.loginName){
           fileName.push('loginName') ;
       }
       if(self.searchInput.entity.fullName){
           fileName.push('fullName') ;
       }
       if(self.searchInput.entity.ouIdentif){
           fileName.push('ouIdentif') ;
       }
       self.searchInput.fieldNames = fileName ;
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

.controller('aptAptmtCreateCtlr',['$scope','aptAptmtsService','$translate','genericResource','$location',
        function($scope,aptAptmtsService,$translate,genericResource,$location){
	
	$scope.aptAptmt = {};
	$scope.aptAptmts = {};
	$scope.error = {};

	$scope.create = function(){
		
		aptAptmtsService.create($scope.aptAptmt)
		.then(function(result){
            $location.path('/aptaptmt/show/'+result.id);
        },function(error){
        	$scope.error = error;
        });
        
    };
    
}]);
