
angular.module('AdBnsptnr').controller('NewBpLegalPtnrIdController', function ($scope, $location, locationParser, BpLegalPtnrIdResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.bpLegalPtnrId = $scope.bpLegalPtnrId || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/BpLegalPtnrIds/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BpLegalPtnrIdResource.save($scope.bpLegalPtnrId, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/BpLegalPtnrIds");
    };
});