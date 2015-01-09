'use strict';

// declare modules
angular.module('SessionManager');
angular.module('AuthInterceptor');

angular.module('AdLogin', [
    'ngRoute',
    'ngCookies',
    'SessionManager',
    'AuthInterceptor',
    'ngSanitize',
    'pascalprecht.translate'
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

        .when('/', {
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

.run(['$rootScope', '$location', '$cookieStore', '$http', 'sessionManager','$translate','APP_CONFIG','workspaceService',
    function ($rootScope, $location, $cookieStore, $http, sessionManager,$translate,APP_CONFIG,workspaceService) {
	    $rootScope.appName = APP_CONFIG.appName ;
	    $rootScope.appVersion = APP_CONFIG.appVersion ;
	    sessionManager.workspaceLink("#/workspaces");// Special handling for the login application.
	    sessionManager.workspaces(function(){
        	if($location.path()!='/workspaces'){
        		$location.path('/workspaces');
        	} else {
        		workspaceService.loadWorkspaces(function(data, status, headers, config){}, function(data, status, headers, config){});
        	}
    	});
	    $rootScope.sessionManager = sessionManager;
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
        	var path = $location.path();
        	var noLoginPath = path != '/login';
        	var noSess = !sessionManager.hasValues(sessionManager.terminalSession(), sessionManager.userSession());
        	if(noLoginPath && noSess){
        		if(path=='/workspaces' || path=='/' || path==''){
        			var sessParam = $location.search();
        			if(sessParam && sessionManager.hasValues(sessParam.trm,sessParam.usr)){
        				sessionManager.wsin(sessParam.trm,sessParam.usr,function(){});
        			}
        		}	
        	}
        	noSess = !sessionManager.hasValues(sessionManager.terminalSession(), sessionManager.userSession());
        	if(noLoginPath && noSess){
				$location.path('/login');
        	}
        });
        $rootScope.changeLanguage = function (langKey) {
            $translate.use(langKey);
        };

        $rootScope.loadWorkspaces = function(){
        	if($location.path()!='/workspaces'){
        		$location.path('/workspaces');
        	} else {
        		workspaceService.loadWorkspaces(function(data, status, headers, config){}, function(data, status, headers, config){});
        	}
    	};
    }]
);
