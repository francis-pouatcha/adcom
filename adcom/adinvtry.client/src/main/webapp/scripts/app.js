'use strict';

angular.module('AdInvtry', [
      'ngRoute',
      'ngCookies',
      'SessionManager',
      'AuthInterceptor',
      'ngSanitize',
      'pascalprecht.translate',
      'NavBar',
      'datePicker'
])
.constant('APP_CONFIG',{
	'appName':'Inventory Management',
	'appVersion':'1.0.0-SNAPSHOT'

})

.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
    .when('/',{templateUrl:'views/InvInvtry/InvInvtrys.html',controller:'invtryCtrl'})
    .when('/InvInvtrys',{templateUrl:'views/InvInvtry/InvInvtrys.html',controller:'invtryCtrl'})
    .when('/InvInvtrys/new',{templateUrl:'views/InvInvtry/InvInvtryCreate.html',controller:'invtryCreateCtlr'})
    .when('/InvInvtrys/show/:identif',{templateUrl:'views/InvInvtry/InvInvtryShow.html',controller:'invInvtryShowCtlr'})
    .when('/InvInvtrys/edit/:identif',{templateUrl:'views/InvInvtry/InvInvtryEdit.html',controller:'invtryEditCtlr'})
    .when('/InvInvtryItems',{templateUrl:'views/InvInvtryItem/search.html',controller:'SearchInvInvtryItemController'})
    .when('/InvInvtryItems/new',{templateUrl:'views/InvInvtryItem/detail.html',controller:'NewInvInvtryItemController'})
    .when('/InvInvtryItems/edit/:InvInvtryItemId',{templateUrl:'views/InvInvtryItem/detail.html',controller:'EditInvInvtryItemController'})
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

