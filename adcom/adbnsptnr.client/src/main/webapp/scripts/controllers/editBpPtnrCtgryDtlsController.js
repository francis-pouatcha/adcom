

angular.module('AdBnsptnr').controller('EditBpPtnrCtgryDtlsController', function($scope, $routeParams, $location, BpPtnrCtgryDtlsResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.bpPtnrCtgryDtls = new BpPtnrCtgryDtlsResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/BpPtnrCtgryDtlss");
        };
        BpPtnrCtgryDtlsResource.get({BpPtnrCtgryDtlsId:$routeParams.BpPtnrCtgryDtlsId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.bpPtnrCtgryDtls);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.bpPtnrCtgryDtls.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/BpPtnrCtgryDtlss");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/BpPtnrCtgryDtlss");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.bpPtnrCtgryDtls.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});