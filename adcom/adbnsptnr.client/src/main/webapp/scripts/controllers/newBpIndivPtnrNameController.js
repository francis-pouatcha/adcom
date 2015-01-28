
angular.module('AdBnsptnr').controller('NewBpIndivPtnrNameController', function ($scope, $location, locationParser, BpIndivPtnrNameResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.bpIndivPtnrName = $scope.bpIndivPtnrName || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/BpIndivPtnrNames/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BpIndivPtnrNameResource.save($scope.bpIndivPtnrName, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/BpIndivPtnrNames");
    };
});