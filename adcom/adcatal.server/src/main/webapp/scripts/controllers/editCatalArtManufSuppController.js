

angular.module('adcatalserver').controller('EditCatalArtManufSuppController', function($scope, $routeParams, $location, CatalArtManufSuppResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.catalArtManufSupp = new CatalArtManufSuppResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CatalArtManufSupps");
        };
        CatalArtManufSuppResource.get({CatalArtManufSuppId:$routeParams.CatalArtManufSuppId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.catalArtManufSupp);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.catalArtManufSupp.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CatalArtManufSupps");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CatalArtManufSupps");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.catalArtManufSupp.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});