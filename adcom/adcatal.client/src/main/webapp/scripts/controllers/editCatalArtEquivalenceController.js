

angular.module('AdCatal').controller('EditCatalArtEquivalenceController', function($scope, $routeParams, $location, CatalArtEquivalenceResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.catalArtEquivalence = new CatalArtEquivalenceResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CatalArtEquivalences");
        };
        CatalArtEquivalenceResource.get({CatalArtEquivalenceId:$routeParams.CatalArtEquivalenceId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.catalArtEquivalence);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.catalArtEquivalence.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CatalArtEquivalences");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CatalArtEquivalences");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.catalArtEquivalence.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});