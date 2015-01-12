
angular.module('adprocmtserver').controller('NewPrcmtDlvryItemController', function ($scope, $location, locationParser, PrcmtDlvryItemResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.prcmtDlvryItem = $scope.prcmtDlvryItem || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/PrcmtDlvryItems/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        PrcmtDlvryItemResource.save($scope.prcmtDlvryItem, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/PrcmtDlvryItems");
    };
});