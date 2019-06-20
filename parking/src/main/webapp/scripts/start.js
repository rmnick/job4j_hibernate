
$(function () {
    var loginSession = Cookies.get("login");
    var passSession = Cookies.get("password");
    $('#login').val(loginSession);
    $('#password').val(passSession);
    checkSession(function(login) {
        if (login != "") {
            clickon();
        }
    });
});


$(document).ready(function () {
    $("#add").click(function () {
        checkSession(function (login) {
            if (login != "") {
                window.location.href = "view/ads.html"
            } else {
                $("#signin").click();
            }
        })
    });
});

function clickon() {
    $('#signin').hide();
    $('#out').html('<button type="button" id="signout" class="btn btn-primary" onclick="clickout()">sign out</button>');
}

function clickout() {
    $.ajax({
        method : "get",
        url : "./out",
        success : function (data) {
            $('#signout').hide();
            $('#in').html('<button type="button" id="signin" class="btn btn-primary" onclick="clickon()">sign in</button>');
            window.location.href = "/index.html";
        }
    });
}

function enter() {
    var login = $('#login').val();
    var password = $('#password').val();
    // if (!validName(userName.split(" "))) {
    //     alert("please enter correct name");
    // } else if (!validPhone(userPhone)) {
    //     alert("please enter correct phone");
    // } else if (!userCheck(userName, userPhone)) {
    //     alert("the phone number is already registered in another name");
    // } else {
        var person = {"login": login, "password": password};
        $.ajax({
            method : "post",
            url :  "./enter",
            data : JSON.stringify(person),
            contentType : "application/json",
            dataType : "text",
            success : function (data) {
                // alert(data);
                // window.location.href = '../index.html';
                window.location.href = "/" + data + ";"
            }
        });
    // }
}

function checkSession(checkLogin) {
    $.ajax({
       method : "get",
        url : "./check",
       dataType : "text",
       success : function (response) {
           checkLogin(response);
       }
    });
}
