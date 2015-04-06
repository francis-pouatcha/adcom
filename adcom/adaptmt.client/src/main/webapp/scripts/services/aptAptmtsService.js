'use strict';
    
angular.module('adaptmt')


.factory('aptAptmtsService',['sessionManager','$translate','genericResource','$q',function(sessionManager,$translate,genericResource,$q){
    var service = {};

    service.urlBase='/adaptmt.server/rest/aptaptmts';
    
    service.create = function(entity){
        var deferred = $q.defer();
        genericResource.create(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(error){
            deferred.reject("Can not create, be sure that the aptAptmt name is !")
        });
        return deferred.promise;
    };

    service.updateAptAptmt = function(entity){
        var deferred = $q.defer();
        genericResource.update(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.loadAptAptmt = function(identif){
       var deferred = $q.defer();
        genericResource.findById(service.urlBase, identif).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.loadAptAptmts = function(searchInput){
       var deferred = $q.defer();
        genericResource.findBy(service.urlBase, searchInput).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.findAptAptmts = function(searchInput){
        var deferred = $q.defer();
        genericResource.findByLike(service.urlBase, searchInput)
            .success(function(data, status, headers, config){
                deferred.resolve(data);
            }).error(function(data, status, headers, config){
                deferred.reject("An error occured while fetching appointments");
            });
        return deferred.promise;
    };


    return service;
}]);