jQuery(function () {
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[header] = token;
    $.ajaxSetup({
        headers: headers
    });

    $("#makeReport").click(function () {
        var customerID = $("#customerID").val();
        var lotID = $("#lotID").val();

        var data = {
            customerID: customerID,
            lotID: lotID,
        };

        console.log(data);
        kendo.ui.progress($("body"), true);
        $.ajax({
            url: '../excel/report',
            type: "POST",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                kendo.ui.progress($("body"), false);
                window.open("../resources/tmp/" + result.excelFile, '_blank');
            }
        });
    });
});
