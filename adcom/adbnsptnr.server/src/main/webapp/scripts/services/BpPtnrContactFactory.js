angular.module('adbnsptnrserver').factory('BpPtnrContactResource', function($resource){
    var resource = $resource('rest/bpptnrcontacts/:BpPtnrContactId',{BpPtnrContactId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});