angular.module('adstockserver').factory('StkArticleLotResource', function($resource){
    var resource = $resource('rest/stkarticlelots/:StkArticleLotId',{StkArticleLotId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});