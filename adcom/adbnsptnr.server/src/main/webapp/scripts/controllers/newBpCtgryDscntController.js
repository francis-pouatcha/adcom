
angular.module('adbnsptnrserver').controller('NewBpCtgryDscntController', function ($scope, $location, locationParser, BpCtgryDscntResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.bpCtgryDscnt = $scope.bpCtgryDscnt || {};
    
    $scope.dscntAuthrzdList = [
        "true",
        " false"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/BpCtgryDscnts/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BpCtgryDscntResource.save($scope.bpCtgryDscnt, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/BpCtgryDscnts");
    };
});