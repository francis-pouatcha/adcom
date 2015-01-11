angular.module('AdStock').factory('StkArtStockQtyResource', function($resource){
    var resource = $resource('rest/stkartstockqtys/:StkArtStockQtyId',{StkArtStockQtyId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});