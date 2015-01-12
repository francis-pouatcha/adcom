angular.module('adprocmtserver').factory('PrcmtPOItemResource', function($resource){
    var resource = $resource('rest/prcmtpoitems/:PrcmtPOItemId',{PrcmtPOItemId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});