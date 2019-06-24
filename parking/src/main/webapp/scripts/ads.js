// Add the name of the file appear on select
$(".custom-file-input").on("change", function() {
    console.log("in script");
    var fileName = $(this).val().split("\\").pop();
    $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});
