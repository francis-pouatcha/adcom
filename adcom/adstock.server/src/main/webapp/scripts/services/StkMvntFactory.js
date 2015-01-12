angular.module('adstockserver').factory('StkMvntResource', function($resource){
    var resource = $resource('rest/stkmvnts/:StkMvntId',{StkMvntId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});