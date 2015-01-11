
angular.module('AdStock').controller('NewStkMvntController', function ($scope, $location, locationParser, StkMvntResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.stkMvnt = $scope.stkMvnt || {};
    
    $scope.mvntTypeList = [
        "OUT",
        "IN"
    ];
    
    $scope.mvntOriginList = [
        "WAREHOUSE",
        "SUPPLIER",
        "CUSTOMER",
        "TRASH"
    ];
    
    $scope.mvntDestList = [
        "WAREHOUSE",
        "SUPPLIER",
        "CUSTOMER",
        "TRASH"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/StkMvnts/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        StkMvntResource.save($scope.stkMvnt, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/StkMvnts");
    };
});