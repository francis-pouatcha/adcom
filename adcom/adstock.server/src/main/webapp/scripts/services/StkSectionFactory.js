angular.module('adstockserver').factory('StkSectionResource', function($resource){
    var resource = $resource('rest/stksections/:StkSectionId',{StkSectionId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});