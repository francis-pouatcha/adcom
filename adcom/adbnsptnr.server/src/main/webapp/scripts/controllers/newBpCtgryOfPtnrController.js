
angular.module('adbnsptnrserver').controller('NewBpCtgryOfPtnrController', function ($scope, $location, locationParser, BpCtgryOfPtnrResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.bpCtgryOfPtnr = $scope.bpCtgryOfPtnr || {};
    
    $scope.whenInRoleList = [
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
            $location.path('/BpCtgryOfPtnrs/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BpCtgryOfPtnrResource.save($scope.bpCtgryOfPtnr, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/BpCtgryOfPtnrs");
    };
});