'use strict';
    
angular.module('AdBnsptnr')

.factory('bpBnsPtnrUtils',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adbnsptnr.server/rest/bpbnsptnrs';
    service.countryNamesUrlBase='/adbnsptnr.server/rest/basecountrynames';

    service.isIndividual = function(bpBnsPtnr){
    	return bpBnsPtnr.ptnrType=='INDIVIDUAL';
    }
    service.isInstitution = function(bpBnsPtnr){
    	return bpBnsPtnr.ptnrType=='LEGAL';
    }
    
    service.ptnrTypeI18nMsgTitleKey = function(enumKey){
    	return "BpPtnrType_"+enumKey+"_description.title";
    }
    service.ptnrTypeI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.ptnrTypeI18nMsgTitleKey(enumKey)];
    }
    
    service.bpBnsPtnrTypes = [
      {enumKey:'INDIVIDUAL', translKey:'BpPtnrType_INDIVIDUAL_description.title'},
      {enumKey:'LEGAL', translKey:'BpPtnrType_LEGAL_description.title'}
    ];
    
    service.genderI18nMsgTitleKey = function(enumKey){
    	return "BaseGender_"+enumKey+"_description.title";
    }
    service.genderI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.genderI18nMsgTitleKey(enumKey)];
    }

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
    }
    
    service.loadCountryNames = function(val){
        return loadCountryNamesPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        })
    }

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
	
	var serv = {
	};
	var activeTab="bpPtnrContact";
	
	serv.bpBnsPtnrs=[];
	serv.bpBnsPtnr;
	serv.bpBnsPtnrIndex=-1;
	serv.replace = function(bpBnsPtnr){
		if(!serv.bpBnsPtnrs || !bpBnsPtnr) return;
		if(serv.bpBnsPtnrIndex>=0 && serv.bpBnsPtnrIndex<serv.bpBnsPtnrs.length && serv.bpBnsPtnrs[serv.bpBnsPtnrIndex].ptnrNbr==bpBnsPtnr.ptnrNbr){
			serv.bpBnsPtnrs[index]=bpBnsPtnr;
		} else {
			for (var index in serv.bpBnsPtnrs) {
				if(serv.bpBnsPtnrs[index].ptnrNbr==bpBnsPtnr.ptnrNbr){
					serv.bpBnsPtnrs[index]=bpPtnrContact;
					serv.bpBnsPtnrIndex=index;
					break;
				}
			}
		}
	};
	
	serv.peek = function(bpBnsPtnr, index){
		if(!serv.bpBnsPtnrs || !bpBnsPtnr) return false;
		serv.bpBnsPtnr=bpBnsPtnr;
		serv.bpBnsPtnrIndex=index;
		return true;
	};

	serv.push = function(bpBnsPtnr){
		if(!serv.bpBnsPtnrs || !bpBnsPtnr) return false;
		var length = serv.bpBnsPtnrs.push(bpBnsPtnr);
		serv.bpBnsPtnr=bpBnsPtnr;
		serv.bpBnsPtnrIndex=length-1;
		return true;
	};
	serv.bpPtnrContactActive=true;
	serv.bpPtnrIdDtlsActive=false;
	serv.tabSelected=function(tabName){
		activeTab=tabName;
		serv.bpPtnrContactActive= tabName=='bpPtnrContact';
		serv.bpPtnrIdDtlsActive= tabName=='bpPtnrIdDtls';
		$rootScope.$broadcast('BpBnsPtnrsSelected', {tabName:tabName,bpBnsPtnr:serv.bpBnsPtnr});
	}

	return serv;

}])
.controller('bpBnsPtnrsCtlr',['$scope','genericResource','bpBnsPtnrUtils','bpBnsPtnrState','$location','$rootScope',
                              function($scope,genericResource,bpBnsPtnrUtils,bpBnsPtnrState,$location,$rootScope){
    var self = this ;
    $scope.bpBnsPtnrsCtlr = self;

    self.searchInput = {
	        entity:{},
	        fieldNames:[],
	        start:0,
	        max:self.itemPerPage
	    };
    self.itemPerPage=25;
    self.totalItems ;
    self.currentPage = 1;
    self.maxSize = 5 ;
    self.bpBnsPtnrs = [];
    self.searchEntity = {};
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.handleSearchRequestEvent = handleSearchRequestEvent;
    self.handlePrintRequestEvent = handlePrintRequestEvent;
    self.paginate = paginate;
    self.error = "";
    self.bpBnsPtnrUtils=bpBnsPtnrUtils;
    self.show=show;
    self.edit=edit;
    
	$rootScope.$on('$translateChangeSuccess', function () {
		bpBnsPtnrUtils.translate();
	});
	
    init();

    function init(){
    	if(bpBnsPtnrState.searchInput){
    		self.searchInput=bpBnsPtnrState.searchInput;
        	self.bpBnsPtnrs=bpBnsPtnrState.bpBnsPtnrs;
        	self.totalItems=bpBnsPtnrState.totalItems;
    	} else {
			self.searchInput = {
					entity:{},
					fieldNames:[],
					start:0,
					max:self.itemPerPage
			};
			findByLike(self.searchInput);
    	}
    }

    function findByLike(searchInput){
		genericResource.findByLike(bpBnsPtnrUtils.urlBase, searchInput)
		.success(function(entitySearchResult) {
			// store search
			bpBnsPtnrState.searchInput=self.searchInput;

			// Store result
			bpBnsPtnrState.bpBnsPtnrs = entitySearchResult.resultList;
			bpBnsPtnrState.totalItems = entitySearchResult.count ;
			bpBnsPtnrState.bpBnsPtnrIndex=-1
			
			// Display
			self.bpBnsPtnrs = bpBnsPtnrState.bpBnsPtnrs;
			self.totalItems = bpBnsPtnrState.totalItems;
		});
    }

    function processSearchInput(){
        var fieldNames = [];
        if(self.searchInput.entity.ptnrNbr){
        	fieldNames.push('ptnrNbr') ;
        }
        if(self.searchInput.entity.fullName){
        	fieldNames.push('fullName') ;
        }
        if(self.searchInput.entity.ptnrType && self.searchInput.entity.ptnrType=='')
        	self.searchInput.entity.ptnrType=undefined;
        
        if(self.searchInput.entity.ptnrType){
        	fieldNames.push('ptnrType') ;
        }
        self.searchInput.fieldNames = fieldNames ;
        return self.searchInput ;
    };

    function  handleSearchRequestEvent(){
    	processSearchInput();
    	findByLike(self.searchInput);
    };

    function paginate(){
    	self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
    	self.searchInput.max = self.itemPerPage ;
    	findByLike(self.searchInput);
    };

	function handlePrintRequestEvent(){		
	}
	
	function show(bpBnsPtnr, index){
		if(bpBnsPtnrState.peek(bpBnsPtnr, index)){
			$location.path('/BpBnsPtnrs/show/'+bpBnsPtnr.ptnrNbr);
		}
	}

	function edit(bpBnsPtnr, index){
		if(bpBnsPtnrState.peek(bpBnsPtnr, index)){
			$location.path('/BpBnsPtnrs/edit/'+bpBnsPtnr.ptnrNbr);
		}
	}
	
}])
.controller('bpBnsPtnrCreateCtlr',['$scope','bpBnsPtnrUtils','$translate','genericResource','$location','bpBnsPtnrState',
                                  function($scope,bpBnsPtnrUtils,$translate,genericResource,$location,bpBnsPtnrState){
	var self = this ;
    $scope.bpBnsPtnrCreateCtlr = self;
    self.bpBnsPtnr = {};
    self.create = create;
    self.error = "";
    self.bpBnsPtnrUtils=bpBnsPtnrUtils;
    self.loadCountryNames=loadCountryNames;

    function create(){
    	genericResource.create(bpBnsPtnrUtils.urlBase, self.bpBnsPtnr)
    	.success(function(bpBnsPtnr){
    		if(bpBnsPtnrState.push(bpBnsPtnr)){
    			$location.path('/BpBnsPtnrs/show/'+bpBnsPtnr.ptnrNbr);
    		}
    	})
    	.error(function(error){
    		self.error = error;
    	});
    };
}])
.controller('bpBnsPtnrEditCtlr',['$scope','genericResource','$routeParams','$location','bpBnsPtnrUtils','$modal','bpBnsPtnrState',
                                 function($scope,genericResource,$routeParams,$location,bpBnsPtnrUtils,$modal,bpBnsPtnrState){
    var self = this ;
    $scope.bpBnsPtnrEditCtlr = self;
    self.bpBnsPtnr = bpBnsPtnrState.bpBnsPtnr;
    self.update = update;
    self.error = "";
    self.bpBnsPtnrUtils=bpBnsPtnrUtils;
    self.selectCountry=selectCountry;

    function update(){
    	genericResource.update(bpBnsPtnrUtils.urlBase, self.bpBnsPtnr)
    	.success(function(bpBnsPtnr){
    		if(bpBnsPtnrState.replace(bpBnsPtnr)){
    			$location.path('/BpBnsPtnrs/show/'+bpBnsPtnr.ptnrNbr);
    		}
        })
    	.error(function(error){
            self.error = error;
        });
    };

    init();

    function init(){
        load();
    }

    function load(){
        var ptnrNbr = $routeParams.ptnrNbr;
        genericResource.findById(bpBnsPtnrUtils.urlBase, ptnrNbr)
        .success(function(bpBnsPtnr){
            if(bpBnsPtnrState.replace(bpBnsPtnr)){
            	self.bpBnsPtnr = bpBnsPtnrState.bpBnsPtnr;
        	}
        })
        .error(function(error){
            self.error = error;
        });
    }
    
    function selectCountry(size){
        var modalInstance = $modal.open({
            templateUrl: '/adres.client/views/CountryNames.html',
            controller: 'countryNamesCtlr',
            size: size,
            resolve : {
            	urlBase : function(){
            		return '/adbnsptnr.server/rest/';
            	},
            	countryNameHolder: function(){
            		return self.bpBnsPtnr;
            	}
            }
        
        });
    }

}])
.controller('bpBnsPtnrShowCtlr',['$scope','genericResource','$routeParams','$location','bpBnsPtnrUtils','bpBnsPtnrState',
                                 function($scope,genericResource,$routeParams,$location,bpBnsPtnrUtils,bpBnsPtnrState){
    var self = this ;
    $scope.bpBnsPtnrShowCtlr = self;
    self.bpBnsPtnr = bpBnsPtnrState.bpBnsPtnr;
    self.error = "";
    self.previousBP = previousBP;
    self.nextBP = nextBP;
    self.bpBnsPtnrUtils=bpBnsPtnrUtils;
    self.selectDependent=selectDependent;
    self.bpPtnrContactActive=bpBnsPtnrState.bpPtnrContactActive;
    self.bpPtnrIdDtlsActive=bpBnsPtnrState.bpPtnrIdDtlsActive;
    
    function previousBP(ptnrNbr){
    	if(!bpBnsPtnrState.bpBnsPtnrs || bpBnsPtnrState.bpBnsPtnrs.length<=0) return;

    	var previousBpBnsPtnr; 
    	for (var index in bpBnsPtnrState.bpBnsPtnrs) {
    	    if(bpBnsPtnrState.bpBnsPtnrs[index].ptnrNbr==ptnrNbr){
    	    	break;
    	    } else {
    	    	previousBpBnsPtnr=bpBnsPtnrState.bpBnsPtnrs[index];
    	    }
    	}
    	if(!previousBpBnsPtnr){
    		previousBpBnsPtnr = bpBnsPtnrState.bpBnsPtnrs[bpBnsPtnrState.bpBnsPtnrs.length-1];
    	}
    	bpBnsPtnrState.bpBnsPtnr = previousBpBnsPtnr;
		$location.path('/BpBnsPtnrs/show/'+previousBpBnsPtnr.ptnrNbr);
    }

    function nextBP(ptnrNbr){
    	if(!bpBnsPtnrState.bpBnsPtnrs || bpBnsPtnrState.bpBnsPtnrs.length<=0) return;

    	var nextBpBnsPtnr; 
    	var found = false;
    	for (var index in bpBnsPtnrState.bpBnsPtnrs) {
    		if(found) {
    			nextBpBnsPtnr = bpBnsPtnrState.bpBnsPtnrs[index];
    			break;
    		}
    	    if(bpBnsPtnrState.bpBnsPtnrs[index].ptnrNbr==ptnrNbr)found=true;
    	}
    	if(!nextBpBnsPtnr){
    		nextBpBnsPtnr = bpBnsPtnrState.bpBnsPtnrs[0];
    	}
    	bpBnsPtnrState.bpBnsPtnr = nextBpBnsPtnr;
		$location.path('/BpBnsPtnrs/show/'+nextBpBnsPtnr.ptnrNbr);
    }
    
    function selectDependent(tabName){
    	bpBnsPtnrState.tabSelected(tabName);
    }

}]);
