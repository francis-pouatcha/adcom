'use strict';

angular.module('AdCatal').controller('editCatalArticleCtrl',['$scope','catalArticleService','$routeParams',function($scope,catalArticleService,$routeParams){
    var self = this ;
    $scope.editCatalArticleCtrl = self;
    self.catalArticle = {};
    self.update = update;
    self.error = "";

    function update(){
        catalArticleService.create(self.catalArticle).then(function(result){
            $location.path('/catalArticle/show/'+result.identif);
        },function(error){
            self.error = error;
        })

    };

    init();

    function init(){
        load();
    }

    function load(){
        var CatalArticleId = $routeParams.CatalArticleId ;
        catalArticleService.load(CatalArticleId).then(function(result){
            self.catalArticle = result;
        },function(error){
            self.error = error;
        })

    };

}]);