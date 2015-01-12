angular.module('AdProcmt').factory('PrcmtDlvryItemResource', function($resource){
    var resource = $resource('rest/prcmtdlvryitems/:PrcmtDlvryItemId',{PrcmtDlvryItemId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});