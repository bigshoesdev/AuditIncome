jQuery(function () {
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[header] = token;
    $.ajaxSetup({
        headers: headers
    });
    var grid = jQuery("#rmagrid").kendoGrid(
            {
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
                    {"field": "id", "lockable": true, "width": "100px", "title": "ID"},
                    {"field": "serial", "lockable": true, "width": "100px", "title": "Serial"},
                    {"field": "orderNumber", "lockable": true, "width": "150px", "title": "Order Number"},
                    {"field": "tech", "lockable": true, "width": "100px", "title": "Tech"},
                    {"field": "manufacturer", "lockable": true, "width": "130px", "title": "Manufacturer"},
                    {"field": "model", "lockable": true, "width": "130px", "title": "Model"},
                    {"field": "intSerial", "lockable": true, "width": "130px", "title": "STS Seria"},
                    {
                        "field": "dateProduced",
                        "lockable": true,
                        "width": "130px",
                        "title": "Date Produced",
                        "template": "#= kendo.toString(kendo.parseDate(dateProduced), 'MM/dd/yyyy HH:mm:ss') #"
                    },
                    {
                        "field": "dateProcessed",
                        "lockable": true,
                        "width": "130px",
                        "title": "Date Processed",
                        "template": "#= kendo.toString(kendo.parseDate(dateProcessed), 'MM/dd/yyyy HH:mm:ss') #"
                    },
                    {"field": "troubleCode", "lockable": true, "width": "100px", "title": "Trouble Code"},
                    {"field": "hddNotes", "lockable": true, "width": "100px", "title": "HDD Notes"},
                    {"field": "memNotes", "lockable": true, "width": "100px", "title": "Mem Notes"},
                    {"field": "sku", "lockable": true, "width": "100px", "title": "Sku"},
                    {"field": "hddSize", "lockable": true, "width": "100px", "title": "HDD Size"},
                    {"field": "hddSerial", "lockable": true, "width": "100px", "title": "HDD Serial"},
                    {"field": "notes", "lockable": true, "width": "150px", "title": "Notes"},
                    {"field": "hddModel", "lockable": true, "width": "130px", "title": "HDD Model"}
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
                            "fields":
                                    {
                                        "id": {"type": "number"},
                                        "tech": {"type": "string"},
                                        "orderNumber": {"type": "number"},
                                        "notes": {"type": "string"},
                                        "hddSerial": {"type": "string"},
                                        "int_serial": {"type": "string"},
                                        "dateProcessed": {"type": "date"},
                                        "dateProduced": {"type": "date"},
                                        "troubleCode": {"type": "string"},
                                        "manufacturer": {"type": "string"},
                                        "hddNotes": {"type": "string"},
                                        "serial": {"type": "string"},
                                        "memNotes": {"type": "string"},
                                        "model": {"type": "string"},
                                        "sku": {"type": "string"},
                                        "hddSize": {"type": "string"},
                                        "hddModel": {"type": "string"}
                                    }
                        }
                    },
                    "serverFiltering": true,
                    "serverGrouping": true,
                    "serverSorting": true,
                    "transport":
                            {
                                "read": {"type": "POST", "contentType": "application/json", "url": "../json/rma"},
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

    if (localStorage['rmagrid-state'] != null && localStorage['rmagrid-state'] != "") {
        var state = JSON.parse(localStorage['rmagrid-state']);
        grid.setOptions(state);
    }
    else {
        grid.dataSource.read();
    }

    function saveGridState() {
        var gridState = grid.getOptions();
        localStorage['rmagrid-state'] = kendo.stringify(gridState);
    }
})