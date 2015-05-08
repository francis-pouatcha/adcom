(function () {
	'use strict';
	angular.module('adaptmt').controller('aptAptmtLoginAddController',aptAptmtLoginAddController);

	aptAptmtLoginAddController.$inject = ['$scope', 'loginService','orgUnitsService','$routeParams','aptAptmtsService','aptAptmtLoginsService','$filter'];

	function aptAptmtLoginAddController($scope,loginService,orgUnitsService,$routeParams,aptAptmtsService,aptAptmtLoginsService,$filter){
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
		self.loginsToRemove = [];
		self.searchEntity = {};
		self.selectedLogin = {} ;
		self.selectedIndex  ;
		self.handleSearchRequestEvent = handleSearchRequestEvent;
		self.handlePrintRequestEvent = handlePrintRequestEvent;
		self.add = add;;
		self.paginate = paginate;
		self.orgUnits = [];
		self.searchInput = {
				entity:{},
				fieldNames:[],
				start:0,
				max:self.itemPerPage
		};
		self.searchInput2 = {
				entity:{},
				fieldNames:[],
				start:0,
				max:self.itemPerPage
		};





		init();




		function add(){
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
						console.log('il nexiste pas dans loginConfirm');
					}

				}
				else{
					var found = $filter('filter')(self.loginsConfirm, {id: idIncome}, true);
					if (found.length) {
						console.log("remove"+ self.logins[i].id);
						self.loginsConfirm.splice(self.logins[i]);
						self.loginsToRemove.push(self.logins[i]);
					}

				}
			};

			console.log("logins to removed");
			console.log(self.loginsToRemove);

			console.log("logins to confirmed");
			console.log(self.loginsConfirm);

			for(var j in self.loginsConfirm){
				console.log("je suis dans la seconde boucle");
				var entity = {aptmtIdentify: $routeParams.id, loginIdentify: self.loginsConfirm[j].id};
				sendToServer(entity);
			}

			for(var k in self.loginsToRemove){
				console.log("je suis dans la seconde boucle");
				//		var entity = {aptmtIdentify: $routeParams.id, loginIdentify: self.loginsToRemove[k].id};
				aptAptmtLoginsService.findAptAptmtLogins(self.searchInput).then(function(entitySearchResult) {


					var data = entitySearchResult.resultList;
					console.log(data[0]);
					for(var i in data){
						console.log("je suis dans la boucle for : " + data[i]);
						if (data[i].loginIdentify == self.loginsToRemove[k].id){

							removeToServer(data[i].id);

						}
						else{
							console.log("la condition  if ne marche pas");
						}

					}



				});

				
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

		function removeToServer(id){
			aptAptmtLoginsService.deleteById(id)
			.then(function(result){
				console.log("entity : " + result + " has remove successfully");
			},function(error){
				console.log(error);
			});
		}


		function show(){
			var identif = $routeParams.id;
			aptAptmtsService.loadAptAptmt(identif).then(function(result){
				self.aptAptmt = result;
			});
			loginService.findLogins(self.searchInput).then(function(results) {
				self.logins.push(results);
				console.log(self.logins);
			});
		};

		function showAptAptmtLogins(){
			
		}
		
		function showLogins(){
			
		}

		function showAptLog(){
			var r = $.Deferred();
			console.log('-----------------FONCTION SHOWAPTLOG---------------------------');
			aptAptmtLoginsService.findAptAptmtLogins(self.searchInput).then(function(entitySearchResult) {
				var donne = entitySearchResult.resultList;
				for(var i in donne){
					console.log("je suis dans la boucle for ");
					console.log(donne[i]);
					if (donne[i].aptmtIdentify == $routeParams.id){
						
						    console.log(self.logins);
							for(var j in self.logins){
								console.log("je suis dans ");
								console.log(self.logins[j]);

								if (self.logins[j].id == donne[i].loginIdentify){
									console.log("Condition bonne");
									self.loginsConfirm.push(self.logins[j]);
									console.log(self.loginConfirm);
								}else{
									console.log("Condition mauvaise");
								}
							}
							console.log(self.loginsConfirm);
							
					
					}
					else{
						console.log("la condition  if ne marche pas");
					}

				}


				r.resolve();
			});




			console.log("-----------------------fin de showAptLog--------------------------------");
			return r;
		}

		function showChecked(){
			console.log('-----------------FONCTION SHOWCHECKED---------------------------');
			console.log(self.loginsConfirm);
			loginService.findLogins(self.searchInput).then(function(results) {
				self.logins.push(results);
				console.log(self.logins);
				for(var i in self.logins){
					var idIncome = self.logins[i].id;
					console.log(" login with id : " +  self.logins[i].id + " checked ? : " + self.logins[i].checkOn);

					var found = $filter('filter')(self.loginsConfirm, {id: idIncome}, true);

					if (found.length) {
						console.log(self.logins[i].id + "deja present a ce rdv");
						self.logins[i].checkOn = true;
						console.log(self.logins[i].checkOn);
						console.log("je coche");
					} 
					else{
						console.log(self.logins[i].id + "encore absent a ce rdv");

					}

				}
			});
		}
		// showAptLog();
		// setTimeout(showChecked, 2500);	
		setTimeout(showAptLog().done(showChecked),2900);

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












	};



})();