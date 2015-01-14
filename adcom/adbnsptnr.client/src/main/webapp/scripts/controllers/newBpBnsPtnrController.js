
angular.module('AdBnsptnr').controller('NewBpBnsPtnrController', function ($scope, $location, locationParser, BpBnsPtnrResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.bpBnsPtnr = $scope.bpBnsPtnr || {};
    
    $scope.ptnrTypeList = [
        "INDIVIDUAL",
        "LEGAL"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/BpBnsPtnrs/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BpBnsPtnrResource.save($scope.bpBnsPtnr, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/BpBnsPtnrs");
    };
});