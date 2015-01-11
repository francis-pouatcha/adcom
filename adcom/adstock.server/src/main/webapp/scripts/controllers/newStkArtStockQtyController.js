
angular.module('adstockserver').controller('NewStkArtStockQtyController', function ($scope, $location, locationParser, StkArtStockQtyResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.stkArtStockQty = $scope.stkArtStockQty || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/StkArtStockQtys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        StkArtStockQtyResource.save($scope.stkArtStockQty, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/StkArtStockQtys");
    };
});