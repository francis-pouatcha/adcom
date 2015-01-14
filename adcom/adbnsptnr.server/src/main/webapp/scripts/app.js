'use strict';

angular.module('adbnsptnrserver',['ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/BpBnsPtnrs',{templateUrl:'views/BpBnsPtnr/search.html',controller:'SearchBpBnsPtnrController'})
      .when('/BpBnsPtnrs/new',{templateUrl:'views/BpBnsPtnr/detail.html',controller:'NewBpBnsPtnrController'})
      .when('/BpBnsPtnrs/edit/:BpBnsPtnrId',{templateUrl:'views/BpBnsPtnr/detail.html',controller:'EditBpBnsPtnrController'})
      .when('/BpCtgryDscnts',{templateUrl:'views/BpCtgryDscnt/search.html',controller:'SearchBpCtgryDscntController'})
      .when('/BpCtgryDscnts/new',{templateUrl:'views/BpCtgryDscnt/detail.html',controller:'NewBpCtgryDscntController'})
      .when('/BpCtgryDscnts/edit/:BpCtgryDscntId',{templateUrl:'views/BpCtgryDscnt/detail.html',controller:'EditBpCtgryDscntController'})
      .when('/BpCtgryOfPtnrs',{templateUrl:'views/BpCtgryOfPtnr/search.html',controller:'SearchBpCtgryOfPtnrController'})
      .when('/BpCtgryOfPtnrs/new',{templateUrl:'views/BpCtgryOfPtnr/detail.html',controller:'NewBpCtgryOfPtnrController'})
      .when('/BpCtgryOfPtnrs/edit/:BpCtgryOfPtnrId',{templateUrl:'views/BpCtgryOfPtnr/detail.html',controller:'EditBpCtgryOfPtnrController'})
      .when('/BpIndivPtnrNames',{templateUrl:'views/BpIndivPtnrName/search.html',controller:'SearchBpIndivPtnrNameController'})
      .when('/BpIndivPtnrNames/new',{templateUrl:'views/BpIndivPtnrName/detail.html',controller:'NewBpIndivPtnrNameController'})
      .when('/BpIndivPtnrNames/edit/:BpIndivPtnrNameId',{templateUrl:'views/BpIndivPtnrName/detail.html',controller:'EditBpIndivPtnrNameController'})
      .when('/BpLegalPtnrIds',{templateUrl:'views/BpLegalPtnrId/search.html',controller:'SearchBpLegalPtnrIdController'})
      .when('/BpLegalPtnrIds/new',{templateUrl:'views/BpLegalPtnrId/detail.html',controller:'NewBpLegalPtnrIdController'})
      .when('/BpLegalPtnrIds/edit/:BpLegalPtnrIdId',{templateUrl:'views/BpLegalPtnrId/detail.html',controller:'EditBpLegalPtnrIdController'})
      .when('/BpPtnrAccntBlnces',{templateUrl:'views/BpPtnrAccntBlnce/search.html',controller:'SearchBpPtnrAccntBlnceController'})
      .when('/BpPtnrAccntBlnces/new',{templateUrl:'views/BpPtnrAccntBlnce/detail.html',controller:'NewBpPtnrAccntBlnceController'})
      .when('/BpPtnrAccntBlnces/edit/:BpPtnrAccntBlnceId',{templateUrl:'views/BpPtnrAccntBlnce/detail.html',controller:'EditBpPtnrAccntBlnceController'})
      .when('/BpPtnrContacts',{templateUrl:'views/BpPtnrContact/search.html',controller:'SearchBpPtnrContactController'})
      .when('/BpPtnrContacts/new',{templateUrl:'views/BpPtnrContact/detail.html',controller:'NewBpPtnrContactController'})
      .when('/BpPtnrContacts/edit/:BpPtnrContactId',{templateUrl:'views/BpPtnrContact/detail.html',controller:'EditBpPtnrContactController'})
      .when('/BpPtnrCreditDtlss',{templateUrl:'views/BpPtnrCreditDtls/search.html',controller:'SearchBpPtnrCreditDtlsController'})
      .when('/BpPtnrCreditDtlss/new',{templateUrl:'views/BpPtnrCreditDtls/detail.html',controller:'NewBpPtnrCreditDtlsController'})
      .when('/BpPtnrCreditDtlss/edit/:BpPtnrCreditDtlsId',{templateUrl:'views/BpPtnrCreditDtls/detail.html',controller:'EditBpPtnrCreditDtlsController'})
      .when('/BpPtnrCtgrys',{templateUrl:'views/BpPtnrCtgry/search.html',controller:'SearchBpPtnrCtgryController'})
      .when('/BpPtnrCtgrys/new',{templateUrl:'views/BpPtnrCtgry/detail.html',controller:'NewBpPtnrCtgryController'})
      .when('/BpPtnrCtgrys/edit/:BpPtnrCtgryId',{templateUrl:'views/BpPtnrCtgry/detail.html',controller:'EditBpPtnrCtgryController'})
      .when('/BpPtnrCtgryDtlss',{templateUrl:'views/BpPtnrCtgryDtls/search.html',controller:'SearchBpPtnrCtgryDtlsController'})
      .when('/BpPtnrCtgryDtlss/new',{templateUrl:'views/BpPtnrCtgryDtls/detail.html',controller:'NewBpPtnrCtgryDtlsController'})
      .when('/BpPtnrCtgryDtlss/edit/:BpPtnrCtgryDtlsId',{templateUrl:'views/BpPtnrCtgryDtls/detail.html',controller:'EditBpPtnrCtgryDtlsController'})
      .when('/BpPtnrIdDtlss',{templateUrl:'views/BpPtnrIdDtls/search.html',controller:'SearchBpPtnrIdDtlsController'})
      .when('/BpPtnrIdDtlss/new',{templateUrl:'views/BpPtnrIdDtls/detail.html',controller:'NewBpPtnrIdDtlsController'})
      .when('/BpPtnrIdDtlss/edit/:BpPtnrIdDtlsId',{templateUrl:'views/BpPtnrIdDtls/detail.html',controller:'EditBpPtnrIdDtlsController'})
      .when('/Insurrances',{templateUrl:'views/Insurrance/search.html',controller:'SearchInsurranceController'})
      .when('/Insurrances/new',{templateUrl:'views/Insurrance/detail.html',controller:'NewInsurranceController'})
      .when('/Insurrances/edit/:InsurranceId',{templateUrl:'views/Insurrance/detail.html',controller:'EditInsurranceController'})
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
