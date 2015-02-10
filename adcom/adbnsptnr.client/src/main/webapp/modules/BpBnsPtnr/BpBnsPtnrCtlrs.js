'use strict';
    
angular.module('AdBnsptnr')

.factory('bpBnsPtnrUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adbnsptnr.server/rest/bpbnsptnrs';
    service.countryNamesUrlBase='/adbnsptnr.server/rest/basecountrynames';

    service.isIndividual = function(bpBnsPtnr){
    	return bpBnsPtnr.ptnrType=='INDIVIDUAL';
    };
    service.isInstitution = function(bpBnsPtnr){
    	return bpBnsPtnr.ptnrType=='LEGAL';
    };
    
    service.ptnrTypeI18nMsgTitleKey = function(enumKey){
    	return "BpPtnrType_"+enumKey+"_description.title";
    };
    service.ptnrTypeI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.ptnrTypeI18nMsgTitleKey(enumKey)];
    };
    
    service.bpBnsPtnrTypes = [
      {enumKey:'INDIVIDUAL', translKey:'BpPtnrType_INDIVIDUAL_description.title'},
      {enumKey:'LEGAL', translKey:'BpPtnrType_LEGAL_description.title'}
    ];
    
    service.genderI18nMsgTitleKey = function(enumKey){
    	return "BaseGender_"+enumKey+"_description.title";
    };
    service.genderI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.genderI18nMsgTitleKey(enumKey)];
    };

    service.baseGenders = [
      {enumKey:'FEMALE', translKey:'BaseGender_FEMALE_description.title'},
      {enumKey:'MALE', translKey:'BaseGender_MALE_description.title'}
    ];
    
    service.language=sessionManager.language;
    
    service.translate = function(){
    	$translate(['BpPtnrType_INDIVIDUAL_description.title',
    	            'BpPtnrType_LEGAL_description.title',
    	            'BaseGender_FEMALE_description.title',
    	            'BaseGender_MALE_description.title',
    	            'BpBnsPtnr_ptnrNbr_description.title',
    	            'BpBnsPtnr_fullName_description.title',
    	            'BpBnsPtnr_ptnrType_description.title',
    	            'BpBnsPtnr_ctryOfRsdnc_description.title',
    	            'BpIndivPtnrName_firstName_description.title',
    	            'BpIndivPtnrName_lastName_description.title',
    	            'BpIndivPtnrName_gender_description.title',
    	            'BpIndivPtnrName_brthDt_description.title',
    	            'BpLegalPtnrId_cpnyName_description.title',
    	            'BpLegalPtnrId_legalForm_description.title',
    	            'BpLegalPtnrId_equity_description.title',
    	            'BpLegalPtnrId_cmrcRgstrNbr_description.title',
    	            'BpLegalPtnrId_taxPayerIdNbr_description.title',
    	            'BpBnsPtnr_ctryOfRsdnc_required.title',
    	            'BpIndivPtnrName_lastName_required.title',
    	            'BpIndivPtnrName_gender_required.title',
    	            'BpIndivPtnrName_cpnyName_required.title',
    	            'BpBnsPtnr_NoCountryFound_description.title',
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
    	            'BpPtnrContact_description.title',
    	            'BpPtnrIdDtls_description.title'
    	            
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.loadCountryNames = function(val){
        return loadCountryNamesPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };

    function loadCountryNamesPromise(countryName){
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        if(countryName){
            searchInput.entity.name = countryName+'%';
            searchInput.fieldNames.push('name');
        }
        var deferred = $q.defer();
        genericResource.findByLike(service.countryNamesUrlBase, searchInput)
		.success(function(entitySearchResult) {
        	deferred.resolve(entitySearchResult);
		})
        .error(function(){
            deferred.reject(service.translations['BpBnsPtnr_NoCountryFound_description.title']);
        });
        return deferred.promise;
    }    

    service.translate();
    
    return service;
}])
.factory('bpBnsPtnrState',['$rootScope',function($rootScope){

    var service = {
    };
    var activeTabVar="bpPtnrContact";

    var bpPtnrContactActiveVar=true;
    service.bpPtnrContactActive=function(bpPtnrContactActiveIn){
        if(bpPtnrContactActiveIn)bpPtnrContactActiveVar=bpPtnrContactActiveIn;
        return bpPtnrContactActiveVar;
    };

    var bpPtnrIdDtlsActiveVar=false;
    service.bpPtnrIdDtlsActive=function(bpPtnrIdDtlsActiveIn){
        if(bpPtnrIdDtlsActiveIn)bpPtnrIdDtlsActiveVar=bpPtnrIdDtlsActiveIn;
        return bpPtnrIdDtlsActiveVar;
    };

    // A cache of dependents by ptnrNbr
    var depCacheVar = {};

    // The search state.
    // The current list of business partners.
    var bpBnsPtnrsVar=[];
    service.hasBnsPtnrs = function(){
        return bpBnsPtnrsVar && bpBnsPtnrsVar.length>0;
    };
    service.bpBnsPtnrs = function(bpBnsPtnrsIn){
        if(bpBnsPtnrsIn){
            var length = bpBnsPtnrsVar.length;
            for	(var index = 0; index < length; index++) {
                bpBnsPtnrsVar.pop();
            }
            length = bpBnsPtnrsIn.length;
            for	(var index1 = 0; index1 < length; index1++) {
                bpBnsPtnrsVar.push(bpBnsPtnrsIn[index1]);
            }
        }
        return bpBnsPtnrsVar;
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
        service.bpBnsPtnrs(entitySearchResult.resultList);
        service.totalItems(entitySearchResult.count);
        service.selectedIndex(-1);
        depCacheVar={};
    };

    service.paginate = function(){
        searchInputVar.start = ((currentPageVar - 1)  * itemPerPageVar);
        searchInputVar.max = itemPerPageVar;
        return service.searchInput();
    };


    // returns sets and returns the business partner at the passed index or
    // if not set the business partner at the currently selected index.
    service.bpBnsPtnr = function(index){
        if(index && index>=0 && index<bpBnsPtnrsVar.length)selectedIndexVar=index;
        if(selectedIndexVar<0 || selectedIndexVar>=bpBnsPtnrsVar.length) return;
        return bpBnsPtnrsVar[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(bpBnsPtnr){
        if(!bpBnsPtnrsVar || !bpBnsPtnr) return;
        var currentBp = service.bpBnsPtnr();
        if(currentBp && currentBp.ptnrNbr==bpBnsPtnr.ptnrNbr){
            bpBnsPtnrsVar[selectedIndexVar]=bpBnsPtnr;
        } else {
            for (var index in bpBnsPtnrsVar) {
                if(index && bpBnsPtnrsVar[index].ptnrNbr==bpBnsPtnr.ptnrNbr){
                    bpBnsPtnrsVar[index]=bpBnsPtnr;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };

    // CHeck if the business partner to be displayed is at the correct index.
    service.peek = function(bpBnsPtnr, index){
        if(!bpBnsPtnrsVar || !bpBnsPtnr) return false;
        if(index && index>=0 && index<bpBnsPtnrsVar.length){
            selectedIndexVar=index;
            return true;
        }
        return false;
    };

    service.push = function(bpBnsPtnr){
        if(!bpBnsPtnrsVar || !bpBnsPtnr) return false;
        var length = bpBnsPtnrsVar.push(bpBnsPtnr);
        selectedIndexVar=length-1;
        return true;
    };

    service.tabSelected=function(tabName){
        if(tabName){
            activeTabVar=tabName;
            bpPtnrContactActiveVar= tabName=='bpPtnrContact';
            bpPtnrIdDtlsActiveVar= tabName=='bpPtnrIdDtls';
        }
        $rootScope.$broadcast('BpBnsPtnrsSelected', {tabName:activeTabVar,bpBnsPtnr:service.bpBnsPtnr()});
    };

    service.previous = function (){
        if(bpBnsPtnrsVar.length<=0 || selectedIndexVar<0 || selectedIndexVar>=bpBnsPtnrsVar.length) return;
        if(selectedIndexVar==0){
            selectedIndexVar=bpBnsPtnrsVar.length-1;
        } else {
            selectedIndexVar-=1;
        }
        return service.bpBnsPtnr();
    };

    service.next = function(){
        if(bpBnsPtnrsVar.length<=0 || selectedIndexVar<0 || selectedIndexVar>=bpBnsPtnrsVar.length) return;
        if(selectedIndexVar>=bpBnsPtnrsVar.length){
            selectedIndexVar=0;
        } else {
            selectedIndexVar+=1;
        }
        return service.bpBnsPtnr();
    };


    return service;

}])
.controller('bpBnsPtnrsCtlr',['$scope','genericResource','bpBnsPtnrUtils','bpBnsPtnrState','$location','$rootScope',
function($scope,genericResource,bpBnsPtnrUtils,bpBnsPtnrState,$location,$rootScope){

    $scope.searchInput = bpBnsPtnrState.searchInput();
    $scope.itemPerPage=bpBnsPtnrState.itemPerPage;
    $scope.totalItems=bpBnsPtnrState.totalItems;
    $scope.currentPage=bpBnsPtnrState.currentPage();
    $scope.maxSize =bpBnsPtnrState.maxSize;
    $scope.bpBnsPtnrs =bpBnsPtnrState.bpBnsPtnrs;
    $scope.selectedIndex=bpBnsPtnrState.selectedIndex;
    $scope.handleSearchRequestEvent = handleSearchRequestEvent;
    $scope.handlePrintRequestEvent = handlePrintRequestEvent;
    $scope.paginate = paginate;
    $scope.error = "";
    $scope.bpBnsPtnrUtils=bpBnsPtnrUtils;
    $scope.show=show;
    $scope.edit=edit;

	var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
		bpBnsPtnrUtils.translate();
	});

    $scope.$on('$destroy', function () {
        translateChangeSuccessHdl();
    });

    init();

    function init(){
        if(bpBnsPtnrState.hasBnsPtnrs())return;
        findByLike($scope.searchInput);
    }

    function findByLike(searchInput){
		genericResource.findByLike(bpBnsPtnrUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			bpBnsPtnrState.consumeSearchResult(searchInput,entitySearchResult);
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
        $scope.searchInput = bpBnsPtnrState.paginate();
    	findByLike($scope.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
	
	function show(bpBnsPtnr, index){
		if(bpBnsPtnrState.peek(bpBnsPtnr, index)){
			$location.path('/BpBnsPtnrs/show/');
		}
	}

	function edit(bpBnsPtnr, index){
		if(bpBnsPtnrState.peek(bpBnsPtnr, index)){
			$location.path('/BpBnsPtnrs/edit/');
		}
	}
}])
.controller('bpBnsPtnrCreateCtlr',['$scope','bpBnsPtnrUtils','$translate','genericResource','$location','bpBnsPtnrState',
        function($scope,bpBnsPtnrUtils,$translate,genericResource,$location,bpBnsPtnrState){
    $scope.bpBnsPtnr = bpBnsPtnrState.bpBnsPtnr();
    $scope.create = create;
    $scope.error = "";
    $scope.bpBnsPtnrUtils=bpBnsPtnrUtils;
    $scope.loadCountryNames=bpBnsPtnrUtils.loadCountryNames;

    function create(){
    	genericResource.create(bpBnsPtnrUtils.urlBase, $scope.bpBnsPtnr)
    	.success(function(bpBnsPtnr){
    		if(bpBnsPtnrState.push(bpBnsPtnr)){
    			$location.path('/BpBnsPtnrs/show/');
    		}
    	})
    	.error(function(error){
    		$scope.error = error;
    	});
    };
}])
.controller('bpBnsPtnrEditCtlr',['$scope','genericResource','$location','bpBnsPtnrUtils','bpBnsPtnrState',
                                 function($scope,genericResource,$location,bpBnsPtnrUtils,bpBnsPtnrState){
    $scope.bpBnsPtnr = bpBnsPtnrState.bpBnsPtnr();
    $scope.error = "";
    $scope.bpBnsPtnrUtils=bpBnsPtnrUtils;
    $scope.loadCountryNames=bpBnsPtnrUtils.loadCountryNames;
    $scope.update = function (){
    	genericResource.update(bpBnsPtnrUtils.urlBase, $scope.bpBnsPtnr)
    	.success(function(bpBnsPtnr){
    		if(bpBnsPtnrState.replace(bpBnsPtnr)){
    			$location.path('/BpBnsPtnrs/show/');
    		}
        })
    	.error(function(error){
            $scope.error = error;
        });
    };
}])
.controller('bpBnsPtnrShowCtlr',['$scope','genericResource','$location','bpBnsPtnrUtils','bpBnsPtnrState','$rootScope',
                                 function($scope,genericResource,$location,bpBnsPtnrUtils,bpBnsPtnrState,$rootScope){
    $scope.bpBnsPtnr = bpBnsPtnrState.bpBnsPtnr();
    $scope.error = "";
    $scope.bpBnsPtnrUtils=bpBnsPtnrUtils;
    $scope.bpPtnrContactActive=bpBnsPtnrState.bpPtnrContactActive();
    $scope.bpPtnrIdDtlsActive=bpBnsPtnrState.bpPtnrIdDtlsActive();
    
    $scope.previous = function (){
        var bp = bpBnsPtnrState.previous();
        if(bp){
            $scope.bpBnsPtnr=bp;
            bpBnsPtnrState.tabSelected();
        }
//		$location.path('/BpBnsPtnrs/show/');
    }

    $scope.next =  function (){
        var bp = bpBnsPtnrState.next();
        if(bp){
            $scope.bpBnsPtnr=bp;
            bpBnsPtnrState.tabSelected();
        }
//        $location.path('/BpBnsPtnrs/show/');
    };
    $scope.tabSelected = function(tabName){
    	bpBnsPtnrState.tabSelected(tabName);
    };
    $scope.edit =function(){
        $location.path('/BpBnsPtnrs/edit/');
    };

}]);
