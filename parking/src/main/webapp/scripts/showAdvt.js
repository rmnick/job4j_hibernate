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

// function getRow(number) {
//     $.ajax({
//         url: "./showAds",
//         method: "get",
//         success: function (data) {
//             console.log(data);
//             number(data);
//         }
//
//     });
// }

// $(function() {
//     var firstBatch = 3;
//     var table = $("table tbody");
//     table.empty();
//     getRow(function (data) {
//         var number = data;
//         if (number < firstBatch) {
//             for (var i = 0; i < number; i++) {
//                 var jsonRequest = {"show" : "all", "start" : i, "max": 1};
//                 showTable(jsonRequest, table);
//             }
//         } else {
//             var jsonRequest = {"show" : "all", "start" : 0, "max": firstBatch};
//             showTable(jsonRequest, table);
//             myScroll(number, firstBatch);
//         }
//     });
//
//     function myScroll(number, start) {
//         $(window).scroll(function() {
//             if($(window).scrollTop() == $(document).height() - $(window).height()) {
//                 var jsonRequest = {"show" : "all", "start" : start, "max": 1};
//                 showTable(jsonRequest, table);
//                 start++;
//                 console.log(number);
//                 if(start == number) {
//                     console.log("equals");
//                     return false;
//                 }
//             }
//         });
//     }
// });


// get all ads when opening the page
$(function () {
    var jsonRequestMap = new Map();
    jsonRequestMap.set("additional", "desc");
    main(jsonRequestMap);
});

//main function for showing ads
function main(jsonRequestMap) {
    var table = $("table tbody");
    table.empty();
    getRow(function (data) {
        for (var i = 0; i < data; i++) {
            var jsonRequestStr = "{";
            jsonRequestMap.forEach(function (value, key) {
               jsonRequestStr += ( "\"" + key + "\"" + ":" + "\"" + value + "\"" + ", ");
            });
            jsonRequestStr += "\"start\" : " + "\"" + i + "\"" + ", ";
            jsonRequestStr +="\"max\" : \"1\"";
            jsonRequestStr += "}";
            var jsonRequest = JSON.parse(jsonRequestStr);
            showTable(jsonRequest, table);
        }
    });
}

//get number of rows
function getRow(forAll) {
    $.ajax({
        url : "./showAds",
        method : "get",
        success: function (data) {
            forAll(data);
        }
    });
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
            var row = "<tr><td><img src=\"data:image/jpeg;base64," + json[i].img + "\" class=\"img-fluid\" alt=\"Responsive image\"></td><td>" + json[i].desc + "</td><td>" + sold+ "</td></tr>";
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
        async : false,
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
            var result =  "<option name=\"model\"></option>";
            var models = JSON.parse(data.responseText);
            for (var i = 0; i < models.length; i++) {
                result += "<option value=\""+ models[i] + "\" name=\"model\">" + models[i] + "</option>";
            }
            document.getElementById("model").innerHTML = result;
        }
    });
}

//reading all option and create request
function searchByOptions() {
    var jsonRequestMap = new Map();
    var brand = $("#brand").val();
    var model = $("#model").val();
    var additional = $("#sort").val();

    if (brand != null && brand != "") {
        jsonRequestMap.set("brand", brand);
    }
    if (model != null && model != "") {
        jsonRequestMap.set("model", model);

    }

    switch (additional) {
        case("sort by newest first"):
            additional = "desc";
            break;
        case("sort by oldest first"):
            additional = "asc";
            break;
        case("show for the last day"):
            additional = "desc";
            jsonRequestMap.set("last", "");
            break;
        default:
            break;
    }
    jsonRequestMap.set("additional", additional);

    if ($('.form-check-input').is(':checked')) {
        jsonRequestMap.set("photo", "");
    }

    main(jsonRequestMap);
}
