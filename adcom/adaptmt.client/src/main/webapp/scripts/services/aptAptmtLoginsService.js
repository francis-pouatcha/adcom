'use strict';
    
angular.module('adaptmt')


.factory('aptAptmtLoginsService',['sessionManager','$translate','genericResource','$q', '$http',function(sessionManager,$translate,genericResource,$q,$http){
    var service = {};

    service.urlBase='/adaptmt.server/rest/aptaptmtLogins';
    service.loginsUrlBase='/adbase.server/rest/loginnamess';
    
    service.create = function(entity){
        var deferred = $q.defer();
        genericResource.create(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(error){
            deferred.reject("Can not create, be sure that the aptAptmtLogin name is !")
        });
        return deferred.promise;
    };
    
    service.deleteById = function(entity){
        var deferred = $q.defer();
        genericResource.deleteById(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(error){
            deferred.reject("Can not create, be sure that the aptAptmtLogin name is !")
        });
        return deferred.promise;
    };
    
    

    service.updateAptAptmtLogin = function(entity){
        var deferred = $q.defer();
        genericResource.update(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.loadAptAptmtLogin = function(identif){
       var deferred = $q.defer();
        genericResource.findById(service.urlBase, identif).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.loadAptAptmtLogins = function(searchInput){
       var deferred = $q.defer();
        genericResource.findBy(service.urlBase, searchInput).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.findAptAptmtLogins = function(searchInput){
        var deferred = $q.defer();
        genericResource.findByLike(service.urlBase, searchInput)
            .success(function(data, status, headers, config){
                deferred.resolve(data);
            }).error(function(data, status, headers, config){
                deferred.reject("An error occured while fetching appointments");
            });
        return deferred.promise;
    };
    
    service.loadUsers = function(loginName){
        return genericResource.findByLikePromissed(service.loginsUrlBase, 'loginName', loginName)
        .then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    };
    
    service.nextAptAptmtLogin = function(id){
        var deferred = $q.defer();
        
        $http.get(service.urlBase+'/nextLogin/'+id)
        .success(function(data){
            deferred.resolve(data);
        }).error(function(data){
            deferred.reject("no more appointment !")
        });
        return deferred.promise;
    }

    service.previousAptAptmtLogin = function(id){
        var deferred = $q.defer();
        
        $http.get(service.urlBase+'/previousLogin/'+id)
        .success(function(data){
            deferred.resolve(data);
        }).error(function(data){
            deferred.reject("no more appointment !")
        });
        return deferred.promise;
    }
    


    return service;
}]);