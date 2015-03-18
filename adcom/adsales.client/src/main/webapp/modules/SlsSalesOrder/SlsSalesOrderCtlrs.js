'use strict';
    
angular.module('AdSales')

.factory('slsSalesOrderUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adsales.server/rest/slssalesorders';
    service.bnsptnrUrlBase='/adbnsptnr.server/rest/bpbnsptnrs';
    
    service.language=sessionManager.language;
    
    service.translate = function(){
    	$translate(['SlsSalesOrder_description.text',
                    'SlsSalesOrder_description.title',
                    'SlsSalesOrder_grossSPPreTax_description.text',
                    'SlsSalesOrder_grossSPPreTax_description.title',
                    'SlsSalesOrder_netAmtToPay_description.text',
                    'SlsSalesOrder_netAmtToPay_description.title',
                    'SlsSalesOrder_netSPPreTax_description.text',
                    'SlsSalesOrder_netSPPreTax_description.title',
                    'SlsSalesOrder_netSPTaxIncl_description.text',
                    'SlsSalesOrder_netSPTaxIncl_description.title',
                    'SlsSalesOrder_netSalesAmt_description.text',
                    'SlsSalesOrder_netSalesAmt_description.title',
                    'SlsSalesOrder_pymtDscntAmt_description.text',
                    'SlsSalesOrder_pymtDscntAmt_description.title',
                    'SlsSalesOrder_pymtDscntPct_description.text',
                    'SlsSalesOrder_pymtDscntPct_description.title',
                    'SlsSalesOrder_rdngDscntAmt_description.text',
                    'SlsSalesOrder_rdngDscntAmt_description.title',
                    'SlsSalesOrder_rebate_description.text',
                    'SlsSalesOrder_rebate_description.title',
                    'SlsSalesOrder_soCur_description.text',
                    'SlsSalesOrder_soCur_description.title',
                    'SlsSalesOrder_soNbr_description.text',
                    'SlsSalesOrder_soNbr_description.title',
                    'SlsSalesOrder_soStatus_description.text',
                    'SlsSalesOrder_soStatus_description.title',
                    'SlsSalesOrder_vatAmount_description.text',
                    'SlsSalesOrder_vatAmount_description.title',
                    
    	            'Entity_show.title',
    	            'Entity_previous.title',
    	            'Entity_list.title',
    	            'Entity_next.title',
    	            'Entity_edit.title',
    	            'Entity_create.title',
    	            'Entity_update.title',
    	            'Entity_Result.title',
    	            'Entity_search.title',
    	            'Entity_cancel.title',
    	            'Entity_save.title',
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.translate();
    
    return service;
}])
.factory('slsSalesOrderState',['$rootScope','searchResultHandler','dependentTabManager',function($rootScope,searchResultHandler,dependentTabManager){

    var service = {};
    service.resultHandler = searchResultHandler.newResultHandler('soNbr');
    service.resultHandler.maxResult=10;
    return service;

}])
.controller('slsSalesOrdersCtlr',['$scope','genericResource','slsSalesOrderUtils','slsSalesOrderState','$location','$rootScope',
function($scope,genericResource,slsSalesOrderUtils,slsSalesOrderState,$location,$rootScope){

    $scope.searchInput = slsSalesOrderState.resultHandler.searchInput();
    $scope.itemPerPage=slsSalesOrderState.resultHandler.itemPerPage;
    $scope.totalItems=slsSalesOrderState.resultHandler.totalItems;
    $scope.currentPage=slsSalesOrderState.resultHandler.currentPage();
    $scope.maxSize =slsSalesOrderState.resultHandler.maxResult;
    $scope.slsSalesOrders =slsSalesOrderState.resultHandler.entities;
    $scope.selectedIndex=slsSalesOrderState.resultHandler.selectedIndex;
    $scope.handleSearchRequestEvent = handleSearchRequestEvent;
    $scope.handlePrintRequestEvent = handlePrintRequestEvent;
    $scope.paginate = paginate;
    $scope.error = "";
    $scope.slsSalesOrderUtils=slsSalesOrderUtils;
    $scope.show=show;
    $scope.edit=edit;

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		slsSalesOrderUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        if(slsSalesOrderState.resultHandler.hasEntities())return;
        findByLike($scope.searchInput);
    }

    function findByLike(searchInput){
		genericResource.findByLike(slsSalesOrderUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			slsSalesOrderState.resultHandler.searchResult(entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }

    function processSearchInput(){
        var fieldNames = [];
        if($scope.searchInput.entity.soNbr){
        	fieldNames.push('soNbr') ;
        }
        if($scope.searchInput.entity.soStatus){
        	fieldNames.push('soStatus') ;
        }
        if($scope.searchInput.entity.soCur){
            fieldNames.push('soCur');
        }
        $scope.searchInput.fieldNames = fieldNames;
        return $scope.searchInput ;
    };

    function  handleSearchRequestEvent(){
    	processSearchInput();
    	findByLike($scope.searchInput);
    };

    function paginate(){
    	slsSalesOrderState.resultHandler.currentPage($scope.currentPage);
        $scope.searchInput = slsSalesOrderState.resultHandler.paginate();
    	findByLike($scope.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
	
	function show(slsSO, index){
		if(slsSalesOrderState.resultHandler.selectedObject(slsSO)){
			$location.path('/SlsSalesOrders/show/');
		}
	}

	function edit(slsSO, index){
		if(slsSalesOrderState.resultHandler.selectedObject(slsSO)){
			$location.path('/SlsSalesOrders/edit/');
		}
	}
}])
.controller('slsSalesOrderCreateCtlr',['$scope','slsSalesOrderUtils','$translate','genericResource','$location','slsSalesOrderState',
        function($scope,slsSalesOrderUtils,$translate,genericResource,$location,slsSalesOrderState){
    $scope.slsSalesOrder = slsSalesOrderState.resultHandler.entity();
    $scope.create = create;
    $scope.error = "";
    $scope.slsSalesOrderUtils=slsSalesOrderUtils;

    function create(){
    	genericResource.create(slsSalesOrderUtils.urlBase, $scope.slsSalesOrder)
    	.success(function(slsSalesOrder){
    		var index = slsSalesOrderState.resultHandler.push(slsSalesOrder);
    		if(slsSalesOrderState.resultHandler.selectedIndex(index)){
    			$location.path('/SlsSalesOrders/show/');
    		}
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
}])
.controller('slsSalesOrderEditCtlr',['$scope','genericResource','$location','slsSalesOrderUtils','slsSalesOrderState',
                                 function($scope,genericResource,$location,slsSalesOrderUtils,slsSalesOrderState){
    $scope.slsSalesOrder = slsSalesOrderState.resultHandler.entity();
    $scope.error = "";
    $scope.slsSalesOrderUtils=slsSalesOrderUtils;
    $scope.update = function (){
    	genericResource.update(slsSalesOrderUtils.urlBase, $scope.slsSalesOrder)
    	.success(function(slsSO){
    		var index = slsSalesOrderState.resultHandler.replace(slsSO);
    		if(index && slsSalesOrderState.resultHandler.selectedIndex(index)){
    			$location.path('/SlsSalesOrders/show/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };
}])
.controller('slsSalesOrderShowCtlr',['$scope','genericResource','$location','slsSalesOrderUtils','slsSalesOrderState','$rootScope',
                                 function($scope,genericResource,$location,slsSalesOrderUtils,slsSalesOrderState,$rootScope){
    $scope.slsSalesOrder = slsSalesOrderState.resultHandler.entity();
    $scope.error = "";
    $scope.slsSalesOrderUtils=slsSalesOrderUtils;
    
    $scope.previous = function (){
        var bp = slsSalesOrderState.resultHandler.previous();
        if(bp){
            $scope.bpBnsPtnr=bp;
            slsSalesOrderState.tabSelected();
        }
    }

    $scope.next =  function (){
        var bp = slsSalesOrderState.resultHandler.next();
        if(bp){
            $scope.slsSalesOrder=bp;
            slsSalesOrderState.tabSelected();
        }
    };
    $scope.tabSelected = function(tabName){
    	slsSalesOrderState.tabSelected(tabName);
    };
    $scope.edit =function(){
        $location.path('/SlsSalesOrders/edit/');
    };

}]);