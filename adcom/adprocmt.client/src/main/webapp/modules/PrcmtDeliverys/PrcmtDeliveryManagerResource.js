'use strict';

angular.module('AdProcmt').factory('prcmtDeliveryManagerResource',['$http', function($http){
    var service = {};
    var urlBase = '/adprocmt.server/rest/delivery';

    
    service.update = function(entity){
        return $http.post(urlBase,entity);
    };

    service.close = function(entity){
        return $http.post(urlBase,entity);
    };

    return service;
    
}]);