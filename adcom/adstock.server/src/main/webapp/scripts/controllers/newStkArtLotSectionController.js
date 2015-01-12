
angular.module('adstockserver').controller('NewStkArtLotSectionController', function ($scope, $location, locationParser, StkArtLotSectionResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.stkArtLotSection = $scope.stkArtLotSection || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/StkArtLotSections/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        StkArtLotSectionResource.save($scope.stkArtLotSection, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/StkArtLotSections");
    };
});