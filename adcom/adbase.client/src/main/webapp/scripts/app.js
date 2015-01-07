'use strict';

// declare modules
angular.module('SessionManager');
angular.module('AuthInterceptor');
angular.module('Login');

angular.module('AdBase', [
    'ngRoute',
    'ngCookies',
    'SessionManager',
    'AuthInterceptor',
    'ngSanitize',
    'pascalprecht.translate',
    'Login'
])
.constant('APP_CONFIG',{
	'appName':'AD Login',
	'appVersion':'1.0.0-SNAPSHOT'

})
.config(['$routeProvider', '$httpProvider','$translateProvider', function ($routeProvider, $httpProvider,$translateProvider) {

    $routeProvider
    .when('/',{templateUrl:'views/logins.html',controller:'loginController'})
    .otherwise({
      redirectTo: '/'
    });
    
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('authInterceptor');
    
	$translateProvider.useStaticFilesLoader({
		prefix: 'i18n/locale-',
		suffix: '.json'
	});

	$translateProvider.preferredLanguage('fr');
    
}])

.run(['$rootScope', '$location','loginService', 'sessionManager','$translate','APP_CONFIG',
    function ($rootScope, $location, loginService, sessionManager,$translate,APP_CONFIG) {
	    $rootScope.appName = APP_CONFIG.appName ;
	    $rootScope.appVersion = APP_CONFIG.appVersion ;
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
        $rootScope.changeLanguage = function (langKey) {
            $translate.use(langKey);
        };
        $rootScope.logout = function(){
        	sessionManager.logout();
        };
        $rootScope.workspaces = function(){
        	sessionManager.wsout();
        };
        $rootScope.logins = function(){
        	return loginService.logins;
        };
    }]
);
