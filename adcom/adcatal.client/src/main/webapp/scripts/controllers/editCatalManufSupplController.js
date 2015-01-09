

angular.module('AdCatal').controller('EditCatalManufSupplController', function($scope, $routeParams, $location, CatalManufSupplResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.catalManufSuppl = new CatalManufSupplResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CatalManufSuppls");
        };
        CatalManufSupplResource.get({CatalManufSupplId:$routeParams.CatalManufSupplId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.catalManufSuppl);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.catalManufSuppl.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CatalManufSuppls");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CatalManufSuppls");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.catalManufSuppl.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});