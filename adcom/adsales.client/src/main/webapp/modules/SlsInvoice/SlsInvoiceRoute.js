'use strict';

angular.module('AdSales')
.config(['$routeProvider', function($routeProvider) {
    $routeProvider
    .when('/SlsInvoices',{templateUrl:'views/SlsInvoice/SlsInvoices.html',controller:'slsInvoicesCtlr'})
    .when('/SlsInvoices/edit/:SlsInvoiceId',{templateUrl:'views/SlsInvoice/SlsInvoiceEdit.html',controller:'slsInvoicesEditCtlr'})
    .when('/SlsInvoices/show',{templateUrl:'views/SlsInvoice/SlsInvoiceShow.html',controller:'slsInvoicesShowCtlr'});
}])
