'use strict';

angular.module('AdBase')
.factory('loginService',
    ['loginResource','$cookieStore','sessionManager','$q',
    function (loginResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.loadLogins = function(){
            var deferred = $q.defer();
        	loginResource.listAll(loginResource.getSearchInput.start,loginResource.getSearchInput.max)
    			.success(function(data, status, headers, config){
                    deferred.resolve(data);
    			}).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
    			});
            return deferred.promise;
    	};

        service.findLogins = function(searchInput){
            var deferred = $q.defer();
            loginResource.findByLike(searchInput)
                .success(function(data, status, headers, config){
                    deferred.resolve(data);
                }).error(function(data, status, headers, config){
                    deferred.reject("An error occured while fetching items");
                });
            return deferred.promise;
        };

    	return service;
    }]
);
