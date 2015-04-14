'use strict';

angular.module('AdCshdwr')
    .factory('cdrSlsInvoicetUtils', ['sessionManager', '$translate', 'genericResource', '$q', function (sessionManager, $translate, genericResource, $q) {
        var service = {};

        service.urlBase = '/adinvtry.server/rest/cdpaymnts';
        service.slsInvoicesUrlBase = '/adsales.server/rest/slsinvoices';
        service.language = sessionManager.language;
        service.currentWsUser = sessionManager.userWsData();
        service.translate = function () {
            $translate([
                    'SlsInvoice_description.text',
                    'SlsInvoice_description.title',
                    'SlsInvoice_grossSPPreTax_description.text',
                    'SlsInvoice_grossSPPreTax_description.title',
                    'SlsInvoice_invceCur_description.text',
                    'SlsInvoice_invceCur_description.title',
                    'SlsInvoice_invceNbr_description.text',
                    'SlsInvoice_invceNbr_description.title',
                    'SlsInvoice_invceStatus_description.text',
                    'SlsInvoice_invceStatus_description.title',
                    'SlsInvoice_invceType_description.text',
                    'SlsInvoice_invceType_description.title',
                    'SlsInvoice_netAmtToPay_description.text',
                    'SlsInvoice_netAmtToPay_description.title',
                    'SlsInvoice_netSPPreTax_description.text',
                    'SlsInvoice_netSPPreTax_description.title',
                    'SlsInvoice_netSPTaxIncl_description.text',
                    'SlsInvoice_netSPTaxIncl_description.title',
                    'SlsInvoice_netSalesAmt_description.text',
                    'SlsInvoice_netSalesAmt_description.title',
                    'SlsInvoice_pymtDscntAmt_description.text',
                    'SlsInvoice_pymtDscntAmt_description.title',
                    'SlsInvoice_pymtDscntPct_description.text',
                    'SlsInvoice_pymtDscntPct_description.title',
                    'SlsInvoice_rdngDscntAmt_description.text',
                    'SlsInvoice_rdngDscntAmt_description.title',
                    'SlsInvoice_rebate_description.text',
                    'SlsInvoice_rebate_description.title',
                    'SlsInvoice_soNbr_description.text',
                    'SlsInvoice_soNbr_description.title',
                    'SlsInvoice_vatAmount_description.text',
                    'SlsInvoice_vatAmount_description.title',
                'SlsInvceItem_lotPic_description.title',
    'SlsInvceItem_netSPPreTax_description.text',
    'SlsInvceItem_netSPPreTax_description.title',
    'SlsInvceItem_netSPTaxIncl_description.text',
    'SlsInvceItem_netSPTaxIncl_description.title',
    'SlsInvceItem_objctOrgUnit_description.text',
    'SlsInvceItem_objctOrgUnit_description.title',
    'SlsInvceItem_qty_description.text',
    'SlsInvceItem_qty_description.title',
    'SlsInvceItem_rebate_description.text',
    'SlsInvceItem_rebate_description.title',
    'SlsInvceItem_sppuCur_description.text',
    'SlsInvceItem_sppuCur_description.title',
    'SlsInvceItem_sppuPreTax_description.text',
    'SlsInvceItem_sppuPreTax_description.title',
    'SlsInvceItem_vatAmount_description.text',
    'SlsInvceItem_vatAmount_description.title',
    'SlsInvceItem_vatPct_description.text',
    'SlsInvceItem_vatPct_description.title',
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
    .factory('cdrSlsInvoiceState', ['$rootScope', '$q', function ($rootScope, $q) {

        var service = {};
        var selectedIndexVar = -1;
        service.selectedIndex = function (selectedIndexIn) {
            if (selectedIndexIn) selectedIndexVar = selectedIndexIn;
            return selectedIndexVar;
        };

        var totalItemsVar = 0;
        service.totalItems = function (totalItemsIn) {
            if (totalItemsIn) totalItemsVar = totalItemsIn;
            return totalItemsVar;
        };

        var currentPageVar = 0;
        service.currentPage = function (currentPageIn) {
            if (currentPageIn) currentPageVar = currentPageIn;
            return currentPageVar;
        };

        var maxSizeVar = 5;
        service.maxSize = function (maxSizeIn) {
            if (maxSizeIn) maxSizeVar = maxSizeIn;
            return maxSizeVar;
        };

        var itemPerPageVar = 25;
        var searchInputVar = {
            entity: {},
            fieldNames: [],
            start: 0,
            max: itemPerPageVar
        };
        service.searchInput = function (searchInputIn) {
            if (!searchInputIn)
                return angular.copy(searchInputVar);

            searchInputVar = angular.copy(searchInputIn);
            return searchInputIn;
        };



        service.searchInputChanged = function (searchInputIn) {
            return angular.equals(searchInputVar, searchInputIn);
        };
        service.itemPerPage = function (itemPerPageIn) {
            if (itemPerPageIn) itemPerPageVar = itemPerPageIn;
            return itemPerPageVar;
        };

        //
        service.consumeSearchResult = function (searchInput, entitySearchResult) {
            // store search
            service.searchInput(searchInput);
            // Store result
            service.invInvtrys(entitySearchResult.resultList);
            service.totalItems(entitySearchResult.count);
            service.selectedIndex(-1);
        };


        service.paginate = function () {
            searchInputVar.start = ((currentPageVar - 1) * itemPerPageVar);
            searchInputVar.max = itemPerPageVar;
            return service.searchInput();
        };

        // returns sets and returns the business partner at the passed index or
        // if not set the business partner at the currently selected index.
        service.invInvtry = function (index) {
            if (index && index >= 0 && index < invInvtrysVar.length) selectedIndexVar = index;
            if (selectedIndexVar < 0 || selectedIndexVar >= invInvtrysVar.length) return;
            return invInvtrysVar[selectedIndexVar];
        };

        // replace the current partner after a change.
        service.replace = function (invInvtry) {
            if (!invInvtrysVar || !invInvtry) return;
            var currentInvt = service.invInvtry();
            if (currentInvt && currentInvt.invtryNbr == invInvtry.invtryNbr) {
                invInvtrysVar[selectedIndexVar] = invInvtry;
            } else {
                for (var index in invInvtrysVar) {
                    if (invInvtrysVar[index].invtryNbr == invInvtry.invtryNbr) {
                        invInvtrysVar[index] = invInvtry;
                        selectedIndexVar = index;
                        break;
                    }
                }
            }
        };
        service.set = function (invInvtry) {
            if (!invInvtrysVar || !invInvtry) return false;
            invInvtrysVar[selectedIndexVar] = invInvtry;
            return true;
        };

        // CHeck if the business partner to be displayed is at the correct index.
        service.peek = function (invInvtry, index) {
            if (!invInvtrysVar || !invInvtry) return false;
            if (index >= 0 && index < invInvtrysVar.length) {
                selectedIndexVar = index;
                return true;
            }
            return false;
        };

        service.push = function (invInvtry) {
            if (!invInvtrysVar || !invInvtry) return false;
            var length = invInvtrysVar.push(invInvtry);
            selectedIndexVar = length - 1;
            return true;
        };

        service.previous = function () {
            if (invInvtrysVar.length <= 0) return;

            if (selectedIndexVar <= 0) {
                selectedIndexVar = invInvtrysVar.length - 1;
            } else {
                selectedIndexVar -= 1;
            }
            return service.invInvtry();
        };

        service.next = function () {
            if (invInvtrysVar.length <= 0) return;

            if (selectedIndexVar >= invInvtrysVar.length - 1 || selectedIndexVar < 0) {
                selectedIndexVar = 0;
            } else {
                selectedIndexVar += 1;
            }

            return service.invInvtry();
        };

        service.slsInvoice = function () {

        }
        return service;

}])
    .controller('slsInvoicesCtlr', ['$scope', 'genericResource', 'cdrSlsInvoicetUtils', 'cdrSlsInvoiceState', '$location', '$rootScope',
function ($scope, genericResource, cdrSlsInvoicetUtils, cdrSlsInvoiceState, $location, $rootScope) {

            $scope.searchInput = cdrSlsInvoiceState.searchInput();
            $scope.itemPerPage = cdrSlsInvoiceState.itemPerPage;
            $scope.totalItems = cdrSlsInvoiceState.totalItems;
            $scope.currentPage = cdrSlsInvoiceState.currentPage();
            $scope.maxSize = cdrSlsInvoiceState.maxSize;
            $scope.slsInvoices = cdrSlsInvoiceState.slsInvoices;
            $scope.selectedIndex = cdrSlsInvoiceState.selectedIndex;
            $scope.handleSearchRequestEvent = handleSearchRequestEvent;
            $scope.handlePrintRequestEvent = handlePrintRequestEvent;
            $scope.paginate = paginate;
            $scope.error = "";
            $scope.cdrSlsInvoicetUtils = cdrSlsInvoicetUtils;
            $scope.show = show;
            $scope.edit = edit;
    
            $scope.searchInput = {
                entity : {},
                start : 0,
                max : 100,
                fieldNames : []
            }

            var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
                cdrSlsInvoicetUtils.translate();
            });

            $scope.$on('$destroy', function () {
                translateChangeSuccessHdl();
            });

            init();

            function handleSearchRequestEvent() {
                $scope.searchInput = processSearchInput($scope,searchInput);
                findCustom($scope.searchInput);
            }
    
            function handlePrintRequestEvent() {
                //todo
            }
    
            function processSearchInput (searchInput) {
                if(!searchInput) return ;
                searchInput.fieldNames = [];
                if(searchInput.entity.invceNbr) {
                    searchInput.fieldNames.push('invceNbr')
                }
                if(searchInput.entity.invceStatus) {
                    searchInput.fieldNames.push('invceStatus')
                }
                if(searchInput.entity.invceType) {
                    searchInput.fieldNames.push('invceType')
                }
                return searchInput;
            }

            function paginate() {
                $scope.searchInput = invInvtryState.paginate();
                findCustom($scope.searchInput);
            };

            function paginate() {
                $scope.searchInput = invInvtryState.paginate();
                findCustom($scope.searchInput);
            }

            function init() {
                findCustom($scope.searchInput);
            }

            function show(cdrPymt, index) {

            }

            function edit(cdrPymt, index) {
            }
            function findCustom(searchInput) {
                if(!searchInput) throw "Invalid search input";
                genericResource.findBy(cdrSlsInvoicetUtils.slsInvoicesUrlBase,searchInput).then(function(result) {
                    $scope.slsInvoices = result.resultList;
                    $scope.searchInput = result.searchInput;
                },function(error){
                    $scope.error = error;
                })
            }

}])
    .controller('cdrSlsInvoiceCreateCtlr', ['$scope', 'cdrSlsInvoicetUtils', '$translate', 'genericResource', '$location', 'cdrSlsInvoiceState',
        function ($scope, cdrSlsInvoicetUtils, $translate, genericResource, $location, cdrSlsInvoiceState) {
            $scope.slsInvoice = cdrSlsInvoiceState.slsInvoice();
            $scope.create = create;
            $scope.error = "";
            $scope.stkSection = "";
            $scope.startRange = "";
            $scope.endRange = "";
            $scope.cdrSlsInvoicetUtils = cdrSlsInvoicetUtils;

            function create() {};


}])
    .controller('cdrSlsInvoiceEditCtlr', ['$scope', 'genericResource', '$location', 'cdrSlsInvoicetUtils', 'cdrSlsInvoiceState',
                                 function ($scope, genericResource, $location, cdrSlsInvoicetUtils, cdrSlsInvoiceState) {
            $scope.slsInvoice = cdrSlsInvoiceState.slsInvoice();
            $scope.error = "";
            $scope.cdrSlsInvoicetUtils = cdrSlsInvoicetUtils;
            $scope.update = function () {
                genericResource.update(cdrSlsInvoicetUtils.urlBase, $scope.slsInvoice)
                    .success(function (slsInvoice) {
                        if (cdrSlsInvoiceState.replace(slsInvoice)) {
                            $location.path('/SlsInvoices/show/');
                        }
                    })
                    .error(function (error) {
                        $scope.error = error;
                    });
            };
}])
    .controller('cdrSlsInvoiceShowCtlr', ['$scope', 'cdrSlsInvoiceManagerResource', '$location', 'cdrSlsInvoicetUtils', 'cdrSlsInvoiceState', '$rootScope', 'genericResource', '$routeParams',
                                 function ($scope, cdrSlsInvoiceManagerResource, $location, cdrSlsInvoicetUtils, cdrSlsInvoiceState, $rootScope, genericResource, $routeParams) {
            $scope.slsInvoice = cdrSlsInvoiceState.slsInvoice();
            $scope.error = "";
            if ($scope.slsInvoice) {
                $scope.slsInvoice.acsngUser = cdrSlsInvoicetUtils.currentWsUser.userFullName;
            };

            function init() {}
}]);