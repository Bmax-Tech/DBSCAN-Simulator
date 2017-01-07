var DBSCANApp = angular.module('DBSCANApp', 
    [
        'ui.router',
        'tooltips',
        'ngResource',
        'LocalStorageModule'
    ]
);

DBSCANApp.config(function (localStorageServiceProvider) {
  localStorageServiceProvider
    .setPrefix('DBSCANApp')
    .setStorageType('sessionStorage')
    .setNotify(true, true)
});