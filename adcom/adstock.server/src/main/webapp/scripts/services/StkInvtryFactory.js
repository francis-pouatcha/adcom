angular.module('adstockserver').factory('StkInvtryResource', function($resource){
    var resource = $resource('rest/stkinvtrys/:StkInvtryId',{StkInvtryId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});