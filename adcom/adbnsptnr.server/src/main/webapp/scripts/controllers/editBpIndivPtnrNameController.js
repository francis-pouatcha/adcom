

angular.module('adbnsptnrserver').controller('EditBpIndivPtnrNameController', function($scope, $routeParams, $location, BpIndivPtnrNameResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.bpIndivPtnrName = new BpIndivPtnrNameResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/BpIndivPtnrNames");
        };
        BpIndivPtnrNameResource.get({BpIndivPtnrNameId:$routeParams.BpIndivPtnrNameId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.bpIndivPtnrName);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.bpIndivPtnrName.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/BpIndivPtnrNames");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/BpIndivPtnrNames");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.bpIndivPtnrName.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});