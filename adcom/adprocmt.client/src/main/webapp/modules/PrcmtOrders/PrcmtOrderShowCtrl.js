'use strict';

angular.module('AdProcmt').controller('prcmtOrderShowCtlr',['$scope','ProcmtUtils','PrcmtOrderState','genericResource','$routeParams','$location','$q',function($scope,ProcmtUtils,PrcmtOrderState,genericResource,$routeParams,$location,$q){
    var self = this ;
    $scope.prcmtOrderShowCtlr = self;
    self.prcmtOrderHolder = {
        prcmtProcOrder:{},
        prcmtOrderItemHolders:[]
    };
    self.prcmtOrderItemHolder = {};
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
    self.closeStatus = false;
    self.show = false;
    self.showMore = showMore;
    self.showLess = showLess;
    self.loadstkSection = loadstkSection;
    self.loadOrgUnit = loadOrgUnit;
    self.loadBusinessPartner = loadBusinessPartner;
    self.prcmtOrderItemHoldersDeleted = [];

    load();

    function load(){
        self.prcmtOrderHolder = PrcmtOrderState.getOrderHolder();
        if(self.prcmtOrderHolder.prcmtProcOrder.poStatus=='ONGOING'){
            self.closeStatus = true;
        }
    };


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
        self.prcmtOrderItemHolder.prcmtPOItem.artPic = item.pic;
        self.prcmtOrderItemHolder.prcmtPOItem.artName = item.features.artName;
    }

    function save(){

        for(var i=0;i<self.prcmtOrderItemHoldersDeleted.length;i++){
            self.prcmtOrderHolder.prcmtOrderItemHolders.push(self.prcmtOrderItemHoldersDeleted[i])
        }
        genericResource.customMethod(ProcmtUtils.urlManageOrder+'/update',self.prcmtOrderHolder).success(function(data){
            self.prcmtOrderHolder = data;
        });

    }

    function close () {

    }

    function addItem(){
        self.prcmtOrderHolder.prcmtOrderItemHolders.unshift(self.prcmtOrderItemHolder);
        self.prcmtOrderItemHolder = {};
        $('#artName').focus();
    }
    function deleteItem(index){
        var prcmtOrderItemHolderDeleted = {};
        angular.copy(self.prcmtOrderHolder.prcmtOrderItemHolders[index],prcmtOrderItemHolderDeleted) ;
        self.prcmtOrderHolder.prcmtOrderItemHolders.splice(index,1);
        if(prcmtOrderItemHolderDeleted.prcmtPOItem && prcmtOrderItemHolderDeleted.prcmtPOItem.id) {
            prcmtOrderItemHolderDeleted.deleted = true;
            self.prcmtOrderItemHoldersDeleted.push(prcmtOrderItemHolderDeleted);
        }
    }
    function editItem(index){
        angular.copy(self.prcmtOrderHolder.prcmtOrderItemHolders[index],self.prcmtOrderItemHolder) ;
        self.prcmtOrderHolder.prcmtOrderItemHolders.splice(index,1);
    }

    function showMore(){
        self.show = true;
    }

    function showLess(){
        self.show = false;
    }

}]);
