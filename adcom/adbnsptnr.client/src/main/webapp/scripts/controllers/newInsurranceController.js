
angular.module('AdBnsptnr').controller('NewInsurranceController', function ($scope, $location, locationParser, InsurranceResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.insurrance = $scope.insurrance || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Insurrances/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        InsurranceResource.save($scope.insurrance, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Insurrances");
    };
});