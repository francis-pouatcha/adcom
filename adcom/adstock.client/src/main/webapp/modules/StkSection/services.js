'use strict';

angular.module('AdStock')
.factory('stkSectionService', ['stkSectionResource','$cookieStore','sessionManager','$q', function (stkSectionResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.list = function(){
            var deferred = $q.defer();
            stkSectionResource.listAll(stkSectionResource.getSearchInput.start,stkSectionResource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.find = function(searchInput){
            var deferred = $q.defer();
            stkSectionResource.findByLike(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

        service.create = function(entity){
            var deferred = $q.defer();
            stkSectionResource.create(entity).success(function(data){
            	console.log("Stock Section successfully created...");
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create, be sure that the login name is unique!")
            });
            return deferred.promise;
        };

        service.update = function(entity){
            var deferred = $q.defer();
            stkSectionResource.update(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update")
            });
            return deferred.promise;
        };

        service.load = function (identif){

            var deferred = $q.defer();

            stkSectionResource.findById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("user do not exist!")
            });
            return deferred.promise;
        };
        return service;
    }]
);
