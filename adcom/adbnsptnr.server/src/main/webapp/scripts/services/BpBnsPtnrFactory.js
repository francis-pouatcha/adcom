angular.module('adbnsptnrserver').factory('BpBnsPtnrResource', function($resource){
    var resource = $resource('rest/bpbnsptnrs/:BpBnsPtnrId',{BpBnsPtnrId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});