(function () {
	'use strict';
	angular.module('adaptmt').controller('aptAptmtCloseController',aptAptmtCloseController);

	aptAptmtCloseController.$inject = ['$scope', 'aptAptmtRepportsService','$location','aptAptmtsService','aptAptmtRepportLoginsService','$routeParams','aptAptmtLoginsService','loginService','$filter'];

	function aptAptmtCloseController($scope,aptAptmtRepportsService, $location,aptAptmtsService,aptAptmtRepportLoginsService,$routeParams,aptAptmtLoginsService,loginService,$filter){

		var self = this ;
		self.AptAptmtRepport = {};
		self.close = close;
		self.aptAptmt = {};
		self.error = "";
		self.aptAptmt = {};
		self.aptAptmts = {};
		self.disTab1 = false;
		self.disTab2 = true;
		self.disTab3 = true;
		self.actTab1 = true;
		self.actTab2 = false;
		self.actTab3 = false;
		self.create = create;
		self.logins = [];
		self.totalItems ;
		self.itemPerPage=25;
		self.currentPage = 1;
		self.maxSize = 5 ;
		self.logins = [];
		self.loginsConfirm = [];
		self.searchEntity = {};
		self.selectedLogin = {} ;
		self.paginate = paginate;
		self.orgUnits = [];
		self.searchInputTwo = {
				entity:{},
				fieldNames:[],
				start:0,
				max:self.itemPerPage
		};
		self.searchInput = {
				entity:{},
				fieldNames:[],
				start:0,
				max:self.itemPerPage
		};
		self.addPresent = addPresent;
		self.pass = pass;



		function processsearchInputTwo(identif){
			var fileName = [];
			fileName.push(identif) ;

			self.searchInput.fieldNames = fileName ;
			return self.searchInput ;
		};

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

			self.disTab1 = true;
			self.disTab2 = false;
			self.actTab1 = false;
			self.actTab2 = true;
			self.aptAptmts.parentIdentify = self.aptAptmt.aptmtnNbr;
			console.log("je sort de la fonction");
		};

		function sendToServer(entity){
			aptAptmtReportLoginsService.create(entity)
			.then(function(result){
				console.log("entity : " + result + " has send successfully");
			},function(error){
				console.log(error);
			});
		}

		function create(){
			aptAptmtsService.create(self.aptAptmts)
			.then(function(result){

			},function(error){
				$scope.error = error;
			});

			pass();

		};

		function pass(){

			self.disTab2 = true;
			self.disTab3 = false;
			self.actTab2 = false;
			self.actTab3 = true;

		}

		function close(){
			if (self.aptAptmt.status != "FORTHCOMMING"){

				self.error = error;
			}
			else{
				if (self.AptAptmtRepport.aptmtIdentify == null){
					self.AptAptmtRepport.aptmtIdentify = self.aptAptmt.aptmtnNbr;
				}
				aptAptmtsService.updateAptAptmt(self.aptAptmt).then(function(result){

				},function(error){
					self.error = error;
				});
				aptAptmtRepportsService.create(self.AptAptmtRepport).then(function(result){

					$location.path('#/aptaptmt/show/'+ self.aptAptmt.id);

				},function(error){
					self.error = error;
				});

			}

		}

		function show(){

			var identif = $routeParams.id;

			aptAptmtsService.loadAptAptmt(identif).then(function(result){

				self.aptAptmt = result;
				self.searchInput.entity.aptaptmtIdentify = self.aptAptmt.aptmtnNbr;
				self.AptAptmtRepport.aptmtIdentify = self.aptAptmt.aptmtnNbr;

			});





			var fileName = [];

			fileName.push(self.aptAptmt.aptmtnNbr);

			self.searchInput.fieldNames = fileName ;

			aptAptmtLoginsService.findAptAptmtLogins(self.searchInput).then(function(entitySearchResult) {

				for(var i in entitySearchResult.resultList){
					if (entitySearchResult.resultList[i].aptmtIdentify == self.aptAptmt.aptmtnNbr){


						var loginName = entitySearchResult.resultList[i].loginIdentify;

						loginService.loadLogin(loginName).then(function(result) {

							self.logins.push(result);

						});

					}

				}


			});



		};

		function tab(){


			$scope.alertMe = function() {

			};
		}

		function init(){
			show(); 
			tab();
			self.searchInput = {
					entity:{},
					fieldNames:[],
					start:0,
					max:$scope.itemPerPage
			}

			aptAptmtsService.loadAptAptmts(self.searchInput).then(function(entitySearchResult) {
				self.aptAptmts = entitySearchResult.resultList;

			});

			self.AptAptmtRepport.title = "CLOTURE DU RENDEZ-VOUS";


		};

		init();




	};

	function paginate(){
		self.searchInput.start = ((self.currentPage - 1)  * self.itemPerPage) ;
		self.searchInput.max = self.itemPerPage ;
		findByLike(self.searchInput);
	};



})();