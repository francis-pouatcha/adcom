angular.module('AdBnsptnr').factory('BpPtnrAccntBlnceResource', function($resource){
    var resource = $resource('rest/bpptnraccntblnces/:BpPtnrAccntBlnceId',{BpPtnrAccntBlnceId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});