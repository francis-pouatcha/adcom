'use strict';
    
angular.module('AdCatal')

.controller('catalPicMappingsCtlr',['$scope','catalPicMappingResource','$modal','$routeParams',function($scope,catalPicMappingResource,$modal,$routeParams){
	
    var self = this ;
    $scope.catalPicMappingsCtlr = self;

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
    self.error = "";
    
    init();
    function init(){
        self.searchInput = {
            entity:{},
            fieldNames:[]
        }
        findByLike(self.searchInput);
    }
    function findByLike(searchInput){
    	searchInput.entity.artIdentif=$routeParams.pic;
    	searchInput.fieldNames.push('artIdentif');
    	catalPicMappingResource.findByLike(searchInput)
    	.success(function(entitySearchResult) {
            self.catalPicMappings = entitySearchResult.resultList;
        })
    	.error(function(error){
    		self.error = error;
    	});
    }

    function handleSelectedItem(index){
        index = index ? index : 0 ;
        self.selectedIndex = index ;
        angular.copy(self.catalPicMappings[self.selectedIndex],self.selectedItem) ;
    };


    function openCreateForm(size){
        var modalInstance = $modal.open({
            templateUrl: 'views/CatalArticle/CatalPicMappingForm.html',
            controller: self.ModalInstanceCreateCtrl,
            size: size
        });
    };

    function ModalInstanceCreateCtrl($scope, $modalInstance) {
        $scope.formCreate = false;
        $scope.catalPicMapping;
        $scope.currentAction="Entity_create.title";
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
            templateUrl: 'views/CatalArticle/CatalPicMappingForm.html',
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
        $scope.currentAction="Entity_edit.title";
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

