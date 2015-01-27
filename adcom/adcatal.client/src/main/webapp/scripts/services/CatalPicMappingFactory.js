angular.module('AdCatal').factory('CatalPicMappingResource', function($resource){
    var resource = $resource('rest/catalpicmappings/:CatalPicMappingId',{CatalPicMappingId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});