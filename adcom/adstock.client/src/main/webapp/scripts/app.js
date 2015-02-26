'use strict';

angular.module('AdStock', [
      'ngRoute',
      'ngCookies',
      'SessionManager',
      'AuthInterceptor',
      'ngSanitize',
      'pascalprecht.translate',
      'NavBar'
])
.constant('APP_CONFIG',{
	'appName':'Stock',
	'appVersion':'1.0.0-SNAPSHOT'

})

.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
    .when('/',{templateUrl:'views/StkSection/search.html',controller:'stkSectionCtrl'})
    .when('/StkSections',{templateUrl:'views/StkSection/search.html',controller:'stkSectionCtrl'})
    .when('/StkSections/new',{templateUrl:'views/StkSection/create.html',controller:'stkSectionCreateCtrl'})
    .when('/StkSections/edit/:StkSectionId',{templateUrl:'views/StkSection/update.html',controller:'stkSectionEditCtrl'})
    .when('/StkSections/show/:StkSectionId',{templateUrl:'views/StkSection/show.html',controller:'stkSectionShowCtrl'})
    .when('/StkArtLotSections',{templateUrl:'views/StkArtLotSection/search.html',controller:'SearchStkArtLotSectionController'})
    .when('/StkArtLotSections/new',{templateUrl:'views/StkArtLotSection/detail.html',controller:'NewStkArtLotSectionController'})
    .when('/StkArtLotSections/edit/:StkArtLotSectionId',{templateUrl:'views/StkArtLotSection/detail.html',controller:'EditStkArtLotSectionController'})
    .when('/StkArtStockQtys',{templateUrl:'views/StkArtStockQty/search.html',controller:'SearchStkArtStockQtyController'})
    .when('/StkArtStockQtys/new',{templateUrl:'views/StkArtStockQty/detail.html',controller:'NewStkArtStockQtyController'})
    .when('/StkArtStockQtys/edit/:StkArtStockQtyId',{templateUrl:'views/StkArtStockQty/detail.html',controller:'EditStkArtStockQtyController'})
    .when('/StkArticleLots',{templateUrl:'views/StkArticleLot/search.html',controller:'SearchStkArticleLotController'})
    .when('/StkArticleLots/new',{templateUrl:'views/StkArticleLot/detail.html',controller:'NewStkArticleLotController'})
    .when('/StkArticleLots/edit/:StkArticleLotId',{templateUrl:'views/StkArticleLot/detail.html',controller:'EditStkArticleLotController'})
    .when('/StkInvtrys',{templateUrl:'views/StkInvtry/search.html',controller:'SearchStkInvtryController'})
    .when('/StkInvtrys/new',{templateUrl:'views/StkInvtry/detail.html',controller:'NewStkInvtryController'})
    .when('/StkInvtrys/edit/:StkInvtryId',{templateUrl:'views/StkInvtry/detail.html',controller:'EditStkInvtryController'})
    .when('/StkInvtryItems',{templateUrl:'views/StkInvtryItem/search.html',controller:'SearchStkInvtryItemController'})
    .when('/StkInvtryItems/new',{templateUrl:'views/StkInvtryItem/detail.html',controller:'NewStkInvtryItemController'})
    .when('/StkInvtryItems/edit/:StkInvtryItemId',{templateUrl:'views/StkInvtryItem/detail.html',controller:'EditStkInvtryItemController'})
    .when('/StkLotStockQtys',{templateUrl:'views/StkLotStockQty/search.html',controller:'SearchStkLotStockQtyController'})
    .when('/StkLotStockQtys/new',{templateUrl:'views/StkLotStockQty/detail.html',controller:'NewStkLotStockQtyController'})
    .when('/StkLotStockQtys/edit/:StkLotStockQtyId',{templateUrl:'views/StkLotStockQty/detail.html',controller:'EditStkLotStockQtyController'})
    .when('/StkMvnts',{templateUrl:'views/StkMvnt/search.html',controller:'SearchStkMvntController'})
    .when('/StkMvnts/new',{templateUrl:'views/StkMvnt/detail.html',controller:'NewStkMvntController'})
    .when('/StkMvnts/edit/:StkMvntId',{templateUrl:'views/StkMvnt/detail.html',controller:'EditStkMvntController'})
    .otherwise({redirectTo: '/' });
    
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('authInterceptor');
    
    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: '{part}/locale-{lang}.json'
    });

	
    
}])

.controller('LandingPageController', function LandingPageController() {})

.controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
})

.run(['$rootScope', '$location','sessionManager','$translate','APP_CONFIG','$translatePartialLoader',
      function ($rootScope, $location, sessionManager,$translate,APP_CONFIG,$translatePartialLoader) {
    $rootScope.appName = APP_CONFIG.appName ;
    $rootScope.appVersion = APP_CONFIG.appVersion ;
    sessionManager.appMenuUrl("/adstock.client/menu.html");
    $translatePartialLoader.addPart('/adstock.client/i18n/main');
    $rootScope.sessionManager = sessionManager;
    $rootScope.$on('$locationChangeStart', function (event, next, current) {
    	var noSess = !sessionManager.hasValues(sessionManager.terminalSession(), sessionManager.userSession());
    	if(noSess){
			var sessParam = $location.search();
			if(sessParam && sessionManager.hasValues(sessParam.trm,sessParam.usr)){
				sessionManager.wsin(sessParam.trm,sessParam.usr,
					function(data, status, headers, config){
						sessionManager.language(headers('X-USER-LANG'),false);
						$location.path('/');
					}
				);
			}
    	}
    });
}]);
