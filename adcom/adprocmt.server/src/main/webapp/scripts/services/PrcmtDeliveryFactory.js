angular.module('adprocmtserver').factory('PrcmtDeliveryResource', function($resource){
    var resource = $resource('rest/prcmtdeliverys/:PrcmtDeliveryId',{PrcmtDeliveryId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});