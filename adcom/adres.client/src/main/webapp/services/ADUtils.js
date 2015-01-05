/**
 * @author Francis Pouatcha
 * @name ADUtils
 */

'use strict';

angular.module('ADUtils',[])

.factory('adUtils',['$http','$location',function($http,$location){
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
    service.logout = function(){
        $http.post('/adbase.server/logout');
        service.loadApp("/adlogin.client");
    };
    return service;
}]);
