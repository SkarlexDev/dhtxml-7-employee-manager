const form = document.querySelector('#loginForm');
form.addEventListener('submit', (e) => {
    e.preventDefault();
   var formValue = {
		email: $("#email").val(),
		password: $("#password").val()
	}
	$.ajax({
		type: "POST",
		url: "login",
		data: JSON.stringify(formValue),
		success: function (data) {
			location.href = "account";
		},
		error: function (data) {
			alert("Invalid data");
		}
	});

})
