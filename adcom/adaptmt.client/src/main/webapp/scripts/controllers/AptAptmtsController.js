'use strict';

angular.module("adaptmt")

.controller('aptAptmtsController',['$scope','genericResource', '$translate', 'aptAptmtsService','$location','$rootScope', '$filter',
                                function($scope,genericResource, $translate, aptAptmtsService,$location,$rootScope, $filter){
	
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
													//    	   		console.log(" appointmentDate : " + $filter('date')(self.aptAptmts[2].appointmentDate, 'medium', 'GMT') + " creationDate : " + self.aptAptmts[2].creationDate);
            });
       
       console.log(self.totalItems);
       
   };
   
   init();
   
   function findCustom(searchInput){
	   aptAptmtsService.loadAptAptmts(searchInput).then(function(entitySearchResult) {
           self.aptAptmts = entitySearchResult.resultList;
           self.totalItems = entitySearchResult.count ;
       });
   }

   function processSearchInput(){
       var fieldNames = [];
       if(self.searchInput.entity.title){
    	   fieldNames.push('title') ;
       }
       if(self.searchInput.entity.description){
    	   fieldNames.push('description') ;
       }
       if(self.searchInput.entity.createdUserId){
    	   fieldNames.push('createdUserId') ;
       }
       if(self.searchInput.entity.closedUserId){
    	   fieldNames.push('closedUserId');
       }
       if(self.searchInput.entity.appointmentDate){
    	   fieldNames.push('appointmentDate');
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
       findByLike(self.searchInput);
   };


   function handlePrintRequestEvent(){

   }

}])

.controller('aptAptmtCreateCtlr',['$scope','aptAptmtsService','$translate','genericResource','$location',
        function($scope,aptAptmtsService,$translate,genericResource,$location){
	
	var self = this;
	$scope.aptAptmt = {};
	self.aptAptmts = {};
	self.searchInput = {};
	self.error = {};
	var currentDate;
	
	function init(){
	       
        self.searchInput = {
                entity:{},
                fieldNames:[],
                start:0,
                max:$scope.itemPerPage
            }
          
       aptAptmtsService.loadAptAptmts(self.searchInput).then(function(entitySearchResult) {
    	   self.aptAptmts = entitySearchResult.resultList;
            });
       
   };
   
   init();

	$scope.create = function(){
		console.log(" appointmentDate : " + $scope.aptAptmt.appointmentDate);
		
		aptAptmtsService.create($scope.aptAptmt)
		.then(function(result){
            $location.path('/aptaptmt/show/'+result.id);
        },function(error){
        	$scope.error = error;
        });
        
    };
    
    
    $scope.$watch(function() {
    	  		return $scope.aptAptmt.appointmentDate;
    		}, function(newValue, oldValue){
    			currentDate = new Date();
    			
		 	    if($scope.aptAptmt.appointmentDate <= currentDate) {
		 	    	$scope.aptAptmt.appointmentDate = '';
		 	    	console.log("the appointment Date cannot be before the current date");
		 	    };
 	});
    
}]);
