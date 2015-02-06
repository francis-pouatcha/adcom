'use strict';

angular.module('AdProcmt').factory('bplegalptnridsResource',['$http', function($http){
    var service = {};
    var urlBase = '/adbnsptnr.server/rest/bplegalptnrids'

    service.findCustom = function(entitySearchInput){
        return $http.post(urlBase+'/findCustom',entitySearchInput);
    };

    service.findByLike = function(entitySearchInput){
        return $http.post(urlBase+'/findByLike',entitySearchInput);
    };


    return service;
    
}]);