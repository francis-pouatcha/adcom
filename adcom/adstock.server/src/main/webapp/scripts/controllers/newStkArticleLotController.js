
angular.module('adstockserver').controller('NewStkArticleLotController', function ($scope, $location, locationParser, StkArticleLotResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.stkArticleLot = $scope.stkArticleLot || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/StkArticleLots/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        StkArticleLotResource.save($scope.stkArticleLot, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/StkArticleLots");
    };
});