$(document).ready(function () {
    var password = $('#newPassword').val();
    var confirmPassword = $('#confirmPassword').val();
    if (password === "" || confirmPassword === "") {
        $('#updateUserInfoButton').prop('disabled', true);
    }

    $('#confirmPassword').keyup(checkPasswordMatch);
    $('#newPassword').keyup(checkPasswordMatch);

    /*$('.collapse').on('shown.bs.collapse', function () {
        $(this).prev().addClass('active');
    });

    $('.collapse').on('hidden.bs.collapse', function () {
        $(this).prev().removeClass('active');
    });*/

});

function checkPasswordMatch() {
    var password = $('#newPassword').val();
    var confirmPassword = $('#confirmPassword').val();
    if (password === "" || confirmPassword === "") {
        $('#updateUserInfoButton').prop('disabled', true);
    }

    if (password === "" && confirmPassword === "") {
        $('#checkPasswordMatch').html("").removeClass("fa fa-check fa-close");
        $('#updateUserInfoButton').prop('disabled', true);
    } else {
        if (password !== confirmPassword) {
            $('#checkPasswordMatch').html("Password do not match").css('color', 'red')
                .removeClass("fa fa-check").addClass("fa fa-close");
            $('#updateUserInfoButton').prop('disabled', true);
        } else {
            $('#checkPasswordMatch').html("Password match").css('color', 'green')
                .removeClass("fa fa-close").addClass("fa fa-check");
            $('#updateUserInfoButton').prop('disabled', false);
        }
    }

}