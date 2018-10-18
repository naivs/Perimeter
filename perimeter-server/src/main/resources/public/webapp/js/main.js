'use strict';

var perimeterApp = angular.module('perimeterApp', ['ngRoute'])
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl:'webapp/partials/home.html',
                controller:'HomeCtrl'
            })
            .when('/devices', {
                templateUrl:'webapp/partials/devices.html',
                controller:'DevicesCtrl'
            }).when('/settings', {
                templateUrl:'webapp/partials/settings.html',
                controller:'SHSettingsCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
}]);

perimeterApp.controller('MainCtrl', ['$scope', '$route', function($scope, $route) {
    $scope.text = 'Project PERIMETER';
    $scope.reloadPage = $route.reload();
}]);

perimeterApp.controller('HomeCtrl', ['$scope', 'navSelcetionService', function($scope, navSelcetionService) {
    navSelcetionService.setSelected(0);
}]);

perimeterApp.controller('DevicesCtrl', ['$scope', '$http', 'dataService', 'navSelcetionService', function($scope, $http, dataService, navSelcetionService) {
    navSelcetionService.setSelected(1);
    $scope.devices = [];
    $scope.deviceTypeList = [];

    $scope.device = {
        typeId: "",
        description: "",
        deviceParams: []
    };

    dataService.getDevices().then(
        function successCallback(response) {
            $scope.devices = response.data;
        }, function errorCallback(response) {
            alert(response);
    });

    dataService.getDeviceTypes().then(
        function successCallback(response) {
            $scope.deviceTypeList = response.data;
        }, function errorCallback(response) {
            alert(response);
    });

    $scope.addDevice = function() {
        $http({
            method: 'POST',
            url: '/devices/create',
            data: {id: '', typeId: $scope.device.typeId, description: $scope.device.description, 
            deviceParams: $scope.device.deviceParams},
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response) {    
            $scope.updateDevices();
        }, function errorCallback(response) {
            alert(response);
        });
    };

    $scope.deleteDevice = function(deviceId) {
        if (confirm('Удалить устройство с идентификатором: ' + deviceId + '?')) {
            $http({
                method: 'DELETE',
                url: '/devices/delete/' + deviceId
            }).then(function successCallback(response) {
                $scope.updateDevices();
            }, function errorCallback(response) {
                alert(response);
            });
        }
    };

    $scope.addParam = function() {
        let deviceParam = {
            name: "",
            val: "",
            description: "",
            deviceId: ""
        };
        deviceParam.name = angular.element(document.querySelector('#nameParamInput')).val();
        deviceParam.val = angular.element(document.querySelector('#valParamInput')).val();
        deviceParam.description = angular.element(document.querySelector('#descriptionParamInput')).val();
        $scope.device.deviceParams[$scope.device.deviceParams.length] = deviceParam;
    }
}]);

perimeterApp.controller('SHSettingsCtrl', ['$scope', '$http', 'dataService', 'navSelcetionService', function($scope, $http, dataService, navSelcetionService) {
    navSelcetionService.setSelected(3);
    $scope.deviceTypeList = [];

    dataService.getDeviceTypes().then(
        function successCallback(response) {
            $scope.deviceTypeList = response.data;
        }, function errorCallback(response) {
            alert(response);
    });
}]);

perimeterApp.factory('navSelcetionService', function() {
    let itemCount = 12;

    return {
        setSelected: function(sel) {
            this.clear();
            angular.element(document.querySelector('#menuItem-' + sel)).addClass("active");
        },

        clear: function() {
            let i = itemCount;
            while (i > 0) {
                i--;
                angular.element(document.querySelector('#menuItem-' + i)).removeClass("active");
            }
        }
    }
});

perimeterApp.factory('dataService', function($http) {
    return {
        getDeviceTypes: function() {
            return $http({
                method: 'GET',
                url: '/deviceTypes/all'
            });
        },

        getDevices: function() {
            return $http({
                method: 'GET',
                url: '/devices/all'
            });
        }
    }
});