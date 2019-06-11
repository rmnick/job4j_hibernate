$(getTask());

//show tasks
function getTask() {
    $.ajax({
        type : "GET",
        url : "./task",
        dataType : "json",
        success : function (data) {
            var table = $("#tbody");
            table.empty();
            var row;
            $.each(data, function (index, value) {
                var date = new Date(value.created);
                var isdone = value.isDone;
                var id = value.id;
                if ($("#task_check").is(':checked')) {
                    console.log("push check");
                    if (isdone) {
                        console.log(value.id);
                        row ="<tr><td>" + id+ "</td>";
                        row+="<td>" + value.description + "</td>";
                        row+="<td>" + "<input type='radio' class='center-block' name='completed' disabled></td>";
                        row+="<td>" + date.toDateString() + "</td></tr>";
                        table.append(row);
                    }
                } else {
                    console.log("push uncheck");
                    console.log(value.id);
                    row ="<tr><td>" + value.id + "</td>";
                    row+="<td>" + value.description + "</td>";
                    if (isdone) {
                        row+="<td>" + "<input type='radio' class='center-block' name='completed' disabled></td>";

                    } else {
                        row+="<td>" + "<input type='radio' class='center-block' name='completed' value='" + id + "'></td>";

                    }
                    row+="<td>" + date.toDateString() + "</td></tr>";
                    table.append(row);
                }
            });
        }
    });
}

function validate(value) {
    var result = false;
    if (value != null && value != "") {
        result = true;
    } else {
        alert("input something");
    }
    return result;
}

//add task
function add() {
    var description = $('#formControlTextarea').val();
    if (validate(description)) {
        $.ajax({
            method : "post",
            url :  "./task",
            data : {desc : description},
            dataType : "text",
            success : function (data) {
                window.location.href = "/index.html";
            }
        });
    }
}

function checkChoose(input) {
    var result = false;
    if (input.length != 0) {
        result = true;
    } else {
        alert("you did not choose your task");
    }
    return result;
}

// mark as completed your task
function complete() {
    var value = $('input[name=completed]:checked');
    if (checkChoose(value)) {
        var id = value.val();
        $.ajax({
            type : "POST",
            url : "./complete",
            data : {id : id},
            dataType : "text",
            success : function (data) {
                location.reload();
            }
        });
    }
}

