
angular.module('adbnsptnrserver').controller('NewBpPtnrContactController', function ($scope, $location, locationParser, BpPtnrContactResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.bpPtnrContact = $scope.bpPtnrContact || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/BpPtnrContacts/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BpPtnrContactResource.save($scope.bpPtnrContact, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/BpPtnrContacts");
    };
});