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
    return service;
}])
.factory('invInvtryUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adinvtry.server/rest/invinvtrys';
    service.stksectionsUrlBase='/adstock.server/rest/stksections';
    service.stkarticlelotsUrlBase='/adstock.server/rest/stkarticlelots';
    service.catalarticlesUrlBase='/adcatal.server/rest/catalarticles';
    service.loginnamessUrlBase='/adbase.server/rest/loginnamess';
    service.stkarticlelot2strgsctnsUrlBase='/adstock.server/rest/stkarticlelot2strgsctns';
    
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
    service.invInvtryTypeI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.invInvtryTypeI18nMsgTitleKey(enumKey)];
    };
    
    service.invInvntrStati = [
      {enumKey:'SUSPENDED', translKey:'InvInvntrStatus_SUSPENDED_description.title'},
      {enumKey:'ONGOING', translKey:'InvInvntrStatus_ONGOING_description.title'},
      {enumKey:'RESUMED', translKey:'InvInvntrStatus_RESUMED_description.title'},
      {enumKey:'CLOSED', translKey:'InvInvntrStatus_CLOSED_description.title'}
    ];

    service.language=sessionManager.language;
    
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
    	            'InvInvtry_section_description.title',
    	            'InvInvtry_section_description.text',
    	            'InvInvtry_invtryNbr_description.title',
    	            'InvInvtry_invtryNbr_description.text',

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
    	            'Entity_add.title'
    	            
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.loadSections = function(val){
        return loadSectionsPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    function loadSectionsPromise(val){
    	if(!val) return;
    	
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        searchInput.codeOrName = val;
        var deferred = $q.defer();
        genericResource.findCustom(service.stksectionsUrlBase, searchInput)
		.success(function(entitySearchResult) {
        	deferred.resolve(entitySearchResult);
		})
        .error(function(){
            deferred.reject(service.translations['InvInvtry_NoSectionFound_description.title']);
        });
        return deferred.promise;
    }    

    
    service.loadArticles = function(val){
        return loadArticlesPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    function loadArticlesPromise(val){
    	if(!val) return;
    	
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };

        searchInput.codeAndNames = val;
        var deferred = $q.defer();
        genericResource.findByLike(service.catalarticlesUrlBase, searchInput)
		.success(function(entitySearchResult) {
        	deferred.resolve(entitySearchResult);
		})
        .error(function(){
            deferred.reject(service.translations['InvInvtry_NoArticleFound_description.title']);
        });
        return deferred.promise;
    }    

    service.loadUsers = function(val){
        return loadUsersPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    function loadUsersPromise(val){
    	if(!val) return;
    	
        var searchInput = {
            entity:{
            },
            fieldNames:[],
            start: 0,
            max: 10
        };

        searchInput.entity.fullName = val;
        if(searchInput.fieldNames.indexOf('fullName')==-1)        
        	searchInput.fieldNames.push('fullName');
        var deferred = $q.defer();
        genericResource.findByLike(service.loginnamessUrlBase, searchInput)
		.success(function(entitySearchResult) {
        	deferred.resolve(entitySearchResult);
		})
        .error(function(){
            deferred.reject(service.translations['InvInvtry_NoArticleFound_description.title']);
        });
        return deferred.promise;
    }    
    
    service.translate();
    
    service.isInvtryBySection = function(invInvtry){
    	return invInvtry && invInvtry.invInvtryType && invInvtry.invInvtryType=='BY_SECTION';
    }

    
    return service;
}])
.factory('invInvtryState',['$rootScope',function($rootScope){

    var service = {
    };

    // The search state.
    // The current list of business partners.
    var invInvtrysVar=[];
    service.hasInvtrys = function(){
        return invInvtrysVar && invInvtrysVar.length>0;
    };
    service.invInvtrys = function(invInvtrysIn){
        if(invInvtrysIn){
            var length = invInvtrysVar.length;
            for	(var index = 0; index < length; index++) {
                invInvtrysVar.pop();
            }
            length = invInvtrysIn.length;
            for	(var index1 = 0; index1 < length; index1++) {
                invInvtrysVar.push(invInvtrysIn[index1]);
            }
        }
        return invInvtrysVar;
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
    
    var stkSection = {};
    service.stkSection = function(stkSectionIn){
    	if(stkSectionIn) stkSection = stkSectionIn;
    	return stkSection;
    }
    return service;

}])
.controller('invInvtrysCtlr',['$scope','genericResource','invInvtryUtils','invInvtryState','$location','$rootScope',
function($scope,genericResource,invInvtryUtils,invInvtryState,$location,$rootScope){

    $scope.searchInput = invInvtryState.searchInput();
    $scope.itemPerPage=invInvtryState.itemPerPage;
    $scope.totalItems=invInvtryState.totalItems;
    $scope.currentPage=invInvtryState.currentPage();
    $scope.maxSize =invInvtryState.maxSize;
    $scope.invInvtrys =invInvtryState.invInvtrys;
    $scope.selectedIndex=invInvtryState.selectedIndex;
    $scope.handleSearchRequestEvent = handleSearchRequestEvent;
    $scope.handlePrintRequestEvent = handlePrintRequestEvent;
    $scope.paginate = paginate;
    $scope.error = "";
    $scope.invInvtryUtils=invInvtryUtils;
    $scope.show=show;
    $scope.edit=edit;

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		invInvtryUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        if(invInvtryState.hasInvtrys())return;
        findCustom($scope.searchInput);
    }

    function findCustom(searchInput){
		genericResource.findByLike(invInvtryUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			invInvtryState.consumeSearchResult(searchInput,entitySearchResult);
		})
        .error(function(error){
            $scope.error=error;
        });
    }

    function  handleSearchRequestEvent(){
    	if($scope.searchInput.acsngUser){
    		$scope.searchInput.entity.acsngUser=$scope.searchInput.acsngUser.loginName;
    	} else {
    		$scope.searchInput.entity.acsngUser='';
    	}
    	findCustom($scope.searchInput);
    };

    function paginate(){
        $scope.searchInput = invInvtryState.paginate();
        findCustom($scope.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
	
	function show(invInvtry, index){
		if(invInvtryState.peek(invInvtry, index)){
			$location.path('/InvInvtrys/show/');
		}
	}

	function edit(invInvtry, index){
		if(invInvtryState.peek(invInvtry, index)){
			$location.path('/InvInvtrys/edit/');
		}
	}
}])
.controller('invInvtryCreateCtlr',['$scope','invInvtryUtils','$translate','genericResource','$location','invInvtryState',
        function($scope,invInvtryUtils,$translate,genericResource,$location,invInvtryState){
    $scope.invInvtry = invInvtryState.invInvtry();
    $scope.create = create;
    $scope.error = "";
    $scope.invInvtryUtils=invInvtryUtils;

    function create(){
    	$scope.invInvtry.invtryDt=Date();
    	$scope.invInvtry.invInvntrStatus='ONGOING';
    	if($scope.stkSection){
    		invInvtryState.stkSection($scope.stkSection);
    	}
		if(invInvtryState.push($scope.invInvtry)){
			$location.path('/InvInvtrys/show/');
		}
//    	
//    	genericResource.create(invInvtryUtils.urlBase, $scope.invInvtry)
//    	.success(function(invInvtry){
//    		if(invInvtryState.push(invInvtry)){
//    			$location.path('/InvInvtrys/show/');
//    		}
//    	})
//    	.error(function(error){
//    		$scope.error = error;
//    	});
    };
}])
.controller('invInvtryEditCtlr',['$scope','genericResource','$location','invInvtryUtils','invInvtryState',
                                 function($scope,genericResource,$location,invInvtryUtils,invInvtryState){
    $scope.invInvtry = invInvtryState.invInvtry();
    $scope.error = "";
    $scope.invInvtryUtils=invInvtryUtils;
    $scope.update = function (){
    	genericResource.update(invInvtryUtils.urlBase, $scope.invInvtry)
    	.success(function(invInvtry){
    		if(invInvtryState.replace(invInvtry)){
    			$location.path('/InvInvtrys/show/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };
}])
.controller('invInvtryShowCtlr',['$scope','invInvtryManagerResource','$location','invInvtryUtils','invInvtryState','$rootScope','genericResource',
                                 function($scope,invInvtryManagerResource,$location,invInvtryUtils,invInvtryState,$rootScope,genericResource){
    $scope.invInvtry = invInvtryState.invInvtry();
    $scope.error = "";
    $scope.invInvtryUtils=invInvtryUtils;
    $scope.invInvtryItemHolder = {
    	invtryItem:{}
    };
    $scope.invInvtryItemHolders = [];

    function init(){
    	var stkSection =invInvtryState.stkSection();
    	if(invInvtryUtils.isInvtryBySection($scope.invInvtry) && stkSection){
    		$scope.invInvtryItemHolder.invtryItem.section=stkSection.sectionCode;
    	}
    }
    init();
    
    $scope.save = function(){
        var invInvtryHolder = {};
        invInvtryHolder.invtry = $scope.invInvtry;
        invInvtryHolder.invtryItemHolders = $scope.invInvtryItemHolders;
        //save
    	
        invInvtryManagerResource.update(invInvtryHolder)
    	.success(function(invInvtryHolder){
    		if($scope.invInvtry.invtryNbr){
    			invInvtryState.replace(invInvtryHolder.invtry);
    		} else {
    			invInvtryState.set(invInvtryHolder.invtry);
    		}
    	    $scope.invInvtry = invInvtryState.invInvtry();
    	    $scope.invInvtryItemHolder = {invtryItem:{}};
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };

    $scope.close = function(){
        var invInvtryHolder = {};
        invInvtryHolder.invtry = $scope.invInvtry;
        invInvtryHolder.invtryItemHolders = $scope.invInvtryItemHolders;
        //save
    	
        invInvtryManagerResource.close(invInvtryHolder)
    	.success(function(invInvtryHolder){
    		if($scope.invInvtry.invtryNbr){
    			invInvtryState.replace(invInvtryHolder.invtry);
    		} else {
    			invInvtryState.set(invInvtryHolder.invtry);
    		}
    	    $scope.invInvtry = invInvtryState.invInvtry();
    	    $scope.invInvtryItemHolder = {invtryItem:{}};
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
    
    $scope.onSectionSelected = function(item,model,label){
    	$scope.invInvtryItemHolder.invtryItem.section=stkSection.sectionCode;
    }

    $scope.onArticleLotSelected = function(item,model,label){
    	$scope.invInvtryItemHolder.invtryItem.lotPic=item.lotPic;
    	$scope.invInvtryItemHolder.invtryItem.artPic=item.artPic;
    	// read the article name
    	genericResource.findByIdentif(invInvtryUtils.catalarticlesUrlBase,item.artPic)
    	.success(function(catalArticle){
    		$scope.invInvtryItemHolder.invtryItem.artPic=item.features.artName;
    	})
    	.error(function(error){
    		$scope.error=error;
    	});
    	// Select the section
        var strgsctnSearchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        strgsctnSearchInput.entity.lotPic=item.lotPic;
        strgsctnSearchInput.entity.artPic=item.artPic;
        if(strgsctnSearchInput.fieldNames.indexOf('lotPic')==-1)
        	strgsctnSearchInput.fieldNames.push('lotPic');
        if(strgsctnSearchInput.fieldNames.indexOf('artPic')==-1)
        	strgsctnSearchInput.fieldNames.push('artPic');
    	genericResource.findByLike(invInvtryUtils.stkarticlelot2strgsctnsUrlBase,strgsctnSearchInput)
    	.success(function(strgsctnSearchResult){
    		if(strgsctnSearchResult.resultList.length==1){
    			var stkSection = strgsctnSearchResult.resultList[0].stkSection;
    			if(stkSection)
    				$scope.invInvtryItemHolder.invtryItem.section=stkSection.sectionCode;    				
    		}
    	})
    	.error(function(error){$scope.error=error;});
    }
    
    
    $scope.edit =function(){
        $location.path('/InvInvtrys/edit/');
    };

}]);
