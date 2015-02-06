'use strict';

angular.module('AdInvtry').controller('invInvtryShowCtlr',['$scope','invtryResource','catalArticleResource','$routeParams','$location','$q',function($scope,invtryResource,catalArticleResource,$routeParams,$location,$q){
    var self = this ;
    $scope.invInvtryShowCtlr = self;
    self.invInvtry = {};
    self.invInvtryItemHolder = {
        dlvryItem:{}
    };
    self.invInvtryItemHolders = [];
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
        invtryResource.findByIdentif(identif)
            .success(function(data){
                self.invInvtry = data;
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
        self.invInvtryItemHolder.dlvryItem.artPic = item.pic;
        self.invInvtryItemHolder.dlvryItem.artName = item.features.artName;
        self.taux = "";
    }

    function save(){
        var invInvtryHolder = {};
        invInvtryHolder.invInvtry = self.invInvtry;
        invInvtryHolder.invInvtryItems = self.invInvtryItemHolders;
        //save
    }

    function close () {

    }

    function addItem(){
        self.invInvtryItemHolders.push(self.invInvtryItemHolder);
        self.invInvtryItemHolder = {dlvryItem:{}};
        self.taux = "";
        calculTotalAmountEntered();
    }
    function deleteItem(index){
        self.invInvtryItemHolders.splice(index);
        calculTotalAmountEntered();
    }
    function editItem(index){
        self.taux = "";
        angular.copy(self.invInvtryItemHolders[index],self.invInvtryItemHolder) ;
        deleteItem(index);
    }

    function tauxMultiplicateur(){
        if(self.invInvtryItemHolder.dlvryItem.pppuPreTax && self.taux){
            self.invInvtryItemHolder.dlvryItem.sppuPreTax = 1 * self.invInvtryItemHolder.dlvryItem.pppuPreTax + (self.invInvtryItemHolder.dlvryItem.pppuPreTax * (self.taux / 100)) ;
        }
    }

    function calculTotalAmountEntered(){
        self.totalAmountEntered = 0;
        for(var i=0;i<self.invInvtryItemHolders.length;i++){
            self.totalAmountEntered = self.totalAmountEntered + (self.invInvtryItemHolders[i].dlvryItem.pppuPreTax * self.invInvtryItemHolders[i].dlvryItem.qtyDlvrd);
        }

    }

}]);