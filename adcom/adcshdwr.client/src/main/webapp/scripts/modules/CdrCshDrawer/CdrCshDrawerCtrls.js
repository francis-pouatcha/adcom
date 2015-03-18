'use strict';
    
angular.module('AdCshdwr')

.factory('cdrCshDrawerManagerResource',['$http', function($http){
    var service = {};
    var urlBase = '/adinvtry.server/rest/inventory';
    service.update = function(invtryHolder){
        return $http.put(urlBase+'/update',invtryHolder);
    };
    service.close = function(invtryHolder){
        return $http.put(urlBase+'/close',invtryHolder);
    };
    return service;
}])
.factory('cdrCshDrawerUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adinvtry.server/rest/cdpaymnts';
    service.cdrpaymntsUrlBase='/adinvtry.server/rest/cdrpaymntitems';
    service.stksectionsUrlBase='/adstock.server/rest/stksections';
    service.stkarticlelotsUrlBase='/adstock.server/rest/stkarticlelots';
    service.catalarticlesUrlBase='/adcatal.server/rest/catalarticles';
    service.loginnamessUrlBase='/adbase.server/rest/loginnamess';
    service.stkarticlelot2strgsctnsUrlBase='/adstock.server/rest/stkarticlelot2strgsctns';

    service.language=sessionManager.language;
    
    service.currentWsUser=sessionManager.userWsData();
    
    service.translate = function(){
    	$translate([
                    'CdrCshDrawer_cashier_description.text',
                    'CdrCshDrawer_cashier_description.title',
                    'CdrCshDrawer_cdrNbr_description.text',
                    'CdrCshDrawer_cdrNbr_description.title',
                    'CdrCshDrawer_closedBy_description.text',
                    'CdrCshDrawer_closedBy_description.title',
                    'CdrCshDrawer_clsngDt_description.text',
                    'CdrCshDrawer_clsngDt_description.title',
                    'CdrCshDrawer_description.text',
                    'CdrCshDrawer_description.title',
                    'CdrCshDrawer_initialAmt_description.text',
                    'CdrCshDrawer_initialAmt_description.title',
                    'CdrCshDrawer_opngDt_description.text',
                    'CdrCshDrawer_opngDt_description.title',
                    'CdrCshDrawer_ttlCashIn_description.text',
                    'CdrCshDrawer_ttlCashIn_description.title',
                    'CdrCshDrawer_ttlCashOut_description.text',
                    'CdrCshDrawer_ttlCashOut_description.title',
                    'CdrCshDrawer_ttlCash_description.text',
                    'CdrCshDrawer_ttlCash_description.title',
                    'CdrCshDrawer_ttlCheck_description.text',
                    'CdrCshDrawer_ttlCheck_description.title',
                    'CdrCshDrawer_ttlCredCard_description.text',
                    'CdrCshDrawer_ttlCredCard_description.title',
                    'CdrCshDrawer_ttlVchrIn_description.text',
                    'CdrCshDrawer_ttlVchrIn_description.title',
                    'CdrCshDrawer_ttlVchrOut_description.text',
                    'CdrCshDrawer_ttlVchrOut_description.title',
            
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
    	            'Entity_By.title',
    	            'Entity_saveleave.title',
    	            'Entity_add.title',
                    'Entity_From.title',
                    'Entity_To.title'
    	            
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });
    };
    
    service.translate();
    return service;
}])
.factory('cdrCshDrawerState',['$rootScope', '$q',function($rootScope,$q){

    var service = {
    };
    var selectedIndexVar=-1;
    service.selectedIndex= function(selectedIndexIn){
        if(selectedIndexIn)selectedIndexVar=selectedIndexIn;
        return selectedIndexVar;
    };

    var totalItemsVar=0;
    service.totalItems = function(totalItemsIn){
        if(totalItemsIn)totalItemsVar=totalItemsIn;
        return totalItemsVar;
    };

    var currentPageVar = 0;
    service.currentPage = function(currentPageIn){
        if(currentPageIn) currentPageVar=currentPageIn;
        return currentPageVar;
    };

    var maxSizeVar = 5;
    service.maxSize = function(maxSizeIn) {
        if(maxSizeIn) maxSizeVar=maxSizeIn;
        return maxSizeVar;
    };

    var itemPerPageVar = 25;
    var searchInputVar = {
        entity:{},
        fieldNames:[],
        start:0,
        max:itemPerPageVar
    };
    service.searchInput = function(searchInputIn){
        if(!searchInputIn)
            return angular.copy(searchInputVar);

        searchInputVar=angular.copy(searchInputIn);
        return searchInputIn;
    };
    
    
            
    service.searchInputChanged = function(searchInputIn){
        return angular.equals(searchInputVar, searchInputIn);
    };
    service.itemPerPage = function(itemPerPageIn){
        if(itemPerPageIn)itemPerPageVar=itemPerPageIn;
        return itemPerPageVar;
    };

    //
    service.consumeSearchResult = function(searchInput, entitySearchResult) {
        // store search
        service.searchInput(searchInput);
        // Store result
        service.invInvtrys(entitySearchResult.resultList);
        service.totalItems(entitySearchResult.count);
        service.selectedIndex(-1);
    };
    

    service.paginate = function(){
        searchInputVar.start = ((currentPageVar - 1)  * itemPerPageVar);
        searchInputVar.max = itemPerPageVar;
        return service.searchInput();
    };

    // returns sets and returns the business partner at the passed index or
    // if not set the business partner at the currently selected index.
    service.invInvtry = function(index){
        if(index && index>=0 && index<invInvtrysVar.length)selectedIndexVar=index;
        if(selectedIndexVar<0 || selectedIndexVar>=invInvtrysVar.length) return;
        return invInvtrysVar[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(invInvtry){
        if(!invInvtrysVar || !invInvtry) return;
        var currentInvt = service.invInvtry();
        if(currentInvt && currentInvt.invtryNbr==invInvtry.invtryNbr){
            invInvtrysVar[selectedIndexVar]=invInvtry;
        } else {
            for (var index in invInvtrysVar) {
                if(invInvtrysVar[index].invtryNbr==invInvtry.invtryNbr){
                    invInvtrysVar[index]=invInvtry;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };
    service.set = function(invInvtry){
        if(!invInvtrysVar || !invInvtry) return false;
        invInvtrysVar[selectedIndexVar]=invInvtry;
        return true;
    };

    // CHeck if the business partner to be displayed is at the correct index.
    service.peek = function(invInvtry, index){
        if(!invInvtrysVar || !invInvtry) return false;
        if(index>=0 && index<invInvtrysVar.length){
            selectedIndexVar=index;
            return true;
        }
        return false;
    };

    service.push = function(invInvtry){
        if(!invInvtrysVar || !invInvtry) return false;
        var length = invInvtrysVar.push(invInvtry);
        selectedIndexVar=length-1;
        return true;
    };

    service.previous = function (){
    	if(invInvtrysVar.length<=0) return;

        if(selectedIndexVar<=0){
            selectedIndexVar=invInvtrysVar.length-1;
        } else {
            selectedIndexVar-=1;
        }
        return service.invInvtry();
    };

    service.next = function(){
    	if(invInvtrysVar.length<=0) return;
    	
    	if(selectedIndexVar>=invInvtrysVar.length-1 || selectedIndexVar<0){
    		selectedIndexVar=0;
    	} else {
            selectedIndexVar+=1;
    	}

        return service.invInvtry();
    };
    return service;

}])
.controller('cdrCshDrawersCtlr',['$scope','genericResource','cdrCshDrawerUtils','cdrCshDrawerState','$location','$rootScope',
function($scope,genericResource,cdrCshDrawerUtils,cdrCshDrawerState,$location,$rootScope){

    $scope.searchInput = cdrCshDrawerState.searchInput();
    $scope.itemPerPage=cdrCshDrawerState.itemPerPage;
    $scope.totalItems=cdrCshDrawerState.totalItems;
    $scope.currentPage=cdrCshDrawerState.currentPage();
    $scope.maxSize =cdrCshDrawerState.maxSize;
    $scope.cdrPaymnts =cdrCshDrawerState.cdrPaymnts;
    $scope.selectedIndex=cdrCshDrawerState.selectedIndex;
    $scope.handleSearchRequestEvent = handleSearchRequestEvent;
    $scope.handlePrintRequestEvent = handlePrintRequestEvent;
    $scope.paginate = paginate;
    $scope.error = "";
    $scope.cdrCshDrawerUtils=cdrCshDrawerUtils;
    $scope.show=show;
    $scope.edit=edit;

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		cdrCshDrawerUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function  handleSearchRequestEvent(){
    	if($scope.searchInput.acsngUser){
    		$scope.searchInput.entity.acsngUser=$scope.searchInput.acsngUser.loginName;
    	} else {
    		$scope.searchInput.entity.acsngUser='';
    	}
    	findCustom($scope.searchInput);
    }
    function handlePrintRequestEvent(){
        // To do
	}
    
    function paginate(){
        $scope.searchInput = invInvtryState.paginate();
        findCustom($scope.searchInput);
    };

    function paginate(){
        $scope.searchInput = invInvtryState.paginate();
        findCustom($scope.searchInput);
    }
    
    function init(){
    }
    
	function show(cdrPymt, index){
		
	}

	function edit(cdrPymt, index){
		
	}

}])
.controller('cdrCshDrawersCreateCtlr',['$scope','cdrCshDrawerUtils','$translate','genericResource','$location','cdrCshDrawerState',
        function($scope,cdrCshDrawerUtils,$translate,genericResource,$location,cdrCshDrawerState){
    $scope.cdrPaymnt = cdrCshDrawerState.cdrPaymnt();
    $scope.create = create;
    $scope.error = "";
    $scope.stkSection = "";
    $scope.startRange = "";
    $scope.endRange = "";
    $scope.cdrCshDrawerUtils=cdrCshDrawerUtils;

    function create(){
    };

  
}])
.controller('cdrCshDrawersEditCtlr',['$scope','genericResource','$location','cdrCshDrawerUtils','cdrCshDrawerState',
                                 function($scope,genericResource,$location,cdrCshDrawerUtils,cdrCshDrawerState){
    $scope.cdrPaymnt = cdrCshDrawerState.cdrPaymnt();
    $scope.error = "";
    $scope.cdrCshDrawerUtils=cdrCshDrawerUtils;
    $scope.update = function (){
    	genericResource.update(cdrCshDrawerUtils.urlBase, $scope.cdrPaymnt)
    	.success(function(cdrPaymnt){
    		if(cdrCshDrawerState.replace(cdrPaymnt)){
    			$location.path('/CdrCshDrawers');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };
}]);
