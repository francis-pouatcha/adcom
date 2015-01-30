
angular.module('AdCatal').controller('NewCatalArtDetailConfigController', function ($scope, $location, locationParser, CatalArtDetailConfigResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.catalArtDetailConfig = $scope.catalArtDetailConfig || {};
    
    $scope.activeList = [
        "true",
        " false"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
           // var id = locationParser(responseHeaders);
            $location.path('/CatalArtDetailConfigs/edit/' + data.id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CatalArtDetailConfigResource.save($scope.catalArtDetailConfig, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CatalArtDetailConfigs");
    };
});