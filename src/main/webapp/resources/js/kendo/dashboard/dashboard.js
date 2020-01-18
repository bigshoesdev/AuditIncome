jQuery(function () {
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[header] = token;
    $.ajaxSetup({
        headers: headers
    });

    loadSlotData();
});


function deleteSlot(id) {
    kendo.confirm("Are you sure that you want to proceed?").then(function () {
        kendo.ui.progress($("body"), true);
        $.ajax({
            url: '../dashboard/delete',
            type: "POST",
            data: {
                id: id
            },
            success: function (result) {
                kendo.ui.progress($("body"), false);
                if (result.status) {
                    kendo.alert("Successfully Delete Slot.");
                    var cssID = "dashboard-slot-container" + id;
                    $("#" + cssID).remove();
                } else {
                    kendo.alert("Fail In Deleting Slot.");
                }
            },
            error: function () {
                kendo.ui.progress($("body"), false);
                kendo.alert("Network Error!");
            }
        });
    }, function () {
    });
}

function changeTitle(id, text) {
    kendo.ui.progress($("body"), true);
    $.ajax({
        url: '../dashboard/update',
        type: "POST",
        data: {
            id: id,
            text: text
        },
        success: function (result) {
            kendo.ui.progress($("body"), false);
            if (result.status) {
                kendo.alert("Successfully Change Slot Title To " + text + ".");
                var cssID = "dashboard-slot-container" + id;
                $("#" + cssID + " .dashboard-slot-title-text").html(text);
            } else {
                kendo.alert("Fail In Changing Slot Title.");
            }
        },
        error: function () {
            kendo.ui.progress($("body"), false);
            kendo.alert("Network Error!");
        }
    });
}

function loadSlotData() {
    $.ajax({
        url: '../dashboard/list',
        type: "POST",
        success: function (result) {
            var slots = result.data;
            showSlot(slots);
        },
        error: function () {
            kendo.ui.progress($("body"), false);
            kendo.alert("Network Error!");
        }
    });
}

function showSlot(dashboard_slots) {
    for (var i = 0; i < dashboard_slots.length; i++) {
        var slot = dashboard_slots[i];
        slot.options = JSON.parse(slot.options);
        switch (slot.type) {
            case "grid":
                console.log("grid");
                showGrid(slot);
                break;
            case "line-chart":
                console.log("line-chart");
                showLineChart(slot);
                break;
            case "pie-chart":
                console.log("pie-chart");
                showPieChart(slot);
                break;
        }
    }
    kendo.ui.progress($("body"), false);
    $("#dashboard-container").css('visibility', 'visible');
}