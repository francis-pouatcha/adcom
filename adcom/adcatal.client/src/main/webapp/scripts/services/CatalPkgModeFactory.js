angular.module('AdCatal').factory('CatalPkgModeResource', function($resource){
    var resource = $resource('rest/catalpkgmodes/:CatalPkgModeId',{CatalPkgModeId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});