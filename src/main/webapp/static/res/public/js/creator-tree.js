const dataset =
	[{
		"value": "View",
		"open": true,
		"opened": true,
		"items":
			[{
				"value": "Employee",
				"open": true,
				"opened": true,
				"items": [{
					"value": "View Roles",
					"id": "employee-info"
				},
				{
					"value": "View Vacations",
					"id": "vacations-info"
				},
				{
					"value": "View Accounts",
					"id": "accounts-info"
				}]
			}]
	},
	{
		"value": "Manage",
		"open": true,
		"opened": true,
		"items":
			[{
				"value": "Employee",
				"open": true,
				"opened": true,
				"items": [{
					"value": "Manage Roles",
					"id": "employee-manage"
				}]
			}]
	}];





;

const tree = new dhx.Tree("tree");
tree.data.parse(dataset);


tree.events.on("itemClick", function(id, e) {
	if(id === "employee-manage"){
		attachDataManageRoles();
		return;			
	}
	$.ajax({
		type: "GET",
		url: "data?request=" + id,
		success: function(result) {
			if (id === "employee-info" 
			|| id === "vacations-info"
			|| id === "accounts-info") {
				attachDataViewTotalThreeValues(result);
			}
		},
		error: function(err) {

		}
	});
});


