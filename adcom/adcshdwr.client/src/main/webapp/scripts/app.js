'use strict';

angular.module('AdCshdwr', [
      'ngRoute',
      'ngCookies',
      'SessionManager',
      'AuthInterceptor',
      'ngSanitize',
      'pascalprecht.translate',
      'NavBar'
])
.constant('APP_CONFIG',{
	'appName':'Cash Drawer',
	'appVersion':'1.0.0-SNAPSHOT'

})

.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
    .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
    .when('/CdrCshDrawers',{templateUrl:'views/CdrCshDrawer/search.html',controller:'SearchCdrCshDrawerController'})
    .when('/CdrCshDrawers/new',{templateUrl:'views/CdrCshDrawer/detail.html',controller:'NewCdrCshDrawerController'})
    .when('/CdrCshDrawers/edit/:CdrCshDrawerId',{templateUrl:'views/CdrCshDrawer/detail.html',controller:'EditCdrCshDrawerController'})
    .when('/CdrCstmrVchrs',{templateUrl:'views/CdrCstmrVchr/search.html',controller:'SearchCdrCstmrVchrController'})
    .when('/CdrCstmrVchrs/new',{templateUrl:'views/CdrCstmrVchr/detail.html',controller:'NewCdrCstmrVchrController'})
    .when('/CdrCstmrVchrs/edit/:CdrCstmrVchrId',{templateUrl:'views/CdrCstmrVchr/detail.html',controller:'EditCdrCstmrVchrController'})
    .when('/CdrDrctSaless',{templateUrl:'views/CdrDrctSales/search.html',controller:'SearchCdrDrctSalesController'})
    .when('/CdrDrctSaless/new',{templateUrl:'views/CdrDrctSales/detail.html',controller:'NewCdrDrctSalesController'})
    .when('/CdrDrctSaless/edit/:CdrDrctSalesId',{templateUrl:'views/CdrDrctSales/detail.html',controller:'EditCdrDrctSalesController'})
    .when('/CdrDsArtItems',{templateUrl:'views/CdrDsArtItem/search.html',controller:'SearchCdrDsArtItemController'})
    .when('/CdrDsArtItems/new',{templateUrl:'views/CdrDsArtItem/detail.html',controller:'NewCdrDsArtItemController'})
    .when('/CdrDsArtItems/edit/:CdrDsArtItemId',{templateUrl:'views/CdrDsArtItem/detail.html',controller:'EditCdrDsArtItemController'})
    .when('/CdrDsHstrys',{templateUrl:'views/CdrDsHstry/search.html',controller:'SearchCdrDsHstryController'})
    .when('/CdrDsHstrys/new',{templateUrl:'views/CdrDsHstry/detail.html',controller:'NewCdrDsHstryController'})
    .when('/CdrDsHstrys/edit/:CdrDsHstryId',{templateUrl:'views/CdrDsHstry/detail.html',controller:'EditCdrDsHstryController'})
    .when('/CdrDsPymntItems',{templateUrl:'views/CdrDsPymntItem/search.html',controller:'SearchCdrDsPymntItemController'})
    .when('/CdrDsPymntItems/new',{templateUrl:'views/CdrDsPymntItem/detail.html',controller:'NewCdrDsPymntItemController'})
    .when('/CdrDsPymntItems/edit/:CdrDsPymntItemId',{templateUrl:'views/CdrDsPymntItem/detail.html',controller:'EditCdrDsPymntItemController'})
    .when('/CdrPymnts',{templateUrl:'views/CdrPymnt/search.html',controller:'SearchCdrPymntController'})
    .when('/CdrPymnts/new',{templateUrl:'views/CdrPymnt/detail.html',controller:'NewCdrPymntController'})
    .when('/CdrPymnts/edit/:CdrPymntId',{templateUrl:'views/CdrPymnt/detail.html',controller:'EditCdrPymntController'})
    .when('/CdrPymntItems',{templateUrl:'views/CdrPymntItem/search.html',controller:'SearchCdrPymntItemController'})
    .when('/CdrPymntItems/new',{templateUrl:'views/CdrPymntItem/detail.html',controller:'NewCdrPymntItemController'})
    .when('/CdrPymntItems/edit/:CdrPymntItemId',{templateUrl:'views/CdrPymntItem/detail.html',controller:'EditCdrPymntItemController'})
    .when('/CdrPymntObjects',{templateUrl:'views/CdrPymntObject/search.html',controller:'SearchCdrPymntObjectController'})
    .when('/CdrPymntObjects/new',{templateUrl:'views/CdrPymntObject/detail.html',controller:'NewCdrPymntObjectController'})
    .when('/CdrPymntObjects/edit/:CdrPymntObjectId',{templateUrl:'views/CdrPymntObject/detail.html',controller:'EditCdrPymntObjectController'})
    .when('/CdrPymtHstrys',{templateUrl:'views/CdrPymtHstry/search.html',controller:'SearchCdrPymtHstryController'})
    .when('/CdrPymtHstrys/new',{templateUrl:'views/CdrPymtHstry/detail.html',controller:'NewCdrPymtHstryController'})
    .when('/CdrPymtHstrys/edit/:CdrPymtHstryId',{templateUrl:'views/CdrPymtHstry/detail.html',controller:'EditCdrPymtHstryController'})
    .when('/CdrVchrHstrys',{templateUrl:'views/CdrVchrHstry/search.html',controller:'SearchCdrVchrHstryController'})
    .when('/CdrVchrHstrys/new',{templateUrl:'views/CdrVchrHstry/detail.html',controller:'NewCdrVchrHstryController'})
    .when('/CdrVchrHstrys/edit/:CdrVchrHstryId',{templateUrl:'views/CdrVchrHstry/detail.html',controller:'EditCdrVchrHstryController'})
    .otherwise({redirectTo: '/'});
    
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('authInterceptor');
    
    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: '{part}/locale-{lang}.json'
    });

	$translateProvider.preferredLanguage('fr');
    
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
    sessionManager.appMenuUrl("/adinvtry.client/menu.html");
    $rootScope.sessionManager = sessionManager;
    $rootScope.$on('$locationChangeStart', function (event, next, current) {
    	var noSess = !sessionManager.hasValues(sessionManager.terminalSession(), sessionManager.userSession());
    	if(noSess){
			var sessParam = $location.search();
			if(sessParam && sessionManager.hasValues(sessParam.trm,sessParam.usr)){
				sessionManager.wsin(sessParam.trm,sessParam.usr,
					function(){
						$location.path('/');
					}
				);
			}
    	}
    });

	$translatePartialLoader.addPart('/adinvtry.client/i18n/main');
	$translate.refresh();
}]);
