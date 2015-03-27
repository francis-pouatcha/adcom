'use strict';

angular.module('adaptmt',['ngRoute',
                          'ngResource', 
                          'SessionManager',
                          'AuthInterceptor',
                          'ngSanitize',
                          'pascalprecht.translate',
                          'NavBar'])
  
   .constant('APP_CONFIG',{
	'appName':'Apointment - Partner',
	'appVersion':'1.0.0-SNAPSHOT'

   })
                          
  .config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider', function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
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
