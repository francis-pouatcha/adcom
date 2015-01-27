angular.module('AdBnsptnr').factory('InsurranceResource', function($resource){
    var resource = $resource('rest/insurrances/:InsurranceId',{InsurranceId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});