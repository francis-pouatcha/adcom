
angular.module('adcatalserver').controller('NewCatalProductFamilyController', function ($scope, $location, locationParser, CatalProductFamilyResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.catalProductFamily = $scope.catalProductFamily || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CatalProductFamilys/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CatalProductFamilyResource.save($scope.catalProductFamily, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CatalProductFamilys");
    };
});