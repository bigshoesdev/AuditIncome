function showPieChart(slot) {
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
            '</div>' +
            '<div class="dashboard-slot-graph" style="height: 350px">' +
            '</div>' +
            '<div class="overlay" style="visibility: hidden"><div>No data available</div></div>' +
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
                createChart(container);
            }
        }
    });

    $("#" + id + " #filter-data-type-input").kendoDropDownList({
        dataTextField: "text",
        dataValueField: "value",
        dataSource: data_type[slot.model],
        change: function () {
            loadPieChartData();
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
            loadPieChartData();
        },
        index: 2
    });

    $("#" + id + " .filter-date-range").kendoTooltip({
        content: "Please select filter date range",
        position: "top",
        width: 150
    });

    $("#" + id + " .dashboard-slot-title-text").click(function () {
        kendo.prompt("PLEASE ENTER SLOT TITLE:").then(function (data) {
            changeTitle(slot.id, data);
        }, function () {
        })
    });

    kendo.ui.progress($("#" + id), true);

    loadPieChartData();

    function createChart(pDFContainer) {
        if (remoteData.length === 0)
        {
            $("#" + id + " .overlay").css("visibility", "visible");
        } else
        {
            $("#" + id + " .overlay").css("visibility", "hidden");
        }

        if (pDFContainer) {
            pDFContainer.kendoChart({
                title: {
                    text: $("#" + id + " .dashboard-slot-title-text").html()
                },
                legend: {
                    visible: false
                },
                chartArea: {
                    background: "",
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
                series: [
                    {
                        type: "pie",
                        startAngle: 150,
                        data: remoteData
                    }
                ],
                categoryAxis: {
                    labels: {
                        step: 6
                    }
                },
                tooltip: {
                    visible: true,
                    format: "{0:N3}%"
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
        } else {
            $("#" + id + " .dashboard-slot-graph").kendoChart({
                legend: {
                    visible: false
                },
                chartArea: {
                    background: "",
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
                series: [
                    {
                        type: "pie",
                        startAngle: 150,
                        data: remoteData
                    }
                ],
                categoryAxis: {
                    labels: {
                        step: 6
                    }
                },
                tooltip: {
                    visible: true,
                    format: "{0:N3}%"
                },
            });
        }
    }

    function loadPieChartData() {
        var api_url = "";

        var dateRange = $("#" + id + " #filter-date-range-input").val();
        var startDate = kendo.date.today();
        var endDate = kendo.date.today();
        var dataType = $("#" + id + " #filter-data-type-input").val();

        console.log(dateRange);

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
                api_url = "../json/mainpie";
                break;
            case "rma" :
                api_url = "../json/rmapie";
                break;
            case "lcd":
                api_url = "../json/lcdpie";
                break;
        }

        var dataSource = new kendo.data.DataSource(
                {
                    "transport": {
                        "read": {
                            type: 'POST',
                            contentType: "application/json",
                            url: api_url,
                            data: {
                                filter: slot.options.filter,
                                groupBy: slot.options.groupBy,
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
                        kendo.ui.progress($("#" + id), false);
                        remoteData = this.data();
                        createChart();
                    }
                }
        );
        dataSource.read();
    }
}

