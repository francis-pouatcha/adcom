'use strict';
    
angular.module('AdCatal')

.controller('catalPicMappingCtrl',['$scope','catalPicMappingResource','$modal',function($scope,catalPicMappingResource,$modal){
	
    var self = this ;
    $scope.catalArticleCtrl = self;

    self.searchInput = {
        entity:{},
        fieldNames:[]
    };
    self.catalPicMappings = [];
    self.selectedItem = {} ;
    self.selectedIndex  ;
    self.openEditForm = openEditForm;
    self.openCreateForm = openCreateForm;
    self.ModalInstanceEditCtrl = ModalInstanceEditCtrl ;
    self.ModalInstanceCreateCtrl = ModalInstanceCreateCtrl ;
    self.handleSelectedItem = handleSelectedItem;

        init();
    function init(){
        self.searchInput = {
            entity:{},
            fieldNames:[]
        }
        findByLike(self.searchInput);
    }
    function findByLike(searchInput){
        catalPicMappingResource.findByLike(searchInput).success(function(entitySearchResult) {
            self.catalPicMappings = entitySearchResult.resultList;
        });
    }

        function handleSelectedItem(index){
            index = index ? index : 0 ;
            self.selectedIndex = index ;
            angular.copy(self.contacts[self.selectedIndex],self.selectedContact ) ;
        };


        function openCreateForm(size){
            var modalInstance = $modal.open({
                templateUrl: 'views/CatalArticle/picMapping-form.html',
                controller: self.ModalInstanceCreateCtrl,
                size: size
            });
        };

        function ModalInstanceCreateCtrl($scope, $modalInstance) {
            $scope.formCreate = false;
            $scope.catalPicMapping;

            $scope.save = function () {
                catalPicMappingResource.create($scope.catalPicMapping).success(function () {
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
                templateUrl: 'views/CatalArticle/picMApping-form.html',
                controller: self.ModalInstanceEditCtrl,
                size: size,
                resolve:{
                    catalPicMapping: function(){
                        return self.selectedItem;
                    }
                }
            });
        };

        function ModalInstanceEditCtrl($scope, $modalInstance,catalPicMapping,$timeout) {
            $scope.formCreate = false;
            $scope.catalPicMapping = catalPicMapping;

            $scope.isClean = function() {
                return angular.equals(catalPicMapping, $scope.catalPicMapping);
            };


            $scope.save = function () {
                catalPicMappingResource.update($scope.catalPicMapping).success(function(){
                   init();
                });
                $modalInstance.dismiss('cancel');
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

        };
    }]);

