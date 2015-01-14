angular.module('adbnsptnrserver').factory('BpPtnrCreditDtlsResource', function($resource){
    var resource = $resource('rest/bpptnrcreditdtlss/:BpPtnrCreditDtlsId',{BpPtnrCreditDtlsId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});