'use strict';

angular.module('AdSales')
  .factory('SlsInvoiceUtils',['genericResource','$q',function(genericResource,$q){
        var service = {};

        service.urlBase='';
        service.adbnsptnr='/adbnsptnr.server/rest/bplegalptnrids';
        service.catalarticles='/adcatal.server/rest/catalarticles';
        service.orgunits='/adbase.server/rest/orgunits';
        service.stkSection = '/adstock.server/rest/stksections';
        service.invoice = '/adsales.server/rest/invoice';
        service.sale = '/adsales.server/rest/sale';
        service.stkArtlot2strgsctnsUrlBase = '/adstock.server/rest/stkarticlelot2strgsctns';

        return service;
  }])
.controller('slsInvoiceNewCtlr',['$scope','$modal','SlsInvoiceUtils','slsSalesOrderState','genericResource','$routeParams','$location','$q', 'conversionPrice',function($scope,$modal,SlsInvoiceUtils,slsSalesOrderState,genericResource,$routeParams,$location,$q,conversionPrice){
    var self = this ;
    $scope.slsInvoiceNewCtlr = self;
    self.showBtnClose = true;
    $scope.cur = "XAF";
    $scope.conversionPrice = conversionPrice;
    self.slsInvoiceHolder = {
        slsInvoice:{},
        slsInvceItemsholder:[],
        slsInvcePtnrsHolder:[]
    };
    if(slsSalesOrderState.slsInvoiceHolder){
        angular.copy(slsSalesOrderState.slsInvoiceHolder.slsInvoice, self.slsInvoiceHolder.slsInvoice);
        angular.copy(slsSalesOrderState.slsInvoiceHolder.slsInvceItemsholder, self.slsInvoiceHolder.slsInvceItemsholder);
        angular.copy(slsSalesOrderState.slsInvoiceHolder.slsInvcePtnrsHolder, self.slsInvoiceHolder.slsInvcePtnrsHolder);
        clearSlsSOState();
        if(self.slsInvoiceHolder.slsInvceItemsholder) self.showBtnClose = false;
     }
      function clearSlsSOState(){
          slsSalesOrderState.slsInvoiceHolder.slsInvoice = {};
          slsSalesOrderState.slsInvoiceHolder.slsInvceItemsholder = [];
          slsSalesOrderState.slsInvoiceHolder.slsInvcePtnrsHolder = [];
      }
    
    if(!self.slsInvoiceHolder){
        self.slsInvoiceHolder.slsInvoice.invceDt = new Date();
        self.slsInvoiceHolder.slsInvoice.grossSPPreTax = 0;
        self.slsInvoiceHolder.slsInvoice.netSPPreTax = 0;
        self.slsInvoiceHolder.slsInvoice.vatAmount = 0;
        self.slsInvoiceHolder.slsInvoice.netSPTaxIncl = 0;
        self.slsInvoiceHolder.slsInvoice.rebate = 0;
        self.slsInvoiceHolder.slsInvoice.netSalesAmt = 0;
        self.slsInvoiceHolder.slsInvoice.invceStatus = 'INITIATED';
    }
    
    /*$scope.itemPerPage=slsSalesOrderState.resultHandler.itemPerPage;
    $scope.currentPage=slsSalesOrderState.resultHandler.currentPage();
    $scope.maxSize =slsSalesOrderState.resultHandler.maxResult;*/
    
    self.slsInvoiceHolderTab = [];
    self.slsInvoiceItemHolder = {};
    self.error = "";
    self.onSelect = onSelect;
    self.addItem = addItem;
    self.editItem = editItem;
    self.deleteItem = deleteItem;
    self.index=0;
    self.selectedItem;
    self.totalAmountEntered = 0;
    self.loadBusinessPartner = loadBusinessPartner;
    self.slsInvceItemsholderDeleted = [];
    self.maxDisctRate;
    self.cloturerCmd = cloturerCmd;
    self.annulerCmd = annulerCmd;
    self.newCmd = newCmd;
    self.addBptnr = addBptnr;
    self.previous = previous;
    self.next = next;
    self.calculAmount = calculAmount;
    self.tabLength = tabLength;
    self.ModalInstanceAddBptrnCtrl = ModalInstanceAddBptrnCtrl;
    self.totalAmount = totalAmount;
    self.ptnrRole;
    self.findArticleByName = findArticleByName;
    self.findArticleByCip = findArticleByCip;
    loadPtnrRole();

     $scope.pageChangeHandler = function(num) {
      //Simple Pagination
    };
    
    function loadBusinessPartner(val){
        return genericResource.findByLikePromissed(SlsInvoiceUtils.adbnsptnr, 'cpnyName', val)
            .then(function(entitySearchResult){
                if(!angular.isUndefined(entitySearchResult))
                    return entitySearchResult.resultList;
                else return "";
            });
    }

        function findArticleByName (value) {
            return findArticle('artName',value);
        }

        function findArticleByCip (value) {
            return findArticle('artPic',value);
        }

    function findArticle(variableName, variableValue){
        return genericResource.findByLikePromissed(SlsInvoiceUtils.stkArtlot2strgsctnsUrlBase, variableName, variableValue)
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


        function loadPtnrRole(){
            genericResource.listAll(SlsInvoiceUtils.sale+'/listAllPtnrRole').success(function(data){
                self.ptnrRole = data;
            })
        }

    function onSelect(item,model,label){
        self.slsInvoiceItemHolder.slsInvceItem.artPic = item.artPic;
        self.slsInvoiceItemHolder.slsInvceItem.artName = item.artName;
        self.slsInvoiceItemHolder.slsInvceItem.lotPic = item.lotPic;
        self.slsInvoiceItemHolder.slsInvceItem.sppuPreTax = item.sppuPreTax;
        self.slsInvoiceItemHolder.slsInvceItem.sppuCur = item.sppuCur;
        self.slsInvoiceItemHolder.slsInvceItem.vatPct = item.vatPct;
        self.slsInvoiceItemHolder.slsInvceItem.stkQty =item.stockQty;
        calculAmount();
    }

        function calculAmount() {
            if(self.slsInvoiceItemHolder.slsInvceItem.qty){
                self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax = self.slsInvoiceItemHolder.slsInvceItem.sppuPreTax*self.slsInvoiceItemHolder.slsInvceItem.qty;
            }
            if(self.slsInvoiceItemHolder.slsInvceItem.rebate)
                self.slsInvoiceItemHolder.slsInvceItem.netSPPreTax=self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax-parseInt(self.slsInvoiceItemHolder.slsInvceItem.rebate);

            if(self.slsInvoiceItemHolder.slsInvceItem.rebatePct)
                self.slsInvoiceItemHolder.slsInvceItem.netSPPreTax=self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax-((parseInt(self.slsInvoiceItemHolder.slsInvceItem.rebatePct)*self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax)/100);

            if(!self.slsInvoiceItemHolder.slsInvceItem.rebate && !self.slsInvoiceItemHolder.slsInvceItem.rebatePct)
                self.slsInvoiceItemHolder.slsInvceItem.netSPPreTax = self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax;

            if(self.slsInvoiceItemHolder.slsInvceItem.qty){
                self.slsInvoiceItemHolder.slsInvceItem.vatAmount = self.slsInvoiceItemHolder.slsInvceItem.grossSPPreTax*self.slsInvoiceItemHolder.slsInvceItem.vatPct/100;
                self.slsInvoiceItemHolder.slsInvceItem.netSPTaxIncl = self.slsInvoiceItemHolder.slsInvceItem.netSPPreTax+self.slsInvoiceItemHolder.slsInvceItem.vatAmount;
            }
        }
    function totalAmount(){
        self.slsInvoiceHolder.slsInvoice.grossSPPreTax = 0;
        self.slsInvoiceHolder.slsInvoice.rebate = 0;
        self.slsInvoiceHolder.slsInvoice.netSPPreTax = 0;
        self.slsInvoiceHolder.slsInvoice.vatAmount = 0;
        self.slsInvoiceHolder.slsInvoice.netSPTaxIncl = 0;
        self.slsInvoiceHolder.slsInvoice.netSalesAmt = 0;

        angular.forEach(self.slsInvoiceHolder.slsInvceItemsholder, function (value, key) {
            self.slsInvoiceHolder.slsInvoice.grossSPPreTax = self.slsInvoiceHolder.slsInvoice.grossSPPreTax + value.slsInvceItem.grossSPPreTax;
            self.slsInvoiceHolder.slsInvoice.rebate = self.slsInvoiceHolder.slsInvoice.rebate + parseInt(value.slsInvceItem.rebate);
            self.slsInvoiceHolder.slsInvoice.netSPPreTax = self.slsInvoiceHolder.slsInvoice.netSPPreTax + value.slsInvceItem.netSPPreTax;
            self.slsInvoiceHolder.slsInvoice.vatAmount = self.slsInvoiceHolder.slsInvoice.vatAmount + value.slsInvceItem.vatAmount;
            self.slsInvoiceHolder.slsInvoice.netSPTaxIncl = self.slsInvoiceHolder.slsInvoice.netSPTaxIncl + value.slsInvceItem.netSPTaxIncl;
        })

        if(self.slsInvoiceHolder.slsInvoice.pymtDscntPct)
            self.slsInvoiceHolder.slsInvoice.netSalesAmt =self.slsInvoiceHolder.slsInvoice.netSPTaxIncl -(self.slsInvoiceHolder.slsInvoice.netSPPreTax*self.slsInvoiceHolder.slsInvoice.pymtDscntPct/100);

        if(self.slsInvoiceHolder.slsInvoice.pymtDscntAmt){
            self.slsInvoiceHolder.slsInvoice.netSalesAmt=self.slsInvoiceHolder.slsInvoice.netSPTaxIncl - parseInt(self.slsInvoiceHolder.slsInvoice.pymtDscntAmt);
        }

        if(!self.slsInvoiceHolder.slsInvoice.pymtDscntAmt && !self.slsInvoiceHolder.slsInvoice.pymtDscntPct){
            self.slsInvoiceHolder.slsInvoice.netSalesAmt = self.slsInvoiceHolder.slsInvoice.netSPTaxIncl;
        }

    }
        
    function addItem(){
        var found = false;
        for(var i=0;i<self.slsInvoiceHolder.slsInvceItemsholder.length;i++){
            if(self.slsInvoiceHolder.slsInvceItemsholder[i].slsInvceItem.artPic==self.slsInvoiceItemHolder.slsInvceItem.artPic){
                self.slsInvoiceHolder.slsInvceItemsholder[i].slsInvceItem.qty = parseInt(self.slsInvoiceHolder.slsInvceItemsholder[i].slsInvceItem.qty) + parseInt(self.slsInvoiceItemHolder.slsInvceItem.qty);
                found = true;
                break;
            }
        }
        if(!found){
            self.slsInvoiceHolder.slsInvceItemsholder.unshift(self.slsInvoiceItemHolder);
        }
        self.slsInvoiceItemHolder = {};
        totalAmount();
        $('#artName').focus();
        enableCloseCmd();
    }
    function deleteItem(index){
        var slsInvoiceItemHolderDeleted = {};
        angular.copy(self.slsInvoiceHolder.slsInvceItemsholder[index],slsInvoiceItemHolderDeleted) ;
        self.slsInvoiceHolder.slsInvceItemsholder.splice(index,1);
        if(slsInvoiceItemHolderDeleted.slsInvceItem && slsInvoiceItemHolderDeleted.slsInvceItem.id) {
            slsInvoiceItemHolderDeleted.deleted = true;
            self.slsInvceItemsholderDeleted.push(slsInvoiceItemHolderDeleted);
        }
        totalAmount();
        enableCloseCmd();
    }
    function editItem(index){
        angular.copy(self.slsInvoiceHolder.slsInvceItemsholder[index],self.slsInvoiceItemHolder) ;
        self.slsInvoiceHolder.slsInvceItemsholder.splice(index,1);
        totalAmount();
        enableCloseCmd();
    }
        function cloturerCmd(){
            for(var i=0;i<self.slsInvceItemsholderDeleted.length;i++){
                self.prcmtOrderHolder.poItems.push(self.slsInvceItemsholderDeleted[i])
            }
            genericResource.customMethod(SlsInvoiceUtils.invoice+'/processInvoice',self.slsInvoiceHolder).success(function(data){
                clearSaleOrder();
            });
        }
        function annulerCmd(){
            clearSaleOrder();
        }
        function newCmd(){

            if(self.slsInvoiceHolder){
                if(self.slsInvoiceHolder.slsInvceItemsholder.length > 0){
                    self.slsInvoiceHolderTab.push(self.slsInvoiceHolder);
                    self.index = self.slsInvoiceHolderTab.length-1;
                }
            }
            clearSaleOrder();
        }
        function addBptnr(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/SlsInvoice/SlsInvcePtnr.html',
                controller: self.ModalInstanceAddBptrnCtrl,
                size: size,
                resolve:{
                    slsInvcePtnrsHolder: function(){
                        return self.slsInvoiceHolder.slsInvcePtnrsHolder;
                    }
                }
            });
        }
        function clearSaleOrder(){
            self.slsInvoiceHolder = {
                slsInvoice:{},
                slsInvceItemsholder:[],
                slsInvcePtnrsHolder:[]
            };
            self.slsInvoiceHolder.slsInvoice.invceDt = new Date();
            self.slsInvoiceHolder.slsInvoice.grossSPPreTax = 0;
            self.slsInvoiceHolder.slsInvoice.netSPPreTax = 0;
            self.slsInvoiceHolder.slsInvoice.vatAmount = 0;
            self.slsInvoiceHolder.slsInvoice.netSPTaxIncl = 0;
            self.slsInvoiceHolder.slsInvoice.rebate = 0;
            self.slsInvoiceHolder.slsInvoice.netSalesAmt = 0;
            self.slsInvoiceHolder.slsInvoice.invceStatus = 'INITIATED';
            self.slsInvoiceItemHolder = {};
            self.showBtnClose = true;
        }
        function ModalInstanceAddBptrnCtrl($scope, $modalInstance, slsInvcePtnrsHolder) {
             $scope.slsInvcePtnrsHolder = slsInvcePtnrsHolder;
            $scope.loadBusinessPartner = function(val){
                return self.loadBusinessPartner(val);
            }
            $scope.roleInInvces = self.ptnrRole;
            $scope.addBptrn = function(){
                console.log('hello');
                var slsInvcePtnrHolder = {};
                slsInvcePtnrHolder.slsInvcePtnr = $scope.slsInvcePtnr;
                $scope.slsInvcePtnrsHolder.push(slsInvcePtnrHolder);
                $scope.slsInvcePtnr = {};

            }
            $scope.deleteItem = function (index) {
                $scope.slsInvcePtnrsHolder.splice(index,1);
            }

            $scope.cancel = function () {
                self.slsInvoiceHolder.slsInvcePtnrsHolder = $scope.slsInvcePtnrsHolder;
                $modalInstance.dismiss('cancel');
            };
            $scope.$on('modal.hide',function(){
                self.slsInvoiceHolder.slsInvcePtnrsHolder = $scope.slsInvcePtnrsHolder;
            });
        };

        function previous(){
            if(self.index > 0 ){
                if(self.slsInvoiceHolder){
                    if(self.slsInvoiceHolder.slsInvceItemsholder && self.slsInvoiceHolder.slsInvceItemsholder.length > 0){
                        self.slsInvoiceHolderTab.push(self.slsInvoiceHolder);
                    }
                }
                self.index-=1;
                angular.copy(self.slsInvoiceHolderTab[self.index],self.slsInvoiceHolder);
                self.slsInvoiceHolderTab.splice(self.index,1);
                self.slsInvoiceItemHolder = {};
                enableCloseCmd ();
                console.log(self.index);
                console.log('lenght tab '+self.slsInvoiceHolderTab.length);
            }

        }
        function next(){
            if(self.slsInvoiceHolderTab.length > self.index){
                if(self.slsInvoiceHolder){
                    if(self.slsInvoiceHolder.slsInvceItemsholder && self.slsInvoiceHolder.slsInvceItemsholder.length > 0){
                        self.slsInvoiceHolderTab.push(self.slsInvoiceHolder);
                    }
                }
                self.index+=1;
                angular.copy(self.slsInvoiceHolderTab[self.index],self.slsInvoiceHolder);
                self.slsInvoiceHolderTab.splice(self.index,1);
                self.slsInvoiceItemHolder = {};
                enableCloseCmd ();
                console.log(self.index);
                console.log('lenght tab '+self.slsInvoiceHolderTab.length);
            }

        }
        function tabLength (){
            return self.slsInvoiceHolderTab.length;
        }

        function enableCloseCmd (){
            if(self.slsInvoiceHolder.slsInvceItemsholder && self.slsInvoiceHolder.slsInvceItemsholder.length > 0)
                self.showBtnClose = false;
            else
                self.showBtnClose = true;
        }

}]);
