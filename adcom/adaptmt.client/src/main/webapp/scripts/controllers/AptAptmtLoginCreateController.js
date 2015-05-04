(function () {
	'use strict';
	angular.module('adaptmt').controller('aptAptmtLoginCreateController',aptAptmtLoginCreateController);

	aptAptmtLoginCreateController.$inject = ['$scope', 'aptAptmtLoginsService','$location','aptAptmtsService','$routeParams','genericResource'];

	function aptAptmtLoginCreateController($scope,aptAptmtLoginsService, $location,aptAptmtsService,$routeParams,genericResource){
		var self = this ;
		self.AptAptmtLogin = {};
		self.create = create;
		self.error = "";
		self.aptAptmt = {};
		self.aptAptmts = {};
		self.login = {};
		

		function show(){

			var identif = $routeParams.id;

			aptAptmtsService.loadAptAptmt(identif).then(function(result){

				self.aptAptmt = result;

			})

		};

		function init(){
			show();   
			self.searchInput = {
					entity:{},
					fieldNames:[],
					start:0,
					max:$scope.itemPerPage
			}

			aptAptmtsService.loadAptAptmts(self.searchInput).then(function(entitySearchResult) {
				self.aptAptmts = entitySearchResult.resultList;
				
			});

		};

		init();
		
		
		function loadUsers(val){
			self.login = aptAptmtLoginsService.loadUsers(val);
	    };
		
		
		function create(){
			if (self.AptAptmtLogin.aptmtIdentify == null){
			self.AptAptmtLogin.aptmtIdentify = self.aptAptmt.aptmtnNbr;
		}
		aptAptmtLoginsService.create(self.AptAptmtLogin).then(function(result){
			$location.path('/aptaptmtLogin/show/'+result.id);
		},function(error){
			self.error = error;
		});

	};


};


})();

