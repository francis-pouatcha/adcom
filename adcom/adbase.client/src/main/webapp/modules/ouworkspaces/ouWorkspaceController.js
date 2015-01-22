(function () {
    'use strict';
    angular.module('AdBase').controller('ouWorkspaceController',ouWorkspaceController);

    ouWorkspaceController.$inject = ['$scope', 'ouWorkspaceService','$location','$routeParams'];

    function ouWorkspaceController($scope,ouWorkspaceService, $location,$routeParams){
        var self = this ;
        self.ouWorkspaceDTOHolder= {};
        self.error = "";
        self.saveWorkspace = saveWorkspace;
        self.cachedDto = [];
        self.store = store;
        
        
        init();
        
        function init() {
            findActiveOuWorkspaces();
        }
        
        function findActiveOuWorkspaces(){
            var targetOuIdentif = $routeParams.targetOuIdentif;
            ouWorkspaceService.findActivesOuWorkspaces(targetOuIdentif).then(function(result){
                self.ouWorkspaceDTOHolder = result;
            },function(error){
                self.error = error;
            });
        };
        
        function saveWorkspace() {
            self.ouWorkspaceDTOHolder.dtos = self.cachedDto;
            ouWorkspaceService.assignWorkspaces(self.ouWorkspaceDTOHolder).then(function(result){
                self.ouWorkspaceDTOHolder = result;
            },function(error){
                self.error = error;
            });
        }
        
        function store(dto) {
            if(self.cachedDto.length >= 1) {
                angular.forEach(self.cachedDto,function(value,index){
                if(value.identif === dto.identif) {
                    self.cachedDto.splice(index);
                }else {
                    self.cachedDto.push(dto);
                };
              });
            }else {
                self.cachedDto.push(dto);
            }
        }
    };



})();