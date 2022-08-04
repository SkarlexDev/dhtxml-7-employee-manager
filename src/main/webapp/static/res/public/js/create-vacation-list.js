function create_vacation_list(data) {
	const vacation = new dhx.Grid("form_vacation_list", {
		css: "dhx_widget--bordered dhx_widget--no-border_top ",
		columns: [
			{
				id: "vacationFrom",
				header: [{
					text: "Birth Date",
					align: "left"
				}],
				type: "date",
				dateFormat: "%d-%M-%Y",
				align: "left"
			},
			{
				id: "vacationTo",
				header: [{
					text: "Birth Date",
					align: "left"
				}],
				type: "date",
				dateFormat: "%d-%M-%Y",
				align: "left",
			},
			{
				id: "reason",
				header: [{
					text: "reason"
				}]
			}
		],
		autoWidth: true,
		height: 300

	});
	vacation.data.load("vacation?id=" + data.row.id);
}