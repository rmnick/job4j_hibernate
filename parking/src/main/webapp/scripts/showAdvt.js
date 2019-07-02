$(document).ready(function () {
    var table = $("table tbody");
    $.ajax({
        url: "./showAds",
        method: "get",
        success: function (data) {
            var json = $.parseJSON(data);
            console.log(data);
            for (var i in json) {
                var row = "<tr></tr><td><img src=\"data:image/jpeg;base64," + json[i] + "\" class=\"img-fluid\" alt=\"Responsive image\"></td><td>desc</td><td>false</td></tr>";
                console.log(json[i]);
                console.log(row);
                table.append(row);
            }
        }
    });
});

// $(function () {
//     var table = $("table tbody");
//     table.empty();
//     for (var i = 0; i < 4; i++) {
//         getImg(function (data) {
//             if(data != null && data.size > 0) {
//                 var table = $("table tbody");
//                 var row = "<tr></tr><td><img src='./showAds' class='img-fluid' alt='Responsive image'></td><td>desc</td><td>false</td></tr>";
//                 console.log(data);
//                 table.append(row);
//             }
//         });
//     }
// });


function getImg(show) {
    $.ajax({
        url: "./showAds",
        method: "get",
        success: function (data) {
            show(data);
        }
    });
}