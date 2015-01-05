'use strict';

// declare modules
angular.module('SessionData');
angular.module('AuthInterceptor');
angular.module('ADUtils');

angular.module('AdLogin', [
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
        .when('/login', {
            templateUrl: '/adlogin.client/modules/login/views/login.html',
            controller: 'loginController'
        })

        .when('/workspaces', {
            templateUrl: '/adlogin.client/modules/workspace/views/workspace.html',
            controller: 'workspaceController'
        })
        
        .otherwise({ redirectTo: '/workspaces' });
    
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('authInterceptor');
    
	$translateProvider.useStaticFilesLoader({
		prefix: 'i18n/locale-',
		suffix: '.json'
	});

	$translateProvider.preferredLanguage('fr');
    
}])

.run(['$rootScope', '$location', '$cookieStore', '$http', 'sessionData','$translate','APP_CONFIG','adUtils',
    function ($rootScope, $location, $cookieStore, $http, sessionData,$translate,APP_CONFIG,adUtils) {
	    $rootScope.appName = APP_CONFIG.appName ;
	    $rootScope.appVersion = APP_CONFIG.appVersion ;
	    $rootScope.sessionData = sessionData;
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
        	var tpe = typeof sessionData.usr;
        	var sess = tpe=='undefined' || (tpe=='object' && sessionData.usr==null);
        	var loc = $location.path() !== '/login';
            if (loc && sess) {
                $location.path('/login');
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
