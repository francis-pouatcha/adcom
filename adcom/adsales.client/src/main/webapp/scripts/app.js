'use strict';

angular.module('AdSales', [
      'ngRoute',
      'ngCookies',
      'SessionManager',
      'AuthInterceptor',
      'ngSanitize',
      'pascalprecht.translate',
      'NavBar'
])
.constant('APP_CONFIG',{
	'appName':'Sales',
	'appVersion':'1.0.0-SNAPSHOT'

})

.config(['$routeProvider', '$httpProvider','$translateProvider','$translatePartialLoaderProvider',
         function($routeProvider,$httpProvider,$translateProvider,$translatePartialLoaderProvider) {
    $routeProvider
    .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
    .when('/DynEnums',{templateUrl:'views/DynEnum/search.html',controller:'SearchDynEnumController'})
    .when('/DynEnums/new',{templateUrl:'views/DynEnum/detail.html',controller:'NewDynEnumController'})
    .when('/DynEnums/edit/:DynEnumId',{templateUrl:'views/DynEnum/detail.html',controller:'EditDynEnumController'})
    .when('/HistoryTypes',{templateUrl:'views/HistoryType/search.html',controller:'SearchHistoryTypeController'})
    .when('/HistoryTypes/new',{templateUrl:'views/HistoryType/detail.html',controller:'NewHistoryTypeController'})
    .when('/HistoryTypes/edit/:HistoryTypeId',{templateUrl:'views/HistoryType/detail.html',controller:'EditHistoryTypeController'})
    .when('/ProcSteps',{templateUrl:'views/ProcStep/search.html',controller:'SearchProcStepController'})
    .when('/ProcSteps/new',{templateUrl:'views/ProcStep/detail.html',controller:'NewProcStepController'})
    .when('/ProcSteps/edit/:ProcStepId',{templateUrl:'views/ProcStep/detail.html',controller:'EditProcStepController'})
    .when('/SlsAccts',{templateUrl:'views/SlsAcct/search.html',controller:'SearchSlsAcctController'})
    .when('/SlsAccts/new',{templateUrl:'views/SlsAcct/detail.html',controller:'NewSlsAcctController'})
    .when('/SlsAccts/edit/:SlsAcctId',{templateUrl:'views/SlsAcct/detail.html',controller:'EditSlsAcctController'})
    .when('/SlsInvceHistorys',{templateUrl:'views/SlsInvceHistory/search.html',controller:'SearchSlsInvceHistoryController'})
    .when('/SlsInvceHistorys/new',{templateUrl:'views/SlsInvceHistory/detail.html',controller:'NewSlsInvceHistoryController'})
    .when('/SlsInvceHistorys/edit/:SlsInvceHistoryId',{templateUrl:'views/SlsInvceHistory/detail.html',controller:'EditSlsInvceHistoryController'})
    .when('/SlsInvceItems',{templateUrl:'views/SlsInvceItem/search.html',controller:'SearchSlsInvceItemController'})
    .when('/SlsInvceItems/new',{templateUrl:'views/SlsInvceItem/detail.html',controller:'NewSlsInvceItemController'})
    .when('/SlsInvceItems/edit/:SlsInvceItemId',{templateUrl:'views/SlsInvceItem/detail.html',controller:'EditSlsInvceItemController'})
    .when('/SlsInvcePtnrs',{templateUrl:'views/SlsInvcePtnr/search.html',controller:'SearchSlsInvcePtnrController'})
    .when('/SlsInvcePtnrs/new',{templateUrl:'views/SlsInvcePtnr/detail.html',controller:'NewSlsInvcePtnrController'})
    .when('/SlsInvcePtnrs/edit/:SlsInvcePtnrId',{templateUrl:'views/SlsInvcePtnr/detail.html',controller:'EditSlsInvcePtnrController'})
    .when('/SlsInvceStatuss',{templateUrl:'views/SlsInvceStatus/search.html',controller:'SearchSlsInvceStatusController'})
    .when('/SlsInvceStatuss/new',{templateUrl:'views/SlsInvceStatus/detail.html',controller:'NewSlsInvceStatusController'})
    .when('/SlsInvceStatuss/edit/:SlsInvceStatusId',{templateUrl:'views/SlsInvceStatus/detail.html',controller:'EditSlsInvceStatusController'})
    .when('/SlsInvoices',{templateUrl:'views/SlsInvoice/search.html',controller:'SearchSlsInvoiceController'})
    .when('/SlsInvoices/new',{templateUrl:'views/SlsInvoice/detail.html',controller:'NewSlsInvoiceController'})
    .when('/SlsInvoices/edit/:SlsInvoiceId',{templateUrl:'views/SlsInvoice/detail.html',controller:'EditSlsInvoiceController'})
    .when('/SlsInvoiceTypes',{templateUrl:'views/SlsInvoiceType/search.html',controller:'SearchSlsInvoiceTypeController'})
    .when('/SlsInvoiceTypes/new',{templateUrl:'views/SlsInvoiceType/detail.html',controller:'NewSlsInvoiceTypeController'})
    .when('/SlsInvoiceTypes/edit/:SlsInvoiceTypeId',{templateUrl:'views/SlsInvoiceType/detail.html',controller:'EditSlsInvoiceTypeController'})
    .when('/SlsRoleInSaless',{templateUrl:'views/SlsRoleInSales/search.html',controller:'SearchSlsRoleInSalesController'})
    .when('/SlsRoleInSaless/new',{templateUrl:'views/SlsRoleInSales/detail.html',controller:'NewSlsRoleInSalesController'})
    .when('/SlsRoleInSaless/edit/:SlsRoleInSalesId',{templateUrl:'views/SlsRoleInSales/detail.html',controller:'EditSlsRoleInSalesController'})
    .when('/SlsSOHstrys',{templateUrl:'views/SlsSOHstry/search.html',controller:'SearchSlsSOHstryController'})
    .when('/SlsSOHstrys/new',{templateUrl:'views/SlsSOHstry/detail.html',controller:'NewSlsSOHstryController'})
    .when('/SlsSOHstrys/edit/:SlsSOHstryId',{templateUrl:'views/SlsSOHstry/detail.html',controller:'EditSlsSOHstryController'})
    .when('/SlsSOItems',{templateUrl:'views/SlsSOItem/search.html',controller:'SearchSlsSOItemController'})
    .when('/SlsSOItems/new',{templateUrl:'views/SlsSOItem/detail.html',controller:'NewSlsSOItemController'})
    .when('/SlsSOItems/edit/:SlsSOItemId',{templateUrl:'views/SlsSOItem/detail.html',controller:'EditSlsSOItemController'})
    .when('/SlsSOPtnrs',{templateUrl:'views/SlsSOPtnr/search.html',controller:'SearchSlsSOPtnrController'})
    .when('/SlsSOPtnrs/new',{templateUrl:'views/SlsSOPtnr/detail.html',controller:'NewSlsSOPtnrController'})
    .when('/SlsSOPtnrs/edit/:SlsSOPtnrId',{templateUrl:'views/SlsSOPtnr/detail.html',controller:'EditSlsSOPtnrController'})
    .when('/SlsSalesOrders',{templateUrl:'views/SlsSalesOrder/search.html',controller:'SearchSlsSalesOrderController'})
    .when('/SlsSalesOrders/new',{templateUrl:'views/SlsSalesOrder/detail.html',controller:'NewSlsSalesOrderController'})
    .when('/SlsSalesOrders/edit/:SlsSalesOrderId',{templateUrl:'views/SlsSalesOrder/detail.html',controller:'EditSlsSalesOrderController'})
    .when('/SlsSalesStatuss',{templateUrl:'views/SlsSalesStatus/search.html',controller:'SearchSlsSalesStatusController'})
    .when('/SlsSalesStatuss/new',{templateUrl:'views/SlsSalesStatus/detail.html',controller:'NewSlsSalesStatusController'})
    .when('/SlsSalesStatuss/edit/:SlsSalesStatusId',{templateUrl:'views/SlsSalesStatus/detail.html',controller:'EditSlsSalesStatusController'})
    .when('/SlsSttlmtOps',{templateUrl:'views/SlsSttlmtOp/search.html',controller:'SearchSlsSttlmtOpController'})
    .when('/SlsSttlmtOps/new',{templateUrl:'views/SlsSttlmtOp/detail.html',controller:'NewSlsSttlmtOpController'})
    .when('/SlsSttlmtOps/edit/:SlsSttlmtOpId',{templateUrl:'views/SlsSttlmtOp/detail.html',controller:'EditSlsSttlmtOpController'})
    .otherwise({
      redirectTo: '/'
    });
    
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('authInterceptor');
    
    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: '{part}/locale-{lang}.json'
    });

	
    
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
    sessionManager.appMenuUrl("/adprocmt.client/menu.html");
    $translatePartialLoader.addPart('/adprocmt.client/i18n/main');
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
