

angular.module('adstockserver').controller('EditStkArtStockQtyController', function($scope, $routeParams, $location, StkArtStockQtyResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.stkArtStockQty = new StkArtStockQtyResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/StkArtStockQtys");
        };
        StkArtStockQtyResource.get({StkArtStockQtyId:$routeParams.StkArtStockQtyId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.stkArtStockQty);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.stkArtStockQty.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/StkArtStockQtys");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/StkArtStockQtys");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.stkArtStockQty.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});