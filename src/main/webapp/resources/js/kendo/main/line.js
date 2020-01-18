function initLineView() {
    var remoteData = [];
    var filterViewIDAry = [];

    $(".export-line-pdf").click(function () {
        var chart = $("#line-main-view").getKendoChart();
        if (chart) {
            chart.exportPDF({paperSize: "auto", margin: {left: "1cm", top: "1cm", right: "1cm", bottom: "1cm"}}).done(function (data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.pdf"
                });
            });
        }
    });

    $(".export-line-img").click(function () {
        var chart = $("#line-main-view").getKendoChart();
        if (chart) {
            chart.exportImage().done(function (data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.png"
                });
            });
        }
    });

    $(".export-line-svg").click(function () {
        var chart = $("#line-main-view").getKendoChart();
        if (chart) {
            chart.exportSVG().done(function (data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.svg"
                });
            });
        }
    });

    $("#filterAddBtn").kendoButton({
        click: function () {
            addFilterData();
        }
    });

    $("#addDashBtn").kendoButton({
        click: function () {
            addToDashboard();
        }
    });

    $("#showChartBtn").kendoButton({
        click: function () {
            showLineChart();
        }
    });

    addFilterData();

    function showLineChart() {
        var filtersAry = [];
        var dataType = $("#line-axis-data-type").val();
        var startDate = $("#line-axis-start-date").data("kendoDatePicker").value();
        var endDate = $("#line-axis-end-date").data("kendoDatePicker").value();
        for (var i = 0; i < filterViewIDAry.length; i++)
        {
            var grid = $("#" + filterViewIDAry[i]).data("kendoGrid");
            var filter = grid.dataSource.filter();
            if (!filter)
            {
                kendo.alert("Please input filter on data set " + (i + 1));
                return;
            }
            filtersAry.push(grid.dataSource.filter());
        }

        if (!startDate || !endDate)
        {
            kendo.alert("Please input date to filter");
            return;
        }

        kendo.ui.progress($(".line-container"), true);
        var dataSource = new kendo.data.DataSource(
                {
                    "transport": {
                        "read": {
                            type: 'POST',
                            contentType: "application/json",
                            url: "../json/mainline",
                            data: {
                                filters: filtersAry,
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
                    },
                    change: function () {
                        var data = this.data();
                        remoteData = [];
                        for (var i = 0; i < data.length; i++)
                        {
                            var ary = [];
                            for (var j = 0; j < data[i].length; j++)
                            {
                                var d = data[i][j].date.split("-");
                                ary.push({
                                    value: data[i][j].value,
                                    date: new Date(d[0], d[1] - 1, d[2]),
                                })
                            }
                            remoteData.push(ary);
                        }
                        kendo.ui.progress($(".line-container"), false);
                        refreshLineChart();
                    }
                }
        );
        dataSource.read();
    }

    function addFilterData() {
        var newIndex = filterViewIDAry.length + 1;
        var wrapper = $(".line-filter-wrapper");
        var id = "line-filter-grid-view" + newIndex;
        var newGridHtml = '<div class="line-filter-grid">' +
                '<h4>Chart Data Set ' + newIndex + '</h4>' +
                '<div id="' + id + '" class="line-filter-grid-view">' +
                '</div>' +
                '</div>';
        wrapper.append(newGridHtml);
        filterViewIDAry.push(id);
        $("#" + id).kendoGrid({
            "filterable": {
                mode: "menu, row"
            },
            "dataSource": {
                transport: {
                    read: {
                        url: "../resources/lib/kendo",
                    }
                },
                schema: {
                    "model": {
                        "fields": {
                            "checkInTech": {"type": "string"},
                            "auditTech": {"type": "string"},
                            "quantity": {"type": "string"},
                            "commodityCode": {"type": "string"},
                            "category": {"type": "string"},
                            "description": {"type": "string"},
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
    }

    var dataTypeAry = [
        {text: "Check-in Date", value: "checkInDate"},
        {text: "Audit Date", value: "auditDate"},
        {text: "Sold Date", value: "soldDate"},
        {text: "Box Open Date", value: "boxOpenDate"},
        {text: "Box Close Date", value: "boxCloseDate"},
    ];

    $("#line-axis-data-type").kendoDropDownList({
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

    var start = $("#line-axis-start-date").kendoDatePicker({
        value: kendo.date.today(),
        change: startChange
    }).data("kendoDatePicker");

    var end = $("#line-axis-end-date").kendoDatePicker({
        value: kendo.date.today(),
        change: endChange,
    }).data("kendoDatePicker");

    start.max(end.value());
    end.min(start.value());

    $(".box").bind("change", refreshLineChart);

    function getRandomColor() {
        var letters = '0123456789ABCDEF';
        var color = '#';
        for (var i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    function refreshLineChart() {
        var seriesConfig = [];
        for (var i = 0; i < remoteData.length; i++)
        {
            seriesConfig.push({
                type: "line",
                aggregate: $("input:radio[name=aggregate]").filter(":checked").val(),
                field: "value",
                categoryField: "date",
                data: remoteData[i],
                color: getRandomColor(),
                name: "Date Set" + (i + 1)
            });
        }
        $("#line-main-view").kendoChart({
            series: seriesConfig,
            categoryAxis: {
                labels: {
                    rotation: -90
                },
                crosshair: {
                    visible: true
                },
                baseUnit: $("input:radio[name=baseUnit]").filter(":checked").val(),
            },
            tooltip: {
                visible: true,
                format: "{0}%",
                template: "#= series.name #: #= value #"
            }
        });
    }

    function addToDashboard() {
        var filtersAry = [];
        for (var i = 0; i < filterViewIDAry.length; i++)
        {
            var grid = $("#" + filterViewIDAry[i]).data("kendoGrid");
            var filter = grid.dataSource.filter();
            if (!filter)
            {
                kendo.alert("Please input filter on data set " + (i + 1));
                return;
            }
            filtersAry.push(grid.dataSource.filter());
        }

        var slot = {};

        slot["title"] = "Main Line Chart";
        slot["type"] = "line-chart";
        slot["model"] = "main";
        slot["options"] = {
            filters: filtersAry
        }

        addSlotToServer(slot);
    }
}
