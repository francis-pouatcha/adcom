/**
 * @author Francis Pouatcha
 * @name AuthInterceptor
 */

'use strict';
angular.module('SessionManager');
angular.module('AuthInterceptor',['SessionManager'])
.factory('authInterceptor',['$q','$injector', function($q,$injector){
	return {
		request: function(config){
			var sessionManager1 = $injector.get('sessionManager');
            config.headers['Authorization'] = 'Basic ' + sessionManager1.encodedSession();
            sessionManager1.clearAuthErrors();
            return config || $q.when(config);
		},
		response: function(response) {
			var sessionManager3 = $injector.get('sessionManager');
            sessionManager3.clearAuthErrors();
			return response || $q.when(response);
		},
		responseError: function(rejection){
			var sessionManager2 = $injector.get('sessionManager');
			if(rejection.headers('X-AUTH-ERROR')){
				sessionManager2.pushAuthError(rejection.headers('X-AUTH-ERROR'));
			}
			return $q.reject(rejection);
		}
	};
}]);
