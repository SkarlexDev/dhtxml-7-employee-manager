function create_vacation_form() {
    form = new dhx.Form("form_vacation", {
        css: "dhx_widget--bordered",
        align: "center",
        padding: "20px",
        rows: [
            {
                name: "vacationFrom",
                type: "datepicker",
                label: "From",
                required: true,
                labelPosition: "top",
                labelWidth: 140,
                dateFormat: "%d-%M-%Y",
                validation: function (value) {
                    fromvalue = value;
                    var state = value && new Date(fromvalue) > new Date();
                    if (state) {
                        form.getItem('vacationTo').enable();
                    }
                    return state;
                },
                errorMessage: "Date is from the past!"
            },
            {
                name: "vacationTo",
                type: "datepicker",
                label: "To",
                required: true,
                labelPosition: "top",
                labelWidth: 140,
                dateFormat: "%d-%M-%Y",
                disabled: true,
                validation: function (value) {
                    return value && new Date(value) > new Date(fromvalue);
                },
                errorMessage: "Invalid end Date!"
            },
            {
                name: "reason",
                type: "combo",
                label: "Reason",
                labelPosition: "top",
                labelWidth: 140,
                required: true,
                placeholder: "Reason",
                newOptions: false,
                data: [
                    {
                        "id": "Holiday",
                        "value": "Holiday"
                    },
                    {
                        "id": "Sick",
                        "value": "Sick"
                    },
                    {
                        "id": "Trip",
                        "value": "Trip"
                    }
                ]
            }
        ]
    });
    form.events.on("change", (date) => {
        if (date != 'reason') {
            form.getItem(date).validate();
        }
    });
}