var app = angular.module('app', ['ngTouch', 'ui.grid', 'ui.grid.edit',
    'ui.grid.cellNav', 'ui.grid.rowEdit', 'ui.grid.validate',
    'ui.grid.pagination', 'ui.grid.selection', 'ui.grid.exporter',
    'ui.grid.resizeColumns']);
app.run(['$http', '$window', '$document', function ($http, $window) {
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        $http.defaults.headers.post[header] = token;
    }]);

app
        .controller(
                'LCDCtrl',
                [
                    '$scope',
                    '$q',
                    '$http',
                    'uiGridConstants',
                    'uiGridValidateService',
                    '$window',
                    '$interval',
                    function ($scope, $q, $http, uiGridConstants,
                            uiGridValidateService, $window, $interval) {

                        // uiGridValidateService.setValidator('startWith',
                        // function(argument) {
                        // return function(newValue, oldValue, rowEntity,
                        // colDef) {
                        // if (!newValue) {
                        // return true; // We should not test for existence
                        // here
                        // } else {
                        // return newValue.startsWith(argument);
                        // }
                        // };
                        // },
                        // function(argument) {
                        // return 'You can only insert names starting with:
                        // "' + argument + '"';
                        // }
                        // );
                        //	  
                        var filters = [];

                        function Filter(col, term) {
                            this.col = col;
                            this.term = term;
                        }

                        var paginationOptions = {
                            sort: null,
                            orderColumn: null
                        };
                        $scope.edit = false;
                        $scope.currentFocused = "";
                        $scope.msg = {};
                        $scope.confirm = false;
                        $scope.canEdit = function () {
                            var rowCol = $scope.gridApi.cellNav
                                    .getFocusedCell();
                            // return rowCol.row.entity.editable;
                            return $scope.edit;
                        }

                        $scope.saveRow = function (rowEntity) {

                            var promise = $q.defer();
                            $scope.gridApi.rowEdit.setSavePromise(
                                    rowEntity, promise.promise);
                            if (!$scope.confirm) {
                                promise.resolve();
                                return false;
                            }
                            if (!rowEntity.$$invalidbox) {
                                var url = '../json/lcdupdate';
                                var id = rowEntity.id;
                                var box = rowEntity.box;

                                return $http(
                                        {
                                            url: url,
                                            method: "POST",
                                            headers: {
                                                'Content-Type': 'application/x-www-form-urlencoded'
                                            },
                                            params: {
                                                box: box,
                                                id: id
                                            }
                                        }).success(function (data) {
                                    promise.resolve();
                                    console.log(data);
                                });
                            } else {
                                promise.reject();
                            }
                            $scope.confirm = false;
                            /*
                             * $interval( function() { promise.resolve(); },
                             * 2000, 1);
                             */

                        };

                        $scope.gridOptions = {
                            paginationPageSizes: [10, 25, 50, 75, 100,
                                500, 1000],
                            paginationPageSize: 25,
                            useExternalPagination: true,
                            useExternalSorting: true,
                            enableGridMenu: true,
                            enableFiltering: true,
                            useExternalFiltering: true,
                            enableCellEditOnFocus: true,
                            columnDefs: [{
                                    name: 'id',
                                    enablesorting: true,
                                    width: 30,
                                    enableCellEdit: false,
                                    visible: false
                                }, {
                                    name: 'serial',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }, {
                                    name: 'manufacturer',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }, {
                                    name: 'screenSize',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }, {
                                    name: 'model',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }, {
                                    name: 'screenCondition',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }, {
                                    name: 'intSerial',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }, {
                                    name: 'customerId',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }, {
                                    name: 'customerAsset',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }

                                , {
                                    name: 'lotId',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }, {
                                    name: 'palette',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }, {
                                    name: 'location',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }, {
                                    name: 'box',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: true
                                }, {
                                    name: 'notes',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }, {
                                    name: 'dateIn',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false,
                                    type: 'date', cellFilter: 'date:\'yyyy-MM-dd\''
                                }, {
                                    name: 'available',
                                    enablesorting: true,
                                    width: 100,
                                    enableCellEdit: false
                                }

                            ],
                            exporterAllDataFn: function () {
                                return getPage(1,
                                        $scope.gridOptions.totalItems,
                                        paginationOptions.sort,
                                        paginationOptions.orderColumn)
                                        .then(
                                                function () {
                                                    $scope.gridOptions.useExternalPagination = false;
                                                    $scope.gridOptions.useExternalSorting = false;
                                                    getPage = null;
                                                });
                            },
                            onRegisterApi: function (gridApi) {
                                $scope.gridApi = gridApi;

                                gridApi.validate.on
                                        .validationFailed(
                                                $scope,
                                                function (rowEntity, colDef,
                                                        newValue, oldValue) {
                                                    console
                                                            .log(' error ::: rowEntity: '
                                                                    + rowEntity
                                                                    + '\n'
                                                                    + 'colDef: '
                                                                    + colDef
                                                                    + '\n'
                                                                    + 'newValue: '
                                                                    + newValue
                                                                    + '\n'
                                                                    + 'oldValue: '
                                                                    + oldValue);

                                                });

                                /*
                                 * gridApi.edit.on.beginCellEdit($scope,
                                 * function(rowEntity, colDef) { //This
                                 * alert just shows which info about the
                                 * edit is available
                                 * colDef.enableCellEdit=false;
                                 * $scope.$apply(); console.log('Column: ' +
                                 * colDef.name + ' box: ' + rowEntity.box)
                                 * });
                                 */
                                $scope
                                        .$on(
                                                'uiGridEventEndCellEdit',
                                                function (data) {
                                                    console
                                                            .log(data.targetScope.row.entity);
                                                    var gridRows = $scope.gridApi.rowEdit
                                                            .getDirtyRows();
                                                    console.log(gridRows);
                                                });

                                gridApi.rowEdit.on.saveRow($scope,
                                        $scope.saveRow);

                                gridApi.edit.on.afterCellEdit($scope,
                                        function (rowEntity, colDef,
                                                newValue, oldValue) {
                                            // console.log('edited row id:'
                                            // + rowEntity.serial + '
                                            // Column:' + colDef.name + '
                                            // newValue:' + newValue + '
                                            // oldValue:' + oldValue) ;
                                            if (newValue == '') {
                                                $scope.confirm = false;
                                                $scope.$apply();
                                                return false;
                                            }
                                            if (oldValue !== newValue) {
                                                $scope.confirm = true;
                                                $scope.$apply();
                                                /*
                                                 * deleteUser = false;//
                                                 * $window.confirm('Are you
                                                 * sure you want to edit
                                                 * '+colDef.name+'?');
                                                 * $('#editMsg').html('Are
                                                 * you sure you want to edit
                                                 * '+colDef.name+'?'); $(
                                                 * "#dialog-confirm"
                                                 * ).dialog({ resizable:
                                                 * false, height: "auto",
                                                 * width: 400, modal: true,
                                                 * autoOpen: true, buttons: {
                                                 * 
                                                 * Cancel: function() {
                                                 * rowEntity.box=oldValue;
                                                 * $scope.confirm=false;
                                                 * $scope.$apply(); $( this
                                                 * ).dialog( "close" ); },
                                                 * "Ok": function() {
                                                 * $scope.confirm=true;
                                                 * $scope.$apply(); $( this
                                                 * ).dialog( "close" ); } }
                                                 * });
                                                 */

                                            }

                                        });

                                $scope.gridApi.core.on
                                        .sortChanged(
                                                $scope,
                                                function (grid, sortColumns) {
                                                    if (getPage) {
                                                        if (sortColumns.length > 0) {
                                                            paginationOptions.sort = sortColumns[0].sort.direction;
                                                            paginationOptions.orderColumn = sortColumns[0].field;
                                                        } else {
                                                            paginationOptions.sort = null;
                                                            paginationOptions.orderColumn = null;
                                                        }
                                                        getPage(
                                                                grid.options.paginationCurrentPage,
                                                                grid.options.paginationPageSize,
                                                                paginationOptions.sort,
                                                                paginationOptions.orderColumn)
                                                    }
                                                });
                                gridApi.pagination.on
                                        .paginationChanged(
                                                $scope,
                                                function (newPage, pageSize) {
                                                    if (getPage) {
                                                        getPage(
                                                                newPage,
                                                                pageSize,
                                                                paginationOptions.sort,
                                                                paginationOptions.orderColumn);
                                                    }
                                                });

                                $scope.gridApi.core.on
                                        .filterChanged(
                                                $scope,
                                                function (column) {
                                                    var grid = this.grid;
                                                    var url;
                                                    var term = column.filters[0].term;
                                                    var orderCol = column.field;

                                                    var curPage = grid.options.paginationCurrentPage;
                                                    var pageSize = grid.options.paginationPageSize;
                                                    var sort = paginationOptions.sort;

                                                    url = '../json/lcd1';

                                                    filters = [];
                                                    $scope.gridApi.grid.columns
                                                            .forEach(function (
                                                                    column) {
                                                                if (column.filters
                                                                        && column.filters[0].term) {
                                                                    filters
                                                                            .push(new Filter(
                                                                                    column.name,
                                                                                    column.filters[0].term));
                                                                }
                                                                ;
                                                            });
                                                    console.log(filters);

                                                    $http(
                                                            {
                                                                url: url,
                                                                method: "GET",
                                                                params: {
                                                                    filter: JSON
                                                                            .stringify(filters),
                                                                    order: sort,
                                                                    page: curPage,
                                                                    pageSize: pageSize,
                                                                    col: orderCol
                                                                }
                                                            })
                                                            .success(
                                                                    function (
                                                                            data) {
                                                                        $scope.edit = data.editable;
                                                                        $scope.gridOptions.totalItems = data.total;
                                                                        $scope.gridOptions.data = data.list;// .slice(firstRow,
                                                                        // firstRow
                                                                        // +
                                                                        // pageSize)
                                                                    });

                                                });

                            }
                        };

                        var getPage = function (curPage, pageSize, sort,
                                orderCol) {
                            var url;

                            url = '../json/lcd1';

                            var _scope = $scope;
                            return $http({
                                url: url,
                                method: "GET",
                                params: {
                                    filter: JSON.stringify(filters),
                                    order: sort,
                                    page: curPage,
                                    pageSize: pageSize,
                                    col: orderCol
                                }
                            }).success(function (data) {
                                console.log(data)
                                $scope.edit = data.editable;
                                var firstRow = (curPage - 1) * pageSize;
                                $scope.gridOptions.totalItems = data.total;
                                $scope.gridOptions.data = data.list;// .slice(firstRow,
                                // firstRow
                                // +
                                // pageSize)
                            });
                        };

                        var saveItem = function () {
                            var url;
                            url = '../json/lcdupdate';

                            var rowCol = $scope.gridApi.cellNav
                                    .getFocusedCell();
                            var id = rowCol.row.entity.id;
                            var box = rowCol.row.entity.box;
                            var _scope = $scope;
                            return $http(
                                    {
                                        url: url,
                                        method: "POST",
                                        headers: {
                                            'Content-Type': 'application/x-www-form-urlencoded'
                                        },
                                        params: {
                                            box: box,
                                            id: id
                                        }
                                    }).success(function (data) {
                                console.log(data);
                            });
                        };

                        getPage(1, $scope.gridOptions.paginationPageSize);
                    }

                ]);
