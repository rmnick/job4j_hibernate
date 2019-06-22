function registration() {
    var name = $('#username').val();
    var login = $('#login').val();
    var password = $('#password').val();
    var phone = $('#phone').val();
    var email = $('#email').val();

    if (!validName(name.split(" "))) {
        alert("please enter correct name");
    } else if (!validLogin(login)) {
        alert("please enter correct login");
    } else if (!validPass(password)) {
        alert("please enter password");
    } else if (!validEmail(email)) {
        alert("please enter correct email")
    } else if (!validPhone(phone)) {
        alert("please enter correct phone")
    } else {
        var person = {"name" : name, "email" : email, "login": login, "phone" : phone, "password" : password};
        console.log("success");
        $.ajax({
            method : "post",
            url :  "../create",
            data : JSON.stringify(person),
            contentType : "application/json",
            dataType : "text",
            success : function (data) {
                // if (data != "") {
                //     window.location.href = "/" + data + ";"
                // } else {
                //     alert("please check your login or password");
                // }
                console.log(data);
            }
        });
    }
}

function validName(name) {
    var regExp =  /^[A-Z]{1}[a-z]{0,15}$/;
    var result = true;
    for (var index in name) {
        if (!regExp.test(name[index])) {
            result = false;
            break;
        }
    }
    return result && name.length === 3;
}

function validLogin(login) {
    var regEx = /[^-\s][a-zA-Z0-9-_\\s]*$/;
    return regEx.test(login);
}

function validEmail(email) {
    var regEx = /.+@.+\..+/;
    return regEx.test(email);
}

function validPhone(phone) {
    var regExp =  /^(7){1}(9){1}(\d){9}$/;
    return regExp.test(phone);
}

function validPass(password) {
    return password != '' && password.length > 3;
}

// function test() {
//     var name = $('#username').val();
//     var login = $('#login').val();
//     var password = $('#password').val();
//
//     var checkMap = new Map();
//
//     var namesMap = new Map();
//
//     namesMap.set(name, "input correct name");
//     namesMap.set(login, "input correct login");
//     namesMap.set(password, "input password");
//
//     checkMap.set(name, validName(name.split(" ")));
//     checkMap.set(login, validLogin(login));
//     checkMap.set(password, validPass(password));
//     checkMap.forEach(function (key, value) {
//         console.log(value);
//     });
// }