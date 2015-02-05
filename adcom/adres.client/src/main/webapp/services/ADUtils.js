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
.factory('genericResource',['$http', function($http){
    var service = {};

    service.findBy = function(urlBase, entitySearchInput){
        return $http.post(urlBase+'/findBy',entitySearchInput);
    };

    service.findByLike = function(urlBase, entitySearchInput){
        return $http.post(urlBase+'/findByLike',entitySearchInput);
    };

    service.findById = function(urlBase, entityId){
        return $http.get(urlBase+'/'+entityId);
    };

    service.findByIdentif = function(identif){
        return $http.get(urlBase+'/findByIdentif/'+identif);
    }
    return service;
    
}]);

