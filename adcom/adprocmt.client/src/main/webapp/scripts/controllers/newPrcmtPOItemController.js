
angular.module('AdProcmt').controller('NewPrcmtPOItemController', function ($scope, $location, locationParser, PrcmtPOItemResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.prcmtPOItem = $scope.prcmtPOItem || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/PrcmtPOItems/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        PrcmtPOItemResource.save($scope.prcmtPOItem, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/PrcmtPOItems");
    };
});