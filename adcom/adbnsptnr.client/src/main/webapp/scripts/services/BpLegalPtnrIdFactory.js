angular.module('AdBnsptnr').factory('BpLegalPtnrIdResource', function($resource){
    var resource = $resource('rest/bplegalptnrids/:BpLegalPtnrIdId',{BpLegalPtnrIdId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});