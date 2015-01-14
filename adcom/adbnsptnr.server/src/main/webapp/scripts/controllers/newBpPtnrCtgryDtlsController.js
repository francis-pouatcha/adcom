
angular.module('adbnsptnrserver').controller('NewBpPtnrCtgryDtlsController', function ($scope, $location, locationParser, BpPtnrCtgryDtlsResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.bpPtnrCtgryDtls = $scope.bpPtnrCtgryDtls || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/BpPtnrCtgryDtlss/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BpPtnrCtgryDtlsResource.save($scope.bpPtnrCtgryDtls, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/BpPtnrCtgryDtlss");
    };
});