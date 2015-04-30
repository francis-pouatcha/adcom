'use strict';
    
angular.module('adaptmt')


.factory('aptAptmtRepportLoginsService',['sessionManager','$translate','genericResource','$q', '$http',function(sessionManager,$translate,genericResource,$q,$http){
    var service = {};

    service.urlBase='/adaptmt.server/rest/aptaptmtReportLogins';
    service.loginsUrlBase='/adbase.server/rest/loginnamess';
    
    service.create = function(entity){
        var deferred = $q.defer();
        genericResource.create(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(error){
            deferred.reject("Can not create, be sure that the AptAptmtReportLogin name is !")
        });
        return deferred.promise;
    };
    
    service.deleteById = function(entity){
        var deferred = $q.defer();
        genericResource.deleteById(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(error){
            deferred.reject("Can not create, be sure that the AptAptmtReportLogin name is !")
        });
        return deferred.promise;
    };
    
    

    service.updateAptAptmtReportLogin = function(entity){
        var deferred = $q.defer();
        genericResource.update(service.urlBase, entity).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.loadAptAptmtReportLogin = function(identif){
       var deferred = $q.defer();
        genericResource.findById(service.urlBase, identif).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.loadAptAptmtReportLogins = function(searchInput){
       var deferred = $q.defer();
        genericResource.findBy(service.urlBase, searchInput).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject("Can not update")
        });
        return deferred.promise;
    };
    
    service.findAptAptmtReportLogins = function(searchInput){
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
    
    service.nextAptAptmtReportLogin = function(id){
        var deferred = $q.defer();
        
        $http.get(service.urlBase+'/nextLogin/'+id)
        .success(function(data){
            deferred.resolve(data);
        }).error(function(data){
            deferred.reject("no more appointment !")
        });
        return deferred.promise;
    }

    service.previousAptAptmtReportLogin = function(id){
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
