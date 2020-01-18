function initGridView() {
    var grid = jQuery("#grid").kendoGrid(
            {
                "toolbar": [
                    {template: '<a class="k-button" href="\\#" onclick="exportCurrent()">Export Current To Excel</a>'},
                    {template: '<a class="k-button" href="\\#" onclick="exportAll()">Export All Page To Excel</a>'},
                    {template: '<a class="k-button" href="\\#" onclick="clearAllFilter()" style="position: absolute;right: 3px;">Clear All Filter</a>'},
                    {template: '<a class="k-button" href="\\#" onclick="addToDashboard()" style="position: absolute;right: 110px;">Add To DashBoard</a>'}
                ],
                "filterable": true,
                "reorderable": true,
                "resizable": true,
                "groupable": true,
                "columnMenu": true,
                "sortable": true,
                "pageable": {
                    "pageSize": 50,
                    "refresh": true,
                    "pageSizes": [10, 20, 50, 100],
                    "buttonCount": 20.0
                },
                "columns": [
                    {"field": "id", "lockable": true, "width": "100px", "title": "ID"},
                    {"field": "serial", "lockable": true, "width": "130px", "title": "Serial"},
                    {"field": "manufacturer", "lockable": true, "width": "130px", "title": "Manufacturer"},
                    {"field": "model", "lockable": true, "width": "130px", "title": "Model"},
                    {"field": "screenSize", "lockable": true, "width": "130px", "title": "Screen Size"},
                    {"field": "screenCondition", "lockable": true, "width": "130px", "title": "Screen Condition"},
                    {"field": "intSerial", "lockable": true, "width": "100px", "title": "STS Serial"},
                    {"field": "customerId", "lockable": true, "width": "130px", "title": "Customer ID"},
                    {"field": "customerAsset", "lockable": true, "width": "130px", "title": "Customer Asset"},
                    {"field": "lotId", "lockable": true, "width": "100px", "title": "Lot ID"},
                    {"field": "palette", "lockable": true, "width": "130px", "title": "Palette"},
                    {"field": "location", "lockable": true, "width": "130px", "title": "Location"},
                    {"field": "box", "lockable": true, "width": "100px", "title": "Box"},
                    {
                        "field": "dateIn",
                        "lockable": true,
                        "width": "150px",
                        "title": "Audit Date",
                        "template": "#= kendo.toString(kendo.parseDate(dateIn), 'MM/dd/yyyy') #",
                        "groupHeaderTemplate": "Audit Date: #=  kendo.toString(value, 'MM/dd/yyyy') #"
                    },
                    {"field": "notes", "lockable": true, "width": "150px", "title": "Notes"},
                    {"field": "available", "lockable": true, "width": "100px", "title": "Available"},
                    {"field": "stand", "lockable": true, "width": "100px", "title": "Stand"},
                    {"field": "hours", "lockable": true, "width": "100px", "title": "Hours"},
                ],
                "dataSource":
                        {
                            "serverPaging": true,
                            "schema":
                                    {
                                        "total": "total",
                                        "data": "data",
                                        "groups": "data",
                                        "model": {
                                            "fields": {
                                                "id": {"type": "number"},
                                                "customerAsset": {"type": "string"},
                                                "dateIn": {"type": "date"},
                                                "hours": {"type": "string"},
                                                "notes": {"type": "string"},
                                                "screenCondition": {"type": "string"},
                                                "available": {"type": "string"},
                                                "lotId": {"type": "string"},
                                                "box": {"type": "string"},
                                                "manufacturer": {"type": "string"},
                                                "intSerial": {"type": "string"},
                                                "screenSize": {"type": "string"},
                                                "serial": {"type": "string"},
                                                "customerId": {"type": "string"},
                                                "model": {"type": "string"},
                                                "palette": {"type": "string"},
                                                "location": {"type": "string"},
                                                "stand": {"type": "string"}
                                            }
                                        }
                                    },
                            "serverFiltering": true,
                            "serverGrouping": true,
                            "serverSorting": true,
                            "transport": {
                                "read": {"type": "POST", "contentType": "application/json", "url": "../json/lcdgrid"},
                                "parameterMap": function (options) {
                                    return JSON.stringify(options);
                                }
                            }
                        },
                "scrollable": {},
                "height": "750px",
                "columnLock": function () {
                    console.log("columnLock");
                    saveGridState();
                },
                "columnUnlock": function () {
                    console.log("columnUnlock");
                    saveGridState();
                },
                "columnReorder": function () {
                    console.log("columnReorder");
                    saveGridState();
                },
                "columnShow": function () {
                    console.log("columnShow");
                    saveGridState();
                },
                "columnHide": function () {
                    console.log("columnHide");
                    saveGridState();
                },
                dataBound: function (e) {
                    saveGridState();
                }
            }
    ).data("kendoGrid");

    $.ajax({
        url: '../grid/get',
        type: "POST",
        data: {
            model: 'lcd'
        },
        success: function (result) {
            if (result.status) {
                var state = JSON.parse(result.data.options);
                grid.setOptions(state);
            }
        },
        error: function () {
            console.log("network error");
        }
    });

    function saveGridState() {
    kendo.ui.progress($("body"), true);
        $.ajax({
            url: '../grid/add',
            type: "POST",
            data: {
                model: 'lcd',
                options: kendo.stringify(grid.getOptions())
            },
            success: function (result) {
                if (result.status) {
                    console.log("successfully log the grid option");
                }
                kendo.ui.progress($("body"), false);
            },
            error: function () {
                kendo.ui.progress($("body"), false);
                console.log("network error");
            }
        });
    }
}

function clearAllFilter() {
    var gridDataSource = jQuery("#grid").data("kendoGrid").dataSource;
    gridDataSource.filter([]);

}

function exportAll() {
    var filter = $("#grid").data("kendoGrid").dataSource.filter();
    var sort = $("#grid").data("kendoGrid").dataSource.sort();
    var colList = $("#grid").data("kendoGrid").columns;

    var columns = [];
    for (var i = 0; i < colList.length; i++) {
        if (colList[i]["hidden"])
            continue;
        columns.push(colList[i]["field"]);
    }

    var data = {
        filter: filter,
        sort: sort,
        columns: columns
    };

    kendo.ui.progress($("#grid"), true);
    $.ajax({
        url: '../excel/lcd',
        type: "POST",
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            kendo.ui.progress($("#grid"), false);
            window.open("../resources/tmp/" + result.excelFile, '_blank');
        }
    });
}

function exportCurrent() {
    jQuery("#grid").data("kendoGrid").saveAsExcel();
}

function addToDashboard() {
    var filter = $("#grid").data("kendoGrid").dataSource.filter();
    var group = $("#grid").data("kendoGrid").dataSource.group();
    var sort = [];
    var colList = $("#grid").data("kendoGrid").columns;

    var columns = [];

    for (var i = 0; i < colList.length; i++) {
        if (colList[i]["hidden"])
            continue;
        columns.push(colList[i]["field"]);
    }

    var slot = {};
    slot["title"] = "LCD Table Grid";
    slot["type"] = "grid";
    slot["model"] = "lcd";
    slot["options"] = {};
    slot["options"]["filter"] = filter;
    slot["options"]["group"] = group;
    slot["options"]["sort"] = sort;
    slot["options"]["columns"] = columns;

    addSlotToServer(slot);
}