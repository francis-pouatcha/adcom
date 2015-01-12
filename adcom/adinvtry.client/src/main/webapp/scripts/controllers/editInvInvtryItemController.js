

angular.module('AdInvtry').controller('EditInvInvtryItemController', function($scope, $routeParams, $location, InvInvtryItemResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.invInvtryItem = new InvInvtryItemResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/InvInvtryItems");
        };
        InvInvtryItemResource.get({InvInvtryItemId:$routeParams.InvInvtryItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.invInvtryItem);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.invInvtryItem.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/InvInvtryItems");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/InvInvtryItems");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.invInvtryItem.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});