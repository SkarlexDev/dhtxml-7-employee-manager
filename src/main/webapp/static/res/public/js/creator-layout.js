const layout = new dhx.Layout("layout", {
	type: "line",
	rows: [{
		id: "toolbar",
		height: "content",
	}, {
		type: "line",
		cols: [{
			id: "list",
			header: "List",
			width: "20%"
		}, {
			id: "dataview",
			header: "dataview",
			width: "80%"
		}]
	},]
});

toolbar = new dhx.Toolbar();
toolbar.data.parse(toolbarData);


toolbar.events.on("click", function (id) {
	if (id === "back") {
		location.href = "admin";
	}
	if (id === "account") {
		location.href = "account";
	}
	if (id === "logout") {
		location.href = "logout";
	}
});


layout.getCell("toolbar").attach(toolbar);
layout.getCell("list").attach(tree);