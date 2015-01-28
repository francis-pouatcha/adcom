angular.module('AdCatal').factory('CatalArtEquivalenceResource', function($resource){
    var resource = $resource('rest/catalartequivalences/:CatalArtEquivalenceId',{CatalArtEquivalenceId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});