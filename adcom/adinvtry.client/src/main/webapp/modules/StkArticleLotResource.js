'use strict';

angular.module('AdInvtry').factory('stkArtResource',['$http', function($http){
    var service = {};
    var urlBase = '/adstock.server/rest/stkarticlelots',
    searchInput = {
        entity:{},
        start:0,
        max:100,
        fieldNames:[]
    },
    entity = {};

    service.findBy = function(entitySearchInput){
        return $http.post(urlBase+'/findBy',entitySearchInput);
    };
    
    service.findStkByArtPic = function(searchInput) {
        return $http.post(urlBase+'/findStkByArtPic',searchInput);
    }
    service.getSearchInput = function(){
        return searchInput ;
    };

    service.getEntity = function(){
        return entity ;
    };
    function getSearchInput(){
        return searchInput ;
    };
    
    return service;
    
}]);