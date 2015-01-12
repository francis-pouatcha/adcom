angular.module('AdCatal').factory('CatalArtManufSuppResource', function($resource){
    var resource = $resource('rest/catalartmanufsupps/:CatalArtManufSuppId',{CatalArtManufSuppId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});