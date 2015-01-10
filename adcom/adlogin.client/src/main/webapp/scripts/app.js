'use strict';

// declare modules
//angular.module('SessionManager');
//angular.module('AuthInterceptor');
//angular.module('NavBar');

angular.module('AdLogin', [
    'ngRoute',
    'ngCookies',
    'SessionManager',
    'AuthInterceptor',
    'ngSanitize',
    'pascalprecht.translate',
    'NavBar'
])
.constant('APP_CONFIG',{
	'appName':'AD Login',
	'appVersion':'1.0.0-SNAPSHOT'

})
.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function ($routeProvider, $httpProvider,$translateProvider,$translatePartialLoaderProvider) {

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
    
    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: '{part}/locale-{lang}.json'
    });

	$translateProvider.preferredLanguage('fr');	
}])

.run(['$rootScope', '$location','sessionManager','$translate','APP_CONFIG','workspaceService','$translatePartialLoader',
    function ($rootScope, $location, sessionManager,$translate,APP_CONFIG,workspaceService,$translatePartialLoader) {
	    $rootScope.appName = APP_CONFIG.appName ;
	    $rootScope.appVersion = APP_CONFIG.appVersion ;
	    $rootScope.sessionManager = sessionManager;
	    
	    sessionManager.workspaceLink("#/workspaces");// Special handling for the login application.
	    sessionManager.workspaces(function(){
        	if($location.path()!='/workspaces'){
        		$location.path('/workspaces');
        	} else {
        		workspaceService.loadWorkspaces(function(data, status, headers, config){}, function(data, status, headers, config){});
        	}
    	});
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

    	$translatePartialLoader.addPart('/adlogin.client/i18n/main');
    	$translate.refresh();
    	
    }]
);
