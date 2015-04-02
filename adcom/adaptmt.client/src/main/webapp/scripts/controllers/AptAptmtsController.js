'use strict';

angular.module("adaptmt")

.controller('aptAptmtsController',['$scope','genericResource', '$translate', 'aptAptmtsService','$location','$rootScope',
                                function($scope,genericResource, $translate, aptAptmtsService,$location,$rootScope){
	
   $scope.error = [];
   $scope.aptAptmtsService=aptAptmtsService;
   $scope.searchInput = {};
   $scope.aptAptmts = {};
   $scope.totalItems = 0;
   $scope.aptAptmtsearchResults = {};
   $scope.itemPerPage=25;
   
   $scope.init = function (){
       
        $scope.searchInput = {
                entity:{},
                fieldNames:[],
                start:0,
                max:$scope.itemPerPage
            }
          
       aptAptmtsService.loadAptAptmts($scope.searchInput).then(function(entitySearchResult) {
                $scope.aptAptmts = entitySearchResult.resultList;
                $scope.totalItems = entitySearchResult.count ;
            });
       
       console.log($scope.totalItems);
       
   }
   
   $scope.init();

}])

.controller('aptAptmtCreateCtlr',['$scope','aptAptmtsService','$translate','genericResource','$location',
        function($scope,aptAptmtsService,$translate,genericResource,$location){
	
	$scope.aptAptmt = {};
	$scope.aptAptmts = {};
	$scope.error = {};

	$scope.create = function(){
		aptAptmtsService.create($scope.aptAptmt);
    	/* .success(function(aptAptmt){
    		/*var index = aptAptmtsService.resultHandler.push(bpBnsPtnr);
    		if(bpBnsPtnrState.resultHandler.selectedIndex(index)){
    			$location.path('/BpBnsPtnrs/show/');
    		}*/
    		
    		// $location.path('/aptAptmt/show/' + aptAptmt.identif);
    	/*	console.log("aptAptmt created successfuly");
    	})
    	.error(function(error){
    		console.log("cannot create aptAptmt");
    		$scope.error = error;
    	});*/
    };
    
}]);
