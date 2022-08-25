const vacation = new dhx.Grid("form_vacation_list_account", {
	css: "dhx_widget--bordered dhx_widget--no-border_top ",
	columns: [{
		hidden: true,
		id: "id",
		header: [{
			text: "id",
			align: "center",
		}],
	},{
		id: "vacationFrom",
		header: [{
			text: "From",
			align: "center",
		}, {
			content: "inputFilter"
		}],
		type: "date",
		dateFormat: "%d-%M-%Y",
		align: "left"
	}, {
		id: "vacationTo",
		header: [{
			text: "To",
			align: "center",
		}, {
			content: "inputFilter"
		}],
		type: "date",
		dateFormat: "%d-%M-%Y",
		align: "left"
	}, {
		id: "reason",
		header: [{
			text: "Reason",
			align: "center",
			rowspan: 2
		}],
	}, {
		id: "status",
		header: [{
			text: "Status",
			align: "center",
		}, {
			content: "comboFilter"
		}],
	}, {
		id: "action",
		gravity: 1.5,
		header: [{
			text: "Action",
			align: "center",
			rowspan: 2
		}],
		htmlEnable: true,
		template: function () {
			return "<span class='action-buttons'><a class='btn btn-info accept'>Accept</a><a class='btn btn-warning manage'>Manage</a><a class='btn btn-danger decline'>Decline</a></span>";
		},
	}],
	autoWidth: true,
	height: 450,
	selection: "row",
	eventHandlers: {
		onclick: {
			"accept": function (e, data) {
				dhx.ajax.post("vacation?action=Accepted", data.row.id).then(function (data) {
					location.reload();
				}).catch(function (err) {
					errorMsg(err)
				});
			},
			"decline": function (e, data) {
				dhx.ajax.post("vacation?action=Declined", data.row.id).then(function (data) {
					location.reload();
				}).catch(function (err) {
					errorMsg(err);
				});
			},
			"manage": function (e, data) {
				let formData = requestVacationForm('Edit vacation', userid, 'edit');
				formData.setValue({
					"id": data.row.id,
					"vacationFrom": data.row.vacationFrom,
					"vacationTo": data.row.vacationTo,
					"reason": data.row.reason
				});

			},
		},
	},
});

function errorMsg(err) {
	bootbox.dialog({
		title: 'Failed',
		message: "<p>" + err.message + "</p>",
		size: 'lg',
		buttons: {
			cancel: {
				label: "ok",
				className: 'btn-warning'
			}
		}
	});
}

$.ajax({
	type: "GET",
	url: "vacation?id=" + userid + "&filter=Pending",
	success: function (data) {
		vacation.data.parse(data);
	}
});
