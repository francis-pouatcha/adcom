

angular.module('adprocmtserver').controller('EditPrcmtDlvryItemController', function($scope, $routeParams, $location, PrcmtDlvryItemResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.prcmtDlvryItem = new PrcmtDlvryItemResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/PrcmtDlvryItems");
        };
        PrcmtDlvryItemResource.get({PrcmtDlvryItemId:$routeParams.PrcmtDlvryItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.prcmtDlvryItem);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.prcmtDlvryItem.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/PrcmtDlvryItems");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/PrcmtDlvryItems");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.prcmtDlvryItem.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});