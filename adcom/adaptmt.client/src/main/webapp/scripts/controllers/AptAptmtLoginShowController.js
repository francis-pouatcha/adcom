'use strict';

angular.module("adaptmt")

.controller('aptAptmtLoginShowController',['$scope','genericResource', '$translate', 'aptAptmtLoginsService','aptAptmtsService','$location','$routeParams',
                                             function($scope,genericResource, $translate, aptAptmtLoginsService,aptAptmtsService,$location,$routeParams){

	var self = this;
	self.aptAptmtLogin = {};
	self.aptAptmts = {};
	self.show = show;
	self.previous = previous;
	self.next = next;

	function show(){

		var identif = $routeParams.id;

		aptAptmtLoginsService.loadAptAptmtLogin(identif).then(function(result){

			self.aptAptmtLogin = result;


		})

	};

	function init(){
		show();
		

	};

	init();

	function previous(){
		self.error = "";
		aptAptmtLoginsService.previousAptAptmtLogin(self.aptAptmtLogin.id).then(function(result){
			self.aptAptmtLogin = result;
		},function(error){
			self.error = error;
		})

	}

	function next(){
		self.error = "";
		aptAptmtLoginsService.nextAptAptmtLogin(self.aptAptmtLogin.id).then(function(result){
			self.aptAptmtLogin = result;
		},function(error){
			self.error = error;
		})

	}


}]);