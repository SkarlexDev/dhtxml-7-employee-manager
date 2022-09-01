$('#password, #confirm_password').on('keyup', function () {
	if ($('#password').val() == $('#confirm_password').val()) {
		$('#message').html('Password Matching').css('color', 'green');
	} else
		$('#message').html('Password Not Matching').css('color', 'red');
});

const form = document.querySelector('#loginForm');
form.addEventListener('submit', (e) => {
	e.preventDefault();
	var formValue = {
		alias: $("#alias").val(),
		password: $("#password").val()
	}
	if (validatePasswords()) {
		$.ajax({
			type: "POST",
			url: "activate?" + window.location.search.substring(1),
			data: JSON.stringify(formValue),
			success: function (data) {
				location.href = "login";
			},
			error: function (err) {
				testing = err;
				bootbox.dialog({
					title: 'Failed',
					message: "<p>" + err.responseText + "</p>",
					closeButton: false,
					buttons: {
						cancel: {
							label: "ok",
							className: 'btn-warning'
						}
					}
				});
			}
		});
	} else {
		alert("Your passwords do not match.");
	}
})


function validatePasswords() {
	var password = document.getElementById("password").value;
	var password_confirm = document.getElementById("confirm_password").value;
	if (password != password_confirm) {
		return false;
	}
	return true;
}