

angular.module('adbnsptnrserver').controller('EditBpLegalPtnrIdController', function($scope, $routeParams, $location, BpLegalPtnrIdResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.bpLegalPtnrId = new BpLegalPtnrIdResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/BpLegalPtnrIds");
        };
        BpLegalPtnrIdResource.get({BpLegalPtnrIdId:$routeParams.BpLegalPtnrIdId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.bpLegalPtnrId);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.bpLegalPtnrId.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/BpLegalPtnrIds");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/BpLegalPtnrIds");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.bpLegalPtnrId.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});