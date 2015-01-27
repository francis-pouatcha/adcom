
angular.module('AdCatal').controller('NewCatalArtFeatMappingController', function ($scope, $location, locationParser, CatalArtFeatMappingResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.catalArtFeatMapping = $scope.catalArtFeatMapping || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CatalArtFeatMappings/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CatalArtFeatMappingResource.save($scope.catalArtFeatMapping, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CatalArtFeatMappings");
    };
});