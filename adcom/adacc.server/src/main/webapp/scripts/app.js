'use strict';

angular.module('adacc',['ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/AccAccounts',{templateUrl:'views/AccAccount/search.html',controller:'SearchAccAccountController'})
      .when('/AccAccounts/new',{templateUrl:'views/AccAccount/detail.html',controller:'NewAccAccountController'})
      .when('/AccAccounts/edit/:AccAccountId',{templateUrl:'views/AccAccount/detail.html',controller:'EditAccAccountController'})
      .when('/AccBlncs',{templateUrl:'views/AccBlnc/search.html',controller:'SearchAccBlncController'})
      .when('/AccBlncs/new',{templateUrl:'views/AccBlnc/detail.html',controller:'NewAccBlncController'})
      .when('/AccBlncs/edit/:AccBlncId',{templateUrl:'views/AccBlnc/detail.html',controller:'EditAccBlncController'})
      .when('/AccBrrs',{templateUrl:'views/AccBrr/search.html',controller:'SearchAccBrrController'})
      .when('/AccBrrs/new',{templateUrl:'views/AccBrr/detail.html',controller:'NewAccBrrController'})
      .when('/AccBrrs/edit/:AccBrrId',{templateUrl:'views/AccBrr/detail.html',controller:'EditAccBrrController'})
      .when('/AccCoAs',{templateUrl:'views/AccCoA/search.html',controller:'SearchAccCoAController'})
      .when('/AccCoAs/new',{templateUrl:'views/AccCoA/detail.html',controller:'NewAccCoAController'})
      .when('/AccCoAs/edit/:AccCoAId',{templateUrl:'views/AccCoA/detail.html',controller:'EditAccCoAController'})
      .when('/AccPstgs',{templateUrl:'views/AccPstg/search.html',controller:'SearchAccPstgController'})
      .when('/AccPstgs/new',{templateUrl:'views/AccPstg/detail.html',controller:'NewAccPstgController'})
      .when('/AccPstgs/edit/:AccPstgId',{templateUrl:'views/AccPstg/detail.html',controller:'EditAccPstgController'})
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
