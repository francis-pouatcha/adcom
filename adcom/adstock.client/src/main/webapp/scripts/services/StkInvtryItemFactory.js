angular.module('AdStock').factory('StkInvtryItemResource', function($resource){
    var resource = $resource('rest/stkinvtryitems/:StkInvtryItemId',{StkInvtryItemId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});