
angular.module('adcatalserver').controller('NewCatalFamilyFeatMapingController', function ($scope, $location, locationParser, CatalFamilyFeatMapingResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.catalFamilyFeatMaping = $scope.catalFamilyFeatMaping || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CatalFamilyFeatMapings/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CatalFamilyFeatMapingResource.save($scope.catalFamilyFeatMaping, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CatalFamilyFeatMapings");
    };
});