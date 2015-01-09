angular.module('adcatalserver').factory('CatalProductFamilyResource', function($resource){
    var resource = $resource('rest/catalproductfamilys/:CatalProductFamilyId',{CatalProductFamilyId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});