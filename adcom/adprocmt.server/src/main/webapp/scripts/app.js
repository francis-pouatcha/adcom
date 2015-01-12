'use strict';

angular.module('adprocmtserver',['ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/PrcmtDeliverys',{templateUrl:'views/PrcmtDelivery/search.html',controller:'SearchPrcmtDeliveryController'})
      .when('/PrcmtDeliverys/new',{templateUrl:'views/PrcmtDelivery/detail.html',controller:'NewPrcmtDeliveryController'})
      .when('/PrcmtDeliverys/edit/:PrcmtDeliveryId',{templateUrl:'views/PrcmtDelivery/detail.html',controller:'EditPrcmtDeliveryController'})
      .when('/PrcmtDlvryItems',{templateUrl:'views/PrcmtDlvryItem/search.html',controller:'SearchPrcmtDlvryItemController'})
      .when('/PrcmtDlvryItems/new',{templateUrl:'views/PrcmtDlvryItem/detail.html',controller:'NewPrcmtDlvryItemController'})
      .when('/PrcmtDlvryItems/edit/:PrcmtDlvryItemId',{templateUrl:'views/PrcmtDlvryItem/detail.html',controller:'EditPrcmtDlvryItemController'})
      .when('/PrcmtPOItems',{templateUrl:'views/PrcmtPOItem/search.html',controller:'SearchPrcmtPOItemController'})
      .when('/PrcmtPOItems/new',{templateUrl:'views/PrcmtPOItem/detail.html',controller:'NewPrcmtPOItemController'})
      .when('/PrcmtPOItems/edit/:PrcmtPOItemId',{templateUrl:'views/PrcmtPOItem/detail.html',controller:'EditPrcmtPOItemController'})
      .when('/PrcmtProcOrders',{templateUrl:'views/PrcmtProcOrder/search.html',controller:'SearchPrcmtProcOrderController'})
      .when('/PrcmtProcOrders/new',{templateUrl:'views/PrcmtProcOrder/detail.html',controller:'NewPrcmtProcOrderController'})
      .when('/PrcmtProcOrders/edit/:PrcmtProcOrderId',{templateUrl:'views/PrcmtProcOrder/detail.html',controller:'EditPrcmtProcOrderController'})
      .otherwise({
        redirectTo: '/'
      });
  }])
  .controller('LandingPageController', function LandingPageController() {
  })
  .controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
  });
