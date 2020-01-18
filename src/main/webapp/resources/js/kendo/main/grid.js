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
                    {"field": "id", "width": "100px", "title": "ID"},
                    {"field": "checkInTech", "width": "150px", "lockable": true, "title": "Check-In Tech"},
                    {
                        "field": "checkInDate",
                        "width": "150px", "lockable": true,
                        "title": "Check In Date",
                        "template": "#= kendo.toString(kendo.parseDate(checkInDate), 'MM/dd/yyyy') #",
                        "groupHeaderTemplate": "Check In Date: #=  kendo.toString(value, 'MM/dd/yyyy') #"
                    },
                    {"field": "auditTech", "width": "150px", "lockable": true, "title": "Audit Tech"},
                    {
                        "field": "auditDate",
                        "width": "150px", "lockable": true,
                        "title": "Audit Date",
                        "template": "#= kendo.toString(kendo.parseDate(auditDate), 'MM/dd/yyyy') #",
                        "groupHeaderTemplate": "Audit Date: #=  kendo.toString(value, 'MM/dd/yyyy') #"
                    },
                    {"field": "quantity", "width": "150px", "lockable": true, "title": "Quantity"},
                    {"field": "commodityCode", "width": "150px", "lockable": true, "title": "Commodity Code"},
                    {"field": "category", "width": "150px", "lockable": true, "title": "Category"},
                    {"field": "description", "width": "150px", "lockable": true, "title": "Description"},
                    {
                        "field": "boxOpenDate",
                        "width": "150px", "lockable": true,
                        "title": "Box Open Date",
                        "template": "#= kendo.toString(kendo.parseDate(boxOpenDate), 'MM/dd/yyyy') #",
                        "groupHeaderTemplate": "Box Open Date: #=  kendo.toString(value, 'MM/dd/yyyy') #"
                    },
                    {
                        "field": "boxCloseDate",
                        "width": "150px", "lockable": true,
                        "title": "Box Close Date",
                        "template": "#= kendo.toString(kendo.parseDate(boxCloseDate), 'MM/dd/yyyy') #",
                        "groupHeaderTemplate": "Box Open Date: #=  kendo.toString(value, 'MM/dd/yyyy') #"
                    },
                    {"field": "stsserial", "width": "150px", "lockable": true, "title": "STS Serial"},
                    {"field": "available", "width": "150px", "lockable": true, "title": "Available"},
                    {"field": "scrap", "width": "150px", "lockable": true, "title": "Scrap"},
                    {"field": "manufacturer", "width": "150px", "lockable": true, "title": "Manufacturer"},
                    {"field": "serial", "width": "150px", "lockable": true, "title": "Serial"},
                    {"field": "model", "width": "150px", "lockable": true, "title": "Model"},
                    {"field": "emc", "width": "150px", "lockable": true, "title": "EMC"},
                    {"field": "year", "width": "150px", "lockable": true, "title": "Year"},
                    {"field": "formFactor", "width": "150px", "lockable": true, "title": "Form Factor"},
                    {"field": "cpu", "width": "150px", "lockable": true, "title": "CPU"},
                    {"field": "cpuqty", "width": "150px", "lockable": true, "title": "CPU Qty"},
                    {"field": "dataWidth", "width": "150px", "lockable": true, "title": "Data Width"},
                    {"field": "cpuspeed", "width": "150px", "lockable": true, "title": "CPU Speed"},
                    {"field": "cores", "width": "150px", "lockable": true, "title": "Data Width"},
                    {"field": "auditHDDSerial", "width": "150px", "lockable": true, "title": "Audit HDD Serial"},
                    {"field": "auditHDDModel", "width": "150px", "lockable": true, "title": "Audit HDD Model"},
                    {"field": "auditHDDSize", "width": "150px", "lockable": true, "title": "Audit HDD Size"},
                    {"field": "auditHDDStatus", "width": "150px", "lockable": true, "title": "Audit HDD Status"},
                    {"field": "videoAdapter", "width": "150px", "lockable": true, "title": "Video Adapter"},
                    {"field": "screenSize", "width": "150px", "lockable": true, "title": "Screen Size"},
                    {"field": "stand", "width": "150px", "lockable": true, "title": "Stand"},
                    {"field": "screenCondition", "width": "150px", "lockable": true, "title": "Screen Condition"},
                    {"field": "plasticCondition", "width": "150px", "lockable": true, "title": "Plastic Condition"},
                    {"field": "totalMemory", "width": "150px", "lockable": true, "title": "Total Memory"},
                    {"field": "ramslots", "width": "150px", "lockable": true, "title": "RAM Slots"},
                    {"field": "ramperSlot", "width": "150px", "lockable": true, "title": "RAM Per Slot"},
                    {"field": "ramparts", "width": "150px", "lockable": true, "title": "RAM Part"},
                    {"field": "rammodel", "width": "150px", "lockable": true, "title": "RAM Model"},
                    {"field": "typeofRAM", "width": "150px", "lockable": true, "title": "Type of RAM"},
                    {"field": "capacitor", "width": "150px", "lockable": true, "title": "Capacitor"},
                    {"field": "battery", "width": "150px", "lockable": true, "title": "Battery"},
                    {"field": "batteryHealth", "width": "150px", "lockable": true, "title": "Battery Health"},
                    {"field": "acAdaptor", "width": "150px", "lockable": true, "title": "A/C Adaptor"},
                    {"field": "camera", "width": "150px", "lockable": true, "title": "Camera"},
                    {"field": "connections", "width": "150px", "lockable": true, "title": "Connections"},
                    {"field": "dvddrive", "width": "150px", "lockable": true, "title": "DVD Drive"},
                    {"field": "coaversion", "width": "150px", "lockable": true, "title": "COA Version"},
                    {"field": "coaedition", "width": "150px", "lockable": true, "title": "COA Edition"},
                    {"field": "customerID", "width": "150px", "lockable": true, "title": "Customer ID"},
                    {"field": "lotID", "width": "150px", "lockable": true, "title": "Lot ID"},
                    {"field": "customerAsset", "width": "150px", "lockable": true, "title": "Customer Asset"},
                    {"field": "box", "width": "150px", "lockable": true, "title": "Box"},
                    {"field": "location", "width": "150px", "lockable": true, "title": "Location"},
                    {"field": "palette", "width": "150px", "lockable": true, "title": "Palette"},
                    {
                        "field": "soldDate",
                        "width": "150px", "lockable": true,
                        "title": "Sold Date",
                        "template": "#= kendo.toString(kendo.parseDate(soldDate), 'MM/dd/yyyy') #",
                        "groupHeaderTemplate": "Sold Date: #=  kendo.toString(value, 'MM/dd/yyyy') #"
                    },
                    {"field": "soldHDDSerial", "width": "150px", "lockable": true, "title": "Sold HDD Serial"},
                    {"field": "soldHDDModel", "width": "150px", "lockable": true, "title": "Sold HDD Model"},
                    {"field": "soldHDDSize", "width": "150px", "lockable": true, "title": "Sold HDD Size"},
                    {"field": "soldHDDSmart", "width": "150px", "lockable": true, "title": "Sold HDD Smart"},
                    {"field": "buildOrder", "width": "150px", "lockable": true, "title": "Build Order"},
                    {"field": "buildOS", "width": "150px", "lockable": true, "title": "Build OS"},
                    {"field": "buildSKU", "width": "150px", "lockable": true, "title": "Build SKU"},
                    {"field": "sku", "width": "150px", "lockable": true, "title": "SKU"},
                    {"field": "notes", "width": "150px", "lockable": true, "title": "Notes"},
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
                                                "checkInTech": {"type": "string"},
                                                "checkInDate": {"type": "date"},
                                                "auditTech": {"type": "string"},
                                                "auditDate": {"type": "date"},
                                                "quantity": {"type": "string"},
                                                "commodityCode": {"type": "string"},
                                                "category": {"type": "string"},
                                                "description": {"type": "string"},
                                                "boxOpenDate": {"type": "date"},
                                                "boxCloseDate": {"type": "date"},
                                                "stsserial": {"type": "string"},
                                                "available": {"type": "string"},
                                                "scrap": {"type": "string"},
                                                "manufacturer": {"type": "string"},
                                                "serial": {"type": "string"},
                                                "model": {"type": "string"},
                                                "emc": {"type": "string"},
                                                "year": {"type": "string"},
                                                "formFactor": {"type": "string"},
                                                "cpu": {"type": "string"},
                                                "cpuqty": {"type": "string"},
                                                "dataWidth": {"type": "string"},
                                                "cpuspeed": {"type": "string"},
                                                "cores": {"type": "string"},
                                                "auditHDDSerial": {"type": "string"},
                                                "auditHDDModel": {"type": "string"},
                                                "auditHDDSize": {"type": "string"},
                                                "auditHDDStatus": {"type": "string"},
                                                "videoAdapter": {"type": "string"},
                                                "screenSize": {"type": "string"},
                                                "stand": {"type": "string"},
                                                "screenCondition": {"type": "string"},
                                                "plasticCondition": {"type": "string"},
                                                "totalMemory": {"type": "string"},
                                                "ramslots": {"type": "string"},
                                                "ramperSlot": {"type": "string"},
                                                "ramparts": {"type": "string"},
                                                "rammodel": {"type": "string"},
                                                "typeofRAM": {"type": "string"},
                                                "capacitor": {"type": "string"},
                                                "battery": {"type": "string"},
                                                "batteryHealth": {"type": "string"},
                                                "acAdaptor": {"type": "string"},
                                                "camera": {"type": "string"},
                                                "connections": {"type": "string"},
                                                "dvddrive": {"type": "string"},
                                                "coaversion": {"type": "string"},
                                                "coaedition": {"type": "string"},
                                                "customerID": {"type": "string"},
                                                "lotID": {"type": "string"},
                                                "customerAsset": {"type": "string"},
                                                "box": {"type": "string"},
                                                "location": {"type": "string"},
                                                "palette": {"type": "string"},
                                                "soldDate": {"type": "date"},
                                                "soldHDDSerial": {"type": "string"},
                                                "soldHDDSize": {"type": "string"},
                                                "soldHDDSmart": {"type": "string"},
                                                "buildOrder": {"type": "string"},
                                                "buildOS": {"type": "string"},
                                                "buildSKU": {"type": "string"},
                                                "sku": {"type": "string"},
                                                "notes": {"type": "string"}
                                            }
                                        }
                                    },
                            "serverFiltering": true,
                            "serverGrouping": true,
                            "serverSorting": true,
                            "transport": {
                                "read": {"type": "POST", "contentType": "application/json", "url": "../json/maingrid"},
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
            model: 'main'
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
                model: 'main',
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
        url: '../excel/main',
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
    slot["title"] = "Main Table Grid";
    slot["type"] = "grid";
    slot["model"] = "main";
    slot["options"] = {};
    slot["options"]["filter"] = filter;
    slot["options"]["group"] = group;
    slot["options"]["sort"] = sort;
    slot["options"]["columns"] = columns;

    addSlotToServer(slot);
}