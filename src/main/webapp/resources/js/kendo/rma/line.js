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
                            url: "../json/rmaline",
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
                }
            },
            "reorderable": true,
            "resizable": true,
            "columnMenu": true,
            "columns":
                    [
                        {"field": "serial", "lockable": true, "title": "Serial"},
                        {"field": "orderNumber", "lockable": true, "title": "Order Number"},
                        {"field": "tech", "lockable": true, "title": "Tech"},
                        {"field": "manufacturer", "lockable": true, "title": "Manufacturer"},
                        {"field": "model", "lockable": true, "title": "Model", "hidden": true},
                        {"field": "intSerial", "lockable": true, "title": "STS Seria", "hidden": true},
                        {"field": "troubleCode", "lockable": true, "title": "Trouble Code", "hidden": true},
                        {"field": "hddNotes", "lockable": true, "title": "HDD Notes", "hidden": true},
                        {"field": "hddSize", "lockable": true, "title": "HDD Size", "hidden": true},
                        {"field": "hddSerial", "lockable": true, "title": "HDD Serial", "hidden": true},
                        {"field": "hddModel", "lockable": true, "title": "HDD Model", "hidden": true},
                        {"field": "memNotes", "lockable": true, "title": "Mem Notes", "hidden": true},
                        {"field": "sku", "lockable": true, "title": "Sku", "hidden": true},
                        {"field": "notes", "lockable": true, "title": "Notes", "hidden": true},
                    ]
        }).data("kendoGrid");
    }

    var dataTypeAry = [
        {text: "Date Processed", value: "dateProcessed"},
        {text: "Date Produced", value: "dateProduced"},
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

        slot["title"] = "RMA Line Chart";
        slot["type"] = "line-chart";
        slot["model"] = "rma";
        slot["options"] = {
            filters: filtersAry
        }

        addSlotToServer(slot);
    }
}
