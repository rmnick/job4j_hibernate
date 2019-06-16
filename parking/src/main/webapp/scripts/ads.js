$(function () {
    checkSession(function(login) {
        if (login == "") {
            out();
        }
    });
});


//show choosing picture
function handleFileSelect(evt) {
    var file = evt.target.files; // FileList object
    var f = file[0];
    // Only process image files.
    if (!f.type.match('image.*')) {
        alert("Image only please....");
        return false;
    } else if (f.size > (1000 * 1024)) {
        alert("too big");
        return false;
    }
    var reader = new FileReader();
    // Closure to capture the file information.
    reader.onload = (function(theFile) {
        return function(e) {
            // Render thumbnail.
            var span = document.createElement('span');
            span.innerHTML = ['<img class="thumb" title="', escape(theFile.name), '" src="', e.target.result, '" />'].join('');
            document.getElementById('output').insertBefore(span, null);
        };
    })(f);
    // Read in the image file as a data URL.
    reader.readAsDataURL(f);
}
document.getElementById('file').addEventListener('change', handleFileSelect, false);

// read all brands for option
$(document).ready(function () {
    $.ajax({
        url: "../select",
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
        url: "../select",
        method: "post",
        data: {"brand" : $("#brand").val()},
        complete: function (data) {
            console.log(data);
            var result =  "<option name=\"model\"></option>";
            var models = JSON.parse(data.responseText);
            for (var i = 0; i < models.length; i++) {
                result += "<option value=\""+ models[i] + "\" name=\"model\">" + models[i] + "</option>";
            }
            document.getElementById("model").innerHTML = result;
        }
    });
}

//show engines, transmission, bodies on models changing
function changeModel() {
    $.ajax({
        url: "../select",
        method: "post",
        data: {"model": $("#model").val()},
        complete: function (data) {
            console.log(data);
            var resultEngine = "<option name=\"engine\"></option>";
            var resultTransmission = "<option name=\"transmission\"></option>";
            var resultBodies = "<option name=\"bodyCar\"></option>";

            var parts = JSON.parse(data.responseText);
            var engines = parts[0];
            var transmission = parts[1];
            var bodyCar = parts[2];

            for (var i = 0; i < engines.length; i++) {
                resultEngine += "<option value=\"" + engines[i] + "\" name=\"engine\">" + engines[i] + "</option>";
            }
            document.getElementById("engine").innerHTML = resultEngine;

            for (var i = 0; i < transmission.length; i++) {
                resultTransmission += "<option value=\"" + transmission[i] + "\" name=\"transmission\">" + transmission[i] + "</option>";
            }
            document.getElementById("transmission").innerHTML = resultTransmission;

            for (var i = 0; i < bodyCar.length; i++) {
                resultBodies += "<option value=\"" + bodyCar[i] + "\" name=\"bodyCar\">" + bodyCar[i] + "</option>";
            }
            document.getElementById("bodyCar").innerHTML = resultBodies;
        }
    });
}

//check all fields for empty spaces
function validate() {
    var result = true;
    var fields = [$('#brand'), $('#model'), $('#engine'), $('#transmission'), $('#bodyCar'), $('#price'), $('#mileage'), $('#month')];
    console.log("in validate");
    for(var i = 0; i < fields.length; i++) {
      if (fields[i].val() == null || fields[i].val() == "") {
          alert("please fill the " + fields[i].attr('name') + " field");
          result = false;
          break;
      }
    }
    return result;
}

//destroy session, change location
function out() {
    $.ajax({
        method : "get",
        url : "../out",
        success : function (data) {
            window.location.href = "/index.html";
        }
    });
}

//check session attribute "login"
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
