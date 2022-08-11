$("#backButton").click(function(e) {
	location.href = "admin";
});
$("#add").click(function(e) {
	let dialog = bootbox.dialog({
		message: '<div id="form_vacation"></div>',
		closeButton: false,
		title: 'Add Vacation for: ' + userName,
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
						fullForm.employeeId = userid;
						dhx.ajax.post("vacation", fullForm).then(function () {
							dialog.modal('hide');
							location.reload();
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
});
const vacation = new dhx.Grid("form_vacation_list", {
	css: "dhx_widget--bordered dhx_widget--no-border_top ",
	columns: [{
		id: "vacationFrom",
		header: [{
			text: "From",
			align: "left"
		}],
		type: "date",
		dateFormat: "%d-%M-%Y",
		align: "left"
	}, {
		id: "vacationTo",
		header: [{
			text: "To",
			align: "left"
		}],
		type: "date",
		dateFormat: "%d-%M-%Y",
		align: "left",
	}, {
		id: "reason",
		header: [{
			text: "Reason"
		}]
	}],
	autoWidth: true,
	height: 450,
	selection: "row"

});
$.ajax({
	type: "GET",
	url: "vacation?id=" + userid,
	success: function(data) {
		document.querySelector('span').innerText = data.length;
		vacation.data.parse(data);
	},
	error: function(data) {
		alert("Invalid data");
	}
});