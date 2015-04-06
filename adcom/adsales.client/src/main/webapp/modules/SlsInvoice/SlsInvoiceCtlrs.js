'use strict';
    
angular.module('AdSales')

.factory('slsInvoicesUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adsales.server/rest/slsinvoices';
    service.bnsptnrUrlBase='/adbnsptnr.server/rest/bpbnsptnrs';
    
    service.language=sessionManager.language;
    
    service.translate = function(){
    	$translate(['SlsInvoice_description.text',
                    'SlsInvoice_description.title',
                    'SlsInvoices_description.text',
                    'SlsInvoices_description.title',
                    'SlsInvoice_grossSPPreTax_description.text',
                    'SlsInvoice_grossSPPreTax_description.title',
                    'SlsInvoice_invceCur_description.text',
                    'SlsInvoice_invceCur_description.title',
                    'SlsInvoice_invceDt_description.text',
                    'SlsInvoice_invceDt_description.title',
                    'SlsInvoice_invceNbr_description.text',
                    'SlsInvoice_invceNbr_description.title',
                    'SlsInvoice_invceStatus_description.text',
                    'SlsInvoice_invceStatus_description.title',
                    'SlsInvoice_invceType_description.text',
                    'SlsInvoice_invceType_description.title',
                    'SlsInvoice_netAmtToPay_description.text',
                    'SlsInvoice_netAmtToPay_description.title',
                    'SlsInvoice_netSPPreTax_description.text',
                    'SlsInvoice_netSPPreTax_description.title',
                    'SlsInvoice_netSPTaxIncl_description.text',
                    'SlsInvoice_netSPTaxIncl_description.title',
                    'SlsInvoice_netSalesAmt_description.text',
                    'SlsInvoice_netSalesAmt_description.title',
                    'SlsInvoice_pymtDscntAmt_description.text',
                    'SlsInvoice_pymtDscntAmt_description.title',
                    'SlsInvoice_pymtDscntPct_description.text',
                    'SlsInvoice_pymtDscntPct_description.title',
                    'SlsInvoice_rdngDscntAmt_description.text',
                    'SlsInvoice_rdngDscntAmt_description.title',
                    'SlsInvoice_rebate_description.text',
                    'SlsInvoice_rebate_description.title',
                    'SlsInvoice_soNbr_description.text',
                    'SlsInvoice_soNbr_description.title',
                    'SlsInvoice_vatAmount_description.text',
                    'SlsInvoice_vatAmount_description.title',
                    'SlsInvoice_invceDtFrom_description.text',
                    'SlsInvoice_invceDtFrom_description.title',
                    'SlsInvoice_invceDtTo_description.text',
                    'SlsInvoice_invceDtTo_description.title',
                    
                    'SlsInvcePtnr_description.text',
                    'SlsInvcePtnr_description.title',
                    
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
                    'Entity_print.title',
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.loadPartnersByName = function(fullName){
        return genericResource.findByLikePromissed(service.bnsptnrUrlBase, 'fullName', fullName)
        .then(function(entitySearchResult){
            if(!angular.isUndefined(entitySearchResult))
            return entitySearchResult.resultList;
            else return "";
        });
    };
    
    service.translate();
    
    return service;
}])
.factory('slsInvoicesState',['$rootScope','searchResultHandler','dependentTabManager',function($rootScope,searchResultHandler,dependentTabManager){

    var service = {};
    service.resultHandler = searchResultHandler.newResultHandler('soNbr');
    service.resultHandler.maxResult=10;
    return service;

}])
.controller('slsInvoicesCtlr',['$scope','genericResource','slsInvoicesUtils','slsInvoicesState','$location','$rootScope',
function($scope,genericResource,slsInvoicesUtils,slsInvoicesState,$location,$rootScope){

    $scope.searchInput = slsInvoicesState.resultHandler.searchInput();
    $scope.itemPerPage=slsInvoicesState.resultHandler.itemPerPage;
    $scope.totalItems=slsInvoicesState.resultHandler.totalItems;
    $scope.currentPage=slsInvoicesState.resultHandler.currentPage();
    $scope.maxSize =slsInvoicesState.resultHandler.maxResult;
    $scope.slsInvoices =slsInvoicesState.resultHandler.entities;
    $scope.selectedIndex=slsInvoicesState.resultHandler.selectedIndex;
    $scope.handleSearchRequestEvent = handleSearchRequestEvent;
    $scope.handlePrintRequestEvent = handlePrintRequestEvent;
    $scope.paginate = paginate;
    $scope.error = "";
    $scope.slsInvoicesUtils=slsInvoicesUtils;
    $scope.show=show;
    $scope.edit=edit;
    
    // test initialization
    $scope.slsInvoices= [
    {invceNbr:"INV001",soNbr:"SO001",invceStatus:"EN_COURS",ptnrNbr:"PART001",invceDt:"01-04-2015",netSPPreTax:"2000",netSPTaxIncl:"2500",netAmtToPay:"2500"},
    {invceNbr:"INV002",soNbr:"SO002",invceStatus:"EN_COURS",ptnrNbr:"PART002",invceDt:"02-04-2015",netSPPreTax:"3000",netSPTaxIncl:"3500",netAmtToPay:"3500"},
    {invceNbr:"INV003",soNbr:"SO003",invceStatus:"EN_COURS",ptnrNbr:"PART003",invceDt:"03-04-2015",netSPPreTax:"4000",netSPTaxIncl:"4500",netAmtToPay:"4500"}
    ]

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		slsInvoicesUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        if(slsInvoicesState.resultHandler.hasEntities())return;
        findByLike($scope.searchInput);
    }

    function findByLike(searchInput){
		genericResource.findByLike(slsInvoicesUtils.urlSearchBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			slsInvoicesState.resultHandler.searchResult(entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }
    

    function processSearchInput(){
        var fieldNames = [];
        if($scope.searchInput.entity.soNbr){
            $scope.searchInput.entity.soNbr= $scope.searchInput.entity.soNbr;
        	fieldNames.push('soNbr');
        }
        if($scope.searchInput.entity.invceNbr){
            $scope.searchInput.entity.invceNbr= $scope.searchInput.entity.invceNbr;
        	fieldNames.push('invceNbr');
        }
        if($scope.searchInput.invceDtFrom) $scope.searchInput.invceDtFrom= $scope.searchInput.invceDtFrom;
        if($scope.searchInput.invceDtTo) $scope.searchInput.invceDtTo= $scope.searchInput.invceDtTo;
        if($scope.searchInput.ptnr){
            $scope.searchInput.ptnrNbr= $scope.searchInput.ptnr.ptnrNbr;
        }
        
        $scope.searchInput.fieldNames = fieldNames;
        return $scope.searchInput ;
    };

    function handleSearchRequestEvent(){
    	processSearchInput();
    	findCustom($scope.searchInput);
    };
    
    function findCustom(searchInput){
        genericResource.findCustom(slsInvoicesUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			slsInvoicesState.resultHandler.searchResult(entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }

    function paginate(){
    	slsInvoicesState.resultHandler.currentPage($scope.currentPage);
        $scope.searchInput = slsInvoicesState.resultHandler.paginate();
    	findByLike($scope.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
	
	function show(slsInv, index){
		if(slsInvoicesState.resultHandler.selectedObject(slsInv)){
			$location.path('/SlsInvoices/show/');
		}
	}

	function edit(slsInv, index){
		if(slsInvoicesState.resultHandler.selectedObject(slsInv)){
			$location.path('/SlsInvoices/edit/');
		}
	}
}])
.controller('slsInvoicesEditCtlr',['$scope','genericResource','$location','slsInvoicesUtils','slsInvoicesState',
                                 function($scope,genericResource,$location,slsInvoicesUtils,slsInvoicesState){
    $scope.slsInvce = slsInvoicesState.resultHandler.entity();
    $scope.error = "";
    $scope.slsInvoicesUtils=slsInvoicesUtils;
    $scope.update = function (){
    	genericResource.update(slsInvoicesUtils.urlBase, $scope.slsInvce)
    	.success(function(slsInv){
    		var index = slsInvoicesState.resultHandler.replace(slsInv);
    		if(index && slsInvoicesState.resultHandler.selectedIndex(index)){
    			$location.path('/SlsInvoices/show/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };
}])
.controller('slsInvoicesShowCtlr',['$scope','genericResource','$location','slsInvoicesUtils','slsInvoicesState','$rootScope',
                                 function($scope,genericResource,$location,slsInvoicesUtils,slsInvoicesState,$rootScope){
    $scope.slsInvce = slsInvoicesState.resultHandler.entity();
    $scope.error = "";
    $scope.slsInvoicesUtils=slsInvoicesUtils;
    
    $scope.previous = function (){
        var bp = slsInvoicesState.resultHandler.previous();
        if(bp){
            $scope.bpBnsPtnr=bp;
            slsInvoicesState.tabSelected();
        }
    }

    $scope.next =  function (){
        var bp = slsInvoicesState.resultHandler.next();
        if(bp){
            $scope.slsSalesOrder=bp;
            slsInvoicesState.tabSelected();
        }
    };
    $scope.tabSelected = function(tabName){
    	slsInvoicesState.tabSelected(tabName);
    };
    $scope.edit =function(){
        $location.path('/SlsInvoices/edit/');
    };

}]);
