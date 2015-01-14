

angular.module('AdBnsptnr').controller('EditBpPtnrCreditDtlsController', function($scope, $routeParams, $location, BpPtnrCreditDtlsResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.bpPtnrCreditDtls = new BpPtnrCreditDtlsResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/BpPtnrCreditDtlss");
        };
        BpPtnrCreditDtlsResource.get({BpPtnrCreditDtlsId:$routeParams.BpPtnrCreditDtlsId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.bpPtnrCreditDtls);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.bpPtnrCreditDtls.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/BpPtnrCreditDtlss");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/BpPtnrCreditDtlss");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.bpPtnrCreditDtls.$remove(successCallback, errorCallback);
    };
    
    $scope.creditAuthrzdList = [
        "true",  
        " false"  
    ];
    
    $scope.get();
});