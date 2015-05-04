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
        service.stkArtlot2strgsctnsUrlBase = '/adstock.server/rest/stkarticlelot2strgsctns';

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
                    'CdrDrctSales_vatAmount_description.title',
                    'CdrDrctSales_paidAmt_description.title',
                    'CdrDrctSales_changeAmt_description.title'
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

        service.loadArticleLotByPic = function (artPic) {
            
            var searchInput = {
                entity: {},
                fieldNames: [],
                start: 0,
                max: 10
            };
            searchInput.artPic = artPic;
            return genericResource.findByLikePromissed(service.stkArtlot2strgsctnsUrlBase, 'artPic', artPic)
                .then(function (entitySearchResult) {
                    var resultList = entitySearchResult.resultList;
                    var displayDatas = [];
                    angular.forEach(resultList, function (item) {
                        var artName = item.artName;
                        var displayable = {};
                        var sectionArticleLot = item.sectionArticleLot;
                        if (sectionArticleLot) {
                            var artQties = sectionArticleLot.artQties;
                            if (!artQties) artQties = [];
                            angular.forEach(artQties, function (artQty) {
                                var displayableStr = "";
                                displayable.artName = artName;
                                displayableStr = artQty.artPic
                                displayableStr += " - "+artName;
                                if (artQty.lotPic) {
                                    displayable.lotPic = artQty.lotPic;
                                    displayableStr += "- lot (" + artQty.lotPic + ")";
                                }
                                if (artQty.section) {
                                    displayable.section = artQty.section;
                                    displayableStr += "- section (" + artQty.section + ")";
                                }
                                if (artQty.stockQty) {
                                    displayable.stockQty = artQty.stockQty;
                                    displayableStr += "- Qty (" + artQty.stockQty + ")";
                                }
                                displayableStr += " - ppuHT (" + sectionArticleLot.sppuHT + ")";
                                displayable.artPic = artQty.artPic;
                                displayable.sppuPreTax = sectionArticleLot.sppuHT;
                                displayable.minSppuHT = sectionArticleLot.minSppuHT;
                                displayable.sppuTaxIncl = sectionArticleLot.sppuTaxIncl;
                                displayable.sppuCur = sectionArticleLot.sppuCur;
                                displayable.vatPct = sectionArticleLot.vatSalesPct;
                                displayable.salesVatAmt = sectionArticleLot.salesVatAmt;
                                displayable.salesWrntyDys = sectionArticleLot.salesWrntyDys;
                                displayable.salesRtrnDays = sectionArticleLot.salesRtrnDays;

                                displayable.displayableStr = displayableStr;
                                displayDatas.push(displayable);
                            });
                        }
                    });
                    return displayDatas;
                });
        }

        service.loadArticlesByName = function (artName) {
            var searchInput = {
                entity: {},
                fieldNames: [],
                start: 0,
                max: 10
            };
            searchInput.artName = artName;
            return genericResource.findByLikePromissed(service.stkArtlot2strgsctnsUrlBase, 'artName', artName)
                .then(function (entitySearchResult) {
                    var resultList = entitySearchResult.resultList;
                    var displayDatas = [];
                    angular.forEach(resultList, function (item) {
                        var artName = item.artName;
                        var displayable = {};
                        var sectionArticleLot = item.sectionArticleLot;
                        if (sectionArticleLot) {
                            var artQties = sectionArticleLot.artQties;
                            if (!artQties) artQties = [];
                            angular.forEach(artQties, function (artQty) {
                                var displayableStr = "";
                                displayable.artName = artName;
                                displayableStr = artQty.artPic
                                displayableStr += " - "+artName;
                                if (artQty.lotPic) {
                                    displayable.lotPic = artQty.lotPic;
                                    displayableStr += " - lot (" + artQty.lotPic + ")";
                                }
                                if (artQty.section) {
                                    displayable.section = artQty.section;
                                    displayableStr += " - section (" + artQty.section + ")";
                                }
                                if (artQty.stockQty) {
                                    displayable.stockQty = artQty.stockQty;
                                    displayableStr += " - Qty (" + artQty.stockQty + ")";
                                }
                                displayableStr += " - ppuHT (" + sectionArticleLot.sppuHT + ")";
                                displayable.artPic = artQty.artPic;
                                displayable.sppuPreTax = sectionArticleLot.sppuHT;
                                displayable.minSppuHT = sectionArticleLot.minSppuHT;
                                displayable.sppuTaxIncl = sectionArticleLot.sppuTaxIncl;
                                displayable.sppuCur = sectionArticleLot.sppuCur;
                                displayable.vatPct = sectionArticleLot.vatSalesPct;
                                displayable.salesVatAmt = sectionArticleLot.salesVatAmt;
                                displayable.salesWrntyDys = sectionArticleLot.salesWrntyDys;
                                displayable.salesRtrnDays = sectionArticleLot.salesRtrnDays;

                                displayable.displayableStr = displayableStr;
                                displayDatas.push(displayable);
                            });
                        }
                    });
                    return displayDatas;
                });
        };

        service.translate();
        return service;
}])
    .factory('cdrDrctSalesState', ['$rootScope', '$q', function ($rootScope, $q) {

        var service = {};
        var selectedIndexVar = -1;
        var searchResult = {};
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
        service.searchResult = function (srchRslt) {
            if (srchRslt) {
                searchResult = srchRslt;
            }
            return searchResult;
        };
        service.hasEntities = function() {
            return searchResult.resultList && searchResult.resultList.length > 0;
        }
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

            function processSearchInput(searchInput) {
                if (angular.isDefined(searchInput.entity.dsNbr ) && searchInput.entity.dsNbr ) {
                    searchInput.fieldNames.push('dsNbr ');
                }
                if (angular.isDefined(searchInput.entity.cashier ) && searchInput.entity.cashier ) {
                    searchInput.fieldNames.push('cashier');
                }
                if (angular.isDefined(searchInput.entity.cdrNbr ) && searchInput.entity.cdrNbr ) {
                    searchInput.fieldNames.push('cdrNbr');
                }
                if (angular.isDefined(searchInput.entity.rcptNbr ) && searchInput.entity.rcptNbr ) {
                    searchInput.fieldNames.push('rcptNbr');
                }
            }

            function findCustom(searchInput) {
                genericResource.findCustom(cdrDrctSalesUtils.urlBase, searchInput)
                    .success(function (entitySearchResult) {
                        // store search
                        cdrDrctSalesState.searchResult(entitySearchResult);
                        $scope.searchInput = cdrDrctSalesState.searchResult().searchInput;
                        $scope.cdrDrctSales = cdrDrctSalesState.searchResult().resultList;
                    })
                    .error(function (error) {
                        $scope.error = error;
                    });
            }

            function handleSearchRequestEvent() {
                processSearchInput($scope.searchInput);
                findCustom($scope.searchInput);
            }

            function handlePrintRequestEvent() {}

            function paginate() {
                $scope.searchInput = cdrDrctSaleState.paginate();
                findCustom($scope.searchInput);
            };

            function paginate() {
                $scope.searchInput = cdrDrctSaleState.paginate();
                findCustom($scope.searchInput);
            }

            function init() {
                findCustom($scope.searchInput);
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
                cdrDrctSales: {}
            };
            $scope.create = create;
            $scope.error = "";
            $scope.cdrDrctSalesUtils = cdrDrctSalesUtils;
            $scope.commonTranslations = commonTranslations;
            $scope.cdrDsArtItemHolder = {
                item: {}
            };
            $scope.cdrDsArtHolder = {
                cdrDrctSales: {},
                items: []
            };
            $scope.showprint = false;

            function create() {};

            $scope.onArticleSelectedInSearch = function (item, model, label) {
                $scope.cdrDsArtItemHolder.item.artPic = item.artPic;
                $scope.cdrDsArtItemHolder.item.lotPic = item.lotPic;
                $scope.cdrDsArtItemHolder.item.section = item.section;
                $scope.cdrDsArtItemHolder.artName = item.artName;
                $scope.cdrDsArtItemHolder.item.sppuPreTax = item.sppuPreTax;
                if (!item.sppuPreTax) item.sppuPreTax = 0.0;
                if (!item.salesVatAmt) item.salesVatAmt = 0.0;
                $scope.cdrDsArtItemHolder.item.netSPPreTax = item.sppuPreTax + item.salesVatAmt;
                $scope.cdrDsArtItemHolder.maxStockQty = item.stockQty;
                $scope.cdrDsArtItemHolder.item.sppuCur = item.sppuCur;
                $scope.cdrDsArtItemHolder.item.vatPct = item.vatPct; //the vatPct
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

            $scope.saveAndLeave = function () {
                if($scope.cdrDsArtHolder && $scope.cdrDsArtHolder.items.length > 0){
                    computeCdrDsArtHolder();
                    genericResource.create(cdrDrctSalesUtils.cdrdrctsalesmanager, $scope.cdrDsArtHolder).success(function (result) {
                        $scope.cdrDsArtHolder = result;
                        $location.path("/CdrDrctSales/show" + result.id);
                    }).error(function (error) {
                        $scope.error = error;
                    });
                }
            };

            function clear(){
                $scope.cdrDsArtHolder = {
                    cdrDrctSales: {},
                    items: []
                };
                $scope.cdrDsArtItemHolder = {
                    item: {}
                };
                $scope.showprint = false;
            }

            $scope.save = function () {
                computeCdrDsArtHolder();
                if(verifCdrDsArtHolder() == false){
                    return;
                }
                genericResource.create(cdrDrctSalesUtils.cdrdrctsalesmanager, $scope.cdrDsArtHolder).success(function (result) {
                    $scope.showprint = true;
                    $scope.cdrDsArtHolder = result;
                }).error(function (error) {
                    $scope.error = error;
                });
            };

            function verifCdrDsArtHolder(){
                if($scope.cdrDsArtHolder.cdrDrctSales.netSalesAmt > $scope.cdrDsArtHolder.paidAmt){
                    $scope.error = "Montant paye est inferieur au montant de vente";
                    return false;
                }
                if($scope.cdrDsArtHolder.cdrDrctSales.pymtDscntAmt > $scope.cdrDsArtHolder.cdrDrctSales.netSPPreTax){
                    $scope.error = "Montant de l'escompte est superieur au montant de vente hors taxe";
                    return false;
                }
            }

            $scope.cancel = function () {
                clear();
            };

            $scope.recompute = function () {
                computeCdrDsArtHolder();
            };

            $scope.printRequest = function(){

            };

            $scope.hasItem = function () {
                return $scope.cdrDsArtHolder.items.length > 0;
            };

            $scope.pymtDscntPctChanged = function () {
                if (!$scope.cdrDsArtHolder.cdrDrctSales.pymtDscntPct) return;
                $scope.cdrDsArtHolder.cdrDrctSales.pymtDscntAmt = ($scope.cdrDsArtHolder.cdrDrctSales.pymtDscntPct * $scope.cdrDsArtHolder.cdrDrctSales.netSPPreTax) / 100;
                $scope.cdrDsArtHolder.cdrDrctSales.netSalesAmt = $scope.cdrDsArtHolder.cdrDrctSales.netSPTaxIncl - $scope.cdrDsArtHolder.cdrDrctSales.pymtDscntAmt;
            };

            $scope.pymtDscntAmtChanged = function () {
                if (!$scope.cdrDsArtHolder.cdrDrctSales.pymtDscntAmt) return;
                $scope.cdrDsArtHolder.cdrDrctSales.pymtDscntPct = ($scope.cdrDsArtHolder.cdrDrctSales.pymtDscntAmt * 100) / $scope.cdrDsArtHolder.cdrDrctSales.netSPPreTax;
                $scope.cdrDsArtHolder.cdrDrctSales.netSalesAmt = $scope.cdrDsArtHolder.cdrDrctSales.netSPTaxIncl - $scope.cdrDsArtHolder.cdrDrctSales.pymtDscntAmt; //update the netSPTaxIncl
            };

            $scope.paidAmtChanged = function () {
                if (!$scope.cdrDsArtHolder.paidAmt) return;
                $scope.cdrDsArtHolder.changeAmt = $scope.cdrDsArtHolder.paidAmt - $scope.cdrDsArtHolder.cdrDrctSales.netSalesAmt;
            }

            function clearObject(anObject) {
                anObject = {
                    item: {}
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
                if (cdrDsArtItemHolder.item.soldQty > cdrDsArtItemHolder.maxStockQty){
                    $scope.error ="Quantite Vendus superieur a la quantite en stock";
                    return;
                }
                if (0 > cdrDsArtItemHolder.item.vatPct){
                    $scope.error ="La TVA ne peut etre negatif";
                    return;
                }
                if (cdrDsArtItemHolder.item.restockgFees > cdrDsArtItemHolder.item.sppuPreTax * parseInt(cdrDsArtItemHolder.item.soldQty)
                    || cdrDsArtItemHolder.item.sppuPreTax * parseInt(cdrDsArtItemHolder.item.soldQty) == cdrDsArtItemHolder.item.restockgFees){
                    $scope.error ="La frais de stockage ne peut etre superieur au montant de vente";
                    return;
                }
                if (cdrDsArtItemHolder.item.rebate > cdrDsArtItemHolder.item.sppuPreTax * parseInt(cdrDsArtItemHolder.item.soldQty)
                    || cdrDsArtItemHolder.item.sppuPreTax * parseInt(cdrDsArtItemHolder.item.soldQty) == cdrDsArtItemHolder.item.rebate){
                    $scope.error ="La remise ne peut etre superieur au montant de vente";
                    return;
                }
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

                var totalNetSalesAmt = 0.0;
                var totalVatAmount = 0.0;
                var totalNetSPPreTax = 0.0;
                var totalNetSPTaxIncl = 0.0;
                var totalGrossSPPreTax = 0.0;

                angular.forEach(items, function (artItemHolder) {
                    var totalRebate = 0.0;
                    if (artItemHolder.item.rebate) totalRebate = artItemHolder.item.rebate * artItemHolder.item.soldQty;
                    var grossSPPTax = artItemHolder.item.sppuPreTax * artItemHolder.item.soldQty;
                    var netSPPreTax = grossSPPTax - totalRebate;
                    var vatAmount = netSPPreTax * (artItemHolder.item.vatPct / 100);
                    var netSPTaxIncl = netSPPreTax + vatAmount + artItemHolder.item.restockgFees;
                    artItemHolder.item.netSPPreTax = netSPPreTax;
                    artItemHolder.item.netSPTaxIncl = netSPTaxIncl;
                    artItemHolder.item.vatAmount = vatAmount;
                    //update the global sale object
                    totalGrossSPPreTax += grossSPPTax;
                    totalNetSPPreTax += netSPPreTax;
                    totalNetSPTaxIncl += netSPTaxIncl;
                    totalVatAmount += vatAmount;
                });
                totalNetSalesAmt = totalNetSPTaxIncl;

                if ($scope.cdrDsArtHolder.cdrDrctSales.rebate) {
                    totalNetSPPreTax = totalNetSPPreTax - $scope.cdrDsArtHolder.cdrDrctSales.rebate;
                }
                $scope.cdrDsArtHolder.cdrDrctSales.netSalesAmt = totalNetSalesAmt;
                $scope.cdrDsArtHolder.cdrDrctSales.netAmtToPay = totalNetSalesAmt;
                $scope.cdrDsArtHolder.cdrDrctSales.netSPPreTax = totalNetSPPreTax;
                $scope.cdrDsArtHolder.cdrDrctSales.netSPTaxIncl = totalNetSPTaxIncl;
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