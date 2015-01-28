

angular.module('AdCatal').controller('EditCatalArtDetailConfigController', function($scope, $routeParams, $location, CatalArtDetailConfigResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.catalArtDetailConfig = new CatalArtDetailConfigResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CatalArtDetailConfigs");
        };
        CatalArtDetailConfigResource.get({CatalArtDetailConfigId:$routeParams.CatalArtDetailConfigId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.catalArtDetailConfig);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.catalArtDetailConfig.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CatalArtDetailConfigs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CatalArtDetailConfigs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.catalArtDetailConfig.$remove(successCallback, errorCallback);
    };
    
    $scope.activeList = [
        "true",  
        " false"  
    ];
    
    $scope.get();
});