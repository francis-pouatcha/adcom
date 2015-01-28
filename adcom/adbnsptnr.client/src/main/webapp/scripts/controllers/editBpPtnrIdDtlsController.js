

angular.module('AdBnsptnr').controller('EditBpPtnrIdDtlsController', function($scope, $routeParams, $location, BpPtnrIdDtlsResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.bpPtnrIdDtls = new BpPtnrIdDtlsResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/BpPtnrIdDtlss");
        };
        BpPtnrIdDtlsResource.get({BpPtnrIdDtlsId:$routeParams.BpPtnrIdDtlsId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.bpPtnrIdDtls);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.bpPtnrIdDtls.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/BpPtnrIdDtlss");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/BpPtnrIdDtlss");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.bpPtnrIdDtls.$remove(successCallback, errorCallback);
    };
    
    $scope.ptnrIdTypeList = [
        "IDCARDNBR",  
        "RESDTCARDNBR",  
        "DRIVERLICNBR",  
        "PASSPORTNBR",  
        "EMPLOYEENBR",  
        "MEMBERSHIP",  
        "INSURER",  
        "INSURED"  
    ];
    
    $scope.get();
});