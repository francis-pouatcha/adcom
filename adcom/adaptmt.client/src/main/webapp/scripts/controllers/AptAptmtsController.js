'use strict';

angular.module("adaptmt")

.controller('aptAptmtsController',['$scope','genericResource', '$translate', 'aptAptmtsService','$location','$rootScope',
                                function($scope,genericResource, $translate, aptAptmtsService,$location,$rootScope){
	
   $scope.error = "";
   $scope.aptAptmtsService=aptAptmtsService;
   
   $scope.previous = function (){
   }

   $scope.next =  function (){
   };
   
   $scope.edit =function(){
       $location.path('/aptaptmt/edit/');
   };

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
