
angular.module('AdBnsptnr').controller('NewBpPtnrCtgryController', function ($scope, $location, locationParser, BpPtnrCtgryResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.bpPtnrCtgry = $scope.bpPtnrCtgry || {};
    
    $scope.ptnrRoleList = [
        "CUSTOMER",
        "SUPPLIER",
        "EMPLOYER",
        "STAFF",
        "MANUFACTURER",
        "GOVERNMENT",
        "BROKER",
        "SHAREHOLDER",
        "BANKER"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/BpPtnrCtgrys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BpPtnrCtgryResource.save($scope.bpPtnrCtgry, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/BpPtnrCtgrys");
    };
});