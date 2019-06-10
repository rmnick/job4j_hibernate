$(getTask());
function getTask() {
    console.log("in task");
    $.ajax({
        type : "GET",
        url : "./task",
        dataType : "json",
        success : function (data) {
            $.each(data, function (index, value) {
                console.log(value);
            })
            var table = $("table tbody");
            table.empty();
            var row;
            $.each(data, function (index, value) {
                if ($("#task_check").is(':checked')) {
                    if (value.isDone != true) {
                        row+="<tr><th>" + value.id + "</th>";
                        row+="<th>" + value.description + "</th>";
                        row+="<th>" + value.isDone + "</th>";
                        row+="<th>" + value.created + "</th></tr>";
                    }
                } else {
                        row+="<tr><th>" + value.id + "</th>";
                        row+="<th>" + value.description + "</th>";
                        row+="<th>" + value.isDone + "</th>";
                        row+="<th>" + value.created + "</th></tr>";
                }
                table.append(row);
            });
        }
    });
}

// function showAll()
// {
//     if ($('#task_check').is(':checked')) {
//         console.log("check");
//     } else {
//         console.log("uncheck");
//     }
// }

// function add() {
//     var descr = $('#description').val();
//         var task = {"id": 0, "description": descr};
//         $.ajax({
//             method : "post",
//             url :  "./add",
//             data : JSON.stringify(account),
//             contentType : "application/json",
//             dataType : "text",
//             success : function (data) {
//                 alert(data);
//                 window.location.href = '../index.html';
//             }
//         });
// }