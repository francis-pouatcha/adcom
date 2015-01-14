
angular.module('adbnsptnrserver').controller('NewBpPtnrCreditDtlsController', function ($scope, $location, locationParser, BpPtnrCreditDtlsResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.bpPtnrCreditDtls = $scope.bpPtnrCreditDtls || {};
    
    $scope.creditAuthrzdList = [
        "true",
        " false"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/BpPtnrCreditDtlss/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BpPtnrCreditDtlsResource.save($scope.bpPtnrCreditDtls, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/BpPtnrCreditDtlss");
    };
});