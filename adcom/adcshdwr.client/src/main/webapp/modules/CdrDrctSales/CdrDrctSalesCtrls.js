'use strict';

angular.module('AdCshdwr')

.factory('cdrDrctSaleManagerResource', ['$http', function ($http) {
        var service = {};
        var urlBase = '/adcshdwr.server/rest/cdrcshdrawers';
        return service;
}])
    .factory('cdrDrctSalesUtils', ['sessionManager', '$translate', 'genericResource', '$q', 'cdrDrctSaleManagerResource', function (sessionManager, $translate, genericResource, $q, cdrDrctSaleManagerResource) {
        var service = {};

        service.urlBase = '/adcshdwr.server/rest/cdrdrctsaless';
        service.cdrdrctsalesmanager = '/adcshdwr.server/rest/cdrdrctsalesmanager';
        service.language = sessionManager.language;
        service.catalarticlesUrlBase = '/adcatal.server/rest/catalarticles';
        service.stkArtLotUrlBase = '/adstock.server/rest/stkarticlelots';

        service.currentWsUser = sessionManager.userWsData();

        service.translate = function () {
            $translate([

                    'CdrDsArtItem_artName_description.text',
                    'CdrDsArtItem_artName_description.title',
                    'CdrDsArtItem_artPic_description.text',
                    'CdrDsArtItem_artPic_description.title',
                    'CdrDsArtItem_description.text',
                    'CdrDsArtItem_description.title',
                    'CdrDsArtItem_dsNbr_description.text',
                    'CdrDsArtItem_dsNbr_description.title',
                    'CdrDsArtItem_grossSPPreTax_description.text',
                    'CdrDsArtItem_grossSPPreTax_description.title',
                    'CdrDsArtItem_lotPic_description.text',
                    'CdrDsArtItem_lotPic_description.title',
                    'CdrDsArtItem_netSPPreTax_description.text',
                    'CdrDsArtItem_netSPPreTax_description.title',
                    'CdrDsArtItem_netSPTaxIncl_description.text',
                    'CdrDsArtItem_netSPTaxIncl_description.title',
                    'CdrDsArtItem_objctOrgUnit_description.text',
                    'CdrDsArtItem_objctOrgUnit_description.title',
                    'CdrDsArtItem_rebate_description.text',
                    'CdrDsArtItem_rebate_description.title',
                    'CdrDsArtItem_restockgFees_description.text',
                    'CdrDsArtItem_restockgFees_description.title',
                    'CdrDsArtItem_returnedQty_description.text',
                    'CdrDsArtItem_returnedQty_description.title',
                    'CdrDsArtItem_soldQty_description.text',
                    'CdrDsArtItem_soldQty_description.title',
                    'CdrDsArtItem_sppuCur_description.text',
                    'CdrDsArtItem_sppuCur_description.title',
                    'CdrDsArtItem_sppuPreTax_description.text',
                    'CdrDsArtItem_sppuPreTax_description.title',
                    'CdrDsArtItem_vatAmount_description.text',
                    'CdrDsArtItem_vatAmount_description.title',
                    'CdrDsArtItem_vatPct_description.text',
                    'CdrDsArtItem_vatPct_description.title',

                    'CdrDsHstry_cdrNbr_description.text',
                    'CdrDsHstry_cdrNbr_description.title',
                    'CdrDsHstry_description.text',
                    'CdrDsHstry_description.title',
                    'CdrDsHstry_dsNbr_description.text',
                    'CdrDsHstry_dsNbr_description.title',
                    'CdrDrctSales_cashier_description.text',
                    'CdrDrctSales_cashier_description.title',
                    'CdrDrctSales_cdrNbr_description.text',
                    'CdrDrctSales_cdrNbr_description.title',
                    'CdrDrctSales_description.text',
                    'CdrDrctSales_description.title',
                    'CdrDrctSales_dsCur_description.text',
                    'CdrDrctSales_dsCur_description.title',
                    'CdrDrctSales_dsNbr_description.text',
                    'CdrDrctSales_dsNbr_description.title',
                    'CdrDrctSales_grossSPPreTax_description.text',
                    'CdrDrctSales_grossSPPreTax_description.title',
                    'CdrDrctSales_netAmtToPay_description.text',
                    'CdrDrctSales_netAmtToPay_description.title',
                    'CdrDrctSales_netSPPreTax_description.text',
                    'CdrDrctSales_netSPPreTax_description.title',
                    'CdrDrctSales_netSPTaxIncl_description.text',
                    'CdrDrctSales_netSPTaxIncl_description.title',
                    'CdrDrctSales_netSalesAmt_description.text',
                    'CdrDrctSales_netSalesAmt_description.title',
                    'CdrDrctSales_pymtDscntAmt_description.text',
                    'CdrDrctSales_pymtDscntAmt_description.title',
                    'CdrDrctSales_pymtDscntPct_description.text',
                    'CdrDrctSales_pymtDscntPct_description.title',
                    'CdrDrctSales_rcptNbr_description.text',
                    'CdrDrctSales_rcptNbr_description.title',
                    'CdrDrctSales_rcptPrntDt_description.text',
                    'CdrDrctSales_rcptPrntDt_description.title',
                    'CdrDrctSales_rdngDscntAmt_description.text',
                    'CdrDrctSales_rdngDscntAmt_description.title',
                    'CdrDrctSales_rebate_description.text',
                    'CdrDrctSales_rebate_description.title',
                    'CdrDrctSales_vatAmount_description.text',
                    'CdrDrctSales_vatAmount_description.title'
                 ])
                .then(function (translations) {
                    service.translations = translations;
                });
        };

        service.loadArticlesByPic = function (artPic) {
            return genericResource.findByLikePromissed(service.catalarticlesUrlBase, 'pic', artPic)
                .then(function (entitySearchResult) {
                    return entitySearchResult.resultList;
                });
        };
        
        service.loadArticleLotByPic = function(artPic) {
           return genericResource.findByLikePromissed(service.stkArtLotUrlBase, 'artPic', artPic)
                .then(function (entitySearchResult) {
                    return entitySearchResult.resultList;
                });
        }

        service.loadArticlesByName = function (artName) {
            var searchInput = {
                entity: {},
                fieldNames: [],
                start: 0,
                max: 10
            };
            searchInput.codesAndNames = artName;
            return genericResource.findByLikeWithSearchInputPromissed(service.catalarticlesUrlBase, searchInput)
                .then(function (entitySearchResult) {
                    return entitySearchResult.resultList;
                });
        };

        service.translate();
        return service;
}])
    .factory('cdrDrctSalesState', ['$rootScope', '$q', function ($rootScope, $q) {

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
            service.cdrDrctSales(entitySearchResult.resultList);
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
        service.cdrDrctSale = function (index) {
            if (index && index >= 0 && index < cdrDrctSalesVar.length) selectedIndexVar = index;
            if (selectedIndexVar < 0 || selectedIndexVar >= cdrDrctSalesVar.length) return;
            return cdrDrctSalesVar[selectedIndexVar];
        };

        // replace the current partner after a change.
        service.replace = function (cdrDrctSale) {
            if (!cdrDrctSalesVar || !cdrDrctSale) return;
            var currentInvt = service.cdrDrctSale();
            if (currentInvt && currentInvt.invtryNbr == cdrDrctSale.invtryNbr) {
                cdrDrctSalesVar[selectedIndexVar] = cdrDrctSale;
            } else {
                for (var index in cdrDrctSalesVar) {
                    if (cdrDrctSalesVar[index].invtryNbr == cdrDrctSale.invtryNbr) {
                        cdrDrctSalesVar[index] = cdrDrctSale;
                        selectedIndexVar = index;
                        break;
                    }
                }
            }
        };
        service.set = function (cdrDrctSale) {
            if (!cdrDrctSalesVar || !cdrDrctSale) return false;
            cdrDrctSalesVar[selectedIndexVar] = cdrDrctSale;
            return true;
        };

        // CHeck if the business partner to be displayed is at the correct index.
        service.peek = function (cdrDrctSale, index) {
            if (!cdrDrctSalesVar || !cdrDrctSale) return false;
            if (index >= 0 && index < cdrDrctSalesVar.length) {
                selectedIndexVar = index;
                return true;
            }
            return false;
        };

        service.push = function (cdrDrctSale) {
            if (!cdrDrctSalesVar || !cdrDrctSale) return false;
            var length = cdrDrctSalesVar.push(cdrDrctSale);
            selectedIndexVar = length - 1;
            return true;
        };

        service.previous = function () {
            if (cdrDrctSalesVar.length <= 0) return;

            if (selectedIndexVar <= 0) {
                selectedIndexVar = cdrDrctSalesVar.length - 1;
            } else {
                selectedIndexVar -= 1;
            }
            return service.cdrDrctSale();
        };

        service.next = function () {
            if (cdrDrctSalesVar.length <= 0) return;

            if (selectedIndexVar >= cdrDrctSalesVar.length - 1 || selectedIndexVar < 0) {
                selectedIndexVar = 0;
            } else {
                selectedIndexVar += 1;
            }

            return service.cdrDrctSale();
        };

        return service;

}])
    .controller('cdrDrctSalesCtlr', ['$scope', 'genericResource', 'cdrDrctSalesUtils', 'cdrDrctSalesState', '$location', '$rootScope', 'commonTranslations',
function ($scope, genericResource, cdrDrctSalesUtils, cdrDrctSalesState, $location, $rootScope, commonTranslations) {

            $scope.searchInput = cdrDrctSalesState.searchInput();
            $scope.itemPerPage = cdrDrctSalesState.itemPerPage;
            $scope.totalItems = cdrDrctSalesState.totalItems;
            $scope.currentPage = cdrDrctSalesState.currentPage();
            $scope.maxSize = cdrDrctSalesState.maxSize;
            $scope.cdrCshDrawers = cdrDrctSalesState.cdrCshDrawers;
            $scope.selectedIndex = cdrDrctSalesState.selectedIndex;
            $scope.handleSearchRequestEvent = handleSearchRequestEvent;
            $scope.handlePrintRequestEvent = handlePrintRequestEvent;
            $scope.paginate = paginate;
            $scope.error = "";
            $scope.cdrDrctSalesUtils = cdrDrctSalesUtils;
            $scope.show = show;
            $scope.edit = edit;
            $scope.commonTranslations = commonTranslations;
            $scope.openCreateForm = openCreateForm;
            var translateChangeSuccessHdl = $rootScope.$on('$translateChangeSuccess', function () {
                cdrDrctSalesUtils.translate();
            });

            $scope.$on('$destroy', function () {
                translateChangeSuccessHdl();
            });

            init();

            function handleSearchRequestEvent() {
                if ($scope.searchInput.acsngUser) {
                    $scope.searchInput.entity.acsngUser = $scope.searchInput.acsngUser.loginName;
                } else {
                    $scope.searchInput.entity.acsngUser = '';
                }
                findCustom($scope.searchInput);
            }

            function handlePrintRequestEvent() {
                // To do
            }

            function paginate() {
                $scope.searchInput = cdrDrctSaleState.paginate();
                findCustom($scope.searchInput);
            };

            function paginate() {
                $scope.searchInput = cdrDrctSaleState.paginate();
                findCustom($scope.searchInput);
            }

            function init() {
                //find previous cdr cshdrawers
                loadPreviousCshDrws();
            }

            function loadPreviousCshDrws() {
                genericResource.listAll(cdrDrctSalesUtils.urlBase).success(function (result) {
                    $scope.cdrCshDrawers = result;
                }).error(function (error) {
                    $scope.error = error;
                });
            }

            function show(cdrCshDrawer, index) {

            }

            function edit(cdrCshDrawer, index) {

            }

            function openCreateForm() {}
}])
    .controller('cdrDrctSalesCreateCtlr', ['$scope', 'cdrDrctSalesUtils', '$translate', 'genericResource', '$location', 'cdrDrctSalesState', 'commonTranslations',
        function ($scope, cdrDrctSalesUtils, $translate, genericResource, $location, cdrDrctSalesState, commonTranslations) {
            $scope.cdrCshDrawer = {
                cdrDrctSales : {}
            };
            $scope.create = create;
            $scope.error = "";
            $scope.cdrDrctSalesUtils = cdrDrctSalesUtils;
            $scope.commonTranslations = commonTranslations;
            $scope.cdrDsArtItemHolder = {
                item : {}
            };
            $scope.cdrDsArtHolder = {
                cdrDrctSales : {},
                items: []
            };

            function create() {};

            $scope.onArticleSelectedInSearch = function (item, model, label) {
                $scope.cdrDsArtItemHolder.item.artPic = item.pic;
                $scope.cdrDsArtItemHolder.item.lotPic = item.pic;
                $scope.cdrDsArtItemHolder.artName = item.features.artName;
                $scope.cdrDsArtItemHolder.item.sppuPreTax = item.sppu;
                $scope.cdrDsArtItemHolder.item.netSPPreTax = item.sppu;
                $scope.cdrDsArtItemHolder.maxStockQty = item.maxStockQty;
                $scope.cdrDsArtItemHolder.item.sppuCur = item.sppuCurrIso3;
                $scope.cdrDsArtItemHolder.item.vatPct = item.vatRate; //compute the percentaage
            };

            $scope.addItem = function () {
                addItem($scope.cdrDsArtItemHolder);
            };

            $scope.cdrDsArtItems = function () {
                return $scope.cdrDsArtItemHolder.items;
            };

            $scope.editItem = function (index) {
                $scope.cdrDsArtItemHolder = angular.copy($scope.cdrDsArtHolder.items[index]);
            };

            $scope.save = function () {
                computeCdrDsArtHolder();
                genericResource.create(cdrDrctSalesUtils.cdrdrctsalesmanager, $scope.cdrDsArtHolder).success(function (result) {
                    $scope.cdrDsArtHolder = result;
                    $location.path("/CdrDrctSales/show"+result.id);
                }).error(function (error) {
                    $scope.error = error;
                });
            };

            function clearObject(anObject) {
                anObject = {
                    item : {}
                };
                return anObject;
            }

            function isNotCorrect(cdrDsArtItemHolder) {
                return isCorrect(cdrDsArtItemHolder) == false;
            }

            function isCorrect(cdrDsArtItemHolder) {
                if (!cdrDsArtItemHolder || angular.isUndefined(cdrDsArtItemHolder) || !(cdrDsArtItemHolder.item.artPic && 0 < cdrDsArtItemHolder.item.soldQty)) {
                    return false;
                }
                return true;
            }

            function addItem(cdrDsArtItemHolder) {
                if (isNotCorrect($scope.cdrDsArtItemHolder)) return;
                if (cdrDsArtItemHolder.item.soldQty > cdrDsArtItemHolder.maxStockQty) return;
                var copy = angular.copy(cdrDsArtItemHolder)
                var i = 0;
                var items = $scope.cdrDsArtHolder.items;
                if (items.length == 0) {
                    $scope.cdrDsArtHolder.items.push(copy);
                } else {
                    var found = false;
                    angular.forEach(items, function (artItemHolder) {
                        if ((artItemHolder.item.artPic == copy.item.artPic) && (artItemHolder.artName == copy.artName)) {
                            items[i] = copy;
                            found = true;
                        }
                        i += 1;
                    });
                    if (!found) {
                        $scope.cdrDsArtHolder.items.push(copy);
                    }
                }
                computeCdrDsArtHolder();
                $scope.cdrDsArtItemHolder = clearObject($scope.cdrDsArtItemHolder);
            }

            function computeCdrDsArtHolder() {
                var items = $scope.cdrDsArtHolder.items;
                var totalAmtHT = 0.0;
                var totalAmtTTC = 0.0;
                var totalVatAmount = 0.0;
                angular.forEach(items, function (artItemHolder) {
                    var netSPPreTax = artItemHolder.item.sppuPreTax * artItemHolder.item.soldQty;
                    var vatAmount = netSPPreTax * artItemHolder.item.vatPct;
                    var netSPTaxIncl = netSPPreTax + vatAmount;
                    artItemHolder.item.netSPPreTax = netSPPreTax;
                    artItemHolder.item.netSPTaxIncl = netSPTaxIncl;
                    artItemHolder.item.vatAmount = vatAmount;
                    totalAmtHT += netSPPreTax;
                    totalAmtTTC += netSPTaxIncl;
                    totalVatAmount += totalVatAmount;
                });

                $scope.cdrDsArtHolder.cdrDrctSales.netSalesAmt = totalAmtHT;
                $scope.cdrDsArtHolder.cdrDrctSales.netAmtToPay = totalAmtTTC;
                $scope.cdrDsArtHolder.cdrDrctSales.netSPPreTax = totalAmtHT;
                $scope.cdrDsArtHolder.cdrDrctSales.netSPTaxIncl = totalAmtTTC;
                $scope.cdrDsArtHolder.cdrDrctSales.vatAmount = totalVatAmount;
            }
}])
    .controller('cdrDrctSalesEditCtlr', ['$scope', 'genericResource', '$location', 'cdrDrctSalesUtils', 'cdrDrctSalesState', 'commonTranslations',
                                 function ($scope, genericResource, $location, cdrDrctSalesUtils, cdrDrctSalesState, commonTranslations) {
            $scope.cdrCshDrawer = cdrDrctSalesState.cdrCshDrawer;
            $scope.error = "";
            $scope.cdrDrctSalesUtils = cdrDrctSalesUtils;
            $scope.commonTranslations = commonTranslations;

            init();

            function init() {}
            $scope.close = function () {
                //close the cashdrawer
            };
}]);