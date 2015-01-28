

angular.module('AdCatal').controller('EditCatalArtFeatMappingController', function($scope, $routeParams, $location, CatalArtFeatMappingResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.catalArtFeatMapping = new CatalArtFeatMappingResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CatalArtFeatMappings");
        };
        CatalArtFeatMappingResource.get({CatalArtFeatMappingId:$routeParams.CatalArtFeatMappingId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.catalArtFeatMapping);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.catalArtFeatMapping.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CatalArtFeatMappings");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CatalArtFeatMappings");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.catalArtFeatMapping.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});