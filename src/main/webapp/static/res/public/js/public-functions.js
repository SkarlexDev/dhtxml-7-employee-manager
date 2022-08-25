function logout() {
	$.ajax({
		type: "GET",
		url: "logout",
		success: () => {
			location.href = "login";
		}
	});
}
function viewVacationList(title) {
	bootbox.dialog({
		message: '<div id="form_vacation_list"></div>',
		closeButton: false,
		title: 'Vacations for: ' + title,
		buttons: {
			cancel: {
				label: 'Close',
				className: 'btn-secondary'
			}
		}
	});
}

function requestVacationForm(title, id, action) {
	let dialog = bootbox.dialog({
		message: '<div id="form_vacation"></div>',
		closeButton: false,
		title: title,
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
						fullForm.employeeId = id;
						dhx.ajax.post("vacation?action=" + action, fullForm).then(function () {
							dialog.modal('hide');
							location.reload();
						}).catch(function (err) {
							bootbox.dialog({
								title: 'Failed',
								message: "<p>" + err.message + "</p>",
								size: 'sm',
								buttons: {
									cancel: {
										label: "ok",
										className: 'btn-warning'
									}
								}
							});
						});

					}
					return false;
				}
			}
		}
	});
	let form = new dhx.Form("form_vacation", {
		css: "dhx_widget--bordered",
		align: "center",
		padding: "20px",
		rows: [
			{
				name: "id",
				type: "input",
				label: "ID",
				required: false,
				labelPosition: "top",
				labelWidth: 140,
				disabled: true,
				hidden: false,
				value : 0,
			},
			{
				name: "vacationFrom",
				type: "datepicker",
				label: "From",
				required: true,
				labelPosition: "top",
				labelWidth: 140,
				dateFormat: "%d-%M-%Y",
				validation: function (value) {
					fromvalue = value;
					var state = value && new Date(fromvalue) > new Date();
					if (state) {
						form.getItem('vacationTo').enable();
					}
					return state;
				},
				errorMessage: "Date is from the past!"
			},
			{
				name: "vacationTo",
				type: "datepicker",
				label: "To",
				required: true,
				labelPosition: "top",
				labelWidth: 140,
				dateFormat: "%d-%M-%Y",
				disabled: true,
				validation: function (value) {
					return value && new Date(value) > new Date(fromvalue);
				},
				errorMessage: "Invalid end Date!"
			},
			{
				name: "reason",
				type: "combo",
				label: "Reason",
				labelPosition: "top",
				labelWidth: 140,
				required: true,
				placeholder: "Reason",
				newOptions: false,
				data: [
					{
						"id": "Holiday",
						"value": "Holiday"
					},
					{
						"id": "Sick",
						"value": "Sick"
					},
					{
						"id": "Trip",
						"value": "Trip"
					}

				]
			}
		]
	});
	form.events.on("change", (date) => {
		if (date != 'reason') {
			form.getItem(date).validate();
		}
	});
	return form;
}

function create_vacation_list(id) {
	const vacation = new dhx.Grid("form_vacation_list", {
		css: "dhx_widget--bordered dhx_widget--no-border_top ",
		columns: [
			{
				id: "vacationFrom",
				header: [{
					text: "From",
					align: "center"
				}, {
					content: "inputFilter"
				}],
				type: "date",
				dateFormat: "%d-%M-%Y",
				align: "left"
			},
			{
				id: "vacationTo",
				header: [{
					text: "To",
					align: "center",
				}, {
					content: "inputFilter"
				}],
				type: "date",
				dateFormat: "%d-%M-%Y",
				align: "left",
			},
			{
				id: "reason",
				header: [{
					text: "Reason",
					align: "center",
					rowspan: 2
				}]
			},
			{
				id: "status",
				header: [{
					text: "Status",
					align: "center"
				}, {
					content: "comboFilter"
				}]
			}
		],
		autoWidth: true,
		height: 300,
		selection: "row"

	});
	test = vacation.data.load("vacation?id=" + id);
}