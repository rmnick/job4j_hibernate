console.log("hey");
$(document).ready(function() {
    console.log("in task");
    $.ajax({
        type: "GET",
        url: "./task",
        complete: function (data) {
            $each(data, function (value) {
               console.log(data);
            });
            console.log(data);
            var tasks = JSON.parse(data.responseText);
            for (var i = 0; i < tasks.length; i++) {
                var id = tasks[i].id;
                var descr = tasks[i].description;
                var isdone = tasks[i].isDone;
                $('#table tr:last').after('<tr><td>' + id + '</td><td>' + desc + '</td></tr>' + '</td><td>' + (isdone ? 'done' : 'process') + '</td></tr>');
                console.log(tasks[i]);
            }
        }
    })
});

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