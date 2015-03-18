'use strict';

angular.module('AdSales')
.config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/SlsSalesOrder/SlsSalesOrders.html',controller:'slsSalesOrdersCtlr'})
      .when('/SlsSalesOrders/new',{templateUrl:'views/SlsSalesOrder/SlsSalesOrderCreate.html',controller:'slsSalesOrderCreateCtlr'})
      .when('/SlsSalesOrders',{templateUrl:'views/SlsSalesOrder/SlsSalesOrders.html',controller:'slsSalesOrdersCtlr'})
      .when('/SlsSalesOrders/show',{templateUrl:'views/SlsSalesOrder/SlsSalesOrderShow.html',controller:'slsSalesOrderShowCtlr'})
      .when('/SlsSalesOrders/edit',{templateUrl:'views/SlsSalesOrder/SlsSalesOrderEdit.html',controller:'slsSalesOrderEditCtlr'});
}])
