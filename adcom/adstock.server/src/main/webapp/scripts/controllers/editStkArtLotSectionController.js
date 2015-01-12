

angular.module('adstockserver').controller('EditStkArtLotSectionController', function($scope, $routeParams, $location, StkArtLotSectionResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.stkArtLotSection = new StkArtLotSectionResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/StkArtLotSections");
        };
        StkArtLotSectionResource.get({StkArtLotSectionId:$routeParams.StkArtLotSectionId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.stkArtLotSection);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.stkArtLotSection.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/StkArtLotSections");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/StkArtLotSections");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.stkArtLotSection.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});