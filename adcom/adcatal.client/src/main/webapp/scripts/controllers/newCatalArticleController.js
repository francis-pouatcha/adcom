
angular.module('AdCatal').controller('NewCatalArticleController', function ($scope, $location, locationParser, CatalArticleResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.catalArticle = $scope.catalArticle || {};
    
    $scope.activeList = [
        "true",
        " false"
    ];
    
    $scope.authorizedSaleList = [
        "true",
        " false"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CatalArticles/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CatalArticleResource.save($scope.catalArticle, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CatalArticles");
    };
});