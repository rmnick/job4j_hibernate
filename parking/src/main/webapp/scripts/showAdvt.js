// $(document).ready(function () {
//     $.ajax({
//         url: "./showAds",
//         method: "get",
//         success: function (data) {
//         }
//     });
// });
$(function () {
    getImg(function (data) {
        var img = window.atob(data);
        var table = $("table tbody");
        table.empty();
        var row = "<tr></tr><td><img src=data:image/jpeg;base64, " + data + "/></td><td>desc</td><td>false</td></tr>";
        table.append(row);
        console.log(data);
    })
})


function getImg(convert) {
    $.ajax({
        url: "./showAds",
        method: "get",
        success: function (data) {
            convert(data);
        }
    });
}