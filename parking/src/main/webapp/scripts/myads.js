$(function () {
    checkSession(function(login) {
        if (login == "") {
            out();
        } else {
            $("#title").html("<h4>" + login + "</h4>");
            showTable();

        }
    });
});

//destroy session out to index.html
function out() {
    console.log("in out");
    $.ajax({
        method : "get",
        url : "../out",
        success : function (data) {
            window.location.href = "/index.html";
        }
    });
}

//validate session by login
function checkSession(checkLogin) {
    $.ajax({
        method : "get",
        url : "../check",
        dataType : "text",
        success : function (response) {
            checkLogin(response);
        }
    });
}

//draw table with ads
function showTable() {
    var table = $("table tbody");
    table.empty();
    var sold;
    getData(function (data) {
        console.log(data);
        var json = $.parseJSON(data);
        for (var i in json) {
            if (json[i].sold == false) {
                sold = "<input type=\"checkbox\" class=\"form-check-input\" value=\"" + json[i].id + "\" onchange=\"handleCheck(this)\">";
            } else {
                sold = "<input type=\"checkbox\" class=\"form-check-input\" checked=\"checked2\" disabled>";
            }
            var row = "<tr><td><img src=\"data:image/jpeg;base64," + json[i].img + "\" class=\"img-fluid\" alt=\"Responsive image\"></td><td>" + json[i].desc + "</td>" +
                "<td>" + sold+ "</td><td><button type=\"button\" class=\"btn btn-primary\" onclick=\"deleteById(this.id)\" id=\"" + json[i].id + "\">delete</button></td></tr>";

            table.append(row);
        }
    });
}

//get data from servlet
function getData(funct) {
    $.ajax({
        url : "../myAds",
        method : "get",
        success: function (data) {
            funct(data);
        }
    });
}

//delete advert with car by id
function deleteById(click_id) {
    $.ajax({
        url : "../myAds",
        method : "post",
        data : {"operation" : "delete", "id" : click_id},
        success: function (data) {
            location.reload();
        }
    });
}

//mark car as sold
function handleCheck(checkbox) {
    if (checkbox.checked) {
        $.ajax({
            url : "../myAds",
            method : "post",
            data : {"operation" : "sell", "id" : checkbox.value},
            success: function (data) {
                location.reload();
            }
        });
    }
}


