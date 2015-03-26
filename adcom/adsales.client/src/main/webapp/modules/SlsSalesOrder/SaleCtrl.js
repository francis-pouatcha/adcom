'use strict';

angular.module('AdSales')
  .factory('saleUtils',['genericResource','$q',function(genericResource,$q){
        var service = {};

        service.urlBase='';
        service.adbnsptnr='/adbnsptnr.server/rest/bplegalptnrids';
        service.catalarticles='/adcatal.server/rest/catalarticles';
        service.orgunits='/adbase.server/rest/orgunits';
        service.stkSection = '/adstock.server/rest/stksections';

        return service;
  }])
.controller('saleCtlr',['$scope','$modal','saleUtils','slsSalesOrderState','genericResource','$routeParams','$location','$q',function($scope,$modal,saleUtils,slsSalesOrderState,genericResource,$routeParams,$location,$q){
    var self = this ;
    $scope.saleCtlr = self;
    self.slsSalesOrderHolder = {
        slsSalesOrder:{},
        slsSOItemsholder:[],
        slsSOPtnrs:[]
    };
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
            if(self.slsSalesOrderItemHolder.slsSOItem.rebate)
                self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax=self.slsSalesOrderItemHolder.slsSOItem.sppuPreTax-parseInt(self.slsSalesOrderItemHolder.slsSOItem.rebate);

            if(self.slsSalesOrderItemHolder.slsSOItem.rebatePct)
                self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax=self.slsSalesOrderItemHolder.slsSOItem.sppuPreTax-((parseInt(self.slsSalesOrderItemHolder.slsSOItem.rebatePct)*self.slsSalesOrderItemHolder.slsSOItem.sppuPreTax)/100);

            if(!self.slsSalesOrderItemHolder.slsSOItem.rebate && !self.slsSalesOrderItemHolder.slsSOItem.rebatePct)
                self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax = self.slsSalesOrderItemHolder.slsSOItem.sppuPreTax;

            if(self.slsSalesOrderItemHolder.slsSOItem.orderedQty){
                self.slsSalesOrderItemHolder.slsSOItem.vatAmount = (self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax*self.vatRate/100)*parseInt(self.slsSalesOrderItemHolder.slsSOItem.orderedQty);
                self.slsSalesOrderItemHolder.slsSOItem.netSPTaxIncl = (self.slsSalesOrderItemHolder.slsSOItem.netSPPreTax*parseInt(self.slsSalesOrderItemHolder.slsSOItem.orderedQty))+self.slsSalesOrderItemHolder.slsSOItem.vatAmount;
            }

        }

    function addItem(){
        self.slsSalesOrderHolder.slsSOItemsholder.unshift(self.slsSalesOrderItemHolder);
        self.slsSalesOrderItemHolder = {};
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
    }
    function editItem(index){
        angular.copy(self.slsSalesOrderHolder.slsSOItemsholder[index],self.slsSalesOrderItemHolder) ;
        self.slsSalesOrderHolder.slsSOItemsholder.splice(index,1);
    }
        function cloturerCmd(){

        }
        function annulerCmd(){
            self.slsSalesOrderHolder = {
                slsSalesOrder:{},
                slsSOItemsholder:[],
                slsSOPtnr:{}
            };
            self.slsSalesOrderItemHolder = {};
        }
        function newCmd(){
            if(self.slsSalesOrderHolder){
                if(self.slsSalesOrderHolder.slsSOItemsholder.length > 0){
                    self.slsSalesOrderHolderTab.push(self.slsSalesOrderHolder);
                    self.index = self.slsSalesOrderHolderTab.length;
                }
            }
            self.slsSalesOrderHolder = {
                slsSalesOrder:{},
                slsSOItemsholder:[],
                slsSOPtnr:{}
            };
            self.slsSalesOrderItemHolder = {};
        }
        function addBptnr(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/SlsSalesOrder/SlsSOPtnr.html',
                controller: self.ModalInstanceAddBptrnCtrl,
                size: size,
                resolve:{
                    slsSOPtnrs: function(){
                        return self.slsSalesOrderHolder.slsSOPtnrs;
                    }
                }
            });
        }

        function ModalInstanceAddBptrnCtrl($scope, $modalInstance, slsSOPtnrs) {
             $scope.slsSOPtnrs = slsSOPtnrs;
            $scope.loadBusinessPartner = function(val){
                return self.loadBusinessPartner(val);
            }
            $scope.addBptrn = function(){
                var slsSOPtnr = $scope.slsSOPtnr;
                $scope.slsSOPtnr = {};
                $scope.slsSOPtnrs.push(slsSOPtnr);
            }
            $scope.deleteItem = function (index) {
                $scope.slsSOPtnrs.splice(index,1);
            }

            $scope.cancel = function () {
                self.slsSalesOrderHolder.slsSOPtnrs = $scope.slsSOPtnrs;
                $modalInstance.dismiss('cancel');
            };
            $scope.$on('modal.hide',function(){
                self.slsSalesOrderHolder.slsSOPtnrs = $scope.slsSOPtnrs;
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
