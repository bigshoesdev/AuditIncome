function initPieView() {
    $(".export-pie-pdf").click(function () {
        var chart = $("#pie-main-view").getKendoChart();
        if (chart) {
            chart.exportPDF({paperSize: "auto", margin: {left: "1cm", top: "1cm", right: "1cm", bottom: "1cm"}}).done(function (data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.pdf"
                });
            });
        }
    });

    $(".export-pie-img").click(function () {
        var chart = $("#pie-main-view").getKendoChart();
        if (chart) {
            chart.exportImage().done(function (data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.png"
                });
            });
        }
    });

    $(".export-pie-svg").click(function () {
        var chart = $("#pie-main-view").getKendoChart();
        if (chart) {
            chart.exportSVG().done(function (data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.svg"
                });
            });
        }
    });
    $("#pie-filter-grid").kendoGrid({
        "filterable": {
            mode: "menu, row"
        },
        "dataSource": {
            transport: {
                read: {
                    url: "../resources/lib/kendo",
                }
            }
        },
        "reorderable": true,
        "resizable": true,
        "columnMenu": true,
        "columns":
                [
                    {"field": "checkInTech", "title": "Check-In Tech"},
                    {"field": "auditTech", "title": "Audit Tech"},
                    {"field": "quantity", "title": "Quantity"},
                    {"field": "commodityCode", "title": "Commodity Code"},
                    {"field": "category", "title": "Category"},
                    {"field": "description", "title": "Description"},
                    {"field": "stsserial", "hidden": true, "title": "STS Serial"},
                    {"field": "available", "hidden": true, "title": "Available"},
                    {"field": "scrap", "hidden": true, "title": "Scrap"},
                    {"field": "manufacturer", "hidden": true, "title": "Manufacturer"},
                    {"field": "serial", "hidden": true, "title": "Serial"},
                    {"field": "model", "hidden": true, "title": "Model"},
                    {"field": "emc", "hidden": true, "title": "EMC"},
                    {"field": "year", "hidden": true, "title": "Year"},
                    {"field": "formFactor", "hidden": true, "title": "Form Factor"},
                    {"field": "cpu", "hidden": true, "title": "CPU"},
                    {"field": "cpuqty", "hidden": true, "title": "CPU Qty"},
                    {"field": "dataWidth", "hidden": true, "title": "Data Width"},
                    {"field": "cpuspeed", "hidden": true, "title": "CPU Speed"},
                    {"field": "cores", "hidden": true, "title": "Data Width"},
                    {"field": "auditHDDSerial", "hidden": true, "title": "Audit HDD Serial"},
                    {"field": "auditHDDModel", "hidden": true, "title": "Audit HDD Model"},
                    {"field": "auditHDDSize", "hidden": true, "title": "Audit HDD Size"},
                    {"field": "auditHDDStatus", "hidden": true, "title": "Audit HDD Status"},
                    {"field": "videoAdapter", "hidden": true, "title": "Video Adapter"},
                    {"field": "screenSize", "hidden": true, "title": "Screen Size"},
                    {"field": "stand", "hidden": true, "title": "Stand"},
                    {"field": "screenCondition", "hidden": true, "title": "Screen Condition"},
                    {"field": "plasticCondition", "hidden": true, "title": "Plastic Condition"},
                    {"field": "totalMemory", "hidden": true, "title": "Total Memory"},
                    {"field": "ramslots", "hidden": true, "title": "RAM Slots"},
                    {"field": "ramperSlot", "hidden": true, "title": "RAM Per Slot"},
                    {"field": "ramparts", "hidden": true, "title": "RAM Part"},
                    {"field": "rammodel", "hidden": true, "title": "RAM Model"},
                    {"field": "typeofRAM", "hidden": true, "title": "Type of RAM"},
                    {"field": "capacitor", "hidden": true, "title": "Capacitor"},
                    {"field": "battery", "hidden": true, "title": "Battery"},
                    {"field": "batteryHealth", "hidden": true, "title": "Battery Health"},
                    {"field": "acAdaptor", "hidden": true, "title": "A/C Adaptor"},
                    {"field": "camera", "hidden": true, "title": "Camera"},
                    {"field": "connections", "hidden": true, "title": "Connections"},
                    {"field": "dvddrive", "hidden": true, "title": "DVD Drive"},
                    {"field": "coaversion", "hidden": true, "title": "COA Version"},
                    {"field": "coaedition", "hidden": true, "title": "COA Edition"},
                    {"field": "customerID", "hidden": true, "title": "Customer ID"},
                    {"field": "lotID", "hidden": true, "title": "Lot ID"},
                    {"field": "customerAsset", "hidden": true, "title": "Customer Asset"},
                    {"field": "box", "hidden": true, "title": "Box"},
                    {"field": "location", "hidden": true, "title": "Location"},
                    {"field": "palette", "hidden": true, "title": "Palette"},
                    {"field": "soldHDDSerial", "hidden": true, "title": "Sold HDD Serial"},
                    {"field": "soldHDDModel", "hidden": true, "title": "Sold HDD Model"},
                    {"field": "soldHDDSize", "hidden": true, "title": "Sold HDD Size"},
                    {"field": "soldHDDSmart", "hidden": true, "title": "Sold HDD Smart"},
                    {"field": "buildOrder", "hidden": true, "title": "Build Order"},
                    {"field": "buildOS", "hidden": true, "title": "Build OS"},
                    {"field": "buildSKU", "hidden": true, "title": "Build SKU"},
                    {"field": "sku", "hidden": true, "title": "SKU"},
                    {"field": "notes", "hidden": true, "title": "Notes"},
                ]
    }).data("kendoGrid");


    $("#showPieBtn").kendoButton({
        click: function () {
            showPieChart();
        }
    });

    $("#addToDashBtn").kendoButton({
        click: function () {
            addToDashboard();
        }
    });

    var dataTypeAry = [
        {text: "Availability", value: "available"},
        {text: "Scrap", value: "scrap"},
        {text: "Manufacturer", value: "manufacturer"},
        {text: "Model", value: "model"},
        {text: "Form Factor", value: "formFactor"},
        {text: "CPU Speed", value: "cpuspeed"},
        {text: "CPU Cores", value: "cores"},
        {text: "Ram Slots", value: "ramslots"},
        {text: "Ram Per Slot", value: "ramperSlot"},
        {text: "CPU", value: "cpu"},
        {text: "Battery", value: "battery"},
        {text: "Screen Condition", value: "screenCondition"},
        {text: "Plastic Condition", value: "plasticCondition"},
        {text: "COA Version", value: "coaversion"},
        {text: "COA Edition", value: "coaedition"},
        {text: "Customer ID", value: "customerID"},
        {text: "Notes", value: "notes"},
        {text: "Lot ID", value: "lotID"},
        {text: "Audit Tech", value: "auditTech"},
        {text: "Screen Size", value: "screenSize"},
        {text: "Stand", value: "stand"},
        {text: "emc", value: "emc"},
        {text: "year", value: "year"},
        {text: "RAM Model", value: "rammodel"},
        {text: "Type of RAM", value: "typeofRAM"},
        {text: "Battery Health", value: "batteryHealth"},
        {text: "Connections", value: "connections"},
        {text: "DVD Drive", value: "dvddrive"},
        {text: "Commodity Code", value: "commodityCode"},
        {text: "Category", value: "category"},
        {text: "Description", value: "description"},
        {text: "Quantity", value: "quantity"},
    ];

    $("#pie-filter-groupby").kendoDropDownList({
        dataTextField: "text",
        dataValueField: "value",
        dataSource: dataTypeAry,
        index: 0,
    });

    function createChart(data) {
        $("#pie-main-view").kendoChart({
            legend: {
                visible: false
            },
            chartArea: {
                background: ""
            },
            seriesDefaults: {
                labels: {
                    visible: true,
                    position: "outsideEnd",
                    align: "column",
                    background: "transparent",
                    template: "#= category #: #= kendo.format('{0:N3}', value)#%"
                },
                overlay: {
                    gradient: "glass"
                }
            },
            series: [{
                    type: "pie",
                    startAngle: 150,
                    data: data
                }],
            categoryAxis: {
                labels: {
                    step: 6
                }
            },
            tooltip: {
                visible: true,
                format: "{0:N3}%"
            }
        });
    }

    function showPieChart() {
        var grid = $("#pie-filter-grid").data("kendoGrid");
        var dataType = $("#pie-filter-data-type").val();
        var startDate = $("#pie-filter-start-date").data("kendoDatePicker").value();
        var endDate = $("#pie-filter-end-date").data("kendoDatePicker").value();

        var filter = grid.dataSource.filter();
        if (!filter)
        {
            filter = {};
        }

        var groupBy = $("#pie-filter-groupby").val();

        kendo.ui.progress($(".pie-container"), true);
        var dataSource = new kendo.data.DataSource(
                {
                    "transport": {
                        "read": {
                            type: 'POST',
                            contentType: "application/json",
                            url: "../json/mainpie",
                            data: {
                                filter: filter,
                                groupBy: groupBy,
                                dataType: dataType,
                                startDate: startDate,
                                endDate: endDate,
                            }
                        },
                        "parameterMap": function (options) {
                            return JSON.stringify(options);
                        }
                    },
                    schema: {
                        data: "data",
                        "model": {
                            "fields": {
                                "value": {"type": "number"},
                                "count": {"type": "number"},
                                "color": {"type": "date"},
                                "category": {"type": "string"},
                            }
                        }
                    },
                    change: function () {
                        kendo.ui.progress($(".pie-container"), false);
                        var data = this.data();
                        createChart(data);
                    }
                }
        );
        dataSource.read();
    }

    var dataTypeAry = [
        {text: "Check-in Date", value: "checkInDate"},
        {text: "Audit Date", value: "auditDate"},
        {text: "Sold Date", value: "soldDate"},
        {text: "Box Open Date", value: "boxOpenDate"},
        {text: "Box Close Date", value: "boxCloseDate"},
    ];

    $("#pie-filter-data-type").kendoDropDownList({
        dataTextField: "text",
        dataValueField: "value",
        dataSource: dataTypeAry,
        index: 0,
    });

    //Kendo Date Picker.
    function startChange() {
        var startDate = start.value(),
                endDate = end.value();

        if (startDate) {
            startDate = new Date(startDate);
            startDate.setDate(startDate.getDate());
            end.min(startDate);
        } else if (endDate) {
            start.max(new Date(endDate));
        } else {
            endDate = new Date();
            start.max(endDate);
            end.min(endDate);
        }
    }

    function endChange() {
        var endDate = end.value(),
                startDate = start.value();

        if (endDate) {
            endDate = new Date(endDate);
            endDate.setDate(endDate.getDate());
            start.max(endDate);
        } else if (startDate) {
            end.min(new Date(startDate));
        } else {
            endDate = new Date();
            start.max(endDate);
            end.min(endDate);
        }
    }

    var start = $("#pie-filter-start-date").kendoDatePicker({
        value: kendo.date.today(),
        change: startChange
    }).data("kendoDatePicker");

    var end = $("#pie-filter-end-date").kendoDatePicker({
        value: kendo.date.today(),
        change: endChange,
    }).data("kendoDatePicker");

    start.max(end.value());
    end.min(start.value());


    function addToDashboard() {
        var grid = $("#pie-filter-grid").data("kendoGrid");
        var groupBy = $("#pie-filter-groupby").val();

        var filter = grid.dataSource.filter();
        if (!filter)
        {
            filter = {};
        }

        var slot = {};

        slot["title"] = "Main Pie Chart";
        slot["type"] = "pie-chart";
        slot["model"] = "main";
        slot["options"] = {
            filter: filter,
            groupBy: groupBy
        }

        addSlotToServer(slot);
    }
}
