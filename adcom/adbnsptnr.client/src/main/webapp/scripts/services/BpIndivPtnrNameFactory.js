angular.module('AdBnsptnr').factory('BpIndivPtnrNameResource', function($resource){
    var resource = $resource('rest/bpindivptnrnames/:BpIndivPtnrNameId',{BpIndivPtnrNameId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});