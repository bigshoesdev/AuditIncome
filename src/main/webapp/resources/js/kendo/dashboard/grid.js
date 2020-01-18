function showGrid(slot) {

    var api_url = "";
    var columns = [];
    var fields = {};
    var filter = slot.options.filter;
    var group = slot.options.group;
    var colShowAry = slot.options.columns;
    var groupable = false;
    if (typeof group !== 'undefined' && group.length > 0) {
        groupable = true;
    }
    switch (slot.model) {
        case "main" :
            api_url = "../json/maingrid";
            break;
        case "rma" :
            api_url = "../json/rmagrid";
            break;
        case "lcd":
            api_url = "../json/lcdgrid";
            break;
    }
    var container = jQuery(".dashboard-container");

    var id = "dashboard-slot-container" + slot.id;

    var slotContent =
            '<div class="col-md-6 dashboard-slot-container" id="' + id + '">' +
            '<div class="dashboard-slot">' +
            '<div class="dashboard-slot-title">' +
            '<a href = "javascript:void(0)" style="text-decoration: none;" class="dashboard-slot-title-text">' + slot.title + '</a>' +
            '</div>' +
            '<div class="dashboard-slot-buttons">' +
            '<em class="excel-export" style="margin-right:10px"></em>' +
            '<em class="remove"></em>' +
            '</div>' +
            '<div class = "dashboard-slot-graph">' +
            '</div>' +
            '</div>' +
            '</div>';

    container.append(slotContent);

    fields = $.extend({}, grid_fields[slot.model]);
    columns = $.map(grid_columns[slot.model], function (col) {
        if ($.inArray(col.field, colShowAry) >= 0)
        {
            return col;
        } else {
            return $.extend({
                hidden: true
            }, col);
        }
    });

    var grid = jQuery("#" + id + " .dashboard-slot-graph").kendoGrid(
            {
                "resizable": true,
                "sortable": true,
                "groupable": groupable,
                "pageable": {
                    input: true,
                    numeric: false
                },
                "columns": columns,
                "dataSource": {
                    "pageSize": 50,
                    "serverPaging": true,
                    "filter": filter,
                    "group": group,
                    "schema":
                            {
                                "total": "total",
                                "data": "data",
                                "groups": "data",
                                "model": {
                                    "fields": fields
                                }
                            },
                    "serverFiltering": true,
                    "serverGrouping": true,
                    "serverSorting": true,
                    "transport": {
                        "read": {"type": "POST", "contentType": "application/json", "url": api_url},
                        "parameterMap": function (options) {
                            return JSON.stringify(options);
                        }
                    }
                },
                "scrollable": {},
                "height": "350px",
            }
    ).data("kendoGrid");


    $("#" + id + " .remove").kendoButton({
        icon: "delete",
        click: function () {
            deleteSlot(slot.id);
        }
    });

    $("#" + id + " .excel-export").kendoButton({
        icon: "excel",
        click: function () {
            grid.saveAsExcel();
        }
    });

    $("#" + id + " .dashboard-slot-title-text").click(function () {
        kendo.prompt("PLEASE ENTER SLOT TITLE:").then(function (data) {
            changeTitle(slot.id, data);
        }, function () {
        })
    })
}

