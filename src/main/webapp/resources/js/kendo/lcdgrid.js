jQuery(function () {
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[header] = token;
    $.ajaxSetup({
        headers: headers
    });
    var grid = jQuery("#lcdgrid").kendoGrid({
        "toolbar": [
            {"name": "excel"},
            {"name": "pdf"}
        ],
        "filterable": true,
        "reorderable": true,
        "resizable": true,
        "groupable": true,
        "columnMenu": true,
        "sortable": true,
        "columns": [
            {"field": "id",  "lockable": true, "width": "100px", "title": "ID"},
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
                "template": "#= kendo.toString(kendo.parseDate(dateIn), 'MM/dd/yyyy HH:mm:ss') #"
            },
            {"field": "notes", "lockable": true, "width": "150px", "title": "Notes"},
            {"field": "available", "lockable": true, "width": "100px", "title": "Available"},
            {"field": "stand", "lockable": true, "width": "100px", "title": "Stand"},
            {"field": "hours", "lockable": true, "width": "100px", "title": "Hours"}
        ],
        "pageable": {
            "pageSize": 50,
            "refresh": true,
            "pageSizes": [10, 20, 50],
            "buttonCount": 20.0
        },
        "dataSource": {
            "serverPaging": true,
            "schema": {
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
                "read": {"type": "POST", "contentType": "application/json", "url": "../json/lcd"}, "parameterMap": function (options) {
                    return JSON.stringify(options);
                }
            }
        }, "scrollable": {},
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
    }).data("kendoGrid");


    if (localStorage['lcdgrid-state'] != null && localStorage['lcdgrid-state'] != "") {
        var state = JSON.parse(localStorage['lcdgrid-state']);
        grid.setOptions(state);
    }
    else {
        grid.dataSource.read();
    }

    function saveGridState() {
        var gridState = grid.getOptions();
        localStorage['lcdgrid-state'] = kendo.stringify(gridState);
    }
})
