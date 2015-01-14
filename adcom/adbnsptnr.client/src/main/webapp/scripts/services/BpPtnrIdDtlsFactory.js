angular.module('AdBnsptnr').factory('BpPtnrIdDtlsResource', function($resource){
    var resource = $resource('rest/bpptnriddtlss/:BpPtnrIdDtlsId',{BpPtnrIdDtlsId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});