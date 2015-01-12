
angular.module('adstockserver').controller('NewStkLotStockQtyController', function ($scope, $location, locationParser, StkLotStockQtyResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.stkLotStockQty = $scope.stkLotStockQty || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/StkLotStockQtys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        StkLotStockQtyResource.save($scope.stkLotStockQty, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/StkLotStockQtys");
    };
});