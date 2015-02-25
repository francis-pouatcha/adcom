'use strict';
    
angular.module('AdProcmt')

.factory('ProcmtUtils',['genericResource','$q',function(genericResource,$q){
    var service = {};

    service.urlBase='/adprocmt.server/rest/prcmtdeliverys';
    service.urlManager='/adprocmt.server/rest/delivery';
    service.adbnsptnr='/adbnsptnr.server/rest/bplegalptnrids';
    service.catalarticles='/adcatal.server/rest/catalarticles';
    service.orgunits='/adbase.server/rest/orgunits';



    return service;
}])
