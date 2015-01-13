
angular.module('AdInvtry').controller('NewInvInvtryItemController', function ($scope, $location, locationParser, InvInvtryItemResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.invInvtryItem = $scope.invInvtryItem || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/InvInvtryItems/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        InvInvtryItemResource.save($scope.invInvtryItem, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/InvInvtryItems");
    };
});