angular.module('AdCatal').factory('CatalManufSupplResource', function($resource){
    var resource = $resource('rest/catalmanufsuppls/:CatalManufSupplId',{CatalManufSupplId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});