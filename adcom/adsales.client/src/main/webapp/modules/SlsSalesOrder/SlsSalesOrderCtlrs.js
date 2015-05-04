'use strict';
    
angular.module('AdSales')

.factory('slsSalesOrderUtils',['sessionManager','$translate', 'commonTranslations','$filter','genericResource','$q',function(sessionManager,$translate,commonTranslations,$filter,genericResource,$q){
    var service = {};
    var dateFormat = $filter('date');
    var defaultDatePattern = 'dd-MM-yyyy HH:mm';

    service.urlBase='/adsales.server/rest/slssalesorders';
    service.bnsptnrUrlBase='/adbnsptnr.server/rest/bpbnsptnrs';
    service.loginnamessUrlBase='/adbase.server/rest/loginnamess';
    service.slsSOItemsUrlBase='/adsales.server/rest/slssoitems';
    
    
    service.formatDate= function(fieldName, inPattern){
        var pattern = '';
        if(!inPattern) pattern = defaultDatePattern;
        else pattern = inPattern;
        return dateFormat(fieldName, pattern, '');
    };
    
    service.slsSOStatusI18nMsgTitleKey = function(enumKey){
    	return "SlsSalesStatus_"+enumKey+"_description.title";
    };
    
    service.slsSOStatusI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.slsSOStatusI18nMsgTitleKey(enumKey)];
    };
    
    service.slsSOStatus = [
      {enumKey:'SUSPENDED', translKey:'SlsSalesStatus_SUSPENDED_description.title'},
      {enumKey:'ONGOING', translKey:'SlsSalesStatus_ONGOING_description.title'},
      {enumKey:'RESUMED', translKey:'SlsSalesStatus_RESUMED_description.title'},
      {enumKey:'CLOSED', translKey:'SlsSalesStatus_CLOSED_description.title'}
    ];
    
    service.language=sessionManager.language;
    
    service.commonTranslations=commonTranslations.translations;
    
    service.translate = function(){
    	$translate(['SlsSalesOrder_description.text',
                    'SlsSalesOrder_description.title',
                    'SlsSalesOrders_description.text',
                    'SlsSalesOrders_description.title',
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
                    'SlsSalesOrder_soDt_description.text',
                    'SlsSalesOrder_soDt_description.title',
                    'SlsSalesOrder_soStatus_description.text',
                    'SlsSalesOrder_soStatus_description.title',
                    'SlsSalesOrder_vatAmount_description.text',
                    'SlsSalesOrder_vatAmount_description.title',
                    'SlsSalesOrder_acsngUser_description.text',
                    'SlsSalesOrder_acsngUser_description.title',
                    
                    'SlsSalesOrder_soDtFrom_description.text',
                    'SlsSalesOrder_soDtFrom_description.title',
                    'SlsSalesOrder_soDtTo_description.text',
                    'SlsSalesOrder_soDtTo_description.title',
                    
                    'SlsSOPtnr_description.text',
                    'SlsSOPtnr_description.title',
                    'SlsSOPtnrs_description.text',
                    'SlsSOPtnrs_description.title',
                    'SlsSOPtnr_ptnrNbr_description.text',
                    'SlsSOPtnr_ptnrNbr_description.title',
                    'SlsSOPtnr_roleInSO_description.text',
                    'SlsSOPtnr_roleInSO_description.title',
                    'SlsSOPtnr_soNbr_description.text',
                    'SlsSOPtnr_soNbr_description.title',
                    
                    'SlsSalesStatus_SUSPENDED_description.title',
    	            'SlsSalesStatus_ONGOING_description.title',
    	            'SlsSalesStatus_RESUMED_description.title',
    	            'SlsSalesStatus_CLOSED_description.title',
                    
                    'SlsSOItems_description.text',
                    'SlsSOItems_description.title',
                    'SlsSOItem_artPic_description.title',
                    'SlsSOItem_artName_description.title',
                    'SlsSOItem_orderedQty_description.title',
                    'SlsSOItem_returnedQty_description.title',
                    'SlsSOItem_deliveredQty_description.title',
                    'SlsSOItem_vatAmount_description.title',
                    'SlsSOItem_rebate_description.title',
                    'SlsSOItem_netSPPreTax_description.title',
                    'SlsSOItem_netSPTaxIncl_description.title',
                    'SlsSOItem_sppuPreTax_description.title',
                    
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
                    'Entity_first.title',
                    'Entity_last.title'
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.loadUsers = function(loginName){
        return genericResource.findByLikePromissed(service.loginnamessUrlBase, 'loginName', loginName)
        .then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    service.loadUsersByName = function(fullName){
        return genericResource.findByLikePromissed(service.loginnamessUrlBase, 'fullName', fullName)
        .then(function(entitySearchResult){
            if(!angular.isUndefined(entitySearchResult))
            return entitySearchResult.resultList;
            else return "";
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
.factory('slsSalesOrderState',['$rootScope','searchResultHandler','dependentTabManager',function($rootScope,searchResultHandler,dependentTabManager){

    var service = {};
    service.resultHandler = searchResultHandler.newResultHandler('soNbr');
    service.resultHandler.slsSOItems=[];
    service.resultHandler.slsSOPtnrs=[];
    service.resultHandler.maxResult=10;
    service.slsSalesOrderHolder = {
        slsSalesOrder:{},
        slsSOItemsholder:[],
        slsSOPtnrsHolder:[]
    };
    service.saveSlsSalesOrderHolder= function(slsSOHolder){
        console.log('Service:'+slsSOHolder);
        if(!service.slsSalesOrderHolder) return;
        angular.copy(slsSOHolder, service.slsSalesOrderHolder);
    }
    return service;

}])
.controller('slsSalesOrdersCtlr',['$scope','genericResource','slsSalesOrderUtils','slsSalesOrderState','$location' ,'$translate','$filter','$rootScope',
function($scope,genericResource,slsSalesOrderUtils,slsSalesOrderState,$location,$translate,$filter,$rootScope){

    var slsSalesOrderHolder = {
        slsSalesOrder:{},
        slsSOItemsholder:[],
        slsSOPtnrsHolder:[]
    };
    var orderBy = $filter('orderBy');
    $scope.slsSalesOrders = [];
    $scope.slsSOHolder = {
        slsSalesOrder:{},
        slsSOItemsholder:[],
        slsSOPtnrsHolder:[]
    };
    $scope.items = [];
    $scope.slsSalesOrderItemHolder = {};
    $scope.searchParam= {};
    $scope.slsSalesOrdersHolder=[];
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
    $scope.prepareSalesOrder=prepareSalesOrder;
    orderSlsSalesOrders();
    fullFillSales();
    
    
    function orderSlsSalesOrders(){
      // $scope.slsSalesOrders = $filter('orderBy')($scope.slsSalesOrders, '-soDt', false);
    }
    
    
    function fullFillSales(){
        for(var i=0; i<$scope.slsSalesOrders.length; i++){
            $scope.slsSalesOrderHolder.slsSalesOrder=$scope.slsSalesOrders[i];
            $scope.slsSalesOrderHolder.slsSOItemsholder=$scope.slsSalesOrders[i].slsSOItems;
            $scope.slsSalesOrderHolder.slsSOPtnrsHolder=$scope.slsSalesOrders[i].slsSOPtnrs;
            $scope.slsSalesOrdersHolder.push($scope.slsSalesOrderHolder);
        }
    }

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
    

    function handleSearchRequestEvent(){
    	processSearchInput();
    	findCustom($scope.searchInput);
    };
    
    function findCustom(searchInput){
        genericResource.findCustom(slsSalesOrderUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			slsSalesOrderState.resultHandler.searchResult(entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    };
    
    function processSearchInput(){
        var fieldNames = [];
        if($scope.searchInput.entity.soNbr){
            $scope.searchInput.entity.soNbr= $scope.searchInput.entity.soNbr;
        	fieldNames.push('soNbr');
        }
        if($scope.searchInput.entity.soStatus){
            $scope.searchInput.entity.soStatus= $scope.searchInput.entity.soStatus;
        	fieldNames.push('soStatus');
        }
        if($scope.searchInput.slsSODtFrom){
            $scope.searchInput.slsSODtFrom = $scope.searchInput.slsSODtFrom;
            console.log('Date from: '+$scope.searchInput.slsSODtFrom);
        }
        if($scope.searchInput.slsSODtTo){
            $scope.searchInput.slsSODtTo = $scope.searchInput.slsSODtTo;
            console.log('Date to: '+$scope.searchInput.slsSODtTo);
        }
        
        if($scope.searchParam.acsngUser){
            $scope.searchInput.entity.acsngUser = $scope.searchParam.acsngUser.loginName;
            fieldNames.push('acsngUser');
        }
        if($scope.searchInput.ptnr){
            $scope.searchInput.ptnrNbr= $scope.searchInput.ptnr.ptnrNbr;
        }
        $scope.searchInput.fieldNames = fieldNames;
        return $scope.searchInput ;
      };

    function paginate(){
    	slsSalesOrderState.resultHandler.currentPage($scope.currentPage);
        $scope.searchInput = slsSalesOrderState.resultHandler.paginate();
    	findByLike($scope.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
	
	function show(slsSO, index){
		if(slsSalesOrderState.resultHandler.selectedObject(slsSO) != -1){
			$location.path('/SlsSalesOrders/show/');
		}
	}

	function edit(slsSO, items, index){
		if(slsSalesOrderState.resultHandler.selectedObject(slsSO) != -1){
            //$scope.slsSOHolder = prepareSalesOrder(slsSO, items);
            //console.log('SalesOrderHolder: '+$scope.slsSOHolder.slsSOItemsholder);
            //slsSalesOrderState.saveSlsSalesOrderHolder($scope.slsSOHolder);
			$location.path('/SlsSalesOrders/edit/');
		}
	}
     
    function prepareSalesOrder(slsSO, items){
        slsSalesOrderHolder.slsSalesOrder= slsSO;
        var slsSalesOrderItemHolder = {slsSOItem:{}};
        for(var i=0; i<items.length; i++){
            slsSalesOrderItemHolder.slsSOItem = items[i];
            slsSalesOrderHolder.slsSOItemsholder.push(slsSalesOrderItemHolder);                               
        };
        slsSalesOrderHolder.slsSOPtnrsHolder= slsSO.slsSOPtnrs;
        console.log(slsSalesOrderHolder);
        return slsSalesOrderHolder;
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
.controller('slsSalesOrderShowCtlr',['$scope','genericResource','$location','slsSalesOrderUtils','slsSalesOrderState', '$filter','$rootScope', function($scope,genericResource,$location,slsSalesOrderUtils,slsSalesOrderState,$filter,$rootScope){
    $scope.slsSalesOrder = slsSalesOrderState.resultHandler.entity();
    $scope.itemPerPage=slsSalesOrderState.resultHandler.itemPerPage;
    $scope.currentPage=slsSalesOrderState.resultHandler.currentPage();
    $scope.maxSize =slsSalesOrderState.resultHandler.maxResult;
    $scope.slsSalesOrder.soDt = $filter('date')($scope.slsSalesOrder.soDt, 'dd-MM-yyyy HH:mm', '');
    $scope.error = "";
    $scope.generateInvoice = generateInvoice;
    $scope.slsSalesOrderUtils=slsSalesOrderUtils;
    $scope.pageChangeHandler = function(num) {
      //nothing to do
    };
    
    
    function generateInvoice(){
        console.log("Generate invoice from sale");
        // To do
    }
    
    
    
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
