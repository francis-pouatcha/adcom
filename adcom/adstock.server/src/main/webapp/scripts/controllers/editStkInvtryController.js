

angular.module('adstockserver').controller('EditStkInvtryController', function($scope, $routeParams, $location, StkInvtryResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.stkInvtry = new StkInvtryResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/StkInvtrys");
        };
        StkInvtryResource.get({StkInvtryId:$routeParams.StkInvtryId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.stkInvtry);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.stkInvtry.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/StkInvtrys");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/StkInvtrys");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.stkInvtry.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});