'use strict';
    
angular.module('AdStock')

.factory('stkSectionAticleLotsUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adstock.server/rest/stkarticlelot2strgsctns';

    service.language=sessionManager.language;
    
    service.translate = function(){
    	$translate(['StkSectionArticleLot_lotPic_description.title',
    	            'StkSectionArticleLot_artPic_description.title',
    	            'StkSectionArticleLot_artName_description.title',
    	            'StkSectionArticleLot_stockQty_description.title',
    	            'StkSectionArticleLot_stockQtyDt_description.title',
    	            'StkSectionArticleLot_lotQty_description.title',
    	            'StkSectionArticleLot_lotQtyDt_description.title',
    	            'StkSectionArticleLot_salesPrice_description.title',

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
    	            'Entity_notfound.title'
    	            
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };

    service.translate();
    
    return service;
}])
.factory('stkSectionAticleLotsState',['stkSectionState',function(stkSectionState){
	
	var service = {
	};
	
	service.stkSection = stkSectionState.stkSection;
    var searchResultsVar = {};
    service.searchResult = function(){
    	var stkSection = stkSectionState.stkSection();
    	if(!stkSection) return;
    	var strgSection = stkSection.strgSection;
        if(strgSection && searchResultsVar[strgSection]) 
        	return searchResultsVar[strgSection];
    };
    var nakedSearchInputVar = {
        entity:{},
        fieldNames:[],
        start: 0,
        max: 5        
    };
    service.nakedSearchInput = function(){
    	return angular.copy(nakedSearchInputVar);
    };
    service.stkSectionArticleLot = function(){
    	var sr = service.searchResult();
    	if(sr) return sr.resultList;
    };
    service.searchInput = function(){
    	var sr = service.searchResult();
    	if(sr) return sr.searchInput;
    };
    service.itemPerPage = function(){
    	var sr = service.searchResult();
    	if(sr) return sr.searchInput.max;
    };
    service.totalItems = function(){
    	var sr = service.searchResult();
    	if(sr) return sr.count;
    };
    service.currentPage = function(){
    	var sr = service.searchResult();
    	if(sr && sr.searchInput && searchInput.start && searchInput.max){
    		if(searchInput.start<0)searchInput.start=0;
    		return 1 + (searchInput.start/searchInput.max)
    	}
    };

    service.consumeSearchResult = function(entitySearchResult){
    	if(entitySearchResult && 
    			entitySearchResult.searchInput && 
    			entitySearchResult.searchInput.entity &&
    			entitySearchResult.searchInput.entity.strgSection)
    		searchResultsVar[entitySearchResult.searchInput.entity.strgSection] = entitySearchResult;
    };
    
    service.hasSearchResult = function(strgSection){
    	return (searchResultsVar[strgSection] && searchResultsVar[strgSection].resultList);
    };

	service.stkSectionAticleLotActive= stkSectionState.stkSectionAticleLotActive;

    service.searchInputChanged = function(searchInputIn){
        return angular.equals(service.searchInput(), searchInputIn);
    };

    // start = currentPage-1 * itemPerPage
    // currentPage = (start/itemPerPage)+1
    service.paginate = function(currentPage){
    	var searchInputVar = service.searchInput();
    	if(currentPage){
            searchInputVar.start = ((currentPage - 1)  * searchInputVar.max);
    	}
        return searchInputVar;
    };
    
	return service;

}])
.controller('stkSectionAticleLotsCtlr',['$scope','genericResource','$modal','$routeParams',
                                  'stkSectionAticleLotsUtils','stkSectionAticleLotsState','$rootScope',
                     function($scope,genericResource,$modal,$routeParams,
                    		 stkSectionAticleLotsUtils,stkSectionAticleLotsState,$rootScope){

    $scope.stkSectionAticleLots=stkSectionAticleLotsState.stkSectionAticleLots;
    $scope.error = "";
    $scope.stkSectionAticleLotsUtils=stkSectionAticleLotsUtils;
    $scope.itemPerPage=stkSectionAticleLotsState.itemPerPage;
    $scope.totalItems=stkSectionAticleLotsState.totalItems;
    $scope.currentPage=stkSectionAticleLotsState.currentPage();

    var sctnSelectedUnregisterHdl = $rootScope.$on('StkSectionAticleLotSelected', function(event, data){
        var stkSection = stkSectionAticleLotsState.stkSection();
        if(!stkSection || !data || !data.stkSection || stkSection.sectionCode!=data.stkSection.sectionCode) return;
        loadStkSectionAticleLots();
    });
    $scope.$on('$destroy', function () {
    	sctnSelectedUnregisterHdl();
    });
    $scope.paginate = function(){
        $scope.searchInput = stkSectionAticleLotsState.paginate($scope.currentPage);
    	findBy($scope.searchInput);
    };
    loadStkSectionAticleLots();
    function loadStkSectionAticleLots (){
    	if(!stkSectionAticleLotsState.stkSectionAticleLotActive()) return;
        var stkSection = stkSectionAticleLotsState.stkSection();
        if(!stkSection) return;
        if(!stkSectionAticleLotsState.hasSearchResult(stkSection.sectionCode)) {
        	var searchInput = stkSectionAticleLotsState.searchInput();
        	if(!searchInput){
        		searchInput = stkSectionAticleLotsState.nakedSearchInput();
        		searchInput.entity.strgSection=stkSection.sectionCode;
        		searchInput.fieldNames.push('strgSection');
        	}
        	findBy(searchInput);
        }
    }

    function findBy(searchInput){
    	genericResource.findBy(stkSectionAticleLotsUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            stkSectionAticleLotsState.consumeSearchResult(entitySearchResult);
        })
    	.error(function(error){
    		$scope.error = error;
    	});
    }

}]);

