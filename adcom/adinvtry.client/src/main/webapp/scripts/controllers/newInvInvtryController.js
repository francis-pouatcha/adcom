
angular.module('AdInvtry').controller('NewInvInvtryController', function ($scope, $location, locationParser, InvInvtryResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.invInvtry = $scope.invInvtry || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/InvInvtrys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        InvInvtryResource.save($scope.invInvtry, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/InvInvtrys");
    };
});