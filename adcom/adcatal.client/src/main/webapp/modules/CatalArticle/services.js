'use strict';

angular.module('AdCatal')
.factory('catalArticleService',
    ['catalArticleResource','$cookieStore','sessionManager','$q',
    function (resource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.list = function(){
            var deferred = $q.defer();
            resource.listAll(resource.getSearchInput.start,resource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.findCustom = function(searchInput){
            var deferred = $q.defer();
            resource.findCustom(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

        service.create = function(entity){
            var deferred = $q.defer();
            resource.create(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create, be sure that the login name is unique!")
            });
            return deferred.promise;
        };

        service.update = function(entity){
            var deferred = $q.defer();
            resource.update(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update")
            });
            return deferred.promise;
        };

        service.load = function (identif){

            var deferred = $q.defer();

            resource.findById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("entity do not exist!")
            });
            return deferred.promise;
        };
        return service;
    }]
);
