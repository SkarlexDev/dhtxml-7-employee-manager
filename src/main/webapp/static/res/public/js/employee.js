function add_emp_bootbox() {
	dialog = bootbox.dialog({
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
						dhx.ajax.post("employee", form.getValue()).then(function () {
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
	create_emp_form();
}

function add_vacation_bootbox(data) {
	dialog = bootbox.dialog({
		message: '<div id="form_vacation"></div>',
		closeButton: false,
		title: 'Add Vacation for: ' + data.row.name,
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
					var fullForm = form.getValue();
					fullForm.employeeId = data.row.id;
					dhx.ajax.post("vacation", fullForm).then(function () {
						dialog.modal('hide');
					});
					return false;
				}
			}
		}
	});
	create_vacation_form();
}
function add_vacation_list_bootbox(data) {
	dialog = bootbox.dialog({
		message: '<div id="form_vacation_list"></div>',
		closeButton: false,
		title: 'Vacations for: ' + data.row.name,
		buttons: {
			cancel: {
				label: 'Close',
				className: 'btn-secondary'
			}
		}
	});
	create_vacation_list(data);
}

function add_delete_emp_bootbox(data) {
	confirm = bootbox.confirm({
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
				dhx.ajax.post("delEmployee", data.row.id).then(function () {
					grid.data.load("employee");
					confirm.modal('hide');
				});
				return false;
			}
		}
	});
}
