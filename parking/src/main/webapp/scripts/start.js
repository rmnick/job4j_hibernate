function clickChange() {

}

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