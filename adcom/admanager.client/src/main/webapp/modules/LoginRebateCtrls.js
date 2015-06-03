'use strict';

angular.module('Admanager')
    .factory('LoginRebateUtils', ['sessionManager', '$translate', 'genericResource', '$q', '$http', function (sessionManager, $translate, genericResource, $q, $http) {
        var service = {};

        service.loginRebate = '/adbase.server/rest/loginrebates';
        service.login = '/adbase.server/rest/logins';
        service.language = sessionManager.language;

        service.create = function(entity){
            var deferred = $q.defer();
            genericResource.create(service.loginRebate, entity).success(function(data){
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject("Can not create, be sure that the loginRebate is correct !")
            });
            return deferred.promise;
        };
        
        service.update = function(entity){
            var deferred = $q.defer();
            genericResource.update(service.loginRebate, entity).success(function(data){
                deferred.resolve(data);
            }).error(function(){
                deferred.reject("Can not update loginRebate")
            });
            return deferred.promise;
        };
        
        service.loadLoginRebate = function(identif){
            var deferred = $q.defer();
             genericResource.findById(service.loginRebate, identif).success(function(data){
                 deferred.resolve(data);
             }).error(function(){
                 deferred.reject("Can not load loginRebate")
             });
             return deferred.promise;
         };
         
         service.loadLoginRebates = function(searchInput){
             var deferred = $q.defer();
              genericResource.findByLike(service.loginRebate, searchInput).success(function(data){
                  deferred.resolve(data);
              }).error(function(){
                  deferred.reject("Can not load loginRebates")
              });
              return deferred.promise;
          };
          
          service.loadLogin = function(value){
              return genericResource.findByLikePromissed(service.login, "loginName", value)
                  .then(function (entitySearchResult) {
                      return entitySearchResult.resultList;
                  })

          };

        service.nextLoginRebate = function(id){
            var deferred = $q.defer();
            $http.get(service.loginRebate+'/nextLogin/'+id)
                .success(function(data){
                    deferred.resolve(data);
                }).error(function(data){
                    deferred.reject("no more loginRebate !")
                });
            return deferred.promise;
        };

        service.previousLoginRebate = function(id){
            var deferred = $q.defer();
            $http.get(service.loginRebate+'/previousLogin/'+id)
                .success(function(data){
                    deferred.resolve(data);
                }).error(function(data){
                    deferred.reject("no more loginRebate !")
                });
            return deferred.promise;
        };

        service.translate = function () {
            $translate([
                    'LoginRebate_loginName_description.title',
                    'LoginRebate_loginName_description.text',
                    'LoginRebate_maxRebate_description.title',
                    'LoginRebate_maxRebate_description.text',
                    
                    'Entity_show.title',
                    'Entity_previous.title',
                    'Entity_list.title',
                    'Entity_next.title',
                    'Entity_edit.title',
                    'Entity_create.title',
                    'Entity_update.title',
                    'Entity_Result.title',
                    'Entity_search.title',
                    'Entity_cancel.title',
                    'Entity_save.title',
                    'Entity_By.title',
                    'Entity_saveleave.title',
                    'Entity_add.title'
                 ])
                .then(function (translations) {
                    service.translations = translations;
                });
        };
        service.translate();
        return service;
}])
.controller('LoginRebateCtrls', ['$scope', 'genericResource', 'LoginRebateUtils',
function ($scope, genericResource, LoginRebateUtils) {

	$scope.searchInput = {};
	$scope.loginRebates = [];
	$scope.totalItems = 0;
    $scope.LoginRebateUtils = LoginRebateUtils;
	$scope.loginRebatesearchResults = {};
	$scope.itemPerPage=25;
	$scope.currentPage = 1;
	$scope.maxSize = 5 ;
	$scope.handleSearchRequestEvent = handleSearchRequestEvent;
	$scope.handlePrintRequestEvent = handlePrintRequestEvent;
	$scope.paginate = paginate;
	
	function init(){
		$scope.searchInput = {
                entity:{},
                fieldNames:[],
                start:0,
                max:$scope.itemPerPage
        }

		findByLike($scope.searchInput);
   };
   
   init();
   
   function findByLike(searchInput){
	   LoginRebateUtils.loadLoginRebates(searchInput).then(function(entitySearchResult) {
		   $scope.loginRebates = entitySearchResult.resultList;
       	   $scope.totalItems = entitySearchResult.count;
       });
   }
   
   function processSearchInput(){
       var fieldNames = [];
       if($scope.searchInput.entity.loginName){
    	   fieldNames.push('loginName') ;
       }
       if($scope.searchInput.entity.maxRebate){
    	   fieldNames.push('maxRebate') ;
       }
       $scope.searchInput.fieldNames = fieldNames ;
       return $scope.searchInput;
   };
   
   function  handleSearchRequestEvent(){
       processSearchInput();
       findByLike($scope.searchInput);
  };
  
  function paginate(){
	  $scope.searchInput.start = (($scope.currentPage - 1)  * $scope.itemPerPage) ;
	  $scope.searchInput.max = $scope.itemPerPage ;
      findByLike($scope.searchInput);
  };


  function handlePrintRequestEvent(){

  };


}])
.controller('LoginRebateCreateCtrls', ['$scope', 'genericResource', 'LoginRebateUtils', '$location',
function ($scope, genericResource, LoginRebateUtils, $location) {

	$scope.loginRebate = {};
	$scope.create = create;
	$scope.error = "";
    $scope.LoginRebateUtils = LoginRebateUtils;

    function create(){
    	LoginRebateUtils.create($scope.loginRebate).then(function(result){
             $location.path('/loginRebate/show/'+result.identif);
         },function(error){
        	 $scope.error = error;
         })
     };

}])
.controller('LoginRebateShowCtrls', ['$scope', 'LoginRebateUtils', '$location', '$routeParams',
function ($scope, LoginRebateUtils, $location, $routeParams) {

	$scope.loginRebate = {};
	$scope.show = show;
	$scope.previous = previous;
	$scope.next = next;
	$scope.error = "";
    $scope.LoginRebateUtils = LoginRebateUtils;

    init();

    function init(){
        show();
    };

    function show(){
        var identif = $routeParams.identif ;
        LoginRebateUtils.loadLoginRebate(identif).then(function(result){
        	$scope.loginRebate = result;
        })
    };
    
    function previous(){
    	$scope.error = "";
    	LoginRebateUtils.previousLoginRebate($scope.loginRebate.loginName).then(function(result){
    		$scope.loginRebate = result;
        },function(error){
        	$scope.error = error;
        })
    };

    function next(){
    	$scope.error = "";
    	LoginRebateUtils.nextLoginRebate($scope.loginRebate.loginName).then(function(result){
    		$scope.loginRebate = result;
        },function(error){
        	$scope.error = error;
        })
    };

}])
.controller('LoginRebateUpdateCtrls', ['$scope', 'genericResource', 'LoginRebateUtils', '$location', '$routeParams',
function ($scope, genericResource, LoginRebateUtils, $location, $routeParams) {

	$scope.loginRebate = {};
	$scope.edit = edit;
	$scope.error = "";
    $scope.LoginRebateUtils = LoginRebateUtils;

       function edit(){
    	   LoginRebateUtils.update($scope.loginRebate).then(function(result){
               $location.path('/loginRebate/show/'+result.identif);
           },function(error){
        	   $scope.error = error;
           })
       };

       init();

       function init(){
           load();
       }

       function load(){
           var identif = $routeParams.identif ;
           LoginRebateUtils.loadLoginRebate(identif).then(function(result){
        	   $scope.loginRebate = result;
           },function(error){
        	   $scope.error = error;
           })
       };

}]);
  