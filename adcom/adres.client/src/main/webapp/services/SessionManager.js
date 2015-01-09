/**
 * @author Francis Pouatcha
 * @name sessionManager
 */

'use strict';

angular.module('Base64Factory');
angular.module('ADUtils');
angular.module('SessionManager',['Base64Factory','ADUtils'])
.factory('sessionManager',['Base64','adUtils','$http',function(Base64,adUtils,$http){
    var auth = {};
    var authErrorList = [];
    var sess = {
        	opr:'',
            lgn:'',
            wrk:'',
            pwd:'',
            trm:'',
            usr:''
    };
    var appMenuTemplUrl;
    var workspaceLink = "#";
    var workspaceFct;

    
    auth.isSet = function(value){
    	return !(typeof (value) === 'undefined') && value;
    };
    
    auth.hasValues = function(trm,usr){
    	return auth.isSet(trm) && auth.isSet(usr); 
    };
    
    auth.login = function(username, password, successCallback, errorCallback){
    	sess.opr='login';
    	sess.lgn=username;
    	sess.pwd=password;
        $http.get('/adbase.server/rest/session/login')
        .success(function(data, status, headers, config) {
        	sess.opr='req';
    		consumeSessData(headers);
        	successCallback(data, status, headers, config);
		})
        .error(function(data, status, headers, config) {
			errorCallback(data, status, headers, config);
		});
    };
    
    auth.logout = function(){
    	sess.opr='logout';
        $http.get('/adbase.server/rest/session/logout')
        .success(function(data, status, headers, config){
            clearCredentials();
    		adUtils.loadApp('/adlogin.client/#/login');
        })
        .error(function(data, status, headers, config){});
    };
            
    auth.wsin = function(trm,usr,successCallback){
        // operation
    	sess.opr='wsin';
    	// credentials as ws params.
    	sess.trm=trm;
    	sess.usr=usr;
        $http.get('/adbase.server/rest/session/wsin')
        .success(function(data, status, headers, config){
    		sess.opr='req';
    		consumeSessData(headers);
    		adUtils.removeSearchOnUrl();
			successCallback(data, status, headers, config);
		}).error(function(data, status, headers, config){
			clearCredentials();
			adUtils.loadApp('/adlogin.client/#/login');
		});
    };
    
    auth.wsout = function(identif){
    	sess.opr='wsout';
    	sess.wrk=identif;
        $http.post('/adbase.server/rest/session/wsout',{"content":identif})
		.success(function(data, status, headers, config){
			consumeSessData(headers);
			var link = data.content+'/#/?trm='+sess.trm+'&usr='+sess.usr;
			clearCredentials();
			adUtils.loadApp(link);
		}).error(function(data, status, headers, config){
			clearCredentials();
			adUtils.loadApp('/adlogin.client/#/login');
		});
    };
    
    auth.encodedSession = function(){
		if(!auth.isSet(sess.opr))sess.opr='req';
    	var auth_json = JSON.stringify(sess);	
        return Base64.encode(auth_json);
    };
    
    auth.operation = function(p){
    	if(typeof (value) === 'undefined'){
    		return sess.opr;
    	} else {
    		sess.opr=value;
    		return auth;
    	}
    };
    auth.loginName = function(value){
    	if(typeof (value) === 'undefined'){
    		return sess.lgn;
    	} else {
    		sess.lgn=value;
    		return auth;
    	}
    };
    auth.workspace = function(p){
    	if(typeof (value) === 'undefined'){
    		return sess.wrk;
    	} else {
    		sess.wrk=value;
    		return auth;
    	}
    };
    auth.password = function(p){
    	if(typeof (value) === 'undefined'){
    		return sess.pwd;
    	} else {
    		sess.pwd=value;
    		return auth;
    	}
    };
    auth.terminalSession = function(p){
    	if(typeof (value) === 'undefined'){
    		return sess.trm;
    	} else {
    		sess.trm=value;
    		return auth;
    	}
    };
    auth.userSession = function(p){
    	if(typeof (value) === 'undefined'){
    		return sess.usr;
    	} else {
    		sess.usr=value;
    		return auth;
    	}
    };
    
    auth.authErrors = function(){
    	return authErrorList;
    };
    auth.pushAuthError = function(error){
    	if(typeof (error) !== 'undefined' && error){
    		authErrorList.push(error);
    	}
    	return auth;
    };
    auth.clearAuthErrors = function(){
		while(authErrorList.length > 0) {
			authErrorList.pop();
		}
    	return auth;
    };
    auth.hasAuthErrors = function(){
		return authErrorList.length > 0;
	};

	auth.appMenuUrl = function(value){
    	if(auth.isSet(value)){
    		appMenuTemplUrl = value;
    	}
    	return appMenuTemplUrl;
    }
    auth.workspaceLink = function(value){
    	if(auth.isSet(value)){
    		workspaceLink = value;
    	}
    	return workspaceLink;
    }
    auth.workspaces = function(fkt){
    	if(auth.isSet(fkt)){
    		workspaceFct = fkt;
    	} else {
        	if(auth.isSet(workspaceFct)){
        		workspaceFct();
        	} else {
        		auth.wsout('_login_');
        	}
    	}
    }

    return auth;

    function consumeSessData(headers){
    	sess.usr=headers("X-USER-SESSION");
    	sess.trm=headers("X-TERM-SESSION");
    	sess.lgn=headers("X-USER-LOGIN");
    };

    function clearCredentials () {
    	sess.opr='';
    	sess.lgn='';
    	sess.wrk='';
    	sess.pwd='';
    	sess.trm='';
    	sess.usr='';
    };
    

}]);
