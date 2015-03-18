'use strict';
    
angular.module('AdInvtry')

.factory('invInvtryManagerResource',['$http', function($http){
    var service = {};
    var urlBase = '/adinvtry.server/rest/inventory';
    service.update = function(invtryHolder){
        return $http.put(urlBase+'/update',invtryHolder);
    };
    service.close = function(invtryHolder){
        return $http.put(urlBase+'/close',invtryHolder);
    };
    service.prepare = function(invtry){
        return $http.put(urlBase+'/prepare',invtry);
    };
    service.updateItem = function(invtryItem){
        return $http.put(urlBase+'/updateItem',invtryItem);
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
      {enumKey:'CLOSED', translKey:'InvInvntrStatus_CLOSED_description.title'}
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
    	            
    	            'InvInvtryItem_section_description.title',
    	            'InvInvtryItem_artPic_description.title',
    	            'InvInvtryItem_lotPic_description.title',
    	            'InvInvtryItem_artName_description.title',
    	            'InvInvtryItem_expectedQty_description.title',
    	            'InvInvtryItem_asseccedQty_description.title',
    	            'InvInvtry_acsngUserFullName_description.title',
    	            'InvInvtryItem_expirDt_description.title',
    	            'InvInvtryItem_acsngDt_description.title',
    	            
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
    	            'Entity_info.title'

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
    }
    
    service.isInvtryBySection = function(invInvtry){
    	return invInvtry && invInvtry.invInvtryType && invInvtry.invInvtryType=='BY_SECTION';
    };
    
    service.isInvtryByOrderAlphabeticRange = function(invInvtry){
    	return invInvtry && invInvtry.invInvtryType && invInvtry.invInvtryType=='ALPHABETICAL_ORDER_RANGE';
    };

    service.isInvInvtryEditable = function (invInvtry) {
        if(invInvtry && "CLOSED" != invInvtry.invtryStatus) return true;
        return false;
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

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		invInvtryUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        if(invInvtryState.resultHandler.hasEntities())return;
        findCustom($scope.searchInput);
    }

    function findCustom(searchInput){
		genericResource.findByLike(invInvtryUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			invInvtryState.resultHandler.searchResult(entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }

    $scope.handleSearchRequestEvent = function(){
    	if($scope.searchInput.acsngUser){
    		$scope.searchInput.entity.acsngUser=$scope.searchInput.acsngUser.loginName;
    	} else {
    		$scope.searchInput.entity.acsngUser='';
    	}
    	findCustom($scope.searchInput);
    };

    $scope.paginate = function paginate(){
    	invInvtryState.resultHandler.currentPage($scope.currentPage);
        $scope.searchInput = invInvtryState.resultHandler.paginate();
        findCustom($scope.searchInput);
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
}])
.controller('invInvtryCreateCtlr',['$scope','invInvtryUtils','$translate','invInvtryManagerResource','$location','invInvtryState',
        function($scope,invInvtryUtils,$translate,invInvtryManagerResource,$location,invInvtryState){
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
    	$scope.display.sectionName=item.sectionName;
    }

    $scope.create = function(){
    	$scope.invInvtry.invtryDt=new Date();
    	$scope.invInvtry.invtryStatus='ONGOING';
    	invInvtryManagerResource.prepare($scope.invInvtry)
    	.success(function(invInvtry){
    		if(invInvtryState.resultHandler.replace(invInvtry)){
    			$location.path('/InvInvtrys/show/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };

  
}])
.controller('invInvtryShowCtlr',['$scope','invInvtryManagerResource','$location','invInvtryUtils','invInvtryState','$rootScope','genericResource','$routeParams','searchResultHandler',
                                 function($scope,invInvtryManagerResource,$location,invInvtryUtils,invInvtryState,$rootScope,genericResource,$routeParams,searchResultHandler){
    $scope.invInvtry = invInvtryState.resultHandler.entity();
    $scope.error = "";
    $scope.invInvtryUtils=invInvtryUtils;
    $scope.invInvtryItemHolder = emptyItemHolder();
    var itemsResultHandler = invInvtryState.itemsResultHandler();
    $scope.searchInput = itemsResultHandler.searchInput();
    $scope.itemPerPage=itemsResultHandler.itemPerPage;
    $scope.totalItems=itemsResultHandler.totalItems;
    $scope.currentPage=itemsResultHandler.currentPage();
    $scope.maxSize =itemsResultHandler.maxResult;
    $scope.invInvtryItems =itemsResultHandler.entities;
    $scope.selectedIndex=itemsResultHandler.selectedIndex;
    $scope.display = itemsResultHandler.displayInfo();

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
    loadInvInvtryItems();    
    
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

    $scope.saveItem = function(invtryItem){
    	delete invtryItem.editing;
        invInvtryManagerResource.update(invtryItem)
    	.success(function(invtryItem){
    		itemsResultHandler.replace(invtryItem);
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };

    $scope.save = function(){
    };

    $scope.close = function(){
//        var invInvtryHolder = {};
//        invInvtryHolder.invtry = $scope.invInvtry;
//        invInvtryHolder.invtryItemHolders = $scope.invInvtryItemHolders;
//        //save
//    	
//        invInvtryManagerResource.close(invInvtryHolder)
//    	.success(function(invInvtryHolder){
//    		if($scope.invInvtry.invtryNbr){
//    			invInvtryState.replace(invInvtryHolder.invtry);
//    		} else {
//    			invInvtryState.set(invInvtryHolder.invtry);
//    		}
//    	    $scope.invInvtry = invInvtryState.invInvtry();
//    	    $scope.invInvtryItemHolder = {invtryItem:{}};
//    	})
//    	.error(function(error){
//    		$scope.error = error;
//    	});
    };
    
    $scope.onSectionSelectedInSearch = function(item,model,label){
    	$scope.searchInput.entity.section=item.strgSection;
    	$scope.display.sectionName=item.sectionName;
    }
    $scope.onArticleLotChangedInSearch = function(){
    	if($scope.searchInput.entity.lotPic && $scope.searchInput.entity.lotPic.length<9) return;
    	
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
//    	if(!$scope.invInvtryItemHolder.invtryItem.section || 
//    			$scope.invInvtryItemHolder.invtryItem.section==''){
//    		var strgSctns = item.strgSctns;
//    		// Select the section
//			if(strgSctns){
//				if(strgSctns.length==1){
//					var stkSection = strgSctns[0].stkSection;
//					if(stkSection)
//						$scope.invInvtryItemHolder.invtryItem.section=stkSection.sectionCode;    				
//				}
//			}
//    	}
    };
    
    $scope.onArticleSelectedInSearch = function(item,model,label){
    	$scope.searchInput.entity.artPic=item.pic;
    	$scope.searchInput.entity.artName=item.features.artName;

//		// find article lots
//        var lotSearchInput = {entity:{},fieldNames:[],start: 0,max: 10};
//        lotSearchInput.entity.artPic = item.artPic;
//        if(lotSearchInput.fieldNames.indexOf('artPic')==-1)
//        	lotSearchInput.fieldNames.push('artPic');
//        // closed date must be null;
//        if(lotSearchInput.fieldNames.indexOf('closedDt')==-1)
//        	lotSearchInput.fieldNames.push('closedDt');
//        genericResource.findBy(invInvtryUtils.stkarticlelotsUrlBase, lotSearchInput)
//		.success(function(entitySearchResult) {
////			$scope.invInvtryItemHolder.candidateLots=entitySearchResult.resultList;
//            var candidateLots=entitySearchResult.resultList;
//			if(candidateLots && candidateLots.length==1){
//		    	$scope.invInvtryItemHolder.invtryItem.lotPic=candidateLots[0].lotPic;
//			} else if (candidateLots && candidateLots.length>1){
//				if($scope.invInvtryItemHolder.invtryItem.section){
//					var candidateLots = $scope.invInvtryItemHolder.candidateLots;
//					var found = false;
//					for (var int = 0; int < candidateLots.length; int++) {
//						var candidateLot = candidateLots[i];
//						var strgSctns = candidateLot.strgSctns;
//						for (var int2 = 0; int2 < strgSctns.length; int2++) {
//							var strgSctn = strgSctns[int2];
//							if($scope.invInvtryItemHolder.invtryItem.section==strgSctn.strgSection){
//								$scope.invInvtryItemHolder.invtryItem.lotPic = candidateLot.lotPic;
//								found =true;
//								break;
//							}
//						}
//						if(found) break;
//					}
//				}
//			}
//		})
//		.error(function(error){$scope.error=error;});
    };
    
    $scope.editItem = function(invtryItem){
    	invtryItem.editing = true;
    };
    
    $scope.addItem = function() {
//        if(!$scope.invInvtryItemHolder) return;
//        $scope.invInvtryItemHolders.push($scope.invInvtryItemHolder);
//        $scope.invInvtryItemHolder = emptyItemHolder();
    };
                                     
    $scope.deleteItem = function(index) {
////          if(!index) return;
//          try {
//              $scope.invInvtryItemHolders.splice(index,1);
//          }finally {}
    };
    function emptyItemHolder () {
//        return {
//          invtryItem : {}  
//        };
    }
}]);
