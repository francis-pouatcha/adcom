

angular.module('adstockserver').controller('EditStkMvntController', function($scope, $routeParams, $location, StkMvntResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.stkMvnt = new StkMvntResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/StkMvnts");
        };
        StkMvntResource.get({StkMvntId:$routeParams.StkMvntId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.stkMvnt);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.stkMvnt.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/StkMvnts");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/StkMvnts");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.stkMvnt.$remove(successCallback, errorCallback);
    };
    
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
    
    $scope.get();
});