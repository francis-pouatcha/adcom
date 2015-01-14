'use strict';

angular.module('AdBase')
.factory('countryService',
    ['countryResource','$cookieStore','sessionManager','$q',
    function (countryResource,$cookieStore,sessionManager,$q) {
        var service = {};

        service.findActifsFromNow = function(){
            var deferred = $q.defer();
            countryResource.findActifsFromNow().success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
               deferred.reject("An error occured when searching actives countries"); 
            });
            return deferred.promise;
    	};

        return service;
    }]
);
