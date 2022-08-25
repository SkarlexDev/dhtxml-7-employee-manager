$("#admin").click(function (e) {
	location.href = "admin";
});

$("#logout").click(function (e) {
	logout();
})

$("#backButton").click(function (e) {
	location.href = "admin";
});

$("#view").click(function (e) {
	viewVacationList("");
	create_vacation_list(userid);
});

$("#add").click(function (e) {
	requestVacationForm('Request vacation', userid, 'add');
});
