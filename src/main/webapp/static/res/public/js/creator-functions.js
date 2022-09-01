function attachDataManageRoles() {
	let dataview = buildViewRoles();
	layout.getCell("dataview").attach(dataview);
	dataview.data.load("employee");
}

function buildViewRoles() {
	const grid = new dhx.Grid(null, {
		columns: [
			{
				id: "id",
				header: [{
					text: "ID",
					align: "center",
					rowspan: 2
				}],
				align: "center",
			}, {
				id: "name",
				header: [{
					text: "Name",
					align: "center",
					rowspan: 2
				}],
			},
			{
				id: "phone",
				header: [{
					text: "Phone",
					align: "center"
				}, {
					content: "inputFilter"
				}],
			},
			{
				id: "email",
				header: [{
					text: "Email",
					align: "center"
				}, {
					content: "inputFilter"
				}],
			},
			{
				id: "roles",
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
		],
		autoWidth: true,
		selection: "row",
		eventHandlers: {
			onclick: {
				"add-addmin": function(e, data) {
					dhx.ajax.post("employee?action=add-admin&id="+data.row.id, null).then(function(data) {
						grid.data.load("employee");
					}).catch(function(err) {
						console.log(err);
					});
				},
				"remove-addmin": function(e, data) {
					dhx.ajax.post("employee?action=remove-admin&id="+data.row.id, null).then(function(data) {
						grid.data.load("employee");
					}).catch(function(err) {
						console.log(err);
					});
				},
			},
		},
	});
	return grid;
}


function accessTemplate(value) {
	if (!value) return;
	if (value.length === 3) return `<div>Creator</div>`;
	let result = "";
	value.forEach(function(role) {
		if (role.name === "User" && value.length === 1) {
			result = result.concat(`<div class="btn btn-primary d-inline-block mr-1 px-1 add-addmin">Add admin</div>`);
		}
		if (role.name === "Admin") {
			result = result.concat(`<div class="btn btn-danger d-inline-block mr-1 px-1 remove-addmin">Remove admin</div>`);
		}
	});
	return `<div>${result}</div>`;
}


/* */

function attachDataViewTotalThreeValues(result) {
	let chart = buildViewThreeValues();
	layout.getCell("dataview").attach(chart);
	chart.data.parse(result);
}

function buildViewThreeValues() {
	const config = {
		type: "pie",
		css: "dhx_widget--bg_white dhx_widget--bordered",
		series: [
			{
				value: "value",
				color: "color",
				text: "month",
				stroke: "#FFFFFF",
				strokeWidth: 2
			}
		],
		legend: {
			values: {
				id: "value",
				text: "id",
				color: "color"
			},
			halign: "right",
			valign: "top"
		}
	};
	const chart = new dhx.Chart(null, config);
	return chart;

}


