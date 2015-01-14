
angular.module('AdBnsptnr').controller('NewBpPtnrIdDtlsController', function ($scope, $location, locationParser, BpPtnrIdDtlsResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.bpPtnrIdDtls = $scope.bpPtnrIdDtls || {};
    
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
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/BpPtnrIdDtlss/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BpPtnrIdDtlsResource.save($scope.bpPtnrIdDtls, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/BpPtnrIdDtlss");
    };
});