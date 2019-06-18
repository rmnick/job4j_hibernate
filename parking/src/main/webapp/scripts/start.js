function clickChange() {

}

$(function () {
    var loginSession = Cookies.get("login");
    console.log(loginSession);
    $('#login').val(loginSession);
});

function clickon() {
    console.log('hey');
    $('#signin').hide();
    $('#out').html('<button type="button" id="signout" class="btn btn-primary" onclick="clickout()">sign out</button>');
}

function clickout() {
    console.log('heyhey');
    $('#signout').hide();
    $('#in').html('<button type="button" id="signin" class="btn btn-primary" onclick="clickon()">sign in</button>');
}

function enter() {
    var login = $('#login').val();
    var password = $('#pass').val();
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
                console.log(data);
            }
        });
    // }
}