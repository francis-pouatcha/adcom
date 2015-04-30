(function () {
	'use strict';
	angular.module('adaptmt').controller('aptAptmtLoginAddController',aptAptmtLoginAddController);

	aptAptmtLoginAddController.$inject = ['$scope', 'loginService','orgUnitsService','$routeParams','aptAptmtsService','aptAptmtLoginsService'];

	function aptAptmtLoginAddController($scope,loginService,orgUnitsService,$routeParams,aptAptmtsService,aptAptmtLoginsService){
		var self = this ;
		self.AptAptmtLogin = {};
		self.AptAptmtLogins = [];
		self.aptAptmt = {} ;
		self.searchInput = {};
		self.totalItems ;
		self.itemPerPage=25;
		self.currentPage = 1;
		self.maxSize = 5 ;
		self.logins = [];
		self.loginsConfirm = [];
		self.searchEntity = {};
		self.selectedLogin = {} ;
		self.selectedIndex  ;
		self.handleSearchRequestEvent = handleSearchRequestEvent;
		self.handlePrintRequestEvent = handlePrintRequestEvent;
		self.add = add;
		self.remove = remove;
		self.confirm = confirm;
		self.paginate = paginate;
		self.orgUnits = [];
		self.searchInput2 = {
				entity:{},
				fieldNames:[],
				start:0,
				max:self.itemPerPage
		};



		init();
		
		function addPresent(){
			console.log("je suis dans la fonction");

			for(var i in self.logins){
				console.log("je suis dans la premiere boucle");
				console.log(" login with id : " +  self.logins[i].id + " checked ? : " + self.logins[i].checkOn);
				var idIncome = self.logins[i].id;
				if(self.logins[i].checkOn == true){

					var found = $filter('filter')(self.loginsConfirm, {id: idIncome}, true);
					if (found.length) {
						console.log("already exist in array to send to the server !");
					} else {
						self.loginsConfirm.push(self.logins[i]);
					}

				}
			};

			for(var j in self.loginsConfirm){
				console.log("je suis dans la seconde boucle");
				var entity = {aptmtIdentify: $routeParams.id, loginIdentify: self.loginsConfirm[j].id};
				sendToServer(entity);
			}

			console.log(self.loginsConfirm);
			console.log("je sort de la fonction");
		};

		function sendToServer(entity){
			aptAptmtLoginsService.create(entity)
			.then(function(result){
				console.log("entity : " + result + " has send successfully");
			},function(error){
				console.log(error);
			});
		}


		function show(){

			var identif = $routeParams.id;

			aptAptmtsService.loadAptAptmt(identif).then(function(result){

				self.aptAptmt = result;

			});


			aptAptmtLoginsService.findAptAptmtLogins(self.searchInput).then(function(entitySearchResult) {

				for(var i in entitySearchResult.resultList){
					


						var loginName = entitySearchResult.resultList[i].loginIdentify;

						loginService.loadLogin(loginName).then(function(result) {

							self.logins.push(result);

						});

					}

				


			});


		};




		function init(){
			self.searchInput = {
					entity:{},
					fieldNames:[],
					start:0,
					max:$scope.itemPerPage
			}

			show();

			findByLike(self.searchInput2);
			loadOrg();
		}

		function loadOrg(){
			orgUnitsService.findActifsFromNow().then(function(entitySearchResult){
				self.orgUnits = entitySearchResult;

			})
		}

		function findByLike(searchInput){
			loginService.findLogins(searchInput).then(function(entitySearchResult) {
				self.logins = entitySearchResult.resultList;
				self.totalItems = entitySearchResult.count ;
			});
		}

		function processSearchInput(){
			var fileName = [];
			if(self.searchInput.entity.loginName){
				fileName.push('loginName') ;
			}
			if(self.searchInput.entity.fullName){
				fileName.push('fullName') ;
			}
			if(self.searchInput.entity.ouIdentif){
				fileName.push('ouIdentif') ;
			}
			self.searchInput.fieldNames = fileName ;
			return self.searchInput ;
		};

		function  handleSearchRequestEvent(){
			processSearchInput();
			findByLike(self.searchInput);
		};

		function paginate(){
			self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
			self.searchInput.max = self.itemPerPage ;
			findByLike(self.searchInput);
		};


		function handlePrintRequestEvent(){

		}



		


		function add(index,login) {
			/* $scope.items.splice(index, 1); */
			self.AptAptmtLogin.aptmtIdentify = self.aptAptmt.aptmtnNbr;
			self.AptAptmtLogin.loginIdentify = login.loginName;

			aptAptmtLoginsService.create(self.AptAptmtLogin).then(function(result){

			},function(error){
				self.error = error;
			});
			var addOrNo = 1;
			for (var i = 0, len = self.AptAptmtLogins.length; i < len; i++) {
				if(self.AptAptmtLogins[i] == login){
					addOrNo = 0;
					break
				}

			}

			if(addOrNo==1)
				self.AptAptmtLogins.push(login);
			else
				alert("already add");


		};

		function remove(index) {

			aptAptmtLoginsService.deleteById(self.AptAptmtLogins.id).then(function(result){

			},function(error){
				self.error = error;
			});

			self.AptAptmtLogins.splice(index, 1);
		}



	};



})();