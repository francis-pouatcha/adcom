'use strict';

angular.module('AdProcmt').controller('prcmtOrderShowCtlr',['$scope','ProcmtUtils','PrcmtOrderState','PrcmtDeliveryState','genericResource','$routeParams','$location','$q',function($scope,ProcmtUtils,PrcmtOrderState,PrcmtDeliveryState,genericResource,$routeParams,$location,$q){
    var self = this ;
    $scope.prcmtOrderShowCtlr = self;
    self.prcmtOrderHolder = {
        prcmtProcOrder:{},
        poItems:[]
    };
    self.prcmtOrderItemHolder = {};
    self.error = "";
    self.loadArticlesByNameLike = findArticleByName;
    self.loadArticlesByCipLike = findArticleByCip;
    self.loading = true;
    self.onSelect = onSelect;
    self.save = save;
    self.close = close;
    self.addItem = addItem;
    self.editItem = editItem;
    self.deleteItem = deleteItem;
    self.selectedIndex;
    self.selectedItem;
    self.totalAmountEntered = 0;
    self.closeStatus = false;
    self.show = false;
    self.showMore = showMore;
    self.showLess = showLess;
    self.loadstkSection = loadstkSection;
    self.loadOrgUnit = loadOrgUnit;
    self.loadBusinessPartner = loadBusinessPartner;
    self.poItemsDeleted = [];
    self.transform = transform;

    load();

    function load(){
        self.prcmtOrderHolder = PrcmtOrderState.getOrderHolder();
        if(self.prcmtOrderHolder.prcmtProcOrder.poStatus=='INITIATED' || self.prcmtOrderHolder.prcmtProcOrder.poStatus=='ONGOING'){
            self.closeStatus = true;
        }
    };


    function loadOrgUnit(val){
        return loadOrgUnitPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        })
    }
    function loadOrgUnitPromise(val){
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        if(val){
            searchInput.entity.identif='hs';
            searchInput.entity.fullName = val+'%';
            searchInput.fieldNames.push('fullName');
        }
        var deferred = $q.defer();
        genericResource.findByLike(ProcmtUtils.orgunits,searchInput).success(function (entitySearchResult) {
            deferred.resolve(entitySearchResult);
        }).error(function(){
            deferred.reject("No organisation unit");
        });
        return deferred.promise;
    }

    function loadstkSection(val){
        return loadstkSectionPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        })
    }
    function loadstkSectionPromise(val){
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        var deferred = $q.defer();
        genericResource.findByLike(ProcmtUtils.stkSection,searchInput).success(function (entitySearchResult) {
            deferred.resolve(entitySearchResult);
        }).error(function(){
            deferred.reject("No section unit");
        });
        return deferred.promise;
    }


    function loadBusinessPartner(val){
        return loadBusinessPartnerPromise(val).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        })
    }
    function loadBusinessPartnerPromise(businessPartnerName){
        var searchInput = {
            entity:{},
            fieldNames:[],
            start: 0,
            max: 10
        };
        if(businessPartnerName){
            searchInput.entity.cpnyName = businessPartnerName+'%';
            searchInput.fieldNames.push('cpnyName');
        }
        var deferred = $q.defer();
        genericResource.findByLike(ProcmtUtils.adbnsptnr,searchInput).success(function (entitySearchResult) {
            deferred.resolve(entitySearchResult);
        }).error(function(){
            deferred.reject("No Manufacturer/Supplier");
        });
        return deferred.promise;
    }


    function findArticleByName (value) {
        return findArticle('artName',value);
    }

    function findArticleByCip (value) {
        return findArticle('artPic',value);
    }

    function findArticle(variableName, variableValue){
        return genericResource.findByLikePromissed(ProcmtUtils.stkArtlot2strgsctnsUrlBase, variableName, variableValue)
            .then(function (entitySearchResult) {
                var resultList = entitySearchResult.resultList;
                var displayDatas = [];
                angular.forEach(resultList,function(item){
                    var artName = item.artName;
                    var displayable = {};
                    var sectionArticleLot = item.sectionArticleLot;
                    if(sectionArticleLot) {
                        var artQties = sectionArticleLot.artQties;
                        if(!artQties) artQties = [];
                        angular.forEach(artQties, function(artQty){
                            var displayableStr = "";
                            displayable.artName = artName;
                            displayableStr = artQty.artPic
                            displayableStr += " - "+artName;
                            if(artQty.lotPic) {
                                displayable.lotPic = artQty.lotPic;
                            }
                            if(artQty.section) {
                                displayable.section = artQty.section;
                                displayableStr += " - "+artQty.section;
                            }
                            if(artQty.stockQty) {
                                displayable.stockQty = artQty.stockQty;
                                displayableStr += " - Qty ("+artQty.stockQty+")";
                            }
                            displayable.artPic = artQty.artPic;
                            displayable.sppuPreTax = sectionArticleLot.sppuHT;
                            displayable.minSppuHT = sectionArticleLot.minSppuHT;
                            displayable.sppuTaxIncl = sectionArticleLot.sppuTaxIncl;
                            displayable.sppuCur = sectionArticleLot.sppuCur;
                            displayable.vatPct = sectionArticleLot.vatPct;
                            displayable.vatSalesPct = sectionArticleLot.vatSalesPct;
                            displayable.salesVatAmt = sectionArticleLot.salesVatAmt;
                            displayable.salesWrntyDys = sectionArticleLot.salesWrntyDys;
                            displayable.salesRtrnDays = sectionArticleLot.salesRtrnDays;
                            displayable.pppuPreTax = sectionArticleLot.pppuHT;
                            displayable.purchWrntyDys = sectionArticleLot.purchWrntyDys;
                            displayable.purchRtrnDays = sectionArticleLot.purchRtrnDays;

                            displayable.displayableStr = displayableStr;
                            displayDatas.push(displayable);
                        });
                    }
                });
                return displayDatas;
            });
    }

    function onSelect(item,model,label){
        self.prcmtOrderItemHolder.prcmtPOItem.artPic = item.artPic;
        self.prcmtOrderItemHolder.prcmtPOItem.artName = item.artName;
        self.prcmtOrderItemHolder.prcmtPOItem.pppuPreTax = item.sppuPreTax;
        self.prcmtOrderItemHolder.prcmtPOItem.pppuCur = item.sppuCur;
        self.prcmtOrderItemHolder.prcmtPOItem.vatPct = item.vatPct;
        self.prcmtOrderItemHolder.prcmtPOItem.stkQtyPreOrder =item.stockQty;
    }

    function save(){
        for(var i=0;i<self.poItemsDeleted.length;i++){
            self.prcmtOrderHolder.poItems.push(self.poItemsDeleted[i])
        }
        genericResource.customMethod(ProcmtUtils.urlManageOrder+'/update',self.prcmtOrderHolder).success(function(data){
            self.prcmtOrderHolder = data;
        });

    }

    function close () {
        for(var i=0;i<self.poItemsDeleted.length;i++){
            self.prcmtOrderHolder.poItems.push(self.poItemsDeleted[i])
        }
        genericResource.customMethod(ProcmtUtils.urlManageOrder+'/close',self.prcmtOrderHolder).success(function(data){
            self.prcmtOrderHolder = data;
            self.closeStatus = false;
        });
    }

    function transform () {
        genericResource.customMethod(ProcmtUtils.urlManageOrder+'/transform',self.prcmtOrderHolder).success(function(data){
            PrcmtDeliveryState.setDeliveryHolder(data);
            $location.path('/PrcmtDeliverys/addItem');
        });
    }

    function addItem(){
        self.prcmtOrderHolder.poItems.unshift(self.prcmtOrderItemHolder);
        self.prcmtOrderItemHolder = {};
        $('#artName').focus();
    }
    function deleteItem(index){
        var prcmtOrderItemHolderDeleted = {};
        angular.copy(self.prcmtOrderHolder.poItems[index],prcmtOrderItemHolderDeleted) ;
        self.prcmtOrderHolder.poItems.splice(index,1);
        if(prcmtOrderItemHolderDeleted.prcmtPOItem && prcmtOrderItemHolderDeleted.prcmtPOItem.id) {
            prcmtOrderItemHolderDeleted.deleted = true;
            self.poItemsDeleted.push(prcmtOrderItemHolderDeleted);
        }
    }
    function editItem(index){
        angular.copy(self.prcmtOrderHolder.poItems[index],self.prcmtOrderItemHolder) ;
        self.prcmtOrderHolder.poItems.splice(index,1);
    }

    function showMore(){
        self.show = true;
    }

    function showLess(){
        self.show = false;
    }

}]);
