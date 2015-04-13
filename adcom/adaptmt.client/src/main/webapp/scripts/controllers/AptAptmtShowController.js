'use strict';

angular.module("adaptmt")

.controller('aptAptmtShowController',['$scope','genericResource', '$translate', 'aptAptmtsService','$location','$routeParams', '$modal', '$log', 'bpBnsPtnrState', 'bpBnsPtnrUtils',
                                function($scope,genericResource, $translate, aptAptmtsService,$location,$routeParams,$modal, $log, bpBnsPtnrState, bpBnsPtnrUtils){

        var self = this;
        self.aptAptmt = {};
        self.show = show;
        self.previous = previous;
        self.next = next;
        self.bnsPtnrIdentif = "";
	
        function show(){

            var identif = $routeParams.id;

            aptAptmtsService.loadAptAptmt(identif).then(function(result){

            	self.aptAptmt = result;

            })

        };
                              
        function init(){
            show();
        }

    init();
    
    function previous(){
        self.error = "";
        aptAptmtsService.previousAptAptmt(self.aptAptmt.id).then(function(result){
            self.aptAptmt = result;
        },function(error){
            self.error = error;
        })

    }

    function next(){
        self.error = "";
        aptAptmtsService.nextAptAptmt(self.aptAptmt.id).then(function(result){
            self.aptAptmt = result;
        },function(error){
            self.error = error;
        })

    }
    
    $scope.showBsnPtnrForm = function () {
        $scope.message = "Show Business Partner Form Button Clicked";
        console.log($scope.message);

        var modalInstance = $modal.open({
            templateUrl: 'views/aptaptmt/aptaptmtBpBnsPtnr.html',
            controller: aptAptmtAddBnsPtnrController,
            windowClass: 'bnsPtnt-Modal',
            scope: $scope,
            resolve: {
                userForm: function () {
                    return $scope.userForm;
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
        	 console.log("select element in form : ---> " + selectedItem);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
    
    
    function aptAptmtAddBnsPtnrController($scope, genericResource, $translate, $location,$routeParams, $modalInstance, $log, bpBnsPtnrState, bpBnsPtnrUtils){
		  
    	   $scope.bpBnsPtnr = bpBnsPtnrState.resultHandler.entity();
    	    $scope.create = create;
    	    $scope.error = "";
    	    $scope.bpBnsPtnrUtils=bpBnsPtnrUtils;
    	    $scope.loadCountryNames=bpBnsPtnrUtils.loadCountryNames;

    	    function create(){
    	    	genericResource.create(bpBnsPtnrUtils.urlBase, $scope.bpBnsPtnr)
    	    	.success(function(bpBnsPtnr){
    	    		console.log("previous created buiness partner identif : " + self.bnsPtnrIdentif);
    	    		self.bnsPtnrIdentif = bpBnsPtnr.identif;
    	    		console.log('/BpBnsPtnrs/show/' + identif + " and business partner itentif is : " + self.bnsPtnrIdentif);
    	    	})
    	    	.error(function(error){
    	    		$scope.error = error;
    	    		console.log($scope.error);
    	    	});
    	    };
    	    
			$scope.form = {}
		    $scope.submitForm = function () {
		        if ($scope.form.userForm.$valid) {
		            console.log('user form is in scope');
		            create();
		            $modalInstance.close('closed');
		        } else {
		            console.log('userform is not in scope');
		        }
		    };
		 
		    $scope.cancel = function () {
		        $modalInstance.dismiss('cancel');
		    };
		    
		    
	   };
	 
    
}]);