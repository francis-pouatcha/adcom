'use strict';

angular.module('Admanager')
    .factory('CdrCstmrVchrUtils', ['sessionManager', '$translate', 'genericResource', '$q', function (sessionManager, $translate, genericResource, $q) {
        var service = {};

        service.cdrCstmr = '/adcshdwr.server/rest/cdrcstmrvchrs';
        service.login = '/adbase.server/rest/logins';
        service.language = sessionManager.language;

        service.currentWsUser = sessionManager.userWsData();

        service.loadLogin = function(value){
            return genericResource.findByLikePromissed(service.login, "loginName", value)
                .then(function (entitySearchResult) {
                    return entitySearchResult.resultList;
                })

        }

        service.translate = function () {
            $translate([
                    'CdrCshDrawer_cashier_description.text',
                    'CdrCshDrawer_cashier_description.title',
                    'CdrCshDrawer_cdrNbr_description.text',
                    'CdrCshDrawer_cdrNbr_description.title',
                    'CdrCshDrawer_closedBy_description.text',
                    'CdrCshDrawer_closedBy_description.title',
                    'CdrCshDrawer_clsngDt_description.text',
                    'CdrCshDrawer_clsngDt_description.title',
                    'CdrCshDrawer_description.text',
                    'CdrCshDrawer_description.title',
                    'CdrCshDrawer_initialAmt_description.text',
                    'CdrCshDrawer_initialAmt_description.title',
                    'CdrCshDrawer_opngDt_description.text',
                    'CdrCshDrawer_opngDt_description.title',
                    'CdrCshDrawer_ttlCashIn_description.text',
                    'CdrCshDrawer_ttlCashIn_description.title',
                    'CdrCshDrawer_ttlCashOut_description.text',
                    'CdrCshDrawer_ttlCashOut_description.title',
                    'CdrCshDrawer_ttlCash_description.text',
                    'CdrCshDrawer_ttlCash_description.title',
                    'CdrCshDrawer_ttlCheck_description.text',
                    'CdrCshDrawer_ttlCheck_description.title',
                    'CdrCshDrawer_ttlCredCard_description.text',
                    'CdrCshDrawer_ttlCredCard_description.title',
                    'CdrCshDrawer_ttlVchrIn_description.text',
                    'CdrCshDrawer_ttlVchrIn_description.title',
                    'CdrCshDrawer_ttlVchrOut_description.text',
                    'CdrCshDrawer_ttlVchrOut_description.title',
                    'CdrCshDrawer_close_description.title'
                 ])
                .then(function (translations) {
                    service.translations = translations;
                });
        };
        service.translate();
        return service;
}])
.controller('CdrCstmrVchrCtrls', ['$scope', 'genericResource', 'CdrCstmrVchrUtils', '$location', '$rootScope', 'commonTranslations',
function ($scope, genericResource, CdrCstmrVchrUtils, $location, $rootScope, commonTranslations) {

            $scope.searchInput = {};
            $scope.itemPerPage = 25;
            $scope.totalItems;
            $scope.currentPage = 1;
            $scope.maxSize = 5;
            $scope.cdrCshDrawers = [];
            $scope.selectedIndex  ;
            $scope.handleSearchRequestEvent = handleSearchRequestEvent;
            $scope.handlePrintRequestEvent = handlePrintRequestEvent;
            $scope.paginate = paginate;
            $scope.error = "";
            $scope.CdrCstmrVchrUtils = CdrCstmrVchrUtils;
            
            $scope.commonTranslations = commonTranslations;
            
            var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
                CshDrawerUtils.translate();
            });

            $scope.$on('$destroy', function () {
                translateChangeSuccessHdl();
            });

            init();

    function findCustom(searchInput) {
        genericResource.findCustom(CdrCstmrVchrUtils.cdrCstmr, searchInput)
            .success(function (entitySearchResult) {
				$scope.cdrCshDrawers = entitySearchResult.resultList;
				console.log("that is the list of cdrCstmrVchrs : " + entitySearchResult + " or can be : " + $scope.cdrCshDrawers );
            })
            .error(function (error) {
                $scope.error = error;
            });
    }

	function handleSearchRequestEvent() {
	    var searchInput = {
	        entity: {},
	        fieldNames: [],
	        start: 0,
	        max: 25
	    };
	    if (angular.isDefined($scope.searchInput.entity.cashier ) && $scope.searchInput.entity.cashier ) {
	        searchInput.entity.cashier = $scope.searchInput.entity.cashier;
	    }
	    if($scope.searchInput.from)
	        searchInput.from = $scope.searchInput.from;
	
	    if($scope.searchInput.to)
	        searchInput.to = $scope.searchInput.to;
	
	    findCustom(searchInput);
	}

    function handlePrintRequestEvent() {
        // To do
    }

    function paginate() {
//                $scope.searchInput = CshDrawerState.paginate();
//                findCustom($scope.searchInput);
    };

    function paginate() {
//                $scope.searchInput = CshDrawerState.paginate();
//                findCustom($scope.searchInput);
    }

    function init() {
    	
    	$scope.searchInput = {
                 entity:{},
                 fieldNames:[],
                 start:0,
                 max:self.itemPerPage
             }
    	 
        findCustom($scope.searchInput);
    }


}]);
  