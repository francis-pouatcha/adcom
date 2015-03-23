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
.controller('saleCtlr',['$scope','saleUtils','slsSalesOrderState','genericResource','$routeParams','$location','$q',function($scope,saleUtils,slsSalesOrderState,genericResource,$routeParams,$location,$q){
    var self = this ;
    $scope.saleCtlr = self;
    self.slsSalesOrderHolder = {
        slsSalesOrder:{},
        slsSOItemsholder:[],
        slsSOPtnr:{}
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
    self.selectedIndex;
    self.selectedItem;
    self.totalAmountEntered = 0;
    self.loadBusinessPartner = loadBusinessPartner;
    self.slsSOItemsholderDeleted = [];
    self.maxDisctRate;
    self.vatRate;
    self.cloturerCmd = cloturerCmd;
    self.annulerCmd = annulerCmd;
    self.newCmd = newCmd;
    self.addClient = addClient;
    self.previous = previous;
    self.next = next;
    self.calculAmount = calculAmount;

    function loadBusinessPartner(val){
        return genericResource.findByLikePromissed(saleUtils.adbnsptnr, 'fullName', val)
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

        }
        function newCmd(){

        }
        function addClient(){

        }
        function previous(){

        }
        function next(){

        }

}]);
