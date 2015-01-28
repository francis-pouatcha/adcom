angular.module('AdBnsptnr').factory('BpPtnrCtgryResource', function($resource){
    var resource = $resource('rest/bpptnrctgrys/:BpPtnrCtgryId',{BpPtnrCtgryId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});