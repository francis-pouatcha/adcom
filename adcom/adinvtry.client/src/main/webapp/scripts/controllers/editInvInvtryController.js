

angular.module('AdInvtry').controller('EditInvInvtryController', function($scope, $routeParams, $location, InvInvtryResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.invInvtry = new InvInvtryResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/InvInvtrys");
        };
        InvInvtryResource.get({InvInvtryId:$routeParams.InvInvtryId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.invInvtry);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.invInvtry.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/InvInvtrys");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/InvInvtrys");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.invInvtry.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});