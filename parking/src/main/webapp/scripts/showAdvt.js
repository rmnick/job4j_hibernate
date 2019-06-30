$(document).ready(function () {
    $.ajax({
        url: "./showAds",
        method: "get",
        success: function (data) {
            var table = $("table tbody");
            var row = "<tr></tr><td><img src='./showAds' class='img-fluid' alt='Responsive image'></td><td>desc</td><td>false</td></tr>";
            table.append(row);
        }
    });
});