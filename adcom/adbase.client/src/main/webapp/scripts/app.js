'use strict';

// declare modules
//angular.module('SessionManager');
//angular.module('AuthInterceptor');

angular.module('AdBase', [
    'ngRoute',
    'ngCookies',
    'SessionManager',
    'AuthInterceptor',
    'ngSanitize',
    'pascalprecht.translate',
    'NavBar'
])
.constant('APP_CONFIG',{
	'appName':'AD Base',
	'appVersion':'1.0.0-SNAPSHOT'

})
.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function ($routeProvider, $httpProvider,$translateProvider,$translatePartialLoaderProvider) {

    $routeProvider
    .when('/',{
            templateUrl:'views/login/logins.html',
            controller:'loginController',
            controllerAs: 'loginCtrl'
        })


    .otherwise({
      redirectTo: '/'
    });
    
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('authInterceptor');
    
    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: '{part}/locale-{lang}.json'
    });

	$translateProvider.preferredLanguage('fr');
    
}])

.run(['$rootScope', '$location','loginService', 'sessionManager','$translate','APP_CONFIG','$translatePartialLoader',
    function ($rootScope, $location, loginService, sessionManager,$translate,APP_CONFIG,$translatePartialLoader) {
	    $rootScope.appName = APP_CONFIG.appName ;
	    $rootScope.appVersion = APP_CONFIG.appVersion ;
	    sessionManager.appMenuUrl("/adbase.client/menu_resp.html");
	    $rootScope.sessionManager = sessionManager;
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
        	var path = $location.path();
        	var noSess = !sessionManager.hasValues(sessionManager.terminalSession(), sessionManager.userSession());
        	if(noSess){
    			var sessParam = $location.search();
    			if(sessParam && sessionManager.hasValues(sessParam.trm,sessParam.usr)){
    				sessionManager.wsin(sessParam.trm,sessParam.usr,
    					function(){
    						loginService.loadLogins(
    		        			function(data, status, headers, config){
    		        				$location.path('/');
    		        			}
    		        		);
    					}
    				);
    			}
        	}
        });
        $translatePartialLoader.addPart('/adbase.client/i18n/main');
    	$translate.refresh();
    }]
);
