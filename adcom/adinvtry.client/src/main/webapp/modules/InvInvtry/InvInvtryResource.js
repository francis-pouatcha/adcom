'use strict';

angular.module('AdInvtry').factory('invtryResource',['$http', function($http){
    var service = {};
    var urlBase = '/adinvtry.server/rest/invinvtrys',
    searchInput = {
        entity:{},
        start:0,
        max:100,
        fieldNames:[]
    },
    entity = {};
    
    service.create = function(entity){
        return $http.post(urlBase,entity);
    };

    service.update = function(entity){
        return $http.put(urlBase+'/'+entity.id,entity);
    };

    service.findBy = function(entitySearchInput){
        return $http.post(urlBase+'/findBy',entitySearchInput);
    };

    service.findCustom = function(entitySearchInput){
        return $http.post(urlBase+'/findCustom',entitySearchInput);
    };
    
    service.findByLike = function(entitySearchInput){
        return $http.post(urlBase+'/findByLike',entitySearchInput);
    };
    
    service.findByIdentif = function(identif){
        return $http.get(urlBase+'/findByIdentif/'+identif);
    };

    service.deleteById = function(entityId){
        return $http.delete(urlBase+'/'+entityId);
    };

    service.next = function(entityId){
        return $http.get(urlBase+'/next/'+entityId);
    };

    service.previous = function(entityId){
        return $http.get(urlBase+'/previous/'+entityId);
    };

    service.listAll = function(start,max){
        return $http.get(urlBase,{params: {start: start,max:max}});
    };

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