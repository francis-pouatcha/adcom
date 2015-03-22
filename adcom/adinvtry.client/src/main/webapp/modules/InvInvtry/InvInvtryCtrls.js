'use strict';
    
angular.module('AdInvtry')

.factory('invInvtryManagerResource',['$http', function($http){
    var service = {};
    var urlBase = '/adinvtry.server/rest/inventory';
    
    // Prepare an inventory, eventually pre creating inventory items.
    // This simplifies the execution of the inventory by reducing the data collection to 
    // quantities only.
    service.prepare = function(invtry){
        return $http.put(urlBase+'/prepare',invtry);
    };
    
    // Update core data of an inventory. 
    // Can not be done if an inventorys posted.
    service.update = function(invtry){
        return $http.put(urlBase+'/update',invtry);
    };
    
  
    // Close an inventory object. No item can be added to the inventory object anymore.
    // Inventory information can be changed. But item data can no be changed anymore.
    service.close = function(invtry){
        return $http.put(urlBase+'/close',invtry);
    };
    
    // Post an inventory. Notifying listener about the inventory.
    // Form this state nothing can be modified on the inventory anymore.
    service.post = function(invtry){
        return $http.put(urlBase+'/post',invtry);
    };

    // Add an item to the inventory. This can only be done if the 
    // inventory is not closed.
    service.addItem = function(invtryItem){
        return $http.put(urlBase+'/addItem',invtryItem);
    };
    
    // Update an existing inventory item. For example setting 
    // quantities.
    service.updateItem = function(invtryItem){
        return $http.put(urlBase+'/updateItem',invtryItem);
    };
    
    // Disable an inventory item. This is preferable to deleting an inventory item.
    // This can only be done if the inventory item is not closed.
    service.disableItem = function(invtryItem){
        return $http.put(urlBase+'/disableItem',invtryItem);
    };
    
    // Reenabling a disabled inventory item.
    // This can only be done if the inventory item is not closed.
    service.enableItem = function(invtryItem){
        return $http.put(urlBase+'/enableItem',invtryItem);
    };
    
    return service;
}])
.factory('invInvtryState',['$rootScope','searchResultHandler',function($rootScope,searchResultHandler){
    var service = {};
    service.resultHandler = searchResultHandler.newResultHandler('invtryNbr');
    service.itemsResultHandler = function(){
    	var itemsResultHandlerVar = service.resultHandler.dependent('items');
    	if(angular.isUndefined(itemsResultHandlerVar)){
    		itemsResultHandlerVar = searchResultHandler.newResultHandler('identif');
    		service.resultHandler.dependent('items', itemsResultHandlerVar);
    	}
    	return itemsResultHandlerVar;
    };
    return service;
}])
.factory('invInvtryUtils',['sessionManager','$translate','genericResource','$q','invInvtryState',
                           function(sessionManager,$translate,genericResource,$q,invInvtryState){
    var service = {};

    service.inventoryManagerUrlBase = "/adinvtry.server/rest/inventory";
    service.urlBase='/adinvtry.server/rest/invinvtrys';
    service.invinvtrysUrlBase='/adinvtry.server/rest/invinvtryitems';
    service.stksectionsUrlBase='/adstock.server/rest/stksections';
    service.stkarticlelotsUrlBase='/adstock.server/rest/stkarticlelots';
    service.catalarticlesUrlBase='/adcatal.server/rest/catalarticles';
    service.loginnamessUrlBase='/adbase.server/rest/loginnamess';
    service.stkarticlelot2strgsctnsUrlBase='/adstock.server/rest/stkarticlelot2strgsctns';
    service.alphabet = "abcdefghijklmnopqrstuvwxyz";
    
    service.invInvtryTypeI18nMsgTitleKey = function(enumKey){
    	return "InvInvtryType_"+enumKey+"_description.title";
    };
    service.invInvtryTypeI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.invInvtryTypeI18nMsgTitleKey(enumKey)];
    };
    
    service.invInvtryTypes = [
      {enumKey:'BY_SECTION', translKey:'InvInvtryType_BY_SECTION_description.title'},
      {enumKey:'ALPHABETICAL_ORDER_RANGE', translKey:'InvInvtryType_ALPHABETICAL_ORDER_RANGE_description.title'},
      {enumKey:'FREE_INV', translKey:'InvInvtryType_FREE_INV_description.title'}
    ];

    service.invInvntrStatusI18nMsgTitleKey = function(enumKey){
    	return "InvInvntrStatus_"+enumKey+"_description.title";
    };
    service.invInvntrStatusI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.invInvntrStatusI18nMsgTitleKey(enumKey)];
    };
    
    service.invInvtryStatusI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.invInvntrStatusI18nMsgTitleKey(enumKey)];
    };
    service.invInvntrStati = [
      {enumKey:'SUSPENDED', translKey:'InvInvntrStatus_SUSPENDED_description.title'},
      {enumKey:'ONGOING', translKey:'InvInvntrStatus_ONGOING_description.title'},
      {enumKey:'RESUMED', translKey:'InvInvntrStatus_RESUMED_description.title'},
      {enumKey:'CLOSED', translKey:'InvInvntrStatus_CLOSED_description.title'},
      {enumKey:'POSTED', translKey:'InvInvntrStatus_POSTED_description.title'}
    ];

    service.language=sessionManager.language;
    
    service.currentWsUser=sessionManager.userWsData();
    
    service.translate = function(){
    	$translate(['InvInvtryType_BY_SECTION_description.title',
    	            'InvInvtryType_ALPHABETICAL_ORDER_RANGE_description.title',
    	            'InvInvtryType_FREE_INV_description.title',
    	            'InvInvntrStatus_SUSPENDED_description.title',
    	            'InvInvntrStatus_ONGOING_description.title',
    	            'InvInvntrStatus_RESUMED_description.title',
    	            'InvInvntrStatus_CLOSED_description.title',
    	            'InvInvntrStatus_POSTED_description.title',
    	            
    	            'InvInvtry_acsngDt_description.title',
    	            'InvInvtry_gapSaleAmtHT_description.title',
    	            'InvInvtry_acsngDt_description.text',
    	            'InvInvtry_gapSaleAmtHT_description.title',
    	            'InvInvtry_gapSaleAmtHT_description.text',
    	            'InvInvtry_gapPurchAmtHT_description.title',
    	            'InvInvtry_gapPurchAmtHT_description.text',
    	            'InvInvtry_invtryStatus_description.title',
    	            'InvInvtry_invtryStatus_description.text',
    	            'InvInvtry_descptn_description.title',
    	            'InvInvtry_descptn_description.text',
    	            'InvInvtry_invInvtryType_description.title',
    	            'InvInvtry_invtryDt_description.title',
    	            'InvInvtry_section_description.title',
    	            'InvInvtry_section_description.text',
    	            'InvInvtry_invtryNbr_description.title',
    	            'InvInvtry_invtryNbr_description.text',
    	            'InvInvtry_startRange_description.text',
    	            'InvInvtry_startRange_description.title',
    	            'InvInvtry_endRange_description.text',
    	            'InvInvtry_endRange_description.title',
    	            'InvInvtryItem_sectionName_description.title',
    	            'InvInvtryItem_artNameStart_description.title',
    	            'InvInvtryItem_artNameEnd_description.title',

    	            'InvInvtryItem_description.title',

    	            'InvInvtry_NoSectionFound_description.title',
    	            'InvInvtry_NoArticleFound_description.title',
    	            
    	            'InvInvtry_invtryDtFrom_description.title',
    	            'InvInvtry_invtryDtTo_description.title',
    	            'InvInvtry_gapPurchAmtHTFrom_description.title',
    	            'InvInvtry_gapPurchAmtHTTo_description.title',
    	            'InvInvtry_acsngUser_description.title',
    	            'InvInvtry_close_description.title',
    	            'InvInvtry_correctStock_description.title',
    	            'InvInvtry_join_description.title',
    	            'InvInvtry_compare_description.title',
    	            'InvInvtry_invtryGroup_description.title',
    	            
    	            
    	            'InvInvtryItem_section_description.title',
    	            'InvInvtryItem_artPic_description.title',
    	            'InvInvtryItem_lotPic_description.title',
    	            'InvInvtryItem_artName_description.title',
    	            'InvInvtryItem_expectedQty_description.title',
    	            'InvInvtryItem_asseccedQty_description.title',
    	            'InvInvtry_acsngUserFullName_description.title',
    	            'InvInvtryItem_expirDt_description.title',
    	            'InvInvtryItem_acsngDt_description.title',
    	            'InvInvtry_acsngDtFrom_description.title',
    	            'InvInvtry_acsngDtTo_description.title',
    	            'InvInvtry_post_description.title',
    	            
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
    	            'Entity_notfound.title',
    	            'Entity_Of.title',
    	            'Entity_info.title',
    	            'Entity_reset.title'

    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });	
    };
    
    service.loadSectionsBySectionCode = function(sectionCode){
        return genericResource.findByLikePromissed(service.stksectionsUrlBase, 'sectionCode', sectionCode)
        .then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    service.loadSectionsBySectionName = function(sectionName){
        return genericResource.findByLikePromissed(service.stksectionsUrlBase, 'name', sectionName)
        .then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    service.loadArticlesByPic = function(artPic){
        return genericResource.findByLikePromissed(service.catalarticlesUrlBase, 'pic', artPic)
        .then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    service.loadArticlesByName = function(artName){
        var searchInput = {entity:{},fieldNames:[],start: 0,max: 10};
        searchInput.codesAndNames = artName;
    	return genericResource.findByLikeWithSearchInputPromissed(service.catalarticlesUrlBase, searchInput)
        .then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };
    
    service.loadStkSectionArticleLots = function(strgSection){
        return genericResource.findByLikePromissed(service.stkarticlelot2strgsctnsUrlBase, 'strgSection', strgSection)
        .then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    service.loadStkSectionArticleLotsByIdentif = function(artPic, lotPic, strgSection){
        var searchInput = {entity:{},fieldNames:[],start: 0,max: 30};
    	if(angular.isDefined(artPic) && artPic){
    		searchInput.entity.artPic = artPic;
    		searchInput.fieldNames.push('artPic');
    	}
    	if(angular.isDefined(strgSection) && strgSection){
    		searchInput.entity.strgSection = strgSection;
    		searchInput.fieldNames.push('strgSection');
    	}
    	if(angular.isDefined(lotPic) && lotPic){
    		searchInput.entity.lotPic = lotPic;
    		searchInput.fieldNames.push('lotPic');
    	}
        return genericResource.findByLikeWithSearchInputPromissed(service.stkarticlelot2strgsctnsUrlBase, searchInput)
        .then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };
    
    service.loadStkSectionArticleLotsByName = function(sectionName){
        return genericResource.findByLikePromissed(service.stkarticlelot2strgsctnsUrlBase, 'sectionName', sectionName)
        .then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };
    
    // Load ArticlesLots from StkSection
    
    service.loadArticleLots = function(lotPic){
        var searchInput = {entity:{},fieldNames:[],start: 0,max: 30};
        searchInput.entity.lotPic = lotPic;
        searchInput.fieldNames.push('lotPic');
        // also load storage section
        searchInput.withStrgSection=true;
    	return genericResource.findByLikeWithSearchInputPromissed(service.stkarticlelotsUrlBase, searchInput)
        .then(function(entitySearchResult){
            return entitySearchResult.resultList;
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
            return entitySearchResult.resultList;
        });
    };

    service.translate();
    service.translatePromise = function(array) {
        return $translate(array);
    };
    
    service.isInvtryBySection = function(invInvtry){
    	return invInvtry && invInvtry.invInvtryType && invInvtry.invInvtryType=='BY_SECTION';
    };
    
    service.isInvtryByOrderAlphabeticRange = function(invInvtry){
    	return invInvtry && invInvtry.invInvtryType && invInvtry.invInvtryType=='ALPHABETICAL_ORDER_RANGE';
    };

    service.isInvtryOpen = function (invInvtry) {
        return angular.isDefined(invInvtry) && !service.isInvtryClosed(invInvtry) && !service.isInvtryPosted(invInvtry);
    }

    service.isInvtryClosed = function (invInvtry) {
        return angular.isDefined(invInvtry) && ("CLOSED" == invInvtry.invtryStatus || "CLOSING" == invInvtry.invtryStatus);
    }
    service.isInvtryPosted = function (invInvtry) {
        return angular.isDefined(invInvtry) && ("POSTED" == invInvtry.invtryStatus || "POSTING" == invInvtry.invtryStatus);
    }
    
    return service;
}])
.controller('invInvtrysCtlr',['$scope','genericResource','invInvtryUtils','invInvtryState','$location','$rootScope',
function($scope,genericResource,invInvtryUtils,invInvtryState,$location,$rootScope){

    $scope.searchInput = invInvtryState.resultHandler.searchInput();
    $scope.itemPerPage=invInvtryState.resultHandler.itemPerPage;
    $scope.totalItems=invInvtryState.resultHandler.totalItems;
    $scope.currentPage=invInvtryState.resultHandler.currentPage();
    $scope.maxSize =invInvtryState.resultHandler.maxResult;
    $scope.invInvtrys =invInvtryState.resultHandler.entities;
    $scope.selectedIndex=invInvtryState.resultHandler.selectedIndex;
    $scope.error = "";
    $scope.invInvtryUtils=invInvtryUtils;
    $scope.display = invInvtryState.resultHandler.displayInfo();


	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		invInvtryUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        if(invInvtryState.resultHandler.hasEntities())return;
        findByLike($scope.searchInput);
    }

    function processSearchInput(searchInput){
    	
    	if(angular.isDefined(searchInput.entity.invtryNbr) && searchInput.entity.invtryNbr){
    		searchInput.fieldNames.push('invtryNbr');
    	}
    	if(angular.isDefined(searchInput.entity.acsngUser) && searchInput.entity.acsngUser){
    		searchInput.fieldNames.push('acsngUser');
    	}
    	if(angular.isDefined(searchInput.entity.invtryStatus) && searchInput.entity.invtryStatus){
    		searchInput.fieldNames.push('invtryStatus');
    	}
    	if(angular.isDefined(searchInput.entity.invtryGroup) && searchInput.entity.invtryGroup){
    		searchInput.fieldNames.push('invtryGroup');
    	}
    	if(angular.isDefined(searchInput.entity.invInvtryType) && searchInput.entity.invInvtryType){
    		searchInput.fieldNames.push('invInvtryType');
    	}
    	if(angular.isDefined(searchInput.entity.descptn) && searchInput.entity.descptn){
    		searchInput.fieldNames.push('descptn');
    	}
    	if(angular.isDefined(searchInput.entity.section) && searchInput.entity.section){
    		searchInput.fieldNames.push('section');
    	}
    	if(angular.isDefined(searchInput.entity.rangeStart) && searchInput.entity.rangeStart){
    		searchInput.fieldNames.push('rangeStart');
    	}
    	if(angular.isDefined(searchInput.entity.rangeEnd) && searchInput.entity.rangeEnd){
    		searchInput.fieldNames.push('rangeEnd');
    	}
    	
    }
    function findByLike(searchInput){
		genericResource.findCustom(invInvtryUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			invInvtryState.resultHandler.searchResult(entitySearchResult);
		    $scope.searchInput = invInvtryState.resultHandler.searchInput();
		    setAccessingUserName();
		    setSectionName();
		})
        .error(function(error){
            $scope.error=error;
        });
    }

    $scope.handleSearchRequestEvent = function(){
    	processSearchInput($scope.searchInput);
    	findByLike($scope.searchInput);
    };

    $scope.paginate = function paginate(){
    	invInvtryState.resultHandler.currentPage($scope.currentPage);
        $scope.searchInput = invInvtryState.resultHandler.paginate();
        findByLike($scope.searchInput);
    };

    $scope.handlePrintRequestEvent = function(){
        // To do
	};
	
	$scope.show = function(invInvtry, index){
		var i = invInvtryState.resultHandler.selectedObject(invInvtry);
		if(i>-1){
			$location.path('/InvInvtrys/show/');
		}
	};

	$scope.edit = function(invInvtry, index){
		if(invInvtryState.resultHandler.selectedObject(invInvtry)){
			$location.path('/InvInvtrys/edit/');
		}
	};
	
    $scope.onUserChanged = function(){
    	setAccessingUserName();
    };
    function setAccessingUserName(){
    	if(angular.isUndefined($scope.searchInput.entity.acsngUser) || !$scope.searchInput.entity.acsngUser){
        	$scope.display.acsngUserFullName='';    		
        	$scope.display.acsngUser='';
    		return;
    	} else if (angular.equals($scope.display.acsngUser,$scope.searchInput.entity.acsngUser)) {
    		return;
    	}
        genericResource.findByLikePromissed(invInvtryUtils.loginnamessUrlBase, 'loginName', $scope.searchInput.entity.acsngUser)
        .then(function(entitySearchResult){
            var resultList = entitySearchResult.resultList;
            if(angular.isDefined(resultList) && resultList.length>0)
            	$scope.display.acsngUser = resultList[0].loginName; 
        		$scope.display.acsngUserFullName = resultList[0].fullName; 
        }, function(error){
        	$scope.searchInput.entity.acsngUser='';
        	$scope.display.acsngUser = ''; 
    		$scope.display.acsngUserFullName = ''; 
        });
    }
    $scope.onSectionChanged = function(){
    	setSectionName();
    };
    function setSectionName(){
    	if(angular.isUndefined($scope.searchInput.entity.section) || !$scope.searchInput.entity.section){
        	$scope.display.section='';    		
        	$scope.display.sectionName='';    		
    		return;
    	} else if (angular.equals($scope.display.section,$scope.searchInput.entity.section)) {
    		return;
    	}
    	
        genericResource.findByLikePromissed(service.stksectionsUrlBase, 'sectionCode', $scope.searchInput.entity.section)
        .then(function(entitySearchResult){
            var resultList = entitySearchResult.resultList;
            if(angular.isDefined(resultList) && resultList.length>0)
            	$scope.display.section = resultList[0].sectionCode; 
        		$scope.display.sectionName = resultList[0].name; 
        }, function(error){
        	$scope.display.section = ''; 
    		$scope.display.sectionName = ''; 
        });
    }

    $scope.onUserSelected = function(item,model,label){
    	$scope.searchInput.entity.acsngUser=item.loginName;
    	$scope.display.acsngUser=item.loginName;
    	$scope.display.acsngUserFullName=item.fullName;
    };
    
    $scope.onSectionSelected = function(item,model,label){
    	$scope.searchInput.entity.section=item.sectionCode;
    	$scope.display.section=item.strgSection;    		
    	$scope.display.sectionName=item.name;
    }
	
}])
.controller('invInvtryCreateCtlr',['$scope','invInvtryUtils','$translate',
                                   'invInvtryManagerResource','$location','invInvtryState','adUtils',
        function($scope,invInvtryUtils,$translate,invInvtryManagerResource,$location,invInvtryState,adUtils){
    $scope.invInvtry = {};
    $scope.display = {};
    $scope.error = "";
    $scope.invInvtryUtils=invInvtryUtils;
    
    $scope.onUserSelectedInCreate = function(item,model,label){
    	$scope.invInvtry.acsngUser=item.loginName;
    	$scope.display.acsngUserFullName=item.fullName;
    }
    
    $scope.onSectionSelectedInCreate = function(item,model,label){
    	$scope.invInvtry.section=item.strgSection;
    	$scope.display.sectionName=item.name;
    }

    $scope.create = function(){
    	$scope.invInvtry.invtryDt=new Date();
    	$scope.invInvtry.invtryStatus='ONGOING';
    	invInvtryManagerResource.prepare($scope.invInvtry)
    	.success(function(invInvtry){
    		var index = invInvtryState.resultHandler.unshift(invInvtry);
    		if(adUtils.greaterThan(index)){
    			$location.path('/InvInvtrys/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };

  
}])
.controller('invInvtryShowCtlr',['$scope','invInvtryManagerResource','$location','invInvtryUtils','invInvtryState','$rootScope','genericResource','$routeParams','searchResultHandler','adUtils',
                                 function($scope,invInvtryManagerResource,$location,invInvtryUtils,invInvtryState,$rootScope,genericResource,$routeParams,searchResultHandler,adUtils){
    $scope.invInvtry = invInvtryState.resultHandler.entity();
    $scope.error = "";
    $scope.invInvtryUtils=invInvtryUtils;
    var itemsResultHandler = invInvtryState.itemsResultHandler();
    $scope.searchInput = itemsResultHandler.searchInput();
    $scope.itemPerPage=itemsResultHandler.itemPerPage;
    $scope.totalItems=itemsResultHandler.totalItems;
    $scope.currentPage=itemsResultHandler.currentPage();
    $scope.maxSize =itemsResultHandler.maxResult;
    $scope.invInvtryItems =itemsResultHandler.entities;
    $scope.selectedIndex=itemsResultHandler.selectedIndex;
    $scope.display = itemsResultHandler.displayInfo();
    
    $scope.invtryEditable = !invInvtryUtils.isInvtryPosted($scope.invInvtry);
    $scope.itemEditable = invInvtryUtils.isInvtryOpen($scope.invInvtry);
    $scope.invtryCopy = angular.copy($scope.invInvtry);
    
    $scope.invtryChanged = invtryChangedFctn;
    
    function refreshDisplay(){
        $scope.invInvtry = invInvtryState.resultHandler.entity();
        $scope.invtryCopy = angular.copy($scope.invInvtry);
        $scope.invtryEditable = !invInvtryUtils.isInvtryPosted();
        $scope.itemEditable = invInvtryUtils.isInvtryOpen();
    }
    
    $scope.onUserSelectedInShow = function(item,model,label){
    	$scope.invInvtry.acsngUser=item.loginName;
    	$scope.display.acsngUserFullName=item.fullName;
    }
    
    function consumeError(error, status) {
        var error_msg=error;            
        if(400 === status && error && error.contains("org.codehaus.jackson.JsonParseException")) {
                        var key = [];
                        key.push("InvInvtry_longPdctJsonArr_exception.title")
                        error_msg = invInvtryUtils.translatePromise(key).then(function(translations){
                            if(translations && translations.length > 0) {
                               error_msg = translations;
                               $scope.error = error_msg;
                            }
                        });
                    }
        return error_msg;
    }

    function loadInvInvtryItems() {
    	$scope.searchInput.entity.invtryNbr=$scope.invInvtry.invtryNbr;
    	$scope.searchInput.fieldNames = [];
    	if(angular.isDefined($scope.searchInput.entity.invtryNbr) && $scope.searchInput.entity.invtryNbr){
            $scope.searchInput.fieldNames.push('invtryNbr');
    	}
        
        if(angular.isDefined($scope.searchInput.entity.lotPic) && $scope.searchInput.entity.lotPic){
            $scope.searchInput.fieldNames.push('lotPic');
        }

        if(angular.isDefined($scope.searchInput.entity.artPic) && $scope.searchInput.entity.artPic){
            $scope.searchInput.fieldNames.push('artPic');
        }

        if(angular.isDefined($scope.searchInput.entity.section) && $scope.searchInput.entity.section){
            $scope.searchInput.fieldNames.push('section');
        }
        
        genericResource.findByLike(invInvtryUtils.invinvtrysUrlBase,$scope.searchInput)
        .success(function(searchResult){
        	itemsResultHandler.searchResult(searchResult);
    	})
    	.error(function(error){
    		$scope.error=error;
    	});
    }
    function initView(){
    	fixDateFields();
    	setAccessingUserName();
    	loadInvInvtryItems();    
    }
    initView();
    function setAccessingUserName(){
    	if(angular.isUndefined($scope.invInvtry.acsngUser) || !$scope.invInvtry.acsngUser)
    		return;
        genericResource.findByLikePromissed(invInvtryUtils.loginnamessUrlBase, 'loginName', $scope.invInvtry.acsngUser)
        .then(function(entitySearchResult){
            var resultList = entitySearchResult.resultList;
            if(angular.isDefined(resultList) && resultList.length>0)
            	$scope.display.acsngUserFullName = resultList[0].fullName; 
        }, function(error){
        	$scope.invInvtry.acsngUser='';
        	$scope.display.acsngUserFullName = '';
        });
    }
    function fixDateFields(){
    	$scope.invInvtry.invtryDt = adUtils.fixDateField($scope.invInvtry.invtryDt);
    	$scope.invInvtry.preparedDt = adUtils.fixDateField($scope.invInvtry.preparedDt);
    	$scope.invInvtry.acsngDt = adUtils.fixDateField($scope.invInvtry.acsngDt);
    }
    
    $scope.handleSearchRequestEvent = function(){
    	loadInvInvtryItems();
    };

    $scope.paginate = function(){
    	itemsResultHandler.currentPage($scope.currentPage);
        $scope.searchInput = itemsResultHandler.paginate();
        loadInvInvtryItems();
    };

	$scope.handlePrintRequestEvent = function(){
        // To do
	}
	$scope.handleResetRequestEvent = function(){
		$scope.searchInput = itemsResultHandler.newSearchInput();	
		$scope.display = itemsResultHandler.displayInfo({});
        loadInvInvtryItems();
	}
	$scope.handleAlphabeticRequestEvent = function(){
		$scope.searchInput = itemsResultHandler.newSearchInput();
		$scope.searchInput.a2z = true;
        loadInvInvtryItems();
	}

    $scope.addItem = function(invtryItem){
    	delete invtryItem.editing;
        invInvtryManagerResource.addItem(invtryItem)
    	.success(function(invtryItem){
    		itemsResultHandler.unshift(invtryItem);
    		$scope.searchInput.entity.artPic=undefined;
    		$scope.searchInput.entity.artName=undefined;
    		$scope.searchInput.entity.lotPic=undefined;
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
	$scope.updateItem = function(invtryItem){
    	delete invtryItem.editing;
        invInvtryManagerResource.updateItem(invtryItem)
    	.success(function(invtryItem){
    		itemsResultHandler.replace(invtryItem);
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
    $scope.disableItem = function(invtryItem){
    	delete invtryItem.editing;
        invInvtryManagerResource.disableItem(invtryItem)
    	.success(function(invtryItem){
    		itemsResultHandler.replace(invtryItem);
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
    $scope.enableItem = function(invtryItem){
    	delete invtryItem.editing;
        invInvtryManagerResource.enableItem(invtryItem)
    	.success(function(invtryItem){
    		itemsResultHandler.replace(invtryItem);
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
    
    $scope.qtyChanged = function(invtryItem){
    	var dt = new Date();
    	invtryItem.acsngDt = dt.getTime();
    };
    
    function invtryChangedFctn(){
    	return !angular.equals($scope.invInvtry, $scope.invtryCopy);
    };

    $scope.update = function(){
    	if(!invtryChangedFctn()) return;
      invInvtryManagerResource.update($scope.invInvtry)
    	.success(function(invInvtry){
    		if(invInvtryState.resultHandler.replace(invInvtry)){
    			refreshDisplay();
    		}
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };

    $scope.close = function(){
        invInvtryManagerResource.close($scope.invInvtry)
    	.success(function(invInvtry){
    		if(invInvtryState.resultHandler.replace(invInvtry)){
    			$scope.invInvtry = invInvtryState.resultHandler.entity();
    		}
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
    $scope.post = function(){
        invInvtryManagerResource.post($scope.invInvtry)
    	.success(function(invInvtry){
    		if(invInvtryState.resultHandler.replace(invInvtry)){
    			$scope.invInvtry = invInvtryState.resultHandler.entity();
    		}
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
    
    $scope.onSectionSelectedInSearch = function(item,model,label){
    	$scope.searchInput.entity.section=item.strgSection;
    	$scope.display.sectionName=item.sectionName;
    	$scope.display.sectionCode=item.strgSection;
    }
    $scope.onArticleLotChangedInSearch = function(){
    	if($scope.searchInput.entity.lotPic && $scope.searchInput.entity.lotPic.length<9) return;
    	if(
    		(angular.isDefined($scope.searchInput.entity.artPic) && $scope.searchInput.entity.artPic) && 
    		(angular.isDefined($scope.searchInput.entity.section) && $scope.searchInput.entity.section) 
    	){
    		return;
    	}
    	if(angular.isUndefined($scope.searchInput.entity.artPic) || !$scope.searchInput.entity.artPic){
        	genericResource.findByIdentif(invInvtryUtils.stkarticlelotsUrlBase,$scope.searchInput.entity.lotPic.toUpperCase())
        	.success(function(articleLot){
        		$scope.searchInput.entity.artPic=articleLot.artPic;
            	genericResource.findByIdentif(invInvtryUtils.catalarticlesUrlBase,$scope.searchInput.entity.artPic)
            	.success(function(catalArticle){
            		$scope.searchInput.entity.artName=catalArticle.features.artName;
            	})
            	.error(function(error){
            		$scope.error=error;
            	});

        	})
        	.error(function(error){
        		// Ignore
        	});
    	} 
    };
    
    $scope.onArticleLotSectionSelectedInSearch = function(item,model,label){
    	$scope.searchInput.entity.lotPic = item.lotPic;
    	var artPic = item.artPic;
    	if(	(angular.isUndefined($scope.searchInput.entity.artPic) || 
    			!$scope.searchInput.entity.artPic) || 
    			$scope.searchInput.entity.artPic!=artPic)
    	{
    		$scope.searchInput.entity.artPic=artPic;
    		$scope.searchInput.entity.artName=item.artName;
    	}
    	
    	var strgSection = item.strgSection;
    	if((angular.isUndefined($scope.searchInput.entity.section) || 
    			!$scope.searchInput.entity.section) || 
    			$scope.searchInput.entity.section!=strgSection)
    	{
    		$scope.searchInput.entity.section=strgSection;
    		$scope.display.sectionName=item.sectionName;
    	}
    };
    
    $scope.showLotsForArtPic = function(lotPic){
    	if((angular.isDefined(lotPic) && lotPic.length>=8)){
    		return [];
    	} 
    	var artPic = $scope.searchInput.entity.artPic;
    	var section = $scope.searchInput.entity.section;
    	if(((angular.isUndefined(artPic) || !artPic)) && ((angular.isUndefined(section) || !section))){
    		return [];
    	} 
    	return invInvtryUtils.loadStkSectionArticleLotsByIdentif(artPic,lotPic,section);
    };
    
    $scope.onArticleLotSelectedInSearch = function(item,model,label){
    	$scope.searchInput.entity.lotPic=item.lotPic;
    	$scope.searchInput.entity.artPic=item.artPic;
//    	// read the article name
    	genericResource.findByIdentif(invInvtryUtils.catalarticlesUrlBase,item.artPic)
    	.success(function(catalArticle){
    		$scope.searchInput.entity.artName=catalArticle.features.artName;
    	})
    	.error(function(error){
    		$scope.error=error;
    	});
    };
    
    $scope.onArticleSelectedInSearch = function(item,model,label){
    	$scope.searchInput.entity.artPic=item.pic;
    	$scope.searchInput.entity.artName=item.features.artName;

    };
}]);
