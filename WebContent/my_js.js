// Validating Empty Field
/*function check_empty() {
if (document.getElementById('name').value == "" || document.getElementById('email').value == "" || document.getElementById('msg').value == "") {
alert("Fill All Fields !");
} else {
document.getElementById('form').submit();
alert("Form Submitted Successfully...");
}
}*/
//Function To Display Popup
function add_show() {
document.getElementById('add-form').style.display = "block";
}
//Function to Hide Popup
function add_hide(){
document.getElementById('add-form').style.display = "none";
}

$(document).on('click', '.notification > button.delete', function() {
    $(this).parent().addClass('is-hidden');
    return false;
});

