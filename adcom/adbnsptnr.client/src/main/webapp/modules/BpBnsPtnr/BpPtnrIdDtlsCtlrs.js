'use strict';
    
angular.module('AdBnsptnr')
.factory('bpPtnrIdDtlsUtils',['$translate','$rootScope',
                function($translate,$rootScope){
    var service = {};
    
    service.urlBase='/adbnsptnr.server/rest/bpptnriddtlss';
    
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
    	            'BpPtnrIdDtls_issuingCtry_description.title',
    	            
    	            'Entity_create.title',
    	            'Entity_required.title',
    	            'Entity_save.title',
    	            'Entity_cancel.title',
    	            'Entity_edit.title',
    	            'BpBnsPtnr_findACountry_description.title'
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.translate();
    
    return service;
}])
.factory('bpPtnrIdDtlssState',['bpBnsPtnrState',function(bpBnsPtnrState){
	
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
	serv.bpPtnrIdDtlsActive= function(){ 
		return bpBnsPtnrState.bpPtnrIdDtlsActive;
	}

	return serv;

}])
.controller('bpPtnrIdDtlssCtlr',['$scope','genericResource','$modal','$routeParams',
                                 'bpPtnrIdDtlsUtils','bpPtnrIdDtlssState','$rootScope','bpBnsPtnrUtils',
                     function($scope,genericResource,$modal,$routeParams,
                    		 bpPtnrIdDtlsUtils,bpPtnrIdDtlssState,$rootScope,bpBnsPtnrUtils){
	
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
    self.searchPerformed=false;

    var unregisterHandle = $rootScope.$on('BpBnsPtnrsSelected', function(event, data){
    	if(!data || data.tabName!='bpPtnrIdDtls') return; // wrong tab
    	if(self.ptnrNbr && self.ptnrNbr!=data.bpBnsPtnr.ptnrNbr) return; //event didn't come form this instance.
    	if(self.searchPerformed) return;
    	self.ptnrNbr=data.bpBnsPtnr.ptnrNbr;
        findByLike(self.searchInput);
    });
    $scope.$on('$destroy', function () {
    	unregisterHandle();
    });
    
    init();
    function init(){
    	if(!bpPtnrIdDtlssState.bpPtnrIdDtlsActive()) return;// return if not the active tab.
        self.ptnrNbr = $routeParams.ptnrNbr;
        findByLike(self.searchInput);
    }
    
    function findByLike(searchInput){
    	searchInput.entity.ptnrNbr=self.ptnrNbr;
    	if(searchInput.fieldNames.indexOf('ptnrNbr')<0){
    		searchInput.fieldNames.push('ptnrNbr');
    	}
    	genericResource.findByLike(bpPtnrIdDtlsUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            bpPtnrIdDtlssState.bpPtnrIdDtlss=entitySearchResult.resultList;
            self.bpPtnrIdDtlss=bpPtnrIdDtlssState.bpPtnrIdDtlss;
            self.searchPerformed=true;
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
        $scope.currentAction=bpPtnrIdDtlsUtils.translations["Entity_create.title"];
        $scope.bpPtnrIdDtlsUtils=bpPtnrIdDtlsUtils;
        $scope.error="";
        $scope.loadCountryNames = bpBnsPtnrUtils.loadCountryNames;
        
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
        $scope.currentAction=bpPtnrIdDtlsUtils.translations["Entity_edit.title"];
        $scope.bpPtnrIdDtlsUtils=bpPtnrIdDtlsUtils;

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
            findByLike(self.searchInput);
        })
    }
}]);

