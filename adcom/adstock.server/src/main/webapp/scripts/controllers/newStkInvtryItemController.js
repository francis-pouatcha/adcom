
angular.module('adstockserver').controller('NewStkInvtryItemController', function ($scope, $location, locationParser, StkInvtryItemResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.stkInvtryItem = $scope.stkInvtryItem || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/StkInvtryItems/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        StkInvtryItemResource.save($scope.stkInvtryItem, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/StkInvtryItems");
    };
});