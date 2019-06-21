
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
                window.location.href = "view/ads.html";
            } else {
                $("#signin").click();
            }
        })
    });
    $("#reg").click(function () {
        window.location.href = "view/registration.html";
    })
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
    if (!validLogin(login)) {
        alert("please enter correct login");
    } else if (!validPass(password)) {
        alert("please enter password");
    } else {
        var person = {"login": login, "password": password};
        $.ajax({
            method : "post",
            url :  "./enter",
            data : JSON.stringify(person),
            contentType : "application/json",
            dataType : "text",
            success : function (data) {
                if (data != "") {
                    window.location.href = "/" + data + ";"
                } else {
                    alert("please check your login or password");
                }
            }
        });
    }
}

function validLogin(login) {
    var regEx = /[^-\s][a-zA-Z0-9-_\\s]*$/;
    return regEx.test(login);
}

function validPass(password) {
    return password != '';
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
