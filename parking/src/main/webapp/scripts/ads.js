
function handleFileSelect(evt) {
    var files = evt.target.files; // FileList object

    // Loop through the FileList and render image files as thumbnails.
    for (var i = 0, f; f = files[i]; i++) {

        // Only process image files.
        if (!f.type.match('image.*')) {
            continue;
        }

        var reader = new FileReader();

        // Closure to capture the file information.
        reader.onload = (function(theFile) {
            return function(e) {
                // Render thumbnail.
                var span = document.createElement('span');
                span.innerHTML = ['<img class="thumb" src="', e.target.result,
                    '" title="', escape(theFile.name), '"/>'].join('');
                document.getElementById('list').insertBefore(span, null);
            };
        })(f);

        // Read in the image file as a data URL.
        reader.readAsDataURL(f);
    }
}

document.getElementById('files').addEventListener('change', handleFileSelect, false);

//read all brands for option
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
function change() {
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

