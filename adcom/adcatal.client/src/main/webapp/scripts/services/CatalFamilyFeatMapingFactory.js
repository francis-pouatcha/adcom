angular.module('AdCatal').factory('CatalFamilyFeatMapingResource', function($resource){
    var resource = $resource('rest/catalfamilyfeatmapings/:CatalFamilyFeatMapingId',{CatalFamilyFeatMapingId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});