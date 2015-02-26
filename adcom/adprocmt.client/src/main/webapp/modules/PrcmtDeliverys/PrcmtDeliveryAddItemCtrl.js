'use strict';

angular.module('AdProcmt').controller('prcmtDeliveryAddItemCtlr',['$scope','$routeParams','$location','$q','ProcmtUtils','genericResource',function($scope,$routeParams,$location,$q,ProcmtUtils,genericResource){
    var self = this ;
    $scope.prcmtDeliveryAddItemCtlr = self;
    self.prcmtDelivery = {};
    self.prcmtDeliveryItemHolder = {
        dlvryItem:{},
        recvngOus:[],
        strgSctns:[]
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
    self.show = false;
    self.showMore = showMore;
    self.showLess = showLess;
    self.loadBusinessPartner = loadBusinessPartner;
    self.loadOrgUnit = loadOrgUnit;
    self.loadstkSection = loadstkSection;

    load();

    function load(){
        var identif = $routeParams.identif;
        genericResource.findById(ProcmtUtils.urlBase,identif)
            .success(function(data){
                self.prcmtDelivery = data;
            })
            .error(function(error){
                self.error = "No procurement delivery";
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

    function onSelect(item,model,label){
        self.prcmtDeliveryItemHolder.dlvryItem.artPic = item.pic;
        self.prcmtDeliveryItemHolder.dlvryItem.artName  = item.features.artName;
        self.taux = "";
    }

    function save(){
        var prcmtDeliveryHolder = {};
        prcmtDeliveryHolder.delivery = self.prcmtDelivery;
        prcmtDeliveryHolder.deliveryItems = self.prcmtDeliveryItemHolders;
        genericResource.find(ProcmtUtils.urlManagerDelivery+'/update',prcmtDeliveryHolder).success(function(){
            //update succes
        });
    }

    function close () {

    }

    function addItem(){
        self.prcmtDeliveryItemHolder.recvngOus[0].rcvngOrgUnit.qtyDlvrd = self.prcmtDeliveryItemHolder.dlvryItem.qtyDlvrd;
        self.prcmtDeliveryItemHolder.recvngOus[0].rcvngOrgUnit.freeQty = self.prcmtDeliveryItemHolder.dlvryItem.freeQty;
        self.prcmtDeliveryItemHolder.strgSctns[0].strgSctn.qtyStrd = self.prcmtDeliveryItemHolder.dlvryItem.freeQty;
        //self.prcmtDeliveryItemHolder.dlvryItem.grossPPPreTax = self.prcmtDeliveryItemHolder.dlvryItem.pppuPreTax * self.prcmtDeliveryItemHolder.dlvryItem.qtyDlvrd;
        self.prcmtDeliveryItemHolders.push(self.prcmtDeliveryItemHolder);
        self.prcmtDeliveryItemHolder = {dlvryItem:{}};
        self.taux = "";

        calculTotalAmountEntered();
        $('#artName').focus();
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

    function showMore(){
        self.show = true;
    }

    function showLess(){
        self.show = false;
    }

}]);