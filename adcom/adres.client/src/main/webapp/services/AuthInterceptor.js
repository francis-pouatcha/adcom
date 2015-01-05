/**
 * @author Francis Pouatcha
 * @name AuthInterceptor
 */

'use strict';
angular.module('SessionData');
angular.module('Base64Factory');
angular.module('AuthInterceptor',['SessionData','Base64Factory'])
.factory('authInterceptor',['$q', 'Base64','sessionData', function($q, Base64, sessionData){
	return {
		request: function(config){
			if(!sessionData.opr)sessionData.opr='req';
        	var auth_json = JSON.stringify(sessionData);	
            var authdata = Base64.encode(auth_json);
            config.headers['Authorization'] = 'Basic ' + authdata;
            
            return config || $q.when(config);
		},
		response: function(response) {
			return response;
		}
	};
}]);
