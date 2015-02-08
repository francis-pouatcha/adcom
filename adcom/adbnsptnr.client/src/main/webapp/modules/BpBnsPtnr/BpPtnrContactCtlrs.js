'use strict';
    
angular.module('AdBnsptnr')
.factory('bpPtnrContactUtils',['$translate','sessionManager',
                function($translate,sessionManager){
    var service = {};
    
    service.urlBase='/adbnsptnr.server/rest/bpptnrcontacts';
    
    service.contactRoleI18nMsgTitleKey = function(enumKey){
    	return "BpPtnrContactRole_"+enumKey+"_description.title";
    };
    service.contactRoleI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.contactRoleI18nMsgTitleKey(enumKey)];
    };
    
    service.bpPtnrContactRoles = [
      {enumKey:'MAIN_CONTACT', translKey:'BpPtnrContactRole_MAIN_CONTACT_description.title'},
      {enumKey:'ALT_CONTACT', translKey:'BpPtnrContactRole_ALT_CONTACT_description.title'},
      {enumKey:'OFFICE_ADDRESS', translKey:'BpPtnrContactRole_OFFICE_ADDRESS_description.title'},
      {enumKey:'HOME_ADDRESS', translKey:'BpPtnrContactRole_HOME_ADDRESS_description.title'},
      {enumKey:'PRIVATE_ADDRESS', translKey:'BpPtnrContactRole_PRIVATE_ADDRESS_description.title'},
      {enumKey:'DELIVERY_ADDRESS', translKey:'BpPtnrContactRole_DELIVERY_ADDRESS_description.title'},
      {enumKey:'INVOICE_ADDRESS', translKey:'BpPtnrContactRole_INVOICE_ADDRESS_description.title'},
      {enumKey:'SUPPORT_CONTACT', translKey:'BpPtnrContactRole_SUPPORT_CONTACT_description.title'},
      {enumKey:'EMERGENCY_CONTACT', translKey:'BpPtnrContactRole_EMERGENCY_CONTACT_description.title'}
    ];
    
    service.languageI18nMsgTitleKey = function(enumKey){
    	return "BaseLanguage_"+enumKey+"_description.title";
    };
    service.languageI18nMsgTitleValue = function(enumKey){
    	return service.translations[service.languageI18nMsgTitleKey(enumKey)];
    };

    service.baseLanguages = [
      {enumKey:'fr', translKey:'BaseLanguage_fr_description.title'},
      {enumKey:'en', translKey:'BaseLanguage_en_description.title'}
    ];
    
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
    	            
    	            'BpBnsPtnr_ptnrNbr_description.title',
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
    	            'Entity_cancel.title'
    	            ])
		 .then(function (translations) {
			 service.translations = translations;
	 	 });    	
    };
    
    service.translate();

    return service;
}])
.factory('bpPtnrContactsState',function(){
	
	var serv = {
	};
	
	serv.bpPtnrContacts=[];
	serv.replace = function(bpPtnrContact){
		if(!serv.bpPtnrContacts || !bpPtnrContact) return;
		for (var index in serv.bpPtnrContacts) {
			if(serv.bpPtnrContacts[index].ptnrNbr==bpPtnrContact.ptnrNbr){
				serv.bpPtnrContacts[index]=bpPtnrContact;
				break;
			}
		}
	};

	return serv;

})
.controller('bpPtnrContactsCtlr',['$scope','genericResource','$modal','$routeParams','bpPtnrContactUtils','bpPtnrContactsState',
                     function($scope,genericResource,$modal,$routeParams,bpPtnrContactUtils,bpPtnrContactsState){
	
    var self = this ;
    $scope.bpPtnrContactsCtlr = self;

    self.searchInput = {
        entity:{},
        fieldNames:[]
    };
    self.bpPtnrContacts=[];
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
    self.bpPtnrContactUtils=bpPtnrContactUtils;
    self.genericResource=genericResource;
    
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
    	genericResource.findByLike(bpPtnrContactUtils.urlBase, searchInput)
    	.success(function(entitySearchResult) {
            bpPtnrContactsState.bpPtnrContacts=entitySearchResult.resultList;
            self.bpPtnrContacts=bpPtnrContactsState.bpPtnrContacts;
        })
    	.error(function(error){
    		self.error = error;
    	});
    }

        function handleSelectedItem(index){
            index = index ? index : 0 ;
            self.selectedIndex = index ;
            angular.copy(self.bpPtnrContacts[self.selectedIndex],self.selectedItem ) ;
        };


        function openCreateForm(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/BpBnsPtnr/BpPtnrContactForm.html',
                controller: self.ModalInstanceCreateCtrl,
                size: size
            });
        };

        function ModalInstanceCreateCtrl($scope, $modalInstance) {
            $scope.formCreate = true;
            $scope.bpPtnrContact;
            $scope.currentAction=bpPtnrContactUtils.translations["Entity_create.title"];
            $scope.bpPtnrContactUtils=bpPtnrContactUtils;
            $scope.error="";
            
            $scope.save = function () {
                $scope.bpPtnrContact.ptnrNbr = self.ptnrNbr;
            	genericResource.create(bpPtnrContactUtils.urlBase, $scope.bpPtnrContact)
            	.success(function (data) {
            		bpPtnrContactsState.bpPtnrContacts.push(data);
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
                templateUrl: 'views/BpBnsPtnr/BpPtnrContactForm.html',
                controller: self.ModalInstanceEditCtrl,
                size: size,
                resolve:{
                	bpPtnrContact: function(){
                        return self.selectedItem;
                    }
                }
            });
        };

    function ModalInstanceEditCtrl($scope, $modalInstance,bpPtnrContact) {
    	$scope.formEdit = true;
        $scope.bpPtnrContact = bpPtnrContact;
        $scope.currentAction=bpPtnrContactUtils.translations["Entity_edit.title"];
        $scope.bpPtnrContactUtils=bpPtnrContactUtils;

        $scope.isClean = function() {
            return !angular.equals(bpPtnrContact, $scope.bpPtnrContact);
        };
        $scope.save = function () {
            $scope.bpPtnrContact.ptnrNbr = self.ptnrNbr;
            genericResource.update(bpPtnrContactUtils.urlBase, $scope.bpPtnrContact)
            .success(function(data){
        		bpPtnrContactsState.replace(data);
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
        genericResource.deleteById(bpPtnrContactUtils.urlBase, self.selectedItem.id).success(function(){
            init();
        })
    }

}]);

