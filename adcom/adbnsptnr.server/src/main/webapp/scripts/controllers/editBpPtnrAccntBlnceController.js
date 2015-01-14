

angular.module('adbnsptnrserver').controller('EditBpPtnrAccntBlnceController', function($scope, $routeParams, $location, BpPtnrAccntBlnceResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.bpPtnrAccntBlnce = new BpPtnrAccntBlnceResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/BpPtnrAccntBlnces");
        };
        BpPtnrAccntBlnceResource.get({BpPtnrAccntBlnceId:$routeParams.BpPtnrAccntBlnceId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.bpPtnrAccntBlnce);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.bpPtnrAccntBlnce.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/BpPtnrAccntBlnces");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/BpPtnrAccntBlnces");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.bpPtnrAccntBlnce.$remove(successCallback, errorCallback);
    };
    
    $scope.sideList = [
        "D",  
        "C"  
    ];
    
    $scope.get();
});