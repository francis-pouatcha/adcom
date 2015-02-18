'use strict';

angular.module('AdProcmt').factory('prcmtOrderManagerResource',['$http', function($http){
    var service = {};
    var urlBase = '/adprocmt.server/rest/order';

    
    service.update = function(entity){
        return $http.post(urlBase+'/update',entity);
    };

    service.close = function(entity){
        return $http.post(urlBase+'/close',entity);
    };

    return service;
    
}]);