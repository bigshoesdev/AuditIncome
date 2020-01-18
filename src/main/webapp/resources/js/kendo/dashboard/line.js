function showLineChart(slot) {
    var container = jQuery(".dashboard-container");
    var id = "dashboard-slot-container" + slot.id;

    var slotContent =
            '<div class="col-md-6 dashboard-slot-container" id="' + id + '">' +
            '<div class="dashboard-slot">' +
            '<div class="dashboard-slot-title">' +
            '<a href="javascript:void(0)" style="text-decoration: none;" class="dashboard-slot-title-text">' + slot.title + '</a>' +
            '</div>' +
            '<div class="dashboard-slot-buttons">' +
            '<em class="pdf-export"  style="margin-right:10px"></em>' +
            '<em class="remove"></em>' +
            '</div>' +
            '<div class="dashboard-slot-filter">' +
            '<div class="col-md-4 dashboard-slot-filter-item filter-data-type">' +
            '<input id="filter-data-type-input" style="width:100%"/>' +
            '</div>' +
            '<div class="col-md-4 dashboard-slot-filter-item filter-date-range">' +
            '<input id="filter-date-range-input" style="width:100%"/>' +
            '</div>' +
            '<div class="col-md-4 dashboard-slot-filter-item filter-data-aggregate">' +
            '<input id="filter-data-aggregate-input" style="width:100%;"/>' +
            '</div>' +
            '</div>' +
            '<div class="dashboard-slot-graph" >' +
            '</div>' +
            '</div>' +
            '</div>';

    container.append(slotContent);

    var remoteData = [];

    $("#" + id + " .remove").kendoButton({
        icon: "delete",
        click: function () {
            deleteSlot(slot.id);
        }
    });
    $("#" + id + " .pdf-export").kendoButton({
        icon: "pdf",
        click: function () {
            var chart = $("#" + id + " .dashboard-slot-graph").getKendoChart();
            if (chart) {
                var container = $('<div style="width:800px;height:400px;display:none;" />').css({
                    position: 'absolute',
                    top: 0,
                    left: -1500
                }).appendTo('body');
                refreshLineChart(container);
            }
        }
    });

    $("#" + id + " #filter-data-type-input").kendoDropDownList({
        dataTextField: "text",
        dataValueField: "value",
        dataSource: data_type[slot.model],
        change: function () {
            loadLineChartData();
        },
        index: 0,
    });

    $("#" + id + " .filter-data-type").kendoTooltip({
        content: "Please select filter data type.",
        position: "top",
        width: 150,
    });


    $("#" + id + " #filter-date-range-input").kendoDropDownList({
        dataTextField: "text",
        dataValueField: "value",
        dataSource: date_range,
        change: function () {
            loadLineChartData();
        },
        index: 2
    });

    $("#" + id + " .filter-date-range").kendoTooltip({
        content: "Please select filter date range",
        position: "top",
        width: 150
    });

    $("#" + id + " #filter-data-aggregate-input").kendoDropDownList({
        dataTextField: "text",
        dataValueField: "value",
        dataSource: data_aggregate,
        change: function () {
            refreshLineChart();
        },
        index: 0
    });

    $("#" + id + " .filter-data-aggregate").kendoTooltip({
        content: "Please select data aggregate function",
        position: "top",
        width: 150
    });

    $("#" + id + " .dashboard-slot-title-text").click(function () {
        kendo.prompt("PLEASE ENTER SLOT TITLE:").then(function (data) {
            changeTitle(slot.id, data);
        }, function () {
        })
    });

    loadLineChartData();

    function refreshLineChart(pDFContainer) {
        var seriesConfig = [];
        var aggregate = $("#" + id + " #filter-data-aggregate-input").val();
        var dateRange = $("#" + id + " #filter-date-range-input").val();
        var baseUnit = "";

        switch (dateRange) {
            case "d7":
                baseUnit = "days";
                break;
            case "d14":
                baseUnit = "days";
                break;
            case "d30":
                baseUnit = "days";
                break;
            case "d90":
                baseUnit = "weeks";
                break;
            case "y1":
                baseUnit = "months";
                break;
        }

        for (var i = 0; i < remoteData.length; i++)
        {
            seriesConfig.push({
                type: "line",
                aggregate: aggregate,
                field: "value",
                categoryField: "date",
                data: remoteData[i],
                color: getRandomColor(),
                name: "Date Set" + (i + 1)
            });
        }
        if (pDFContainer) {
            pDFContainer.kendoChart({
                series: seriesConfig,
                categoryAxis: {
                    title: {
                        text: $("#" + id + " .dashboard-slot-title-text").html()
                    },
                    labels: {
                        rotation: -90
                    },
                    crosshair: {
                        visible: true
                    },
                    baseUnit: baseUnit,
                },
                tooltip: {
                    visible: true,
                    format: "{0}%",
                    template: "#= series.name #: #= value #"
                },
                render: function (e) {
                    setTimeout(function () {
                        pDFContainer.getKendoChart().exportPDF({
                            paperSize: "auto",
                            margin: {left: "1cm", top: "1cm", right: "1cm", bottom: "1cm"},
                        }).done(function (data) {
                            kendo.saveAs({
                                dataURI: data,
                                fileName: "chart.pdf"
                            });
                        });
                        pDFContainer.getKendoChart().destroy();
                        pDFContainer.remove();
                    }, 2000);
                }
            });
        } else
        {
            $("#" + id + " .dashboard-slot-graph").kendoChart({
                series: seriesConfig,
                categoryAxis: {
                    labels: {
                        rotation: -90
                    },
                    crosshair: {
                        visible: true
                    },
                    baseUnit: baseUnit,
                },
                tooltip: {
                    visible: true,
                    format: "{0}%",
                    template: "#= series.name #: #= value #"
                }
            });
        }
    }

    function getRandomColor() {
        var letters = '0123456789ABCDEF';
        var color = '#';
        for (var i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    function loadLineChartData() {
        var api_url = "";

        var dateRange = $("#" + id + " #filter-date-range-input").val();

        var startDate = kendo.date.today();
        var endDate = kendo.date.today();
        var dataType = $("#" + id + " #filter-data-type-input").val();

        switch (dateRange) {
            case "d7":
                startDate.setDate(startDate.getDate() - 7);
                break;
            case "d14":
                startDate.setDate(startDate.getDate() - 14);
                break;
            case "d30":
                startDate.setDate(startDate.getDate() - 30);
                break;
            case "d90":
                startDate.setDate(startDate.getDate() - 90);
                break;
            case "y1":
                startDate.setDate(startDate.getDate() - 365);
                break;
        }

        switch (slot.model) {
            case "main" :
                api_url = "../json/mainline";
                break;
            case "rma" :
                api_url = "../json/rmaline";
                break;
            case "lcd":
                api_url = "../json/lcdline";
                break;
        }

        kendo.ui.progress($("#" + id), true);

        var dataSource = new kendo.data.DataSource(
                {
                    "transport": {
                        "read": {
                            type: 'POST',
                            contentType: "application/json",
                            url: api_url,
                            data: {
                                filters: slot.options.filters,
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
                        kendo.ui.progress($("#" + id), false);
                        refreshLineChart();
                    }
                }
        );
        dataSource.read();
    }
}

