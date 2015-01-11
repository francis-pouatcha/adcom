angular.module('adstockserver').factory('StkArtLotSectionResource', function($resource){
    var resource = $resource('rest/stkartlotsections/:StkArtLotSectionId',{StkArtLotSectionId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});