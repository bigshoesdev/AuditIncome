var app = angular.module('app', []);
app.run(['$http', '$window', '$document', function ($http, $window) {
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        $http.defaults.headers.post[header] = token;
    }]);

app.controller('MainCtrl', ['$scope', '$q', '$http',
    function ($scope, $q, $http) {
        var url = '../json/reports/dailyaudited';
        $scope.loadData = function () {
            $http({
                url: url,
                method: "GET",
                params: {}
            }).success(function (data) {
                console.log(data)
                $scope.dailyaudited = data;

            });
        }

        $scope.loadData();

    }

]);
