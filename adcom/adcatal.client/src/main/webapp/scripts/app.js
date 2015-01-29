'use strict';

//angular.module('SessionManager');
//angular.module('AuthInterceptor');
//angular.module('NavBar');

angular.module('AdCatal', [
      'ngRoute',
      'ngCookies',
      'SessionManager',
      'AuthInterceptor',
      'ngSanitize',
      'pascalprecht.translate',
      'NavBar',
       'ngResource'
])
.constant('APP_CONFIG',{
	'appName':'Product Catalogue',
	'appVersion':'1.0.0-SNAPSHOT'

})

.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/CatalArticle/search.html',controller:'catalArticleCtrl'})
      .when('/CatalArticles/new',{templateUrl:'views/CatalArticle/create.html',controller:'catalArticleCreateCtrl'})
      .when('/CatalArticles',{templateUrl:'views/CatalArticle/search.html',controller:'catalArticleCtrl'})
      .when('/CatalArticles/edit/:CatalArticleId',{templateUrl:'views/CatalArticle/detail.html',controller:'EditCatalArticleController'})
      .when('/CatalArtDetailConfigs',{templateUrl:'views/CatalArtDetailConfig/search.html',controller:'SearchCatalArtDetailConfigController'})
      .when('/CatalArtDetailConfigs/new',{templateUrl:'views/CatalArtDetailConfig/detail.html',controller:'NewCatalArtDetailConfigController'})
      .when('/CatalArtDetailConfigs/edit/:CatalArtDetailConfigId',{templateUrl:'views/CatalArtDetailConfig/detail.html',controller:'EditCatalArtDetailConfigController'})
      .when('/CatalArtEquivalences',{templateUrl:'views/CatalArtEquivalence/search.html',controller:'SearchCatalArtEquivalenceController'})
      .when('/CatalArtEquivalences/new',{templateUrl:'views/CatalArtEquivalence/detail.html',controller:'NewCatalArtEquivalenceController'})
      .when('/CatalArtEquivalences/edit/:CatalArtEquivalenceId',{templateUrl:'views/CatalArtEquivalence/detail.html',controller:'EditCatalArtEquivalenceController'})
      .when('/CatalArtFeatMappings',{templateUrl:'views/CatalArtFeatMapping/search.html',controller:'SearchCatalArtFeatMappingController'})
      .when('/CatalArtFeatMappings/new',{templateUrl:'views/CatalArtFeatMapping/detail.html',controller:'NewCatalArtFeatMappingController'})
      .when('/CatalArtFeatMappings/edit/:CatalArtFeatMappingId',{templateUrl:'views/CatalArtFeatMapping/detail.html',controller:'EditCatalArtFeatMappingController'})
      .when('/CatalArtManufSupps',{templateUrl:'views/CatalArtManufSupp/search.html',controller:'SearchCatalArtManufSuppController'})
      .when('/CatalArtManufSupps/new',{templateUrl:'views/CatalArtManufSupp/detail.html',controller:'NewCatalArtManufSuppController'})
      .when('/CatalArtManufSupps/edit/:CatalArtManufSuppId',{templateUrl:'views/CatalArtManufSupp/detail.html',controller:'EditCatalArtManufSuppController'})
      .when('/CatalFamilyFeatMapings',{templateUrl:'views/CatalFamilyFeatMaping/search.html',controller:'SearchCatalFamilyFeatMapingController'})
      .when('/CatalFamilyFeatMapings/new',{templateUrl:'views/CatalFamilyFeatMaping/detail.html',controller:'NewCatalFamilyFeatMapingController'})
      .when('/CatalFamilyFeatMapings/edit/:CatalFamilyFeatMapingId',{templateUrl:'views/CatalFamilyFeatMaping/detail.html',controller:'EditCatalFamilyFeatMapingController'})
      .when('/CatalManufSuppls',{templateUrl:'views/CatalManufSuppl/search.html',controller:'SearchCatalManufSupplController'})
      .when('/CatalManufSuppls/new',{templateUrl:'views/CatalManufSuppl/detail.html',controller:'NewCatalManufSupplController'})
      .when('/CatalManufSuppls/edit/:CatalManufSupplId',{templateUrl:'views/CatalManufSuppl/detail.html',controller:'EditCatalManufSupplController'})
      .when('/CatalPicMappings',{templateUrl:'views/CatalPicMapping/search.html',controller:'SearchCatalPicMappingController'})
      .when('/CatalPicMappings/new',{templateUrl:'views/CatalPicMapping/detail.html',controller:'NewCatalPicMappingController'})
      .when('/CatalPicMappings/edit/:CatalPicMappingId',{templateUrl:'views/CatalPicMapping/detail.html',controller:'EditCatalPicMappingController'})
      .when('/CatalPkgModes',{templateUrl:'views/CatalPkgMode/search.html',controller:'SearchCatalPkgModeController'})
      .when('/CatalPkgModes/new',{templateUrl:'views/CatalPkgMode/detail.html',controller:'NewCatalPkgModeController'})
      .when('/CatalPkgModes/edit/:CatalPkgModeId',{templateUrl:'views/CatalPkgMode/detail.html',controller:'EditCatalPkgModeController'})
      .when('/CatalProductFamilys',{templateUrl:'views/CatalProductFamily/search.html',controller:'SearchCatalProductFamilyController'})
      .when('/CatalProductFamilys/new',{templateUrl:'views/CatalProductFamily/detail.html',controller:'NewCatalProductFamilyController'})
      .when('/CatalProductFamilys/edit/:CatalProductFamilyId',{templateUrl:'views/CatalProductFamily/detail.html',controller:'EditCatalProductFamilyController'})
      .otherwise({ redirectTo: '/' });
    
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
    sessionManager.appMenuUrl("/adcatal.client/menu.html");
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

	$translatePartialLoader.addPart('/adcatal.client/i18n/main');
	$translate.refresh();
}]);
