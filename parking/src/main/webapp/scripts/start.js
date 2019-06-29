
$(function () {
    var loginSession = Cookies.get("login");
    var passSession = Cookies.get("password");
    $('#login').val(loginSession);
    $('#password').val(passSession);
    checkSession(function(login) {
        if (login != "") {
            clickon(login);
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

function clickon(data) {
    $('#signin').hide();
    $('#out').html('<div class="btn-group" id="signout">\n' +
        '                        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">\n'
        + data + '\n' +
        '                        </button>\n' +
        '                        <div class="dropdown-menu">\n' +
        '                            <button class="dropdown-item" onclick="clickout()">Profile</button>\n' +
        '                            <button class="dropdown-item" onclick="clickout()">My ads</button>\n' +
        '                            <button class="dropdown-item" onclick="clickout()">Sign out</button>\n' +
        '                        </div>\n' +
        '                    </div>')
}

function clickout() {
    $.ajax({
        method : "get",
        url : "./out",
        success : function (data) {
            $('#signout').hide();
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
