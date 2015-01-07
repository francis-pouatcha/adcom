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
			var sessionManager = $injector.get('sessionManager');
            config.headers['Authorization'] = 'Basic ' + sessionManager.encodedSession();
            
            return config || $q.when(config);
		},
		response: function(response) {
			return response;
		}
	};
}]);
