angular.module('AdBnsptnr').factory('BpCtgryDscntResource', function($resource){
    var resource = $resource('rest/bpctgrydscnts/:BpCtgryDscntId',{BpCtgryDscntId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});