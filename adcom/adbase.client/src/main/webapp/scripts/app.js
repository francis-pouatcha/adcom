'use strict';

// declare modules
//angular.module('SessionManager');
//angular.module('AuthInterceptor');

angular.module('AdBase', [
    'ngRoute',
    'ngCookies',
    'ui.bootstrap',
    'SessionManager',
    'AuthInterceptor',
    'ngSanitize',
    'pascalprecht.translate',
    'NavBar',
    'datePicker'
])
.constant('APP_CONFIG',{
	'appName':'Administration',
	'appVersion':'1.0.0-SNAPSHOT'

})
.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function ($routeProvider, $httpProvider,$translateProvider,$translatePartialLoaderProvider) {

    $routeProvider
    .when('/outypes/new',{templateUrl:'views/outypes/new.html',controller:'newOuTypeController'})
    .when('/outypes/show/:ouTypeId',{templateUrl:'views/outypes/show.html',controller:'showOuTypeController'})
    .when('/outypes/update/:ouTypeId',{templateUrl:'views/outypes/update.html',controller:'updateOuTypeController'})
    .when('/outypes/list',{templateUrl:'views/outypes/list.html',controller:'listOuTypeController'})
    .when('/',{
            templateUrl:'views/login/logins.html',
            controller:'loginController',
            controllerAs: 'loginCtrl',
            module: 'AdBase'
        })
    .when('/login/create',{
            templateUrl:'views/login/create.html',
            controller:'loginCreateController',
            controllerAs: 'loginCreateCtrl',
            module: 'AdBase'
    })
    .when('/login/show/:identif',{
            templateUrl:'views/login/show.html',
            controller:'loginShowController',
            controllerAs: 'loginShowCtrl',
            module: 'AdBase'
    })
    .when('/login/edit/:identif',{
            templateUrl:'views/login/edit.html',
            controller:'loginEditController',
            controllerAs: 'loginEditCtrl',
            module: 'AdBase'
    })
        .when('/login/changepass/:identif',{
            templateUrl:'views/login/change-pwd.html',
            controller:'loginChangePwdController',
            controllerAs: 'changePwdCtrl',
            module: 'AdBase'
        })
    .when('/orgunits',{
            templateUrl:'views/orgunits/orgunits.html',
            controller:'orgUnitController',
            controllerAs: 'orgUnitCtrl',
            module: 'AdBase'
    })
    .when('/orgunits/create',{
            templateUrl:'views/orgunits/create.html',
            controller:'orgUnitCreateController',
            controllerAs: 'orgUnitCreateCtrl',
            module: 'AdBase'
    })
    .when('/orgunits/show/:identif',{
            templateUrl:'views/orgunits/show.html',
            controller:'orgUnitShowController',
            controllerAs: 'orgUnitShowCtrl',
            module: 'AdBase'
    })
    .when('/orgunits/edit/:identif',{
            templateUrl:'views/orgunits/edit.html',
            controller:'orgUnitEditController',
            controllerAs: 'orgUnitEditCtlr',
            module: 'AdBase'
    })
        .when('/secTerm/create',{
            templateUrl:'views/terminal/create.html',
            controller:'secTermCreateController',
            controllerAs: 'secTermCreateCtrl',
            module: 'AdBase'
        })
        .when('/secTerm/show/:identif',{
            templateUrl:'views/terminal/show.html',
            controller:'secTermShowController',
            controllerAs: 'secTermShowCtrl',
            module: 'AdBase'
        })
        .when('/secTerm/edit/:identif',{
            templateUrl:'views/terminal/edit.html',
            controller:'secTermEditController',
            controllerAs: 'secTermEditCtrl',
            module: 'AdBase'
        })
        .when('/secTerm',{
            templateUrl:'views/terminal/list.html',
            controller:'secTerminalController',
            controllerAs: 'secTermCtrl',
            module: 'AdBase'
        })

        .when('/locality/create',{
            templateUrl:'views/locality/create.html',
            controller:'localityCreateController',
            controllerAs: 'localityCreateCtrl',
            module: 'AdBase'
        })
        .when('/locality/show/:identif',{
            templateUrl:'views/locality/show.html',
            controller:'localityShowController',
            controllerAs: 'localityShowCtrl',
            module: 'AdBase'
        })
        .when('/locality/edit/:identif',{
            templateUrl:'views/locality/edit.html',
            controller:'localityEditController',
            controllerAs: 'localityEditCtrl',
            module: 'AdBase'
        })
        .when('/locality',{
            templateUrl:'views/locality/list.html',
            controller:'localityController',
            controllerAs: 'localityCtrl',
            module: 'AdBase'
        })

    .when('/countrys',{
            templateUrl:'views/countrys/countrys.html',
            controller:'countrysController',
            controllerAs: 'countrysCtrl',
            module: 'AdBase'
    })
    .when('/countrys/create',{
            templateUrl:'views/countrys/createCountry.html',
            controller:'countrysCreateController',
            controllerAs: 'ctryCreateCtrl',
            module: 'AdBase'
    })
    .when('/countrys/show/:identif',{
            templateUrl:'views/countrys/showCountry.html',
            controller:'countrysShowController',
            controllerAs: 'ctryShowCtrl',
            module: 'AdBase'
    })
    .when('/countrys/edit/:identif',{
            templateUrl:'views/countrys/editCountry.html',
            controller:'countrysEditController',
            controllerAs: 'ctryEditCtrl',
            module: 'AdBase'
    })

        .when('/converterCurrRate',{
            templateUrl:'views/converterCurrRate/list.html',
            controller:'converterCurrRateController',
            controllerAs: 'converterCurrRateCtrl',
            module: 'AdBase'
        })
        .when('/converterCurrRate/create',{
            templateUrl:'views/converterCurrRate/create.html',
            controller:'converterCurrRateCreateController',
            controllerAs: 'converterCurrRateCreateCtrl',
            module: 'AdBase'
        })
        .when('/converterCurrRate/show/:identif',{
            templateUrl:'views/converterCurrRate/show.html',
            controller:'converterCurrRateShowController',
            controllerAs: 'converterCurrRateShowCtrl',
            module: 'AdBase'
        })
        .when('/converterCurrRate/edit/:identif',{
            templateUrl:'views/converterCurrRate/edit.html',
            controller:'converterCurrRateEditController',
            controllerAs: 'converterCurrRateEditCtrl',
            module: 'AdBase'
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
