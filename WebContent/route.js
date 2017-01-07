DBSCANApp.config(function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/home');
    
    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: 'views/home/home.html',
            controller: 'homeController'
        })
        .state('charts', {
            url: '/charts',
            templateUrl: 'views/charts/charts.html',
            controller: 'chartsController'
        });
    
});