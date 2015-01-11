
angular.module('AdStock').controller('NewStkInvtryController', function ($scope, $location, locationParser, StkInvtryResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.stkInvtry = $scope.stkInvtry || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/StkInvtrys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        StkInvtryResource.save($scope.stkInvtry, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/StkInvtrys");
    };
});