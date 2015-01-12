
angular.module('AdProcmt').controller('NewPrcmtProcOrderController', function ($scope, $location, locationParser, PrcmtProcOrderResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.prcmtProcOrder = $scope.prcmtProcOrder || {};
    
    $scope.poTriggerModeList = [
        "MANUAL",
        "STOCK_SHORTAGE",
        "MOST_SOLD"
    ];
    
    $scope.poTypeList = [
        "ORDINARY",
        "PACKAGED",
        "SPECIAL"
    ];
    
    $scope.poStatusList = [
        "SUSPENDED",
        "ONGOING",
        "RESTORED",
        "CLOSED"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/PrcmtProcOrders/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        PrcmtProcOrderResource.save($scope.prcmtProcOrder, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/PrcmtProcOrders");
    };
});