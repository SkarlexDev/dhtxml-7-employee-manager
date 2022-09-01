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
		logout();
	}
});

const grid = new dhx.Grid(null, {
	columns: [{
		id: "name",
		gravity: 2,
		header: [{
			text: "Name",
			align: "center",
			rowspan: 2
		}],
	},
	{
		id: "phone",
		gravity: 1.5,
		header: [{
			text: "Phone",
			align: "center"
		}, {
			content: "inputFilter"
		}],
	},
	{
		id: "email",
		gravity: 1.5,
		header: [{
			text: "Email",
			align: "center"
		}, {
			content: "inputFilter"
		}],
	},
	{
		id: "birthDate",
		gravity: 2,
		header: [{
			text: "Birth Date",
			align: "center",
			rowspan: 2
		}],
		type: "date",
		dateFormat: "%d-%M-%Y",
		align: "left",
	},
	{
		id: "address",
		gravity: 2,
		header: [{
			text: "Address",
			align: "center",
			rowspan: 2
		}]
	},
	{
		id: "country",
		header: [{
			text: "Country",
			align: "center",
			rowspan: 2
		}],
	},
	{
		id: "roles",
		gravity: 1.7,
		header: [{
			text: "Roles",
			align: "center",
			rowspan: 2
		}],
		template: accessTemplate,
		htmlEnable: true,
		editable: false,
		align: "center"
	},
	{
		id: "action",
		gravity: 3.5,
		header: [{
			text: "Actions",
			align: "center",
			rowspan: 2
		}],
		htmlEnable: true,
		template: template_buttons,
		align: "center"
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

function accessTemplate(value) {
	if (!value) return;
	let result = "";
	value.forEach(function (role) {
		result = result.concat(`<div class="d-inline-block mr-1 px-1 border border-primary">${role.name}</div>`);

	});
	return `<div>${result}</div>`;
}

function template_buttons(value, row, col) {
	let template = "<div class='btn btn-success view-button'>View</div>"
		+ "<div class='btn btn-primary add-button'>Add</div>"
		+ "<div class='btn btn-info list-button'>List</div>";

	if (row.roles.filter(x => x.name == "Creator").length == 1) {
		template = template.concat("<div class='btn btn-dark disabled'>Edit</div><div class='btn btn-dark disabled'>Delete</div>");
	} else {
		if (row.roles.filter(x => x.name == "Admin").length == 1) {
			template = template.concat("<div class='btn btn-warning edit-button'>Edit</div><div class='btn btn-dark disabled'>Delete</div>");
		} else {
			template = template.concat("<div class='btn btn-warning edit-button'>Edit</div><div class='btn btn-danger remove-button'>Delete</div>");
		}
	}
	return `<span class='action-buttons'>${template}</span>`;
}

grid.data.load("employee");

layout.getCell("toolbar").attach(toolbar);
layout.getCell("grid").attach(grid);
