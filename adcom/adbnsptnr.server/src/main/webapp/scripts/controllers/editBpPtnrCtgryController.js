

angular.module('adbnsptnrserver').controller('EditBpPtnrCtgryController', function($scope, $routeParams, $location, BpPtnrCtgryResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.bpPtnrCtgry = new BpPtnrCtgryResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/BpPtnrCtgrys");
        };
        BpPtnrCtgryResource.get({BpPtnrCtgryId:$routeParams.BpPtnrCtgryId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.bpPtnrCtgry);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.bpPtnrCtgry.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/BpPtnrCtgrys");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/BpPtnrCtgrys");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.bpPtnrCtgry.$remove(successCallback, errorCallback);
    };
    
    $scope.ptnrRoleList = [
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