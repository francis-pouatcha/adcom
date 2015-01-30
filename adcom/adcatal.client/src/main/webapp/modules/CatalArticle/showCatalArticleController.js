'use strict';

angular.module('AdCatal').controller('showCatalArticleCtrl',['$scope','catalArticleService','$routeParams','$location',function($scope,catalArticleService,$routeParams,$location){
    var self = this ;
    $scope.showCatalArticleCtrl = self;
    self.catalArticle = {};
    self.error = "";
    self.previousSuperAgent = previousSuperAgent;
    self.nextSuperAgent = nextSuperAgent;

    function update(){
        catalArticleService.create(self.catalArticle).then(function(result){
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

    function previousSuperAgent(){

    }

    function nextSuperAgent(){

    }

}]);