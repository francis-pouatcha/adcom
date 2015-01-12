

angular.module('adprocmtserver').controller('EditPrcmtDeliveryController', function($scope, $routeParams, $location, PrcmtDeliveryResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.prcmtDelivery = new PrcmtDeliveryResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/PrcmtDeliverys");
        };
        PrcmtDeliveryResource.get({PrcmtDeliveryId:$routeParams.PrcmtDeliveryId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.prcmtDelivery);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.prcmtDelivery.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/PrcmtDeliverys");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/PrcmtDeliverys");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.prcmtDelivery.$remove(successCallback, errorCallback);
    };
    
    $scope.dlvryStatusList = [
        "SUSPENDED",  
        "ONGOING",  
        "RESTORED",  
        "CLOSED"  
    ];
    
    $scope.get();
});