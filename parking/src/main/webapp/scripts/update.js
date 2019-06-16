function update() {
    var name = $('#username').val();
    var password = $('#password').val();
    var passwordNew = $('#passwordNew').val();
    var phone = $('#phone').val();
    var email = $('#email').val();

    if (!validName(name.split(" "))) {
        alert("please enter correct name");
    } else if (!validPass(password)) {
        alert("please enter password");
    } else if (!validEmail(email)) {
        alert("please enter correct email")
    } else if (!validPhone(phone)) {
        alert("please enter correct phone")
    } else {
        var person = {"name" : name, "email" : email, "phone" : phone, "password" : password};
        $.ajax({
            method : "post",
            url :  "../update",
            data : JSON.stringify(person),
            contentType : "application/json",
            dataType : "text",
            success : function (data) {
                if (data != "") {
                    checkExist(data);
                } else {
                    alert("sorry, something went wrong, try again later");
                }
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

function getMap() {
    var map;
    var success = "success";
    var phone = "phone";
    var email = "email";


    var phoneValue = $('#phone').val();
    var emailValue = $('#email').val();

    map = new Map();
    map.set(phone, failed(phone, phoneValue));
    map.set(email, failed(email, emailValue));
    map.set(success, succeed());

    return map;
}

function failed(data, name) {
    return "this " + data + " " + "\"" + name + "\"" + " is already exist";
}

function succeed() {
    return "updating was successful";
}

function checkExist(data) {
    var map = getMap();
    var result = map.get(data);
    alert(result);
    if (result == succeed()) {
        window.location.href = "../index.html";
    }
}