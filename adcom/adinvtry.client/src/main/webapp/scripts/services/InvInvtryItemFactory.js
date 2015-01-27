angular.module('AdInvtry').factory('InvInvtryItemResource', function($resource){
    var resource = $resource('rest/invinvtryitems/:InvInvtryItemId',{InvInvtryItemId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});