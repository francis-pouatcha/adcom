/**
 * @author Francis Pouatcha
 * @name ADUtils
 */

'use strict';

angular.module('ADUtils',[])
.factory('adUtils',['$location',function($location){
    var service = {};
    service.loadApp = function(contextRoot){
		var absUrl = $location.protocol() + '://' + $location.host();
		var port = $location.port();
		if(port && port!=80 && port!=443){
			absUrl += ':'+port;
		}
		absUrl +=contextRoot;
		window.location.href=absUrl;
    };
    service.removeSearchOnUrl = function(){
    	$location.url('trm',null);
    	$location.url('usr',null);
    };
    service.greaterThan = function(a,b){
    	if(typeof a === 'undefined') return false;
    	if(typeof b === 'undefined') return a>-1;
    	return a>b;
    };
    return service;
}])
.factory('commonTranslations',['$translate',function($translate){
	var service={};
	service.translations=[];
    service.translate = function(){
    	$translate([
    	     'current_language',
            'Entity_leave.title',
            'Entity_change.title',
            'Entity_activate.title',
            'Entity_desactivate.title',
            'Entity_block.title',
            'Entity_unblock.title',
            'Entity_create.title',
            'Entity_delete.title',
            'Entity_save.title',
            'Entity_saveleave.title',
            'Entity_search.title',
            'Entity_update.title',
            'Entity_edit.title',
            'Entity_cancel.title',
            'Entity_Finish.title',
            'Entity_reset.title',
            'Entity_ok.title',
            'Entity_yes.title',
            'Entity_no.title',
            'Entity_select.title',
            'Entity_add.title',
            'Entity_show.title',
            'Entity_export.title',
            'Entity_confirm.title',
            'Window_close.title',
            'Entity_login.title',
            'Entity_userName.title',
            'Entity_password.title',
            'Entity_print.title',
            'Entity_next.title',
            'Entity_reprint.title',
            'Entity_previous.title',
            'Entity_back.title',
            'Entity_validFrom.title',
            'Entity_validTo.title',
            'Entity_Result.title',
            'Entity_By.title',
            'Entity_Of.title',
            'Entity_required.title',
            'BaseGender_FEMALE_description.title',
        	'BaseGender_MALE_description.title',
            'BaseLanguage_fr_description.title',
            'BaseLanguage_en_description.title'
            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };

    return service;
	
}])
.factory('genericResource',['$http','$q', function($http,$q){
    var service = {};

    service.create = function(urlBase, entity){
        return $http.post(urlBase,entity);
    };

    service.update = function(urlBase, entity){
        return $http.put(urlBase+'/'+entity.id,entity);
    };

    service.findBy = function(urlBase, entitySearchInput){
        return $http.post(urlBase+'/findBy',entitySearchInput);
    };
    
    service.findByLike = function(urlBase, entitySearchInput){
        return $http.post(urlBase+'/findByLike',entitySearchInput);
    };

    service.findByLikePromissed = function (urlBase, fieldName, fieldValue){
    	if(angular.isUndefined(urlBase) || !urlBase ||
    		angular.isUndefined(fieldName) || !fieldName || 
    		angular.isUndefined(fieldValue) || !fieldValue) return;
          
        var searchInput = {entity:{},fieldNames:[],start:0,max:10};
        searchInput.entity[fieldName]= fieldValue;
        if(searchInput.fieldNames.indexOf(fieldName)==-1)
        	searchInput.fieldNames.push(fieldName);
          var deferred = $q.defer();
          service.findByLike(urlBase, searchInput)
          .success(function(entitySearchResult) {
              deferred.resolve(entitySearchResult);
            })
          .error(function(error){
              deferred.reject('No entity found');
          });
      return deferred.promise;
    }
    service.findByLikeWithSearchInputPromissed = function (urlBase, searchInput){
    	if(angular.isUndefined(urlBase) || !urlBase ||
    		angular.isUndefined(searchInput) || !searchInput) return;          
          var deferred = $q.defer();
          service.findByLike(urlBase, searchInput)
          .success(function(entitySearchResult) {
              deferred.resolve(entitySearchResult);
            })
          .error(function(error){
              deferred.reject('No entity found');
          });
      return deferred.promise;
    }

    service.findById = function(urlBase, entityId){
        return $http.get(urlBase+'/'+entityId);
    };

    service.findByIdentif = function(urlBase, identif){
        return $http.get(urlBase+'/findByIdentif/'+identif);
    };

    service.findCustom = function(urlBase, entitySearchInput){
        return $http.post(urlBase+'/findCustom',entitySearchInput);
    };
    service.findCustomPromissed = function (urlBase, fieldName, fieldValue){
    	if(angular.isUndefined(urlBase) || !urlBase ||
    		angular.isUndefined(fieldName) || !fieldName || 
    		angular.isUndefined(fieldValue) || !fieldValue) return;
          
        var searchInput = {entity:{},fieldNames:[],start:0,max:10};
        searchInput.entity[fieldName]= fieldValue;
        if(searchInput.fieldNames.indexOf(fieldName)==-1)
        	searchInput.fieldNames.push(fieldName);
          var deferred = $q.defer();
          service.findCustom(urlBase, searchInput)
          .success(function(entitySearchResult) {
              deferred.resolve(entitySearchResult);
            })
          .error(function(error){
              deferred.reject('No entity found');
          });
      return deferred.promise;
    }
    service.findCustomWithSearchInputPromissed = function (urlBase, searchInput){
    	if(angular.isUndefined(urlBase) || !urlBase ||
    		angular.isUndefined(searchInput) || !searchInput) return;          
          var deferred = $q.defer();
          service.findCustom(urlBase, searchInput)
          .success(function(entitySearchResult) {
              deferred.resolve(entitySearchResult);
            })
          .error(function(error){
              deferred.reject('No entity found');
          });
      return deferred.promise;
    }

    service.find = function(urlBase, entitySearchInput){
        return $http.post(urlBase,entitySearchInput);
    };
    service.customMethod = function(urlBase, entitySearchInput){
        return $http.post(urlBase,entitySearchInput);
    };
    service.listAll = function(urlBase){
        return $http.get(urlBase);
    };
    service.deleteById = function(urlBase, entityId){
        return $http.delete(urlBase+'/'+entityId);
    };
    
    return service;
    
}])
.factory('searchResultHandler',['adUtils',function(adUtils){
    var service = {};
    service.newResultHandler = function(keyField){
    	return new ResultHandler(keyField); 
    };

    var ResultHandler = function(keyFieldIn){
        var handler = this;
        var itemPerPageVar = 10;
        var currentPageVar = 1;
        var nakedSearchResult = {
	    	count:0,resultList:[],
	    	searchInput:{
	    		entity:{},
	    		start:0, max:itemPerPageVar,
	    		fieldNames:[]
	    	},
	    	// not exposed to the server environment.
	    	currentPage:currentPageVar,itemPerPage:itemPerPageVar,selectedIndex:-1,
        };
        var searchResultVar = angular.copy(nakedSearchResult);
        var keyField = keyFieldIn;
        var displayInfoVar = {};
        var equalsFnct = function(entityA, entityB){
			if(!entityA && !entityB) return true;
			if(!entityB) return false;
			return entityA[keyField]==entityB[keyField];
        };
        var dependents = {};
        function setSelectedIndex(selectedIndexIn){
        	searchResultVar.selectedIndex=selectedIndexIn;
        	dependents = {};
        }
        this.searchResult = function(searchResultIn){
        	if(searchResultVar===searchResultIn) return;
        	if(angular.isUndefined(searchResultIn)) return;
        	
    		searchResultVar.count = searchResultIn.count;
    		
//    		displayInfoVar = {};
    		
    		angular.copy(searchResultIn.resultList, searchResultVar.resultList);
    		
    		angular.copy(searchResultIn.searchInput, searchResultVar.searchInput);

    		if(angular.isDefined(searchResultIn.currentPage)){
    			searchResultVar.currentPage=searchResultIn.currentPage;
    		} else {
    			searchResultVar.currentPage=currentPageVar;
    		}

    		if(angular.isDefined(searchResultIn.itemPerPage)){
    			searchResultVar.itemPerPage=searchResultIn.itemPerPage;
    		} else {
    			searchResultVar.itemPerPage=itemPerPageVar;
    		}
    		
    		if(angular.isDefined(searchResultIn.selectedIndex)){
    			setSelectedIndex(searchResultIn.selectedIndex);
    		} else {
    			setSelectedIndex(-1);
    		}
        };
        this.hasEntities = function(){
            return searchResultVar.resultList && searchResultVar.resultList.length>0;
        };
        this.entities = function(){
        	return searchResultVar.resultList;
        };
        this.selectedIndex= function(selectedIndexIn){
            if(adUtils.greaterThan(selectedIndexIn) && angular.isDefined(searchResultVar.resultList[selectedIndexIn])){
            	setSelectedIndex(selectedIndexIn);
            }
            return searchResultVar.selectedIndex;
        };
        this.selectedObject= function(selectedIn){
        	if(angular.isUndefined(selectedIn))return searchResultVar.selectedIndex; 
    		var length = searchResultVar.resultList.length;
    		for	(var index = 0; index < length; index++) {
    			if(!equalsFnct(selectedIn, searchResultVar.resultList[index])) continue;
    			searchResultVar.selectedIndex = index;
    			return searchResultVar.selectedIndex; 
    		}
    		length = searchResultVar.resultList.push(selectedIn);
    		searchResultVar.count +=1;
    		searchResultVar.selectedIndex = length -1;
            return searchResultVar.selectedIndex;
        };
        this.entity = function(){
        	if(!adUtils.greaterThan(searchResultVar.selectedIndex)) return;
        	if(angular.isUndefined(searchResultVar.resultList[searchResultVar.selectedIndex])) return;
        	return searchResultVar.resultList[searchResultVar.selectedIndex];
        };
        this.totalItems = function(){
            return searchResultVar.count;
        };
        this.currentPage = function(currentPageIn){
            if(adUtils.greaterThan(currentPageIn,-1)) searchResultVar.currentPage=currentPageIn;
            return searchResultVar.currentPage;
        };
        this.maxResult = function(maxResultIn) {
            if(adUtils.greaterThan(maxResultIn,-1)) searchResultVar.searchInput.max=maxResultIn;
            return searchResultVar.searchInput.max;
        };
        this.itemPerPage = function(itemPerPageIn){
            if(adUtils.greaterThan(itemPerPageIn,-1))searchResultVar.itemPerPage=itemPerPageIn;
            return searchResultVar.itemPerPage;
        };
        this.searchInput = function(searchInputIn){
            if(angular.isUndefined(searchInputIn))
                return angular.copy(searchResultVar.searchInput);

    		angular.copy(searchInputIn, searchResultVar.searchInput);
            return searchInputIn;
        };
        this.searchInputChanged = function(searchInputIn){
            return angular.equals(searchResultVar.searchInput, searchInputIn);
        };
        this.newSearchInput = function(){
            return angular.copy(nakedSearchResult.searchInput);
        };
        this.paginate = function(){
        	searchResultVar.searchInput.start = ((searchResultVar.currentPage - 1)  * searchResultVar.itemPerPage);
        	searchResultVar.searchInput.max = searchResultVar.itemPerPage;
            return handler.searchInput();
        };
        this.indexOf = function(entity){
        	if(angular.isUndefined(entity) || !entity) return -1;
    		var length = searchResultVar.resultList.length;
    		for	(var index = 0; index < length; index++) {
    			if(equalsFnct(entity, searchResultVar.resultList[index])) return index;
    		}
    		return -1;
        };
        // Replaces the object at the specified index.
        this.replace = function(entity){
        	if(angular.isUndefined(entity) || !entity) return;
        	var index = handler.indexOf(entity);
        	if(angular.isUndefined(index) || index<0) return;
        	searchResultVar.resultList[index]=entity;
        	return index;
        };
        // Add the specified entity to the end of the list. Remove the copy
        // from the array if existent.
        this.push = function(entity){
        	if(angular.isUndefined(entity) || !entity) return;
        	var index = handler.indexOf(entity);
        	if(angular.isDefined(index) && index>=0){ // slice
        		searchResultVar.resultList.splice(index, 1);
        		searchResultVar.count -=1;
        	}
            var length = searchResultVar.resultList.push(entity);
    		searchResultVar.count +=1;
    		return length -1;
        };
        // Add the specified entity to the start of the list. Remove the copy
        // from the array if existent.
        this.unshift = function(entity){
        	if(angular.isUndefined(entity) || !entity) return;
        	var index = handler.indexOf(entity);
        	if(angular.isDefined(index) && index>=0){ // slice
        		searchResultVar.resultList.splice(index, 1);
        		searchResultVar.count -=1;
        	}
            var length = searchResultVar.resultList.unshift(entity);
    		searchResultVar.count +=1;
    		return 0;
        }
        this.previous = function (){
        	if(searchResultVar.resultList.length<=0) return;

            if(searchResultVar.selectedIndex<=0){
            	setSelectedIndex(searchResultVar.resultList.length-1);
            } else {
            	setSelectedIndex(searchResultVar.selectedIndex-=1);
            }
            return handler.entity();
        };
        this.next = function(){
        	if(searchResultVar.resultList.length<=0) return;
        	
        	if(searchResultVar.selectedIndex>=searchResultVar.resultList.length-1 || searchResultVar.selectedIndex<0){
        		setSelectedIndex(0);
        	} else {
        		setSelectedIndex(searchResultVar.selectedIndex+=1);
        	}

            return handler.entity();
        };
        this.unsetDependent = function(fieldName){
        	if(angular.isUndefined(fieldName)) return;
        	
        	if(angular.isDefined(dependents[fieldName]))
        		delete dependents[fieldName];
        };
        this.dependent = function(fieldName, dependentIn){
        	if(angular.isUndefined(fieldName)) return;
        	
        	if(angular.isDefined(dependentIn) && dependentIn)
        		dependents[fieldName] = dependentIn;

        	return dependents[fieldName];
        };
        this.displayInfo = function(displayInfoIn){
        	if(angular.isDefined(displayInfoIn) && displayInfoIn)
        		displayInfoVar = displayInfoIn;
        	return displayInfoVar;
        };
    };
    
    return service;
	
}])
.factory('dependentTabManager',[function(){
    var service = {};
    
    // Instantiates a new TabManager
    service.newTabManager = function(tabNameList){
    	return new TabManager(tabNameList); 
    };

    // Create a new tab manager with a tab name list.
    var TabManager = function(tabNameListIn){
    	if(!angular.isArray(tabNameListIn) || tabNameListIn.length<1)
    		throw "Tab manager expecting an array of string, with tab name.";
    	var tabNameList = tabNameListIn;
        var activeTabNameVar= tabNameList[0];
        
        this.activeTab=function(activeTabNameIn){
        	if(angular.isDefined(activeTabNameIn) && tabNameList.indexOf(activeTabNameIn)>-1)
        		activeTabNameVar=activeTabNameIn;
        	return activeTabNameVar;
        };
        
        this.isActive = function(tabName){
        	if(angular.isDefined(tabName) && tabNameList.indexOf(tabName)>-1)
        		return angular.equals(activeTabNameVar,tabName);

        	return false;
        };
    };
    
    return service;
}]);

