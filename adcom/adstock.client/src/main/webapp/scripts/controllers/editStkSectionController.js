

angular.module('AdStock').controller('EditStkSectionController', function($scope, $routeParams, $location, StkSectionResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.stkSection = new StkSectionResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/StkSections");
        };
        StkSectionResource.get({StkSectionId:$routeParams.StkSectionId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.stkSection);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.stkSection.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/StkSections");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/StkSections");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.stkSection.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});