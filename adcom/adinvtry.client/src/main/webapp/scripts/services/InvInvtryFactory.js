angular.module('AdInvtry').factory('InvInvtryResource', function($resource){
    var resource = $resource('rest/invinvtrys/:InvInvtryId',{InvInvtryId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});