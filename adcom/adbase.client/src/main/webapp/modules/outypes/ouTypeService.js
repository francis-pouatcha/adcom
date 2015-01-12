'use strict';

angular.module('AdBase')
.factory('ouTypeService',
    ['ouTypeResource',
    function (ouTypeResource) {
        var service = {};

        service.getActifsOuTypeAsArray = function(getData){
            ouTypeResource.findActifsFromNow().success(function(data){
                return getData(data);
            }).error(function(){
                //log
            });
    	};

    	return service;
    }]
);
