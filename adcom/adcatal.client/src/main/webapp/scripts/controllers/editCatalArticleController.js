

angular.module('AdCatal').controller('EditCatalArticleController', function($scope, $routeParams, $location, CatalArticleResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.catalArticle = new CatalArticleResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CatalArticles");
        };
        CatalArticleResource.get({CatalArticleId:$routeParams.CatalArticleId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.catalArticle);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.catalArticle.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CatalArticles");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CatalArticles");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.catalArticle.$remove(successCallback, errorCallback);
    };
    
    $scope.activeList = [
        "true",  
        " false"  
    ];
    $scope.authorizedSaleList = [
        "true",  
        " false"  
    ];
    
    $scope.get();
});