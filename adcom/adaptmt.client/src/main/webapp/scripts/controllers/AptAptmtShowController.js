'use strict';

angular.module("adaptmt")

.controller('aptAptmtShowController',['$scope', '$rootScope','genericResource', '$translate', 'aptAptmtsService','$location','$routeParams', '$modal', '$log', 'bpBnsPtnrState', 'bpBnsPtnrUtils', '$filter', 'aptAptmtBsPtnrService',
                                function($scope, $rootScope,genericResource, $translate, aptAptmtsService,$location,$routeParams,$modal, $log, bpBnsPtnrState, bpBnsPtnrUtils, $filter, aptAptmtBsPtnrService){

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
    
    
    function aptAptmtAddBnsPtnrController($scope, $rootScope, genericResource, $translate, $location,$routeParams, $modalInstance, $log, bpBnsPtnrState, bpBnsPtnrUtils, $filter, aptAptmtBsPtnrService){
		  
    		// create bnptnr
	    	$scope.bpBnsPtnr = {};
	        $scope.create = create;
	        $scope.addBnsPtnr = addBnsPtnr;
	        $scope.error = "";
	        $scope.bpBnsPtnrUtils=bpBnsPtnrUtils;
	        $scope.loadCountryNames=bpBnsPtnrUtils.loadCountryNames;
	        
	        
	        $scope.searchInput = bpBnsPtnrState.resultHandler.searchInput();
	        $scope.itemPerPage=bpBnsPtnrState.resultHandler.itemPerPage;
	        $scope.totalItems=bpBnsPtnrState.resultHandler.totalItems;
	        $scope.currentPage=bpBnsPtnrState.resultHandler.currentPage();
	        $scope.maxSize =bpBnsPtnrState.resultHandler.maxResult;
	        $scope.bpBnsPtnrs =bpBnsPtnrState.resultHandler.entities;
	        $scope.selectedIndex=bpBnsPtnrState.resultHandler.selectedIndex;
	        $scope.handleSearchRequestEvent = handleSearchRequestEvent;
	        $scope.handlePrintRequestEvent = handlePrintRequestEvent;
	        $scope.paginate = paginate;
	        $scope.error = "";
	        $scope.bpBnsPtnrUtils=bpBnsPtnrUtils;
	        $scope.show=show;
	        $scope.edit=edit;
	        
	        $scope.eventualBsnPtnrs = bpBnsPtnrState.resultHandler.entities;
	        $scope.confirmedBsnPtnrs = [];
    	    
    	   

    	 // create bnptnr
    	    function create(){
    	    	genericResource.create(bpBnsPtnrUtils.urlBase, $scope.bpBnsPtnr)
    	    	.success(function(bpBnsPtnr){
    	    		console.log(" old created buiness partner identif : " + self.bnsPtnrIdentif + " for appointment : " + self.aptAptmt.title);
    	    		self.bnsPtnrIdentif = bpBnsPtnr.identif;
    	    		console.log(" new business partner itentif is : " + self.bnsPtnrIdentif + " for appointment : " + self.aptAptmt.title);
    	    	})
    	    	.error(function(error){
    	    		$scope.error = error;
    	    		console.log($scope.error);
    	    	});
    	    };
    	    // create bnptnr
    	    
    	    // add bnsptnr to appointement
    	    
    	    function addBnsPtnr(){
    	    	
    	    	for(var i in $scope.eventualBsnPtnrs()){
    	    		console.log(" partner with id : " +  $scope.eventualBsnPtnrs()[i].id + " checked ? : " + $scope.eventualBsnPtnrs()[i].checkOn);
    	    		
    	    		var idIncome = $scope.eventualBsnPtnrs()[i].id;
    	    		if($scope.eventualBsnPtnrs()[i].checkOn == true){
    	    		
    	    			 var found = $filter('filter')($scope.confirmedBsnPtnrs, {id: idIncome}, true);
    	    	         if (found.length) {
    	    	             console.log("already exist in array to send to the server !");
    	    	         } else {
    	    	        	 $scope.confirmedBsnPtnrs.push($scope.eventualBsnPtnrs()[i]);
    	    	         }
    	    			
    	    		}
    	    	};
    	    	
    	    	for(var j in $scope.confirmedBsnPtnrs){
    	    		var entity = {aptmtIdentify: $routeParams.id, bsPtnrIdentify: $scope.confirmedBsnPtnrs[j].id};
    	    		sendToServer(entity);
    	    	}
    	    	
    	    	console.log($scope.confirmedBsnPtnrs);
    	    };
    	    
    	    
    	    function sendToServer(entity){
    	    	
    	    	aptAptmtBsPtnrService.create(entity)
    			.then(function(result){
    	            console.log("entity : " + result + " has send successfully");
    	        },function(error){
    	        	console.log(error);
    	        });
    	    	
    	    }
    	    
    	    
    	    
    	    // add bnsptnr to appointement
    	    
    	    // select bnptnr
    	    
    	    var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
    			bpBnsPtnrUtils.translate();
    		});

    	    $scope.$on('$destroy', function () {
    	        translateChangeSuccessHdl();
    	    });

    	    init();

    	    function init(){
    	        if(bpBnsPtnrState.resultHandler.hasEntities())return;
    	        findByLike($scope.searchInput);
    	    }

    	    function findByLike(searchInput){
    			genericResource.findByLike(bpBnsPtnrUtils.urlBase, searchInput)
    			.success(function(entitySearchResult) {
    				// store search
    				bpBnsPtnrState.resultHandler.searchResult(entitySearchResult);
    			})
    	        .error(function(error){
    	            $scope.error=error;
    	        });
    	    }

    	    function processSearchInput(){
    	        var fieldNames = [];
    	        if($scope.searchInput.entity.ptnrNbr){
    	        	fieldNames.push('ptnrNbr') ;
    	        }
    	        if($scope.searchInput.entity.fullName){
    	        	fieldNames.push('fullName') ;
    	        }
    	        if($scope.searchInput.entity.ptnrType && $scope.searchInput.entity.ptnrType=='')
    	        	$scope.searchInput.entity.ptnrType=undefined;
    	        
    	        if($scope.searchInput.entity.ptnrType){
    	        	fieldNames.push('ptnrType') ;
    	        }
    	        $scope.searchInput.fieldNames = fieldNames;
    	        return $scope.searchInput ;
    	    };

    	    function  handleSearchRequestEvent(){
    	    	processSearchInput();
    	    	findByLike($scope.searchInput);
    	    };

    	    function paginate(){
    	    	bpBnsPtnrState.resultHandler.currentPage($scope.currentPage);
    	        $scope.searchInput = bpBnsPtnrState.resultHandler.paginate();
    	    	findByLike($scope.searchInput);
    	    };

    		function handlePrintRequestEvent(){		
    		}
    		
    		function show(bpBnsPtnr, index){
    			if(bpBnsPtnrState.resultHandler.selectedObject(bpBnsPtnr)){
    				$location.path('/BpBnsPtnrs/show/');
    			}
    		}

    		function edit(bpBnsPtnr, index){
    			if(bpBnsPtnrState.resultHandler.selectedObject(bpBnsPtnr)){
    				$location.path('/BpBnsPtnrs/edit/');
    			}
    		}
    		
    		// select bnptnr
    	    
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