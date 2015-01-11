angular.module('adstockserver').factory('StkLotStockQtyResource', function($resource){
    var resource = $resource('rest/stklotstockqtys/:StkLotStockQtyId',{StkLotStockQtyId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});