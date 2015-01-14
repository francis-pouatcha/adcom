

angular.module('AdBnsptnr').controller('EditInsurranceController', function($scope, $routeParams, $location, InsurranceResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.insurrance = new InsurranceResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/Insurrances");
        };
        InsurranceResource.get({InsurranceId:$routeParams.InsurranceId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.insurrance);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.insurrance.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Insurrances");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Insurrances");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.insurrance.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});