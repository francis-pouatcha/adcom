angular.module('adcatalserver').factory('CatalArtFeatMappingResource', function($resource){
    var resource = $resource('rest/catalartfeatmappings/:CatalArtFeatMappingId',{CatalArtFeatMappingId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});