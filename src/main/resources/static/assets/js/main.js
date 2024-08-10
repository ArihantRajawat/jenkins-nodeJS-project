// Check current user login or logout
function loginUser() {
    $.ajax({
        method: 'POST',
        url: '/verify-token',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
        },
        success: function (response) {
            if (response.status === 200) {
                var user = response.data;
                $('#uname').text('Hello ' + user.firstname);
                $('#account-btn').removeClass('d-none');
                $('#login-btn').addClass('d-none');
            } else if (response.status === 400) {
                showToast(response.message, 'bg-danger');
                window.location.href = '/home';
            }
            return true;
        },
        error: function () {
            $('#account-btn').addClass('d-none');
            $('#login-btn').removeClass('d-none');
            return false;
        }
    });
}