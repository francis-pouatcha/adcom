

angular.module('adprocmtserver').controller('EditPrcmtPOItemController', function($scope, $routeParams, $location, PrcmtPOItemResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.prcmtPOItem = new PrcmtPOItemResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/PrcmtPOItems");
        };
        PrcmtPOItemResource.get({PrcmtPOItemId:$routeParams.PrcmtPOItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.prcmtPOItem);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.prcmtPOItem.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/PrcmtPOItems");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/PrcmtPOItems");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.prcmtPOItem.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});