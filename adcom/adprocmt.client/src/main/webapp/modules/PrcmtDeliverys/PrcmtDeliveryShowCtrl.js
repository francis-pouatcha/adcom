'use strict';

angular.module('AdProcmt').controller('prcmtDeliveryShowCtlr',['$scope','prcmtDeliveryResource','catalArticleResource','prcmtDeliveryManagerResource','$routeParams','$location','$q',function($scope,prcmtDeliveryResource,catalArticleResource,prcmtDeliveryManagerResource,$routeParams,$location,$q){
    var self = this ;
    $scope.prcmtDeliveryShowCtlr = self;
    self.prcmtDelivery = {};
    self.prcmtDeliveryItemHolder = {
        dlvryItem:{}
    };
    self.prcmtDeliveryItemHolders = [];
    self.error = "";
    self.loadArticlesByNameLike = loadArticlesByNameLike;
    self.loadArticlesByCipLike = loadArticlesByCipLike;
    self.loading = true;
    self.onSelect = onSelect;
    self.save = save;
    self.close = close;
    self.addItem = addItem;
    self.editItem = editItem;
    self.deleteItem = deleteItem;
    self.selectedIndex;
    self.selectedItem;
    self.taux;
    self.tauxMultiplicateur = tauxMultiplicateur;
    self.totalAmountEntered = 0;

    load();

    function load(){
        var identif = $routeParams.identif;
        prcmtDeliveryResource.findByIdentif(identif)
            .success(function(data){
                self.prcmtDelivery = data;
            })
            .error(function(error){
                self.error = error;
            });
    };

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
        return findCustomArticle(searchInput).then(function(entitySearchResult){
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
        return findCustomArticle(searchInput).then(function(entitySearchResult){
            return entitySearchResult.resultList;
        });
    }

    function findCustomArticle (searchInput) {
        var deferred = $q.defer();
        catalArticleResource.findCustom(searchInput)
            .success(function(entitySearchResult) {
                deferred.resolve(entitySearchResult);
            })
            .error(function(){
                deferred.reject("No Article");
            });
        return deferred.promise;
    }

    function onSelect(item,model,label){
        self.prcmtDeliveryItemHolder.dlvryItem.artPic = item.pic;
        self.prcmtDeliveryItemHolder.dlvryItem.artName = item.features.artName;
        self.taux = "";
    }

    function save(){
        var prcmtDeliveryHolder = {};
        prcmtDeliveryHolder.delivery = self.prcmtDelivery;
        prcmtDeliveryHolder.deliveryItems = self.prcmtDeliveryItemHolders;
        prcmtDeliveryManagerResource.update(prcmtDeliveryHolder).success(function(){
            //update succes
        });
    }

    function close () {

    }

    function addItem(){
        self.prcmtDeliveryItemHolders.push(self.prcmtDeliveryItemHolder);
        self.prcmtDeliveryItemHolder = {dlvryItem:{}};
        self.taux = "";
        calculTotalAmountEntered();
    }
    function deleteItem(index){
        self.prcmtDeliveryItemHolders.splice(index);
        calculTotalAmountEntered();
    }
    function editItem(index){
        self.taux = "";
        angular.copy(self.prcmtDeliveryItemHolders[index],self.prcmtDeliveryItemHolder) ;
        deleteItem(index);
    }

    function tauxMultiplicateur(){
        if(self.prcmtDeliveryItemHolder.dlvryItem.pppuPreTax && self.taux){
            self.prcmtDeliveryItemHolder.dlvryItem.sppuPreTax = 1 * self.prcmtDeliveryItemHolder.dlvryItem.pppuPreTax + (self.prcmtDeliveryItemHolder.dlvryItem.pppuPreTax * (self.taux / 100)) ;
        }
    }

    function calculTotalAmountEntered(){
        self.totalAmountEntered = 0;
        for(var i=0;i<self.prcmtDeliveryItemHolders.length;i++){
            self.totalAmountEntered = self.totalAmountEntered + (self.prcmtDeliveryItemHolders[i].dlvryItem.pppuPreTax * self.prcmtDeliveryItemHolders[i].dlvryItem.qtyDlvrd);
        }

    }

}]);
