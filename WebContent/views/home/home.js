DBSCANApp.controller('homeController', function($scope,$http,$location,localStorageService) {
    
    $scope.minimumPoints = 0;
    $scope.thresholdDistance = 0;
    
    $scope.pointX = 0;
    $scope.pointY = 0;
   
    $scope.pointsList = [];
    
    $scope.addPoint = function(){
        var temp = {
            x: $scope.pointX,
            y: $scope.pointY
        };
        $scope.pointsList.push(temp);
    };
    
    $scope.removePoint = function(pointIndex){
        $scope.pointsList.splice(pointIndex,1);
    };
    
    $scope.next = function(){
        if($scope.minimumPoints > 0 && $scope.thresholdDistance > 0 && $scope.pointsList.length > 0){
            // send data to DBSCAN server 
            $http({
              method: 'POST',
              url: 'api/getScanned',
              headers: {'Content-Type': 'application/json'},
              data: {
                    minimumPoints:$scope.minimumPoints,
                    thresholdDistance:$scope.thresholdDistance,
                    pointsList:$scope.pointsList
                }
            }).then(function successCallback(response) {
                localStorageService.set('chartData', {
                    minimumPoints:$scope.minimumPoints,
                    thresholdDistance:$scope.thresholdDistance,
                    pointsList:response.data
                });
                $location.url('/charts');
            }, function errorCallback(response) {
                console.log(response);
            });
        } else {
            swal('Please Check ...', 'Some Details are missing', 'error')
        }
    };
});