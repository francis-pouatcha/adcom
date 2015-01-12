
angular.module('adprocmtserver').controller('NewPrcmtDeliveryController', function ($scope, $location, locationParser, PrcmtDeliveryResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.prcmtDelivery = $scope.prcmtDelivery || {};
    
    $scope.dlvryStatusList = [
        "SUSPENDED",
        "ONGOING",
        "RESTORED",
        "CLOSED"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/PrcmtDeliverys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        PrcmtDeliveryResource.save($scope.prcmtDelivery, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/PrcmtDeliverys");
    };
});