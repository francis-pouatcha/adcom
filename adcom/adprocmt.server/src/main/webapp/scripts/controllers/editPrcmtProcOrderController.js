

angular.module('adprocmtserver').controller('EditPrcmtProcOrderController', function($scope, $routeParams, $location, PrcmtProcOrderResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.prcmtProcOrder = new PrcmtProcOrderResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/PrcmtProcOrders");
        };
        PrcmtProcOrderResource.get({PrcmtProcOrderId:$routeParams.PrcmtProcOrderId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.prcmtProcOrder);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.prcmtProcOrder.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/PrcmtProcOrders");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/PrcmtProcOrders");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.prcmtProcOrder.$remove(successCallback, errorCallback);
    };
    
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
    
    $scope.get();
});