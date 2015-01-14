angular.module('adbnsptnrserver').factory('BpCtgryOfPtnrResource', function($resource){
    var resource = $resource('rest/bpctgryofptnrs/:BpCtgryOfPtnrId',{BpCtgryOfPtnrId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});