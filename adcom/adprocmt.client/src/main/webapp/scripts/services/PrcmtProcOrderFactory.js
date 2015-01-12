angular.module('AdProcmt').factory('PrcmtProcOrderResource', function($resource){
    var resource = $resource('rest/prcmtprocorders/:PrcmtProcOrderId',{PrcmtProcOrderId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});