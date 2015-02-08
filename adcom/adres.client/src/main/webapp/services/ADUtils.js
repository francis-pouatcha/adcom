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
.factory('genericResource',['$http', function($http){
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

    service.findById = function(urlBase, entityId){
        return $http.get(urlBase+'/'+entityId);
    };

    service.findByIdentif = function(urlBase, identif){
        return $http.get(urlBase+'/findByIdentif/'+identif);
    };

    service.findCustom = function(urlBase, entitySearchInput){
        return $http.post(urlBase+'/findCustom',entitySearchInput);
    };
    service.deleteById = function(urlBase, entityId){
        return $http.delete(urlBase+'/'+entityId);
    };
    
    return service;
    
}]);

