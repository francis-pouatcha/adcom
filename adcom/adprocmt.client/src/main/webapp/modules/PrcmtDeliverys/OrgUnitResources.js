'use strict';

angular.module('AdProcmt').factory('orgUnitsResource',['$http', function($http){
    var service = {};
    var urlBase = '/adbase.server/rest/orgunits'

    service.findActifsFromNow = function() {
        return $http.get(urlBase+'/findActifsFromNow');
    };
    service.findByLike = function(entitySearchInput){
        return $http.post(urlBase+'/findByLike',entitySearchInput);
    };
    return service;
    
}]);