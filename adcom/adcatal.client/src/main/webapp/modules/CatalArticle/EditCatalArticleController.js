'use strict';

angular.module('AdCatal').controller('editCatalArticleCtrl',['$scope','catalArticleService','$routeParams','$location',function($scope,catalArticleService,$routeParams,$location){
    var self = this ;
    $scope.editCatalArticleCtrl = self;
    self.catalArticle = {};
    self.update = update;
    self.error = "";

    function update(){
        catalArticleService.update(self.catalArticle).then(function(result){
            $location.path('/CatalArticles/show/'+result.identif);
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