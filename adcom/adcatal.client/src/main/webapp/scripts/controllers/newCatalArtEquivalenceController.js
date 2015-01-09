
angular.module('adcatalserver').controller('NewCatalArtEquivalenceController', function ($scope, $location, locationParser, CatalArtEquivalenceResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.catalArtEquivalence = $scope.catalArtEquivalence || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/CatalArtEquivalences/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CatalArtEquivalenceResource.save($scope.catalArtEquivalence, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CatalArtEquivalences");
    };
});