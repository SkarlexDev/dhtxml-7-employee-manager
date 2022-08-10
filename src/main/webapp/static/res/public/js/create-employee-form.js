function create_emp_form() {
	let form = new dhx.Form("form_emp", {
		css: "dhx_widget--bordered",
		align: "center",
		padding: "20px",
		rows: [
			{
				name: "name",
				type: "input",
				label: "Name",
				labelPosition: "top",
				labelWidth: 140,
				required: true,
				placeholder: "Joe Smith",
			},
			{
				name: "phone",
				type: "input",
				label: "Phone",
				labelPosition: "top",
				labelWidth: 140,
				required: true,
				placeholder: "02521897135",
				validation: "numeric",
				errorMessage: "Only digits are allowed!",
				successMessage: "Correctly",
				preMessage: "Type only Digits"
			},
			{
				name: "email",
				type: "input",
				label: "Email",
				labelPosition: "top",
				labelWidth: 140,
				required: true,
				placeholder: "joe_Smith@email.com",
				validation: "email",
				errorMessage: "Invalid email!",
				successMessage: "Correctly"
			},
			{
				name: "birthDate",
				type: "datepicker",
				label: "Birth Date",
				required: true,
				labelPosition: "top",
				labelWidth: 140,
				dateFormat: "%d-%M-%Y",
				validation: function (value) {
					return value && (new Date().getFullYear() - Number(value.substring(7))) > 18;
				},
				errorMessage: "Invalid age! Need to have at last 18"
			},
			{
				name: "address",
				type: "input",
				label: "Address",
				labelPosition: "top",
				labelWidth: 140,
				required: true,
				placeholder: "Time Square 345",
			},
			{
				name: "country",
				type: "combo",
				label: "Country",
				labelPosition: "top",
				labelWidth: 140,
				required: true,
				placeholder: "Country",
				newOptions: false,
				data: [
					{
						"id": "Austria",
						"value": "Austria",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/at.png"
					},
					{
						"id": "Belarus",
						"value": "Belarus",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/by.png"
					},
					{
						"id": "Belgium",
						"value": "Belgium",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/be.png"
					},
					{
						"id": "Bulgaria",
						"value": "Bulgaria",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/bg.png"
					},
					{
						"id": "Cyprus",
						"value": "Cyprus",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/cy.png"
					},
					{
						"id": "Czech Republic",
						"value": "Czech Republic",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/cz.png"
					},
					{
						"id": "Denmark",
						"value": "Denmark",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/dk.png"
					},
					{
						"id": "Estonia",
						"value": "Estonia",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/ee.png"
					},
					{
						"id": "Finland",
						"value": "Finland",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/fi.png"
					},
					{
						"id": "France",
						"value": "France",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/fr.png"
					},
					{
						"id": "Germany",
						"value": "Germany",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/de.png"
					},
					{
						"id": "Greece",
						"value": "Greece",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/gr.png"
					},
					{
						"id": "Hungary",
						"value": "Hungary",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/hu.png"
					},
					{
						"id": "Ireland",
						"value": "Ireland",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/ie.png"
					},
					{
						"id": "Italy",
						"value": "Italy",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/it.png"
					},
					{
						"id": "Latvia",
						"value": "Latvia",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/lv.png"
					},
					{
						"id": "Lithuania",
						"value": "Lithuania",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/lt.png"
					},
					{
						"id": "Luxembourg",
						"value": "Luxembourg",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/lu.png"
					},
					{
						"id": "Malta",
						"value": "Malta",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/mt.png"
					},
					{
						"id": "Netherlands",
						"value": "Netherlands",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/nl.png"
					},
					{
						"id": "Poland",
						"value": "Poland",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/pl.png"
					},
					{
						"id": "Portugal",
						"value": "Portugal",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/pt.png"
					},
					{
						"id": "Russia",
						"value": "Russia",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/ru.png"
					},
					{
						"id": "Romania",
						"value": "Romania",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/ro.png"
					},
					{
						"id": "Slovakia",
						"value": "Slovakia",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/sk.png"
					},
					{
						"id": "Slovenia",
						"value": "Slovenia",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/si.png"
					},
					{
						"id": "Spain",
						"value": "Spain",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/es.png"
					},
					{
						"id": "Sweden",
						"value": "Sweden",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/se.png"
					},
					{
						"id": "United Kingdom",
						"value": "United Kingdom",
						"src": "https://snippet.dhtmlx.com/codebase/data/combobox/01/img/gb.png"
					}
				]
			}
		]
	});

	form.events.on("change", (date) => {
		if (date == 'birthDate') {
			form.getItem("birthDate").validate();
		}
	});
	return form;
}

