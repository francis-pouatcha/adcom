

angular.module('AdBnsptnr').controller('EditBpCtgryDscntController', function($scope, $routeParams, $location, BpCtgryDscntResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.bpCtgryDscnt = new BpCtgryDscntResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/BpCtgryDscnts");
        };
        BpCtgryDscntResource.get({BpCtgryDscntId:$routeParams.BpCtgryDscntId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.bpCtgryDscnt);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.bpCtgryDscnt.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/BpCtgryDscnts");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/BpCtgryDscnts");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.bpCtgryDscnt.$remove(successCallback, errorCallback);
    };
    
    $scope.dscntAuthrzdList = [
        "true",  
        " false"  
    ];
    
    $scope.get();
});