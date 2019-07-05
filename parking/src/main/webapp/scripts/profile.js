$(function () {
    checkSession(function(login) {
        if (login == "") {
            out();
        } else {
            $("#userTitle").html("<h4>" + login + "</h4>");
            person();
        }
    });
});

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

function person() {
    console.log("in get person");
    $.ajax({
        method : "get",
        url : "../create",
        success: function (data) {
            console.log(data);
            var jsonField = $.parseJSON(data);
            $("#username").val(jsonField.name);
            $("#email").val(jsonField.email);
            $("#phone").val(jsonField.phone);
        }
    });
}

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