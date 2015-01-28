/**
 * @author Francis Pouatcha
 * @name navbarCtrl
 */

'use strict';
angular.module('SessionManager');
angular.module('AuthInterceptor');
angular.module('pascalprecht.translate');
angular.module('NavBar',[
     'SessionManager',
     'AuthInterceptor',
     'pascalprecht.translate'
])

.controller('navbarCtrl', 
	['$scope', '$translate','$translatePartialLoader', '$rootScope','sessionManager', 
		 function ($scope, $translate, $translatePartialLoader, $rootScope,sessionManager) {
			$translatePartialLoader.addPart('/adres.client/i18n/shared');
			$translate.refresh();

			$rootScope.$on('$translateChangeSuccess', function () {
				 $translate(['workspace_ouWsIdentif', 'workspace_user', 'workspace_appMenu','workspace_logOut'])
				 .then(function (translations) {
					 $scope.workspace_ouWsIdentif = translations.workspace_ouWsIdentif;
					 $scope.workspace_user = translations.workspace_user;
					 $scope.workspace_appMenu = translations.workspace_appMenu;
					 $scope.workspace_logOut = translations.workspace_logOut;
			 	 });
			});
			
			$scope.userSession = sessionManager.userSession;
			$scope.loginName = sessionManager.loginName;
			$scope.workspaceLink =sessionManager.workspaceLink;
			$scope.workspaces = sessionManager.workspaces;
			$scope.logout=sessionManager.logout;
			$scope.changeLanguage = function (langKey) {
	            $translate.use(langKey);
	        };
	        $scope.appMenuUrl = sessionManager.appMenuUrl;
	        $scope.currentWs = sessionManager.userWsData();
	        $scope.hasWorkspace = function(){
	        	return sessionManager.isSet($scope.currentWs.roleIdentif);
	        }
		}
	]
);
