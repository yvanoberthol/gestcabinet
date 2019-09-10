$(document).ready(function () {
    var password = $('#newPassword');
    var confirmPassword = $('#confirmPassword');

    if (password.value == "" || confirmPassword.value == "") {
        $('#checkPasswordMatch').html("").removeClass("fa fa-check fa-close");
        $('#updateUserInfoButton').prop('disabled', true);
    }

    password.keyup(checkPasswordMatch);
    confirmPassword.keyup(checkPasswordMatch);
});

function checkPasswordMatch() {
    var password = $('#newPassword').value;
    var confirmPassword = $('#confirmPassword').value;

    if (password === "" || confirmPassword === "") {
        $('#checkPasswordMatch').html("").removeClass("fa fa-check fa-close");
        $('#updateUserInfoButton').prop('disabled', true);
    } else {
        var x = getCookie("lang");
        if (x == "fr"){
            if (password !== confirmPassword) {
                $('#checkPasswordMatch').html(" mots de passe incohérents").css('color', '#ccae45')
                    .removeClass("fa fa-check").addClass("fa fa-close");
                $('#updateUserInfoButton').prop('disabled', true);
            } else {
                $('#checkPasswordMatch').html(" mots de passe corrects").css('color', '#92A18A')
                    .removeClass("fa fa-close").addClass("fa fa-check");
                $('#updateUserInfoButton').prop('disabled', false);
            }
        }else if (x == "en"){
            if (password !== confirmPassword) {
                $('#checkPasswordMatch').html(" Password do not match").css('color', '#ccae45')
                    .removeClass("fa fa-check").addClass("fa fa-close");
                $('#updateUserInfoButton').prop('disabled', true);
            } else {
                $('#checkPasswordMatch').html(" Password match").css('color', '#92A18A')
                    .removeClass("fa fa-close").addClass("fa fa-check");
                $('#updateUserInfoButton').prop('disabled', false);
            }
        }

    }

}