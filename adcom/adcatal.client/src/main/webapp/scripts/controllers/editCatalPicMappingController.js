

angular.module('adcatalserver').controller('EditCatalPicMappingController', function($scope, $routeParams, $location, CatalPicMappingResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.catalPicMapping = new CatalPicMappingResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CatalPicMappings");
        };
        CatalPicMappingResource.get({CatalPicMappingId:$routeParams.CatalPicMappingId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.catalPicMapping);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.catalPicMapping.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CatalPicMappings");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CatalPicMappings");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.catalPicMapping.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});