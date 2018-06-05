'use strict';

var perimeterApp = angular.module('perimeterApp', ['ngRoute']);

perimeterApp.config(['$routeProvider', function($routeProvide) {
    $routeProvide
        .when('/', {
            templateUrl:'webapp/partials/home.html',
            controller:'HomeCtrl'
        })
        .when('/devices', {
            templateUrl:'webapp/partials/devices.html',
            controller:'DevicesCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });
}]);

perimeterApp.controller('MainCtrl', ['$scope', function($scope) {
    $scope.selected = 0;
    $scope.text = 'Project PERIMETER';
}]);

perimeterApp.controller('HomeCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
    $scope.location = $location;
}]);

perimeterApp.controller('DevicesCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {

    $scope.devices = [];

    $scope.typeId = 0;
    $scope.description = '';

    $scope.updateDevices = function() {
        $http({
            method: 'GET',
            url: '/devices/all'
        }).then(function successCallback(response) {
            $scope.devices = response.data;
        }, function errorCallback(response) {
            alert(response);
        });
    };

    $scope.addDevice = function() {
        $http({
            method: 'POST',
            url: '/devices/create',
            data: {id: '', TypeId: $scope.typeId, Description: $scope.description},
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response) {    
            updateDevices();
        }, function errorCallback(response) {
            alert(response);
        });
    };
}]);