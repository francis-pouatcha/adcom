
angular.module('AdStock').controller('NewStkSectionController', function ($scope, $location, locationParser, StkSectionResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.stkSection = $scope.stkSection || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/StkSections/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        StkSectionResource.save($scope.stkSection, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/StkSections");
    };
});