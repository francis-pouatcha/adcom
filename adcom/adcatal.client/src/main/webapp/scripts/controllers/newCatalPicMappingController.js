
angular.module('AdCatal').controller('NewCatalPicMappingController', function ($scope, $location, locationParser, CatalPicMappingResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.catalPicMapping = $scope.catalPicMapping || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CatalPicMappings/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CatalPicMappingResource.save($scope.catalPicMapping, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CatalPicMappings");
    };
});