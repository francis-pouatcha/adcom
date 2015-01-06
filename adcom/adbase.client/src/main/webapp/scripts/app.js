'use strict';

// declare modules
angular.module('SessionData');
angular.module('AuthInterceptor');
angular.module('ADUtils');

angular.module('AdBase', [
    'ngRoute',
    'ngCookies',
    'SessionData',
    'AuthInterceptor',
    'ngSanitize',
    'pascalprecht.translate',
    'ADUtils'
])
.constant('APP_CONFIG',{
	'appName':'AD Login',
	'appVersion':'1.0.0-SNAPSHOT'

})
.config(['$routeProvider', '$httpProvider','$translateProvider', function ($routeProvider, $httpProvider,$translateProvider) {

    $routeProvider
    .when('/',{templateUrl:'views/logins.html',controller:'LoginController'})
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

.run(['$rootScope', '$location', '$cookieStore', 'loginService', 'sessionData','$translate','APP_CONFIG','adUtils',
    function ($rootScope, $location, $cookieStore, loginService, sessionData,$translate,APP_CONFIG,adUtils) {
	    $rootScope.appName = APP_CONFIG.appName ;
	    $rootScope.appVersion = APP_CONFIG.appVersion ;
	    $rootScope.sessionData = sessionData;
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
        	// redirect to login page if not logged in
        	var tpe = typeof sessionData.usr;
        	var noSess = tpe=='undefined' || (tpe=='object' && sessionData.usr==null);
            if (noSess) {
                // wsin
            	sessionData.opr='wsin';
            	// read cookie store and set data
            	sessionData.trm=$cookieStore.get(trm);
            	sessionData.usr=$cookieStore.get(usr);
            	loginService.loadLogins();
            	$cookieStore.remove('trm');
            	$cookieStore.remove('usr');
            }
        });
        $rootScope.changeLanguage = function (langKey) {
            $translate.use(langKey);
        };
        $rootScope.logout = function(){
        	adUtils.logout();
        };
    }]
);
