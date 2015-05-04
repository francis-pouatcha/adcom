'use strict';

angular
		.module(
				'adaptmt',
				[ 'ngRoute', 'ngCookies', 'ui.bootstrap', 'SessionManager',
						'AuthInterceptor', 'ngSanitize',
						'pascalprecht.translate', 'NavBar', 'ngResource',
						'datePicker', 'ADUtils', 'httpProgress', 'AdBnsptnr', 'AdBase' ])

		.constant('APP_CONFIG', {
			'appName' : 'Appointment',
			'appVersion' : '1.0.0-SNAPSHOT'

		})

		.config(
				[
						'$routeProvider',
						'$httpProvider',
						'$translateProvider',
						'$translatePartialLoaderProvider',
						function($routeProvider, $httpProvider,
								$translateProvider,
								$translatePartialLoaderProvider) {
							$routeProvider
									.when(
											'/',
											{
												templateUrl : 'views/aptaptmt/aptaptmts.html',
												controller : 'aptAptmtsController',
												controllerAs : 'aptAptmtsCtrl',
												module : 'adaptmt'
											})
									.when(
											'/aptaptmt',
											{
												templateUrl : 'views/aptaptmt/search.html',
												controller : 'SearchAptAptmtController'
											})
									.when(
											'/aptaptmt/create',
											{
												templateUrl : 'views/aptaptmt/create.html',
												controller : 'aptAptmtCreateCtlr',
												controllerAs : 'aptAptmtCreateCtlr',
												module : 'adaptmt'
											})
									.when(
											'/aptaptmt/show/:id',
											{
												templateUrl : 'views/aptaptmt/show.html',
												controller : 'aptAptmtShowController',
												controllerAs : 'aptAptmtShowCtlr',
												module : 'adaptmt'
											})
									.when(
											'/aptaptmt/update/:id',
											{
												templateUrl : 'views/aptaptmt/update.html',
												controller : 'aptAptmtUpdateController',
												controllerAs : 'aptAptmtUpdateCtlr',
												module : 'adaptmt'
											})
									.when(
											'/aptaptmtRepport/create',
											{
												templateUrl : 'views/aptaptmtRepport/create.html',
												controller : 'aptAptmtRepportCreateController',
												controllerAs : 'aptAptmtRepportCreateCtrl'
											})

									.when(
											'/aptaptmtRepport/show/:id',
											{
												templateUrl : 'views/aptaptmtRepport/show.html',
												controller : 'aptAptmtRepportShowController',
												controllerAs : 'aptAptmtRepportShowCtlr'
											})

									.when(
											'/aptaptmtRepport/edit',
											{
												templateUrl : 'views/aptaptmtRepport/edit.html',
												controller : 'aptAptmtRepportEditController',
												controllerAs : 'aptAptmtRepportEditCtrl'
											})

									.when(
											'/aptaptmtRepport',
											{
												templateUrl : 'views/aptaptmtRepport/aptaptmtRepports.html',
												controller : 'aptAptmtRepportsController',
												controllerAs : 'aptAptmtRepportsCtrl'
											})

									.when(
											'/aptaptmtRepport/update/:id',
											{
												templateUrl : 'views/aptaptmtRepport/update.html',
												controller : 'aptAptmtRepportUpdateController',
												controllerAs : 'aptAptmtRepportUpdateCtlr',
												module : 'adaptmt'
											})
									.when(
											'/aptaptmtRepport/add/:id',
											{
												templateUrl : 'views/aptaptmtRepport/add.html',
												controller : 'aptAptmtRepportCreateController',
												controllerAs : 'aptAptmtRepportCreateCtrl',
												module : 'adaptmt'
											})
									.when(
											'/aptaptmtRepport/list/:id',
											{
												templateUrl : 'views/aptaptmtRepport/list.html',
												controller : 'aptAptmtRepportListController',
												controllerAs : 'aptAptmtRepportListCtrl',
												module : 'adaptmt'
											})
									.when(
											'/aptaptmt/cancel/:id',
											{
												templateUrl : 'views/aptaptmt/cancel.html',
												controller : 'aptAptmtCancelController',
												controllerAs : 'aptAptmtCancelCtrl',
												module : 'adaptmt'
											})
									.when(
											'/aptaptmt/report/:id',
											{
												templateUrl : 'views/aptaptmt/report.html',
												controller : 'aptAptmtReportController',
												controllerAs : 'aptAptmtReportCtrl',
												module : 'adaptmt'
											})
									.when(
											'/aptaptmt/close/:id',
											{
												templateUrl : 'views/aptaptmt/close.html',
												controller : 'aptAptmtCloseController',
												controllerAs : 'aptAptmtCloseCtrl',
												module : 'adaptmt'
											})
									.when(
											'/aptaptmtLogin/create',
											{
												templateUrl : 'views/aptaptmtLogin/create.html',
												controller : 'aptAptmtLoginCreateController',
												controllerAs : 'aptAptmtLoginCreateCtrl'
											})
									.when(
											'/aptaptmtLogin/show/:id',
											{
												templateUrl : 'views/aptaptmtLogin/show.html',
												controller : 'aptAptmtLoginShowController',
												controllerAs : 'aptAptmtLoginShowCtrl'
											})

									.when(
											'/aptaptmtLogin/edit',
											{
												templateUrl : 'views/aptaptmtLogin/edit.html',
												controller : 'aptAptmtRepportLoginController',
												controllerAs : 'aptAptmtRepportLoginCtrl'
											})

									.when(
											'/aptaptmtLogin',
											{
												templateUrl : 'views/aptaptmtLogin/aptaptmtLogins.html',
												controller : 'aptAptmtLoginsController',
												controllerAs : 'aptAptmtLoginsCtrl'
											})

									.when(
											'/aptaptmtLogin/update/:id',
											{
												templateUrl : 'views/aptaptmtLogin/update.html',
												controller : 'aptAptmtLoginUpdateController',
												controllerAs : 'aptAptmtLoginUpdateCtlr',
												module : 'adaptmt'
											})
									.when(
											'/aptaptmtLogin/add/:id',
											{
												templateUrl : 'views/aptaptmtLogin/add.html',
												controller : 'aptAptmtLoginAddController',
												controllerAs : 'aptAptmtLoginAddCtrl',
												module : 'adaptmt'
											})

									.otherwise({
										redirectTo : '/'
									});

							$httpProvider.defaults.withCredentials = true;
							$httpProvider.interceptors.push('authInterceptor');

							$translateProvider
									.useLoader(
											'$translatePartialLoader',
											{
												urlTemplate : '{part}/locale-{lang}.json'
											});

						} ])

		.controller('LandingPageController', function LandingPageController() {
		})

		.controller(
				'NavController',
				function NavController($scope, $location) {
					$scope.matchesRoute = function(route) {
						var path = $location.path();
						return (path === ("/" + route) || path.indexOf("/"
								+ route + "/") == 0);
					};
				})

		.run(
				[
						'$rootScope',
						'$location',
						'sessionManager',
						'$translate',
						'APP_CONFIG',
						'$translatePartialLoader',
						function($rootScope, $location, sessionManager,
								$translate, APP_CONFIG, $translatePartialLoader) {
							$rootScope.appName = APP_CONFIG.appName;
							$rootScope.appVersion = APP_CONFIG.appVersion;
							$translatePartialLoader
									.addPart('/adaptmt.client/i18n/main');
							sessionManager
									.appMenuUrl("/adaptmt.client/menu.html");
							$rootScope.sessionManager = sessionManager;
							$rootScope
									.$on(
											'$locationChangeStart',
											function(event, next, current) {
												var noSess = !sessionManager
														.hasValues(
																sessionManager
																		.terminalSession(),
																sessionManager
																		.userSession());
												if (noSess) {
													var sessParam = $location
															.search();
													if (sessParam
															&& sessionManager
																	.hasValues(
																			sessParam.trm,
																			sessParam.usr)) {
														sessionManager
																.wsin(
																		sessParam.trm,
																		sessParam.usr,
																		function(
																				data,
																				status,
																				headers,
																				config) {
																			sessionManager
																					.language(
																							headers('X-USER-LANG'),
																							false);
																			$location
																					.path('/');
																		});
													}
												}
											});

						} ]);
