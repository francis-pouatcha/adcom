
angular.module('AdCatal').controller('NewCatalArtManufSuppController', function ($scope, $location, locationParser, CatalArtManufSuppResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.catalArtManufSupp = $scope.catalArtManufSupp || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CatalArtManufSupps/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CatalArtManufSuppResource.save($scope.catalArtManufSupp, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CatalArtManufSupps");
    };
});