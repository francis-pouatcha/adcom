

angular.module('adbnsptnrserver').controller('EditBpBnsPtnrController', function($scope, $routeParams, $location, BpBnsPtnrResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.bpBnsPtnr = new BpBnsPtnrResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/BpBnsPtnrs");
        };
        BpBnsPtnrResource.get({BpBnsPtnrId:$routeParams.BpBnsPtnrId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.bpBnsPtnr);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.bpBnsPtnr.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/BpBnsPtnrs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/BpBnsPtnrs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.bpBnsPtnr.$remove(successCallback, errorCallback);
    };
    
    $scope.ptnrTypeList = [
        "INDIVIDUAL",  
        "LEGAL"  
    ];
    
    $scope.get();
});