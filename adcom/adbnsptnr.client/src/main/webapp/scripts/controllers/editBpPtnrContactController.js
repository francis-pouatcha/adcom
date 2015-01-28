

angular.module('AdBnsptnr').controller('EditBpPtnrContactController', function($scope, $routeParams, $location, BpPtnrContactResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.bpPtnrContact = new BpPtnrContactResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/BpPtnrContacts");
        };
        BpPtnrContactResource.get({BpPtnrContactId:$routeParams.BpPtnrContactId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.bpPtnrContact);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.bpPtnrContact.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/BpPtnrContacts");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/BpPtnrContacts");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.bpPtnrContact.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});