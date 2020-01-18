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
            },
        },
        "reorderable": true,
        "resizable": true,
        "columnMenu": true,
        "columns":
                [
                    {"field": "serial", "title": "Serial"},
                    {"field": "manufacturer", "title": "Manufacturer"},
                    {"field": "model", "title": "Model"},
                    {"field": "screenSize", "title": "Screen Size"},
                    {"field": "screenCondition", "title": "Screen Condition", hidden: true},
                    {"field": "intSerial", "title": "STS Serial", hidden: true},
                    {"field": "customerId", "title": "Customer ID", hidden: true},
                    {"field": "customerAsset", "title": "Customer Asset", hidden: true},
                    {"field": "lotId", "title": "Lot ID", hidden: true},
                    {"field": "palette", "title": "Palette", hidden: true},
                    {"field": "location", "title": "Location", hidden: true},
                    {"field": "box", "title": "Box", hidden: true},
                    {"field": "notes", "title": "Notes", hidden: true},
                    {"field": "available", "title": "Available", hidden: true},
                    {"field": "stand", "title": "Stand", hidden: true},
                    {"field": "hours", "title": "Hours", hidden: true},
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
        {text: "Manufacturer", value: "manufacturer"},
        {text: "Model", value: "model"},
        {text: "Screen Condition", value: "screenCondition"},
        {text: "Customer ID", value: "customerId"},
        {text: "Lot ID", value: "lotId"},
        {text: "Screen Size", value: "screenSize"},
        {text: "Availability", value: "available"},
        {text: "Stand", value: "stand"},
        {text: "Hours", value: "hours"},
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
                            url: "../json/lcdpie",
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
                        createChart(data)
                    }
                }
        );
        dataSource.read();
    }

    var dataTypeAry = [
        {text: "Audit Date", value: "dateIn"},
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

        slot["title"] = "LCD Pie Chart";
        slot["type"] = "pie-chart";
        slot["model"] = "lcd";
        slot["options"] = {
            filter: filter,
            groupBy: groupBy
        }

        addSlotToServer(slot);
    }
}
