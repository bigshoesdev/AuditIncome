jQuery(function () {
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[header] = token;
    $.ajaxSetup({
        headers: headers
    });
    $("#tabstrip").kendoTabStrip({
        animation: {
            open: {
                effects: "fadeIn"
            }
        },
        select: showLoading,
        contentLoaded: hideLoading,
        activate: hideLoading,
    });

    initGridView();
    initLineView();
    initPieView();

    function showLoading(e) {
        var tabstrip = e.sender;
        window.setTimeout(function () {
            kendo.ui.progress(tabstrip.element, true);
        });
    }

    function hideLoading(e) {
        var tabstrip = e.sender;
        kendo.ui.progress($("#tabstrip"), false);
        window.setTimeout(function () {
            kendo.ui.progress(tabstrip.element, false);
        });
    }


    kendo.ui.progress($("body"), false);
    $("#tabstrip").css('visibility', 'visible');
});

function addSlotToServer(slot) {
    kendo.ui.progress($("body"), true);
    $.ajax({
        url: '../dashboard/add',
        type: "POST",
        data: {
            type: slot.type,
            title: slot.title,
            model: slot.model,
            options: kendo.stringify(slot.options)
        },
        success: function (result) {
            kendo.ui.progress($("body"), false);
            if (result.status && result.id > 0) {
                kendo.alert("Successfully Added to the Dashboard");
            } else if (!result.status && result.id == 0) {
                kendo.alert("The number of slots in the dashboard is more than 10.Please remove one of them.");
            }
        },
        error: function () {
            kendo.ui.progress($("body"), false);
            kendo.alert("Network Error!");
        }
    });
}