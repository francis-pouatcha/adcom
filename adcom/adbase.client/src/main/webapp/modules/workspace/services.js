'use strict';

angular.module('AdBase')
.factory('workspaceService',
    ['workspaceResource','$cookieStore','sessionManager','$q',
    function (workspaceResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.loadLogins = function(){
            var deferred = $q.defer();
        	workspaceResource.listAll(workspaceResource.getSearchInput.start,workspaceResource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.findLogins = function(searchInput){
            var deferred = $q.defer();
            workspaceResource.findByLike(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

        service.create = function(entity){
            var deferred = $q.defer();
            workspaceResource.create(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create, be sure that the login name is unique!")
            });
            return deferred.promise;
        };

        service.update = function(entity){
            var deferred = $q.defer();
            workspaceResource.update(entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update")
            });
            return deferred.promise;
        };

        service.load = function (identif){

            var deferred = $q.defer();

            workspaceResource.findById(identif).success(function(data){

                deferred.resolve(data);
            }).error(function(data){
                deferred.reject("user do not exist!")
            });
            return deferred.promise;
        };




        return service;
    }]
);
