layout = new dhx.Layout("layout", {
	cols: [{
		rows: [{
			id: "toolbar",
			height: "content",
		},
		{
			type: "space",
			rows: [{
				id: "grid",
			},],
		},
		],
	},],
});

toolbar = new dhx.Toolbar();
toolbar.data.parse(toolbarData);

toolbar.events.on("click", function (id) {
	if (id === "add") {
		add_emp_bootbox();
	}
	if (id === "download") {
		window.location = "csv";
	}
	if (id === "upload") {
		add_vacation_bootbox_upload();
	}
	if (id === "account") {
		location.href = "account";
	}
	if (id === "logout") {
		$.ajax({
			type: "GET",
			url: "logout",
			success: () => {
				location.href = "login";
			}
		});
	}
});

const grid = new dhx.Grid(null, {
	columns: [{
		id: "name",
		gravity: 3,
		header: [{
			text: "Name"
		}]
	},
	{
		id: "phone",
		gravity: 2,
		header: [{
			text: "Phone"
		}]
	},
	{
		id: "email",
		header: [{
			text: "Email"
		}]
	},
	{
		id: "birthDate",
		header: [{
			text: "Birth Date",
			align: "left"
		}],
		type: "date",
		dateFormat: "%d-%M-%Y",
		align: "left",
	},
	{
		id: "address",
		header: [{
			text: "Address"
		}]
	},
	{
		id: "country",
		header: [{
			text: "Country"
		}]
	},
	{
		id: "action",
		gravity: 2.5,
		header: [{
			text: "Actions",
			align: "center"
		}],
		htmlEnable: true,
		align: "center",
		template: function () {
			return "<span class='action-buttons'><a class='btn btn-success view-button'>View</a><a class='btn btn-primary add-button'>Add</a><a class='btn btn-secondary list-button'>List</a><a class='btn btn-info edit-button'>Edit</a><a class='btn btn-danger remove-button'>Delete</a></span>";
		},
	},
	],
	autoWidth: true,
	selection: "row",
	eventHandlers: {
		onclick: {
			"view-button": function (e, data) {
				location.href = "account?id=" + data.row.id;
			},
			"add-button": function (e, data) {
				add_vacation_bootbox(data);
			},
			"list-button": function (e, data) {
				add_vacation_list_bootbox(data);
			},
			"remove-button": function (e, data) {
				add_delete_emp_bootbox(data);
			},
			"edit-button": function (e, data) {
				edit_employee_bootbox(data);
			},
		},
	},
});

grid.data.load("employee");

layout.getCell("toolbar").attach(toolbar);
layout.getCell("grid").attach(grid);
