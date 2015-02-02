'use strict';
    
angular.module('AdCatal')

.controller('catalArtManufSuppsCtlr',['$scope','catalArtManufSuppResource','$modal','$routeParams',function($scope,catalArtManufSuppResource,$modal,$routeParams){
	
    var self = this ;
    $scope.catalArtManufSuppsCtlr = self;

    self.searchInput = {
        entity:{},
        fieldNames:[]
    };
    self.catalArtManufSupps = [];
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.artIdentif;
    self.openEditForm = openEditForm;
    self.openCreateForm = openCreateForm;
    self.ModalInstanceEditCtrl = ModalInstanceEditCtrl ;
    self.ModalInstanceCreateCtrl = ModalInstanceCreateCtrl ;
    self.handleSelectedItem = handleSelectedItem;
    self.error = "";
    self.deleteItem = deleteItem;
    self.cipOrigines = [];
    
    init();
    function init(){
        self.artIdentif = $routeParams.pic;
        self.searchInput = {
            entity:{},
            fieldNames:[]
        }
        findByLike(self.searchInput);
    }
    function findByLike(searchInput){
    	searchInput.entity.artIdentif=self.artIdentif;
    	searchInput.fieldNames.push('artIdentif');
    	catalArtManufSuppResource.findByLike(searchInput)
    	.success(function(entitySearchResult) {
            self.catalArtManufSupps = entitySearchResult.resultList;
            self.cipOrigines = entitySearchResult.cipOrigines;
        })
    	.error(function(error){
    		self.error = error;
    	});
    }

        function handleSelectedItem(index){
            index = index ? index : 0 ;
            self.selectedIndex = index ;
            angular.copy(self.catalArtManufSupps[self.selectedIndex],self.selectedItem ) ;
        };


        function openCreateForm(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/CatalArticle/CatalArtManufSuppForm.html',
                controller: self.ModalInstanceCreateCtrl,
                size: size,
                resolve: {
                    cipOrigines: function () {
                        return self.cipOrigines;
                    }
                }
            });
        };

        function ModalInstanceCreateCtrl($scope, $modalInstance,cipOrigines) {
            $scope.formCreate = false;
            $scope.catalArtManufSupp;
            $scope.currentAction="Entity_create.title";
            $scope.cipOrigines=cipOrigines;
            $scope.selectedCipOrigine=cipOrigines.length>0?cipOrigines[0]:null;

            $scope.save = function () {
                $scope.catalArtManufSupp.msType=$scope.selectedCipOrigine.enumKey;
                $scope.catalArtManufSupp.artIdentif = self.artIdentif;
            	catalArtManufSuppResource.create($scope.catalArtManufSupp).success(function () {
                    init();
                });
                $modalInstance.dismiss('cancel');
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

        }


        function openEditForm(size,index){
            handleSelectedItem(index);
            var modalInstance = $modal.open({
                templateUrl: 'views/CatalArticle/CatalArtManufSuppForm.html',
                controller: self.ModalInstanceEditCtrl,
                size: size,
                resolve:{
                	catalArtManufSupp: function(){
                        return self.selectedItem;
                    },
                    cipOrigines: function(){
                        return self.cipOrigines;
                    }
                }

            });
        };

        function ModalInstanceEditCtrl($scope, $modalInstance,catalArtManufSupp,cipOrigines) {
            $scope.formCreate = false;
            $scope.catalArtManufSupp = catalArtManufSupp;
            $scope.currentAction="Entity_edit.title";
            $scope.cipOrigines=cipOrigines;
            $scope.selectedCipOrigine=function(){
                for (var i = 0; i < cipOrigines.length; i++) {
                    if(cipOrigines[i].enumKey==catalArtManufSupp.msType) return cipOrigines[i];
                }
                if(cipOrigines.length>0)return cipOrigines[0];
                return null;
            }();

            $scope.isClean = function() {
                return !angular.equals(catalArtManufSupp, $scope.catalArtManufSupp);
            };

            $scope.save = function () {
                $scope.catalArtManufSupp.artIdentif = self.artIdentif;
                $scope.catalArtManufSupp.msType=$scope.selectedCipOrigine.enumKey;
            	catalArtManufSuppResource.update($scope.catalArtManufSupp).success(function(){
                   init();
                });
                $modalInstance.dismiss('cancel');
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

        };

        function deleteItem(index){
            handleSelectedItem();
            catalArtManufSuppResource.deleteById(self.selectedItem.id).success(function(){
                init();
            })
        }

    }]);

