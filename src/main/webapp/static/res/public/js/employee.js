function add_emp_bootbox() {
	let dialog = bootbox.dialog({
		message: '<div id="form_emp"></div>',
		closeButton: false,
		title: 'New Employee',
		buttons: {
			cancel: {
				label: 'Cancel',
				className: 'btn-secondary',
				callback: function () {
					form.clear();
				}
			},
			submit: {
				label: 'Submit',
				className: 'btn-primary',
				callback: function () {
					if (form.validate()) {
						dhx.ajax.post("employee?action=add", form.getValue()).then(function () {
							grid.data.load("employee");
							dialog.modal('hide');
						}).catch(function (err) {
							form.getItem("email").validate(false, "");
						});
					}
					return false;
				}
			}
		}
	});
	let form = create_emp_form();
}

function add_vacation_bootbox(data) {
	requestVacationForm(data.row.name, data.row.id, 'add');
}
function add_vacation_list_bootbox(data) {
	viewVacationList(data.row.name);
	create_vacation_list(data.row.id);
}

function edit_employee_bootbox(data) {
	let dialog = bootbox.dialog({
		message: '<div id="form_emp"></div>',
		closeButton: false,
		title: 'Edit ' + data.row.name,
		buttons: {
			cancel: {
				label: 'Cancel',
				className: 'btn-secondary',
				callback: function () {
					form.clear();
				}
			},
			submit: {
				label: 'Submit',
				className: 'btn-primary',
				callback: function () {
					if (form.validate()) {
						var fullForm = form.getValue();
						fullForm.id = data.row.id;
						dhx.ajax.post("employee?action=edit&id=" + data.row.id, fullForm).then(function () {
							grid.data.load("employee");
							dialog.modal('hide');
						}).catch(function (err) {
							form.getItem("email").validate(false, "");
						});
					}
					return false;
				}
			}
		}
	});
	let form = create_emp_form();
	form.setValue({
		"name": data.row.name,
		"phone": data.row.phone,
		"email": data.row.email,
		"birthDate": data.row.birthDate,
		"address": data.row.address,
		"country": data.row.country
	});
}

function add_delete_emp_bootbox(data) {
	let confirm = bootbox.confirm({
		size: "small",
		closeButton: false,
		title: "Are you sure? ",
		message: "Delete " + data.row.name + " ?",
		buttons: {
			confirm: {
				label: 'Yes',
				className: 'btn-danger'
			},
			cancel: {
				label: 'Cancel',
				className: 'btn-secondary'
			}
		},
		callback: function (result) {
			if (result) {
				dhx.ajax.post("employee?action=delete&id=" + data.row.id).then(function () {
					grid.data.load("employee");
					confirm.modal('hide');
				});
				return false;
			}
		}
	});
}
