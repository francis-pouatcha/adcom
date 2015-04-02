'use strict';

angular.module('adaptmt',['ngRoute',
                          'ngCookies',
                          'ui.bootstrap',
                          'SessionManager',
                          'AuthInterceptor',
                          'ngSanitize',
                          'pascalprecht.translate',
                          'NavBar',
                           'ngResource',
                           'datePicker',
                           'ADUtils',
                           'httpProgress'])
  
   .constant('APP_CONFIG',{
	'appName':'Appointment',
	'appVersion':'1.0.0-SNAPSHOT'

   })
                          
  .config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider', function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/aptaptmt/aptaptmts.html',controller:'aptAptmtsController'})
      .when('/aptaptmt',{templateUrl:'views/aptaptmt/search.html',controller:'SearchAptAptmtController'})
      .when('/aptaptmt/create',{templateUrl:'views/aptaptmt/create.html',controller:'aptAptmtCreateCtlr'})
      .otherwise({
        redirectTo: '/'
      });
    
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('authInterceptor');
    
    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: '{part}/locale-{lang}.json'
    });
    
  }])
  
  .controller('LandingPageController', function LandingPageController() {
  })
  
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
    $translatePartialLoader.addPart('/adaptmt.client/i18n/main');
    sessionManager.appMenuUrl("/adaptmt.client/menu.html");
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
