'use strict';

angular.module('AdSales')
  .factory('saleUtils',['genericResource','$q',function(genericResource,$q){
        var service = {};

        service.urlBase='';
        service.adbnsptnr='/adbnsptnr.server/rest/bplegalptnrids';
        service.catalarticles='/adcatal.server/rest/catalarticles';
        service.orgunits='/adbase.server/rest/orgunits';
        service.stkSection = '/adstock.server/rest/stksections';
        service.sale = '/adsales.server/rest/sale';

        return service;
  }])
.controller('saleCtlr',['$scope','$modal','saleUtils','slsSalesOrderState','genericResource','$routeParams','$location','$q',function($scope,$modal,saleUtils,slsSalesOrderState,genericResource,$routeParams,$location,$q){
    var self = this ;
    $scope.saleCtlr = self;
    self.slsSalesOrderHolder = {
        slsSalesOrder:{},
        slsSOItemsholder:[],
        slsSOPtnrsHolder:[]
    };
        self.slsSalesOrderHolder.slsSalesOrder.soDt = new Date();
        self.slsSalesOrderHolder.slsSalesOrder.grossSPPreTax = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax = 0;
        self.slsSalesOrderHolder.slsSalesOrder.vatAmount = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl = 0;
        self.slsSalesOrderHolder.slsSalesOrder.rebate = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt = 0;
        self.slsSalesOrderHolder.slsSalesOrder.soStatus = 'INITIATED';
    self.slsSalesOrderHolderTab = [];
    self.slsSalesOrderItemHolder = {};
    self.error = "";
    self.loadArticlesByNameLike = loadArticlesByNameLike;
    self.loadArticlesByCipLike = loadArticlesByCipLike;
    self.onSelect = onSelect;
    self.addItem = addItem;
    self.editItem = editItem;
    self.deleteItem = deleteItem;
    self.index=0;
    self.selectedItem;
    self.totalAmountEntered = 0;
    self.loadBusinessPartner = loadBusinessPartner;
    self.slsSOItemsholderDeleted = [];
    self.maxDisctRate;
    self.vatRate;
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

    function loadBusinessPartner(val){
        return genericResource.findByLikePromissed(saleUtils.adbnsptnr, 'cpnyName', val)
            .then(function(entitySearchResult){
                if(!angular.isUndefined(entitySearchResult))
                    return entitySearchResult.resultList;
                else return "";
            });
    }

    function loadArticlesByNameLike(name){
        var searchInput = {
            entity:{
                features:{}
            },
            fieldNames:[],
            start:0,
            max:10
        };
        if(name){
            searchInput.entity.features.artName = name+'%';
            searchInput.fieldNames.push('features.artName') ;
            searchInput.entity.features.langIso2='fr';
        }
        return findByNameStartWith(searchInput).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    }

    function loadArticlesByCipLike(pic){
        var searchInput = {
            entity:{
                features:{}
            },
            fieldNames:[],
            start:0,
            max:10
        };
        if(pic){
            searchInput.entity.pic = pic;
            searchInput.fieldNames.push('pic') ;
        }
        return findByPicLike(searchInput).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    }

    function findByNameStartWith (searchInput) {
        var deferred = $q.defer();
        genericResource.find(saleUtils.catalarticles+'/findByNameStartWith',searchInput)
            .success(function(entitySearchResult) {
                deferred.resolve(entitySearchResult);
            })
            .error(function(){
                deferred.reject("No Article");
            });
        return deferred.promise;
    }

    function findByPicLike (searchInput) {
        var deferred = $q.defer();
        genericResource.find(saleUtils.catalarticles+'/findByPicLike',searchInput)
            .success(function(entitySearchResult) {
                deferred.resolve(entitySearchResult);
            })
            .error(function(){
                deferred.reject("No Article");
            });
        return deferred.promise;
    }

    function onSelect(item,model,label){
        self.slsSalesOrderItemHolder.slsSOItem.artPic = item.pic;
        self.slsSalesOrderItemHolder.slsSOItem.artName = item.features.artName;
        self.slsSalesOrderItemHolder.slsSOItem.sppuPreTax = item.sppu;
        self.maxDisctRate = item.maxDisctRate;
        self.vatRate = item.vatRate;
        calculAmount();
    }

        function calculAmount() {
            if(self.slsSalesOrderItemHolder.slsSOItem.orderedQty){
                if(!self.slsSalesOrderItemHolder.slsSOItem.returnedQty)
                    self.slsSalesOrderItemHolder.slsSOItem.returnedQty = 0;
                self.slsSalesOrderItemHolder.slsSOItem.deliveredQty = parseInt(self.slsSalesOrderItemHolder.slsSOItem.orderedQty) - parseInt(self.slsSalesOrderItemHolder.slsSOItem.returnedQty);
                self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax = self.slsSalesOrderItemHolder.slsSOItem.sppuPreTax*self.slsSalesOrderItemHolder.slsSOItem.deliveredQty;
            }
            if(self.slsSalesOrderItemHolder.slsSOItem.rebate)
                self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax=self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax-parseInt(self.slsSalesOrderItemHolder.slsSOItem.rebate);

            if(self.slsSalesOrderItemHolder.slsSOItem.rebatePct)
                self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax=self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax-((parseInt(self.slsSalesOrderItemHolder.slsSOItem.rebatePct)*self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax)/100);

            if(!self.slsSalesOrderItemHolder.slsSOItem.rebate && !self.slsSalesOrderItemHolder.slsSOItem.rebatePct)
                self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax = self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax;

            if(self.slsSalesOrderItemHolder.slsSOItem.orderedQty){
                self.slsSalesOrderItemHolder.slsSOItem.vatAmount = self.slsSalesOrderItemHolder.slsSOItem.grossSPPreTax*self.vatRate/100;
                self.slsSalesOrderItemHolder.slsSOItem.netSPTaxIncl = self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax+self.slsSalesOrderItemHolder.slsSOItem.vatAmount;
            }
        }
    function totalAmount(){
        self.slsSalesOrderHolder.slsSalesOrder.grossSPPreTax = 0;
        self.slsSalesOrderHolder.slsSalesOrder.rebate = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax = 0;
        self.slsSalesOrderHolder.slsSalesOrder.vatAmount = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl = 0;
        self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt = 0;

        angular.forEach(self.slsSalesOrderHolder.slsSOItemsholder, function (value, key) {
            self.slsSalesOrderHolder.slsSalesOrder.grossSPPreTax = self.slsSalesOrderHolder.slsSalesOrder.grossSPPreTax + value.slsSOItem.grossSPPreTax;
            self.slsSalesOrderHolder.slsSalesOrder.rebate = self.slsSalesOrderHolder.slsSalesOrder.rebate + value.slsSOItem.rebate;
            self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax = self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax + value.slsSOItem.netSPPreTax;
            self.slsSalesOrderHolder.slsSalesOrder.vatAmount = self.slsSalesOrderHolder.slsSalesOrder.vatAmount + value.slsSOItem.vatAmount;
            self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl = self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl + value.slsSOItem.netSPTaxIncl;
        })

        if(self.slsSalesOrderHolder.slsSalesOrder.pymtDscntPct)
            self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt =self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl -(self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax*self.slsSalesOrderHolder.slsSalesOrder.pymtDscntPct/100);

        if(self.slsSalesOrderHolder.slsSalesOrder.pymtDscntAmt){
            self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt=self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl - parseInt(self.slsSalesOrderHolder.slsSalesOrder.pymtDscntAmt);
        }

        if(!self.slsSalesOrderHolder.slsSalesOrder.pymtDscntAmt && !self.slsSalesOrderHolder.slsSalesOrder.pymtDscntPct){
            self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt = self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl;
        }

    }
        
    function addItem(){
        self.slsSalesOrderHolder.slsSOItemsholder.unshift(self.slsSalesOrderItemHolder);
        self.slsSalesOrderItemHolder = {};
        totalAmount();
        $('#artName').focus();
    }
    function deleteItem(index){
        var slsSalesOrderItemHolderDeleted = {};
        angular.copy(self.slsSalesOrderHolder.slsSOItemsholder[index],slsSalesOrderItemHolderDeleted) ;
        self.slsSalesOrderHolder.slsSOItemsholder.splice(index,1);
        if(slsSalesOrderItemHolderDeleted.slsSOItem && slsSalesOrderItemHolderDeleted.slsSOItem.id) {
            slsSalesOrderItemHolderDeleted.deleted = true;
            self.slsSOItemsholderDeleted.push(slsSalesOrderItemHolderDeleted);
        }
        totalAmount();
    }
    function editItem(index){
        angular.copy(self.slsSalesOrderHolder.slsSOItemsholder[index],self.slsSalesOrderItemHolder) ;
        self.slsSalesOrderHolder.slsSOItemsholder.splice(index,1);
        totalAmount();
    }
        function cloturerCmd(){
            for(var i=0;i<self.slsSOItemsholderDeleted.length;i++){
                self.prcmtOrderHolder.poItems.push(self.slsSOItemsholderDeleted[i])
            }
            genericResource.customMethod(saleUtils.sale+'/doSale',self.slsSalesOrderHolder).success(function(data){
                clearSaleOrder();
            });
        }
        function annulerCmd(){
            clearSaleOrder();
        }
        function newCmd(){
            if(self.slsSalesOrderHolder){
                if(self.slsSalesOrderHolder.slsSOItemsholder.length > 0){
                    self.slsSalesOrderHolderTab.push(self.slsSalesOrderHolder);
                    self.index = self.slsSalesOrderHolderTab.length;
                }
            }
            clearSaleOrder();
        }
        function addBptnr(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/SlsSalesOrder/SlsSOPtnr.html',
                controller: self.ModalInstanceAddBptrnCtrl,
                size: size,
                resolve:{
                    slsSOPtnrsHolder: function(){
                        return self.slsSalesOrderHolder.slsSOPtnrsHolder;
                    }
                }
            });
        }
        function clearSaleOrder(){
            self.slsSalesOrderHolder = {
                slsSalesOrder:{},
                slsSOItemsholder:[],
                slsSOPtnr:{}
            };
            self.slsSalesOrderHolder.slsSalesOrder.soDt = new Date();
            self.slsSalesOrderHolder.slsSalesOrder.grossSPPreTax = 0;
            self.slsSalesOrderHolder.slsSalesOrder.netSPPreTax = 0;
            self.slsSalesOrderHolder.slsSalesOrder.vatAmount = 0;
            self.slsSalesOrderHolder.slsSalesOrder.netSPTaxIncl = 0;
            self.slsSalesOrderHolder.slsSalesOrder.rebate = 0;
            self.slsSalesOrderHolder.slsSalesOrder.pymtDscntAmt = 0;
            self.slsSalesOrderHolder.slsSalesOrder.netSalesAmt = 0;
            self.slsSalesOrderHolder.slsSalesOrder.soStatus = 'INITIATED';
            self.slsSalesOrderItemHolder = {};
        }
        function ModalInstanceAddBptrnCtrl($scope, $modalInstance, slsSOPtnrsHolder) {
             $scope.slsSOPtnrsHolder = slsSOPtnrsHolder;
            $scope.loadBusinessPartner = function(val){
                return self.loadBusinessPartner(val);
            }
            $scope.addBptrn = function(){
                var slsSOPtnr = {};
                slsSOPtnr.slsSOPtnr = $scope.slsSOPtnr;
                $scope.slsSOPtnr = {};
                $scope.slsSOPtnrsHolder.push(slsSOPtnr);
            }
            $scope.deleteItem = function (index) {
                $scope.slsSOPtnrsHolder.splice(index,1);
            }

            $scope.cancel = function () {
                self.slsSalesOrderHolder.slsSOPtnrsHolder = $scope.slsSOPtnrsHolder;
                $modalInstance.dismiss('cancel');
            };
            $scope.$on('modal.hide',function(){
                self.slsSalesOrderHolder.slsSOPtnrsHolder = $scope.slsSOPtnrsHolder;
            });
        };

        function previous(){
            if(self.index > 0 ){
                self.index-=1;
                angular.copy(self.slsSalesOrderHolderTab[self.index],self.slsSalesOrderHolder);
                self.slsSalesOrderHolderTab.splice(self.index,1);

            }

        }
        function next(){
            if(self.slsSalesOrderHolderTab.length > self.index){
                angular.copy(self.slsSalesOrderHolderTab[self.index],self.slsSalesOrderHolder);
                self.slsSalesOrderHolderTab.splice(self.index,1);
            }

        }
        function tabLength (){
            return self.slsSalesOrderHolderTab.length;
        }

}]);
