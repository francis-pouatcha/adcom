angular.module('adbnsptnrserver').factory('BpPtnrCtgryDtlsResource', function($resource){
    var resource = $resource('rest/bpptnrctgrydtlss/:BpPtnrCtgryDtlsId',{BpPtnrCtgryDtlsId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});