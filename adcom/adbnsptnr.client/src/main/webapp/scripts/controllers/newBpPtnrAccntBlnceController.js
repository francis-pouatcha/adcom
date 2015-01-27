
angular.module('AdBnsptnr').controller('NewBpPtnrAccntBlnceController', function ($scope, $location, locationParser, BpPtnrAccntBlnceResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.bpPtnrAccntBlnce = $scope.bpPtnrAccntBlnce || {};
    
    $scope.sideList = [
        "D",
        "C"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/BpPtnrAccntBlnces/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BpPtnrAccntBlnceResource.save($scope.bpPtnrAccntBlnce, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/BpPtnrAccntBlnces");
    };
});