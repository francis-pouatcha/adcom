

angular.module('adstockserver').controller('EditStkInvtryItemController', function($scope, $routeParams, $location, StkInvtryItemResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.stkInvtryItem = new StkInvtryItemResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/StkInvtryItems");
        };
        StkInvtryItemResource.get({StkInvtryItemId:$routeParams.StkInvtryItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.stkInvtryItem);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.stkInvtryItem.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/StkInvtryItems");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/StkInvtryItems");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.stkInvtryItem.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});