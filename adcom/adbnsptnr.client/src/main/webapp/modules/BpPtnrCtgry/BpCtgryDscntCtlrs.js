'use strict';
    
angular.module('AdBnsptnr')
.factory('bpCtgryDscntUtils',['$translate','sessionManager',
                function($translate,sessionManager){
    var service = {};
    
    service.urlBase='/adbnsptnr.server/rest/bpctgrydscnts';

    service.booleanI18nMsgTitleValue = function(val){
    	if(val){
    		return service.translations['Entity_yes.title'];
    	} else {
    		return service.translations['Entity_no.title'];
    	}
    };

    service.translate = function(){
    	$translate(['BpPtnrContactRole_MAIN_CONTACT_description.title',
    	            'BpPtnrContactRole_ALT_CONTACT_description.title',
    	            'BpPtnrContactRole_OFFICE_ADDRESS_description.title',
    	            'BpPtnrContactRole_HOME_ADDRESS_description.title',
    	            'BpPtnrContactRole_PRIVATE_ADDRESS_description.title',
    	            'BpPtnrContactRole_DELIVERY_ADDRESS_description.title',
    	            'BpPtnrContactRole_INVOICE_ADDRESS_description.title',
    	            'BpPtnrContactRole_SUPPORT_CONTACT_description.title',
    	            'BpPtnrContactRole_EMERGENCY_CONTACT_description.title',
    	            
    	            'BpBnsPtnr_ctgryCode_description.title',
    	            'BpPtnrContact_langIso2_description.title',
    	            'BpPtnrContact_cntctRole_description.title',
    	            'BpPtnrContact_cntctIndex_description.title',
    	            'BpPtnrContact_description_description.title',
    	            'BpPtnrContact_langIso2_description.text',
    	            'BpPtnrContact_cntctRole_description.text',
    	            'BpPtnrContact_cntctIndex_description.text',
    	            'BpPtnrContact_description_description.text',
    	            
    	            'BaseLanguage_fr_description.title',
    	            'BaseLanguage_en_description.title',
    	            
    	            'Entity_create.title',
    	            'Entity_edit.title',
    	            'Entity_required.title',
    	            'Entity_save.title',
    	            'Entity_cancel.title',
    	            'Entity_yes.title',
    	            'Entity_no.title'
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.translate();

    return service;
}])
.factory('bpCtgryDscntsState',['bpPtnrCtgryState',function(bpPtnrCtgryState){
	
	var service = {
	};
	
	service.bpPtnrCtgry = bpPtnrCtgryState.bpPtnrCtgry;
    var searchResultsVar = {};

    // The search state.
    // The current list of business partners.
    service.bpCtgryDscnts = function(ctgryCode){
        var nbr = ctgryCode;
        if(!ctgryCode) {
            var bpPtnrCtgry = bpPtnrCtgryState.bpPtnrCtgry();
            if(bpPtnrCtgry)
                nbr = bpPtnrCtgry.ctgryCode;
        }
        if(nbr && searchResultsVar[nbr]) return searchResultsVar[nbr];
        return [];
    };

    var selectedIndexVar=-1;
    service.selectedIndex= function(selectedIndexIn){
        if(selectedIndexIn)selectedIndexVar=selectedIndexIn;
        return selectedIndexVar;
    };

    service.consumeSearchResult = function(ctgryCode, entitySearchResult){
        if(entitySearchResult.resultList){
            searchResultsVar[ctgryCode] = entitySearchResult.resultList;
        } else {
            searchResultsVar[ctgryCode] = [];
        }
    };
    
    service.hasSearchResult = function(ctgryCode){
    	var res = searchResultsVar[ctgryCode];
    	if(res) return true;
    	return false;
    }

    service.bpCtgryDscnt = function(index,ctgryCode){
        var list = service.bpCtgryDscnts(ctgryCode);
        if(!index || index<0 || index>=list.length) return;
        selectedIndexVar=index;
        return list[selectedIndexVar];
    };

    // replace the current partner after a change.
    service.replace = function(bpCtgryDscnt){
        if(!bpCtgryDscnt) return;
        var list = service.bpCtgryDscnts(bpCtgryDscnt.ctgryCode);

        if(selectedIndexVar>=0 && selectedIndexVar<list.length && list[selectedIndexVar].id==bpCtgryDscnt.id){
            list[selectedIndexVar]=bpCtgryDscnt;
        }else {
            for (var index in list) {
                if(list[index].id==bpCtgryDscnt.id){
                    list[index]=bpCtgryDscnt;
                    selectedIndexVar=index;
                    break;
                }
            }
        }
    };

    service.push = function(bpCtgryDscnt){
        if(!bpCtgryDscnt) return false;
        var list = searchResultsVar[bpCtgryDscnt.ctgryCode];
        if(!list){
            list = [];
            searchResultsVar[bpCtgryDscnt.ctgryCode]=list;
        }
        var length = list.push(bpCtgryDscnt);
        selectedIndexVar = length-1;
    };

	service.bpCtgryDscntActive= bpPtnrCtgryState.bpCtgryDscntActive;

    service.searchInput = {
        entity:{},
        fieldNames:[]
    };

	return service;

}])
.controller('bpCtgryDscntsCtlr',['$scope','genericResource','$modal','$routeParams',
                                  'bpCtgryDscntUtils','bpCtgryDscntsState','$rootScope',
                     function($scope,genericResource,$modal,$routeParams,
                    		 bpCtgryDscntUtils,bpCtgryDscntsState,$rootScope){

    $scope.bpCtgryDscnts=bpCtgryDscntsState.bpCtgryDscnts;
    $scope.error = "";
    $scope.bpCtgryDscntUtils=bpCtgryDscntUtils;
    $scope.genericResource=genericResource;

    var ptnrSelectedUnregisterHdl = $rootScope.$on('BpPtnrCtgrysSelected', function(event, data){
        var bpPtnrCtgry = bpCtgryDscntsState.bpPtnrCtgry();
        if(!bpPtnrCtgry || !data || !data.bpPtnrCtgry || bpPtnrCtgry.ctgryCode!=data.bpPtnrCtgry.ctgryCode) return;
        loadBpCtgryDscnts(data.bpPtnrCtgry.ctgryCode);
    });
    $scope.$on('$destroy', function () {
        ptnrSelectedUnregisterHdl();
    });
    init();
    function init(){
        var bpPtnrCtgry = bpCtgryDscntsState.bpPtnrCtgry();
        if(bpPtnrCtgry && bpPtnrCtgry.ctgryCode)loadBpCtgryDscnts(bpPtnrCtgry.ctgryCode);
    }
    function loadBpCtgryDscnts (ctgryCode){
        if(!bpCtgryDscntsState.bpCtgryDscntActive()) return;
        if(!bpCtgryDscntsState.hasSearchResult(ctgryCode)) {
            findByLike(bpCtgryDscntsState.searchInput, ctgryCode);
        }
    }

    function findByLike(searchInput,ctgryCode){
    	searchInput.entity.ctgryCode=ctgryCode;
    	if(searchInput.fieldNames.indexOf('ctgryCode')<0){
    		searchInput.fieldNames.push('ctgryCode');
    	}
    	genericResource.findByLike(bpCtgryDscntUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            bpCtgryDscntsState.consumeSearchResult(ctgryCode, entitySearchResult);
        })
    	.error(function(error){
    		$scope.error = error;
    	});
    }

    $scope.openCreateForm =function(size){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpPtnrCtgry/BpCtgryDscntForm.html',
            controller: ModalInstanceCreateCtrl,
            size: size,
            resolve: {
                bpPtnrCtgry: function(){
                    return bpCtgryDscntsState.bpPtnrCtgry();
                }
            }
        });
    };

    var ModalInstanceCreateCtrl = function($scope, $modalInstance,bpPtnrCtgry) {
        $scope.formCreate = true;
        $scope.bpCtgryDscnt;
        $scope.currentAction=bpCtgryDscntUtils.translations["Entity_create.title"];
        $scope.bpCtgryDscntUtils=bpCtgryDscntUtils;
        $scope.error="";
        $scope.bpPtnrCtgry=bpPtnrCtgry;

        $scope.save = function () {
            $scope.bpCtgryDscnt.ctgryCode = bpPtnrCtgry.ctgryCode;
        	genericResource.create(bpCtgryDscntUtils.urlBase, $scope.bpCtgryDscnt)
        	.success(function (bpCtgryDscnt) {
        		bpCtgryDscntsState.push(bpCtgryDscnt);
        		$modalInstance.dismiss('cancel');
            })
            .error(function(data, status){
            	$scope.error= status + " " + data;
            });

        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.isClean = function() {
            return false;
        };
    };

    $scope.openEditForm = function(size,bpCtgryDscnt){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpPtnrCtgry/BpCtgryDscntForm.html',
            controller: ModalInstanceEditCtrl,
            size: size,
            resolve:{
            	bpCtgryDscnt: function(){
                    return bpCtgryDscnt;
                },
                bpPtnrCtgry: function(){
                    return bpCtgryDscntsState.bpPtnrCtgry();
                }
            }
        });
    };

    var ModalInstanceEditCtrl = function($scope, $modalInstance,bpCtgryDscnt,bpPtnrCtgry) {
    	$scope.formEdit = true;
        $scope.bpCtgryDscnt = angular.copy(bpCtgryDscnt);
        $scope.currentAction=bpCtgryDscntUtils.translations["Entity_edit.title"];
        $scope.bpCtgryDscntUtils=bpCtgryDscntUtils;
        $scope.bpPtnrCtgry=bpPtnrCtgry;

        $scope.isClean = function() {
            return angular.equals(bpCtgryDscnt, $scope.bpCtgryDscnt);
        };
        $scope.save = function () {
            genericResource.update(bpCtgryDscntUtils.urlBase, $scope.bpCtgryDscnt)
            .success(function(data){
        		bpCtgryDscntsState.replace(data);
        		$modalInstance.dismiss('cancel');
            })
            .error(function(data, status){
            	$scope.error= status + " " + data;
            });
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };

    $scope.deleteItem = function(bpCtgryDscnt){
        genericResource.deleteById(bpCtgryDscntUtils.urlBase, bpCtgryDscnt.id).success(function(){
            findByLike(bpCtgryDscntsState.searchInput, bpCtgryDscnt.ctgryCode);
        })
    };

}]);

