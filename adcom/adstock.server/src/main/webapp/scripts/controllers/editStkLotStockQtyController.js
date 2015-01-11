

angular.module('adstockserver').controller('EditStkLotStockQtyController', function($scope, $routeParams, $location, StkLotStockQtyResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.stkLotStockQty = new StkLotStockQtyResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/StkLotStockQtys");
        };
        StkLotStockQtyResource.get({StkLotStockQtyId:$routeParams.StkLotStockQtyId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.stkLotStockQty);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.stkLotStockQty.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/StkLotStockQtys");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/StkLotStockQtys");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.stkLotStockQty.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});