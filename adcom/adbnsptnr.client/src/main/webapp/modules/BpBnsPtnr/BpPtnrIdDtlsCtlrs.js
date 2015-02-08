﻿'use strict';
    
angular.module('AdBnsptnr')
.factory('bpPtnrIdDtlsUtils',['$translate','commonTranslations','$rootScope','sessionManager',
                function($translate,commonTranslations,$rootScope,sessionManager){
    var service = {};
    
    service.urlBase='/adbnsptnr.server/rest/bpptnriddtlss';
    service.commonTranslations=commonTranslations;
    
    service.ptnrIdTypeI18nMsgTitleKey = function(enumKey){
    	return "BpPtnrIdType_"+enumKey+"_description.title";
    };
    service.ptnrIdTypeI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.ptnrIdTypeI18nMsgTitleKey(enumKey)];
    };
    
    service.bpPtnrIdDtlsRoles = [
      {enumKey:'IDCARDNBR', translKey:'BpPtnrIdType_IDCARDNBR_description.title'},
      {enumKey:'RESDTCARDNBR', translKey:'BpPtnrIdType_RESDTCARDNBR_description.title'},
      {enumKey:'DRIVERLICNBR', translKey:'BpPtnrIdType_DRIVERLICNBR_description.title'},
      {enumKey:'PASSPORTNBR', translKey:'BpPtnrIdType_PASSPORTNBR_description.title'},
      {enumKey:'EMPLOYEENBR', translKey:'BpPtnrIdType_EMPLOYEENBR_description.title'},
      {enumKey:'MEMBERSHIP', translKey:'BpPtnrIdType_MEMBERSHIP_description.title'},
      {enumKey:'INSURER', translKey:'BpPtnrIdType_INSURER_description.title'},
      {enumKey:'INSURED', translKey:'BpPtnrIdType_INSURED_description.title'}
    ];

    service.translate = function(){
    	$translate(['BpPtnrIdType_IDCARDNBR_description.title',
    	            'BpPtnrIdType_RESDTCARDNBR_description.title',
    	            'BpPtnrIdType_DRIVERLICNBR_description.title',
    	            'BpPtnrIdType_PASSPORTNBR_description.title',
    	            'BpPtnrIdType_EMPLOYEENBR_description.title',
    	            'BpPtnrIdType_MEMBERSHIP_description.title',
    	            'BpPtnrIdType_INSURER_description.title',
    	            'BpPtnrIdType_INSURED_description.title',
    	            
    	            'BpPtnrIdDtls_ptnrIdType_description.title',
    	            'BpPtnrIdDtls_idNbr_description.title',
    	            'BpPtnrIdDtls_issuedDt_description.title',
    	            'BpPtnrIdDtls_expirdDt_description.title',
    	            'BpPtnrIdDtls_issuedBy_description.title',
    	            'BpPtnrIdDtls_issuedIn_description.title',
    	            'BpPtnrIdDtls_issuingCtry_description.title'
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.load = function(){
		service.translate();
		var current_language = sessionManager.language();
		if(service.commonTranslations.translations.length<=0 || current_language!=service.commonTranslations.translations['current_language']){
			service.commonTranslations.translate();
		}
	};
	
	service.load();
    
    return service;
}])
.factory('bpPtnrIdDtlssState',function(){
	
	var serv = {
	};
	
	serv.bpPtnrIdDtlss=[];
	serv.replace = function(bpPtnrIdDtls){
		if(!serv.bpPtnrIdDtlss || !bpPtnrIdDtls) return;
		for (var index in serv.bpPtnrIdDtlss) {
			if(serv.bpPtnrIdDtlss[index].ptnrNbr==bpPtnrIdDtls.ptnrNbr){
				serv.bpPtnrIdDtlss[index]=bpPtnrIdDtls;
				break;
			}
		}
	};

	return serv;

})
.controller('bpPtnrIdDtlssCtlr',['$scope','genericResource','$modal','$routeParams',
                                 'bpPtnrIdDtlsUtils','bpPtnrIdDtlssState',
                     function($scope,genericResource,$modal,$routeParams,
                    		 bpPtnrIdDtlsUtils,bpPtnrIdDtlssState){
	
    var self = this ;
    $scope.bpPtnrIdDtlssCtlr = self;

    self.searchInput = {
        entity:{},
        fieldNames:[]
    };
    self.bpPtnrIdDtlss=[];
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.ptnrNbr;
    self.openEditForm = openEditForm;
    self.openCreateForm = openCreateForm;
    self.ModalInstanceEditCtrl = ModalInstanceEditCtrl ;
    self.ModalInstanceCreateCtrl = ModalInstanceCreateCtrl ;
    self.handleSelectedItem = handleSelectedItem;
    self.error = "";
    self.deleteItem = deleteItem;
    self.bpPtnrIdDtlsUtils=bpPtnrIdDtlsUtils;
    self.genericResource=genericResource;
    self.commonTranslations=bpPtnrIdDtlsUtils.commonTranslations;
    
    init();
    function init(){
    	self.ptnrNbr = $routeParams.ptnrNbr;
        self.searchInput = {
            entity:{},
            fieldNames:[]
        }
        findByLike(self.searchInput);
    }
    function findByLike(searchInput){
    	searchInput.entity.ptnrNbr=self.ptnrNbr;
    	searchInput.fieldNames.push('ptnrNbr');
    	genericResource.findByLike(bpPtnrIdDtlsUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            bpPtnrIdDtlssState.bpPtnrIdDtlss=entitySearchResult.resultList;
            self.bpPtnrIdDtlss=bpPtnrIdDtlssState.bpPtnrIdDtlss;
        })
    	.error(function(error){
    		self.error = error;
    	});
    }

    function handleSelectedItem(index){
        index = index ? index : 0 ;
        self.selectedIndex = index ;
        angular.copy(self.bpPtnrIdDtlss[self.selectedIndex],self.selectedItem ) ;
    };

    function openCreateForm(size){
        var modalInstance = $modal.open({
            templateUrl: 'views/BpBnsPtnr/BpPtnrIdDtlsForm.html',
            controller: self.ModalInstanceCreateCtrl,
            size: size
        });
    };

    function ModalInstanceCreateCtrl($scope, $modalInstance) {
        $scope.formCreate = true;
        $scope.bpPtnrIdDtls;
        $scope.currentAction=bpPtnrIdDtlsUtils.commonTranslations.translations["Entity_create.title"];
        $scope.bpPtnrIdDtlsUtils=bpPtnrIdDtlsUtils;
        $scope.error="";
        $scope.commonTranslations=bpPtnrIdDtlsUtils.commonTranslations;
        
        $scope.save = function () {
            $scope.bpPtnrIdDtls.ptnrNbr = self.ptnrNbr;
        	genericResource.create(bpPtnrIdDtlsUtils.urlBase, $scope.bpPtnrIdDtls)
        	.success(function (data) {
        		bpPtnrIdDtlssState.bpPtnrIdDtlss.push(data);
        		$modalInstance.dismiss('cancel');
            })
            .error(function(data, status){
            	$scope.error= status + " " + data;
            });
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }

    function openEditForm(size,index){
        handleSelectedItem(index);
        var modalInstance = $modal.open({
            templateUrl: 'views/BpBnsPtnr/BpPtnrIdDtlsForm.html',
            controller: self.ModalInstanceEditCtrl,
            size: size,
            resolve:{
            	bpPtnrIdDtls: function(){
                    return self.selectedItem;
                }
            }
        });
    };

    function ModalInstanceEditCtrl($scope, $modalInstance,bpPtnrIdDtls) {
    	$scope.formEdit = true;
        $scope.bpPtnrIdDtls = bpPtnrIdDtls;
        $scope.currentAction=bpPtnrIdDtlsUtils.commonTranslations.translations["Entity_edit.title"];
        $scope.bpPtnrIdDtlsUtils=bpPtnrIdDtlsUtils;
        $scope.commonTranslations=bpPtnrIdDtlsUtils.commonTranslations;

        $scope.isClean = function() {
            return !angular.equals(bpPtnrIdDtls, $scope.bpPtnrIdDtls);
        };
        $scope.save = function () {
            $scope.bpPtnrIdDtls.ptnrNbr = self.ptnrNbr;
            genericResource.update(bpPtnrIdDtlsUtils.urlBase, $scope.bpPtnrIdDtls)
            .success(function(data){
        		bpPtnrIdDtlssState.replace(data);
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

    function deleteItem(index){
        handleSelectedItem();
        genericResource.deleteById(bpPtnrIdDtlsUtils.urlBase, self.selectedItem.id).success(function(){
            init();
        })
    }
}]);

