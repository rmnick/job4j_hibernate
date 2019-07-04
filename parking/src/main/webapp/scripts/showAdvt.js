// $(function () {
//     var table = $("table tbody");
//     table.empty();
//     var sold;
//     var jsonRequest = {"show":"all"};
//     $.ajax({
//         url: "./showAds",
//         data: jsonRequest,
//         method: "get",
//         success: function (data) {
//             var json = $.parseJSON(data);
//             for (var i in json) {
//                 if (json[i].sold == false) {
//                     sold = "";
//                 } else {
//                     sold = "<input type=\"checkbox\" class=\"form-check-input\" checked=\"checked2\" disabled>";
//                 }
//                 var row = "<tr></tr><td><img src=\"data:image/jpeg;base64," + json[i].img + "\" class=\"img-fluid\" alt=\"Responsive image\"></td><td>" + json[i].desc + "</td><td>" + sold+ "</td></tr>";
//                 table.append(row);
//             }
//         }
//     });
// });

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

$(window).scroll(function() {
    if($(window).scrollTop() == $(document).height() - $(window).height()) {
        alert("scroll");
    }
});

//show number of rows
function getRow(forAll) {
    var row;
    $.ajax({
        url : "./showAds",
        method : "get",
        success: function (data) {
            forAll(data);
        }

    });
}

//show all ads
$(function () {
    var table = $("table tbody");
    table.empty();
    getRow(function (data) {
        for (var i = 0; i < data; i++) {
            var jsonRequest = {"show" : "all", "start" : i, "max": 1};
            showTable(jsonRequest, table);
        }
    });
});

function desc() {

}

function acs() {

}

function brand() {

}

//draw table with ads
function showTable(jsonValue, table) {
    var sold;
    getData(function (data) {
        var json = $.parseJSON(data);
        for (var i in json) {
            if (json[i].sold == false) {
                sold = "";
            } else {
                sold = "<input type=\"checkbox\" class=\"form-check-input\" checked=\"checked2\" disabled>";
            }
            var row = "<tr></tr><td><img src=\"data:image/jpeg;base64," + json[i].img + "\" class=\"img-fluid\" alt=\"Responsive image\"></td><td>" + json[i].desc + "</td><td>" + sold+ "</td></tr>";
            table.append(row);
        }
    }, jsonValue);
}

//get data from servlet, send "show" option
function getData(funct, jsonValue) {
    $.ajax({
        url : "./showAds",
        data : jsonValue,
        method : "post",
        success: function (data) {
            funct(data);
        }
    });
}

// read all brands for option
$(document).ready(function () {
    $.ajax({
        url: "./select",
        method: "get",
        complete: function (data) {
            var result =  "<option name=\"brand\"></option>";
            var brands = JSON.parse(data.responseText);
            for (var i = 0; i < brands.length; i++) {
                result += "<option value=\""+ brands[i].name + "\" name=\"brand\">" + brands[i].name + "</option>";
            }
            document.getElementById("brand").innerHTML = result;
        }
    });
});

//show model on brands changing
function changeBrand() {
    $.ajax({
        url: "./select",
        method: "post",
        data: {"brand" : $("#brand").val()},
        complete: function (data) {
            console.log(data);
            var result =  "<option name=\"model\"></option>";
            var models = JSON.parse(data.responseText);
            for (var i = 0; i < models.length; i++) {
                result += "<option value=\""+ models[i] + "\" name=\"model\">" + models[i] + "</option>";
            }
            document.getElementById("model").innerHTML = result;
        }
    });
}

//show engines, transmission, bodies on models changing
function changeModel() {
    $.ajax({
        url: "./select",
        method: "post",
        data: {"model": $("#model").val()},
        complete: function (data) {
            console.log(data);
            var resultEngine = "<option name=\"engine\"></option>";
            var resultTransmission = "<option name=\"transmission\"></option>";
            var resultBodies = "<option name=\"bodyCar\"></option>";

            var parts = JSON.parse(data.responseText);
            var engines = parts[0];
            var transmission = parts[1];
            var bodyCar = parts[2];

            for (var i = 0; i < engines.length; i++) {
                resultEngine += "<option value=\"" + engines[i] + "\" name=\"engine\">" + engines[i] + "</option>";
            }
            document.getElementById("engine").innerHTML = resultEngine;

            for (var i = 0; i < transmission.length; i++) {
                resultTransmission += "<option value=\"" + transmission[i] + "\" name=\"transmission\">" + transmission[i] + "</option>";
            }
            document.getElementById("transmission").innerHTML = resultTransmission;

            for (var i = 0; i < bodyCar.length; i++) {
                resultBodies += "<option value=\"" + bodyCar[i] + "\" name=\"bodyCar\">" + bodyCar[i] + "</option>";
            }
            document.getElementById("bodyCar").innerHTML = resultBodies;
        }
    });
}

//search by condition
function search() {
    var brand = $("#brand").val();
    var model = $("#model").val();
    var engine = $("#engine").val();
    var transmission = $("#transmission").val();
    var bodyCar = $("#bodyCar").val();

    console.log(model);
}
