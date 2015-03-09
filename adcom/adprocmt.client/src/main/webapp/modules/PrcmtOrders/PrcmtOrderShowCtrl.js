'use strict';

angular.module('AdProcmt').controller('prcmtOrderShowCtlr',['$scope','ProcmtUtils','PrcmtOrderState','genericResource','$routeParams','$location','$q',function($scope,ProcmtUtils,PrcmtOrderState,genericResource,$routeParams,$location,$q){
    var self = this ;
    $scope.prcmtOrderShowCtlr = self;
    self.prcmtOrderHolder = {
        prcmtOrder:{},
        prcmtOrderItemHolders:[]
    }
    self.prcmtOrderItemHolders = [];
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
    self.totalAmountEntered = 0;

    load();

    function load(){
        self.prcmtOrderHolder = PrcmtOrderState.getOrderHolder();
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
        genericResource.find(ProcmtUtils.catalarticles+'/findByNameStartWith',searchInput)
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
        genericResource.find(ProcmtUtils.catalarticles+'/findByPicLike',searchInput)
            .success(function(entitySearchResult) {
                deferred.resolve(entitySearchResult);
            })
            .error(function(){
                deferred.reject("No Article");
            });
        return deferred.promise;
    }

    function onSelect(item,model,label){
        self.prcmtOrderItemHolder.dlvryItem.artPic = item.pic;
        self.prcmtOrderItemHolder.dlvryItem.artName = item.features.artName;
        self.taux = "";
    }

    function save(){
        var prcmtOrderHolder = {};
        prcmtOrderHolder.Order = self.prcmtOrder;
        prcmtOrderHolder.OrderItems = self.prcmtOrderItemHolders;

    }

    function close () {

    }

    function addItem(){
        self.prcmtOrderItemHolders.push(self.prcmtOrderItemHolder);
        self.prcmtOrderItemHolder = {dlvryItem:{}};
        self.taux = "";
        calculTotalAmountEntered();
        $('#artName').focus();
    }
    function deleteItem(index){
        self.prcmtOrderItemHolders.splice(index);
        calculTotalAmountEntered();
    }
    function editItem(index){
        self.taux = "";
        angular.copy(self.prcmtOrderItemHolders[index],self.prcmtOrderItemHolder) ;
        deleteItem(index);
    }

    function calculTotalAmountEntered(){
        self.totalAmountEntered = 0;
        for(var i=0;i<self.prcmtOrderItemHolders.length;i++){
            self.totalAmountEntered = self.totalAmountEntered + (self.prcmtOrderItemHolders[i].dlvryItem.pppuPreTax * self.prcmtOrderItemHolders[i].dlvryItem.qtyDlvrd);
        }

    }

}]);
