'use strict';

angular.module('AdProcmt').controller('prcmtDeliveryAddItemCtlr',['$scope','$routeParams','$location','$q','ProcmtUtils','genericResource','PrcmtDeliveryState',function($scope,$routeParams,$location,$q,ProcmtUtils,genericResource,PrcmtDeliveryState){
    var self = this ;
    $scope.prcmtDeliveryAddItemCtlr = self;
    self.prcmtDelivery = {};
    self.prcmtDeliveryItemHolder = {
        dlvryItem:{},
        recvngOus:[],
        strgSctns:[]

    };
    self.closeStatus = false;
    self.prcmtDeliveryItemHolders = [];
    self.prcmtDeliveryItemHoldersDeleted = [];
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
        var data = PrcmtDeliveryState.getDeliveryHolder();
        self.prcmtDelivery = data.delivery;
        self.prcmtDeliveryItemHolders = data.deliveryItems;
        if(self.prcmtDelivery.dlvryStatus=='INITIATED' || self.prcmtDelivery.dlvryStatus=='ONGOING'){
            self.closeStatus = true;
        }
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
        return genericResource.findByLikePromissed(ProcmtUtils.adbnsptnr, 'fullName', val)
            .then(function(entitySearchResult){
                if(!angular.isUndefined(entitySearchResult))
                    return entitySearchResult.resultList;
                else return "";
            });
    }

    function loadOrgUnit(val){
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
        return genericResource.findByLikeWithSearchInputPromissed(ProcmtUtils.orgunits,searchInput)
            .then(function(entitySearchResult){
                if(!angular.isUndefined(entitySearchResult))
                    return entitySearchResult.resultList;
                else return "";
            });
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
        searchInput.entity.name = val;
        searchInput.fieldNames.push('name');
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
        for(var i=0;i<self.prcmtDeliveryItemHoldersDeleted.length;i++){
                prcmtDeliveryHolder.deliveryItems.push(self.prcmtDeliveryItemHoldersDeleted[i])
            }
        genericResource.customMethod(ProcmtUtils.urlManagerDelivery+'/update',prcmtDeliveryHolder).success(function(data){
            self.prcmtDelivery = data.delivery;
            self.prcmtDeliveryItemHolders = data.deliveryItems;
        });
    }

    function close () {
        var prcmtDeliveryHolder = {};
        prcmtDeliveryHolder.delivery = self.prcmtDelivery;
        prcmtDeliveryHolder.deliveryItems = self.prcmtDeliveryItemHolders;
        genericResource.customMethod(ProcmtUtils.urlManagerDelivery+'/close',prcmtDeliveryHolder).success(function(data){
            self.closeStatus = false;
            self.prcmtDelivery = data.delivery;
            self.prcmtDeliveryItemHolders = data.deliveryItems;
        });
    }

    function addItem(){
        if(self.rcvngOrgUnit){
            var rcvngOrgUnitHolder = {rcvngOrgUnit:{}};
            rcvngOrgUnitHolder.rcvngOrgUnit.rcvngOrgUnit = self.rcvngOrgUnit;
            rcvngOrgUnitHolder.rcvngOrgUnit.qtyDlvrd = self.prcmtDeliveryItemHolder.dlvryItem.qtyDlvrd;
            rcvngOrgUnitHolder.rcvngOrgUnit.freeQty = self.prcmtDeliveryItemHolder.dlvryItem.freeQty;
            if(self.prcmtDeliveryItemHolder.dlvryItem.dlvryItemNbr){
                rcvngOrgUnitHolder.rcvngOrgUnit.dlvryItemNbr = self.prcmtDeliveryItemHolder.dlvryItem.dlvryItemNbr;
            }
            self.prcmtDeliveryItemHolder.recvngOus = [];
             self.prcmtDeliveryItemHolder.recvngOus.push(rcvngOrgUnitHolder);
        }
        if(self.strgSection){
            var strgSctnHolder = {strgSctn:{}};
            strgSctnHolder.strgSctn.strgSection = self.strgSection;
            strgSctnHolder.strgSctn.qtyStrd = self.prcmtDeliveryItemHolder.dlvryItem.qtyDlvrd;
            if(self.prcmtDeliveryItemHolder.dlvryItem.dlvryItemNbr){
                strgSctnHolder.strgSctn.dlvryItemNbr = self.prcmtDeliveryItemHolder.dlvryItem.dlvryItemNbr;
            }
            self.prcmtDeliveryItemHolder.strgSctns = [];
            self.prcmtDeliveryItemHolder.strgSctns.push(strgSctnHolder);
        }
        self.prcmtDeliveryItemHolders.unshift(self.prcmtDeliveryItemHolder);
        //CLEAR
        self.prcmtDeliveryItemHolder = {dlvryItem:{},recvngOus:[],strgSctns:[]};
        self.taux = "";
        self.rcvngOrgUnit = "";
        self.strgSection = "";

        calculTotalAmountEntered();
        $('#artName').focus();
    }
    function deleteItem(index){
        var prcmtDeliveryItemHolderDeleted = {};
        angular.copy(self.prcmtDeliveryItemHolders[index],prcmtDeliveryItemHolderDeleted) ;
        self.prcmtDeliveryItemHolders.splice(index,1);
        if(prcmtDeliveryItemHolderDeleted.dlvryItem && prcmtDeliveryItemHolderDeleted.dlvryItem.id){
            prcmtDeliveryItemHolderDeleted.deleted = true;
            self.prcmtDeliveryItemHoldersDeleted.push(prcmtDeliveryItemHolderDeleted);
        }
        calculTotalAmountEntered();
    }
    function editItem(index){
        self.taux = "";
        angular.copy(self.prcmtDeliveryItemHolders[index],self.prcmtDeliveryItemHolder) ;
        self.prcmtDeliveryItemHolders.splice(index,1);
        calculTotalAmountEntered();

        if(self.prcmtDeliveryItemHolder.recvngOus[0]){
            self.rcvngOrgUnit = self.prcmtDeliveryItemHolder.recvngOus[0].rcvngOrgUnit.rcvngOrgUnit;
        }
        if(self.prcmtDeliveryItemHolder.strgSctns[0]){
            self.strgSection = self.prcmtDeliveryItemHolder.strgSctns[0].strgSctn.strgSection;
        }
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
