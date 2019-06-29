$(document).ready(function () {
    $.ajax({
        url: "./showAds",
        method: "get",
        complete: function (data) {
            var table = $("table tbody");
            var row = "<tr></tr><td>" + data + "</td><td>desc</td><td>false</td></tr>";
            table.append(row);
        }
    });
});