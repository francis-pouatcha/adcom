

angular.module('adstockserver').controller('EditStkArticleLotController', function($scope, $routeParams, $location, StkArticleLotResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.stkArticleLot = new StkArticleLotResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/StkArticleLots");
        };
        StkArticleLotResource.get({StkArticleLotId:$routeParams.StkArticleLotId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.stkArticleLot);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.stkArticleLot.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/StkArticleLots");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/StkArticleLots");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.stkArticleLot.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});