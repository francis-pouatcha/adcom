
angular.module('AdCatal').controller('NewCatalPkgModeController', function ($scope, $location, locationParser, CatalPkgModeResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.catalPkgMode = $scope.catalPkgMode || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CatalPkgModes/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CatalPkgModeResource.save($scope.catalPkgMode, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CatalPkgModes");
    };
});