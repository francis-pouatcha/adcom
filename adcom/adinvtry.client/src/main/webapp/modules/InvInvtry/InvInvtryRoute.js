'use strict';

angular.module('AdInvtry')
.config(['$routeProvider',
         function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/InvInvtry/InvInvtrys.html',controller:'invInvtrysCtlr'})
      .when('/InvInvtrys/new',{templateUrl:'views/InvInvtry/InvInvtryCreate.html',controller:'invInvtryCreateCtlr'})
      .when('/InvInvtrys',{templateUrl:'views/InvInvtry/InvInvtrys.html',controller:'invInvtrysCtlr'})
      .when('/InvInvtrys/show',{templateUrl:'views/InvInvtry/InvInvtryShow.html',controller:'invInvtryShowCtlr'})
      .when('/InvInvtrys/edit',{templateUrl:'views/InvInvtry/InvInvtryEdit.html',controller:'invInvtryEditCtlr'});
}])
