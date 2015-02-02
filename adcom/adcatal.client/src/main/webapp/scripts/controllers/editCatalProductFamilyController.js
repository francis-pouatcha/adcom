

angular.module('AdCatal').controller('EditCatalProductFamilyController', function($scope, $routeParams, $location, CatalProductFamilyResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.catalProductFamily = new CatalProductFamilyResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/CatalProductFamilies");
        };
        CatalProductFamilyResource.get({CatalProductFamilyId:$routeParams.CatalProductFamilyId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.catalProductFamily);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.catalProductFamily.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CatalProductFamilies");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/CatalProductFamilies");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.catalProductFamily.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});