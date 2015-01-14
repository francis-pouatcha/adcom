

angular.module('adbnsptnrserver').controller('EditBpCtgryOfPtnrController', function($scope, $routeParams, $location, BpCtgryOfPtnrResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.bpCtgryOfPtnr = new BpCtgryOfPtnrResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/BpCtgryOfPtnrs");
        };
        BpCtgryOfPtnrResource.get({BpCtgryOfPtnrId:$routeParams.BpCtgryOfPtnrId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.bpCtgryOfPtnr);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.bpCtgryOfPtnr.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/BpCtgryOfPtnrs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/BpCtgryOfPtnrs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.bpCtgryOfPtnr.$remove(successCallback, errorCallback);
    };
    
    $scope.whenInRoleList = [
        "CUSTOMER",  
        "SUPPLIER",  
        "EMPLOYER",  
        "STAFF",  
        "MANUFACTURER",  
        "GOVERNMENT",  
        "BROKER",  
        "SHAREHOLDER",  
        "BANKER"  
    ];
    
    $scope.get();
});