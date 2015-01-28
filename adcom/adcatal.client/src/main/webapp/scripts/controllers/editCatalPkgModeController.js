

angular.module('AdCatal').controller('EditCatalPkgModeController', function($scope, $routeParams, $location, CatalPkgModeResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.catalPkgMode = new CatalPkgModeResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CatalPkgModes");
        };
        CatalPkgModeResource.get({CatalPkgModeId:$routeParams.CatalPkgModeId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.catalPkgMode);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.catalPkgMode.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CatalPkgModes");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CatalPkgModes");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.catalPkgMode.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});