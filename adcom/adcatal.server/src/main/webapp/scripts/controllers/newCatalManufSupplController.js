
angular.module('adcatalserver').controller('NewCatalManufSupplController', function ($scope, $location, locationParser, CatalManufSupplResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.catalManufSuppl = $scope.catalManufSuppl || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CatalManufSuppls/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CatalManufSupplResource.save($scope.catalManufSuppl, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CatalManufSuppls");
    };
});