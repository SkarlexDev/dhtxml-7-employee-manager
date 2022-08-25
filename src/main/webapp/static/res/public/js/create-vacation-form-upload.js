function add_vacation_bootbox_upload() {
	let dialog = bootbox.dialog({
		message: '<form id="upload" enctype="multipart/form-data"><input type="file" name="file" /></form>',
		closeButton: false,
		title: 'Upload vacations',
		buttons: {
			cancel: {
				label: 'Cancel',
				className: 'btn-secondary',
				callback: function () {

				}
			},
			submit: {
				label: 'Submit',
				className: 'btn-primary',
				callback: function () {
					var form = $('#upload')[0];
					var data = new FormData(form);
					$.ajax({
						type: "POST",
						url: "csv",
						data: data,
						enctype: 'multipart/form-data',
						processData: false,
						contentType: false,
						cache: false,
						success: function (data) {
							dialog.modal('hide');
							bootbox.alert("Uploaded!");
						},
						error: function (data) {
							bootbox.dialog({
								title: 'Failed to upload',
								message: "<p>" + data.responseText + "</p>",
								size: 'large',
								buttons: {
									cancel: {
										label: "ok",
										className: 'btn-warning'
									}
								}
							});
						}
					});
					return false;
				}
			}
		}
	});
}