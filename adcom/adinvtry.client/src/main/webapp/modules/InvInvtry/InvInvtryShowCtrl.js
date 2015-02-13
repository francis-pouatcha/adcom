'use strict';

angular.module('AdInvtry').controller('invInvtryShowCtlr',['$scope','invtryResource','catalArticleResource','$routeParams','$location','$q','stkArtResource',function($scope,invtryResource,catalArticleResource,$routeParams,$location,$q,stkArtResource){
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
            var articles = entitySearchResult.resultList;
            return findStkByArtPic(articles).then(function(result){
                return result;
            });
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
            var articles = entitySearchResult.resultList;
            return findStkByArtPic(articles).then(function(result){
                return result;
            });
        });
    }
    
    function findStkByArtPic(articles) {
        var deferred = $q.defer();
        if(angular.isDefined(articles)) {
            var artPics = [];
            angular.forEach(articles, function(article){
               artPics.push(article.pic) 
            });
            var searchInput = {};
            searchInput.artPics = artPics;
            searchInput.applyLike = true;
            stkArtResource.findStkByArtPic(searchInput).success(function(data){
                deferred.resolve(data.articleLots);
            }).error(function(error){
                deferred.reject("No Article");
            });
        }
        return deferred.promise;
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
        self.invInvtryItemHolder.invInvtryItem.artPic = item.pic;
        self.invInvtryItemHolder.invInvtryItem.artName = item.features.artName;
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
        if(self.invInvtryItemHolder.invInvtryItem.pppuPreTax && self.taux){
            self.invInvtryItemHolder.invInvtryItem.sppuPreTax = 1 * self.invInvtryItemHolder.invInvtryItem.pppuPreTax + (self.invInvtryItemHolder.invInvtryItem.pppuPreTax * (self.taux / 100)) ;
        }
    }

    function calculTotalAmountEntered(){
        self.totalAmountEntered = 0;
        for(var i=0;i<self.invInvtryItemHolders.length;i++){
            self.totalAmountEntered = self.totalAmountEntered + (self.invInvtryItemHolders[i].invInvtryItem.pppuPreTax * self.invInvtryItemHolders[i].invInvtryItem.qtyDlvrd);
        }

    }

}]);