angular.module('AdCatal').factory('CatalArtDetailConfigResource', function($resource){
    var resource = $resource('/adcatal.server/rest/catalartdetailconfigs/:CatalArtDetailConfigId',{CatalArtDetailConfigId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});