'use strict';

angular.module('AdSales').controller('saleCtlr',['$scope','saleUtils','slsSalesOrderState','genericResource','$routeParams','$location','$q',function($scope,saleUtils,slsSalesOrderState,genericResource,$routeParams,$location,$q){
    var self = this ;
    $scope.saleCtlr = self;
    self.slsSalesOrder = {
        slsSalesOrder:{},
        slsSOItemsholderholder:[],
        slsSOPtnr:{}
    };
    self.slsSalesOrderItemHolder = {};
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
    self.loadstkSection = loadstkSection;
    self.loadOrgUnit = loadOrgUnit;
    self.loadBusinessPartner = loadBusinessPartner;
    self.slsSOItemsholderDeleted = [];
    self.transform = transform;

    load();

    function load(){
        self.slsSalesOrder = slsSalesOrderState.getOrderHolder();
        if(self.slsSalesOrder.slsSalesOrder.poStatus=='ONGOING'){
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
        genericResource.findByLike(saleUtils.orgunits,searchInput).success(function (entitySearchResult) {
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
        genericResource.findByLike(saleUtils.stkSection,searchInput).success(function (entitySearchResult) {
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
        genericResource.findByLike(saleUtils.adbnsptnr,searchInput).success(function (entitySearchResult) {
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
    }

    function save(){
        for(var i=0;i<self.slsSOItemsholderDeleted.length;i++){
            self.slsSalesOrder.slsSOItemsholder.push(self.slsSOItemsholderDeleted[i])
        }
        genericResource.customMethod(saleUtils.urlManageOrder+'/update',self.slsSalesOrder).success(function(data){
            self.slsSalesOrder = data;
        });

    }

    function close () {
        for(var i=0;i<self.slsSOItemsholderDeleted.length;i++){
            self.slsSalesOrder.slsSOItemsholder.push(self.slsSOItemsholderDeleted[i])
        }
        genericResource.customMethod(saleUtils.urlManageOrder+'/close',self.slsSalesOrder).success(function(data){
            self.slsSalesOrder = data;
            self.closeStatus = false;
        });
    }

    function addItem(){
        self.slsSalesOrder.slsSOItemsholder.unshift(self.slsSalesOrderItemHolder);
        self.slsSalesOrderItemHolder = {};
        $('#artName').focus();
    }
    function deleteItem(index){
        var slsSalesOrderItemHolderDeleted = {};
        angular.copy(self.slsSalesOrder.slsSOItemsholder[index],slsSalesOrderItemHolderDeleted) ;
        self.slsSalesOrder.slsSOItemsholder.splice(index,1);
        if(slsSalesOrderItemHolderDeleted.slsSOItem && slsSalesOrderItemHolderDeleted.slsSOItem.id) {
            slsSalesOrderItemHolderDeleted.deleted = true;
            self.slsSOItemsholderDeleted.push(slsSalesOrderItemHolderDeleted);
        }
    }
    function editItem(index){
        angular.copy(self.slsSalesOrder.slsSOItemsholder[index],self.slsSalesOrderItemHolder) ;
        self.slsSalesOrder.slsSOItemsholder.splice(index,1);
    }

}]);
