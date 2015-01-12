

angular.module('AdCatal').controller('EditCatalFamilyFeatMapingController', function($scope, $routeParams, $location, CatalFamilyFeatMapingResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.catalFamilyFeatMaping = new CatalFamilyFeatMapingResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CatalFamilyFeatMapings");
        };
        CatalFamilyFeatMapingResource.get({CatalFamilyFeatMapingId:$routeParams.CatalFamilyFeatMapingId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.catalFamilyFeatMaping);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.catalFamilyFeatMaping.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CatalFamilyFeatMapings");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CatalFamilyFeatMapings");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.catalFamilyFeatMaping.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});