const app = {
    rows: [],
    selectedUser: null,
    start() {

        // Load libraries
        const EventGetter = require("../../../services/EventGetter").EventGetter;
        const TextGetter = require("../../../services/TextGetter").TextGetter;
        const {ipcRenderer} = require('electron');
        var dt = require( 'datatables.net-dt' )( window, $ );
        require( 'datatables.net-responsive-dt' )( window, $ );

        const SellController = require("../../../controllers/SellController.js").SellController;
        let sellController = new SellController();
        const settings = require('electron-settings');
        let headers = Object({'Content-Type': 'application/json'});
        headers['Authorization'] = "Bearer " + settings.get('account_info.token');
      
        // Create table 
        dataTable = $('#realtime').DataTable({
            "serverSide": true,
            "ajax": {
                "url": sellController.getAjaxAPIUrl(),
                "type": 'POST',
                "headers": headers,
                "processData": true,
                "contentType": 'application/json',
                "data": function ( d ) {

                    // Add the Date Filter info
                    if (isUsingDatePicker()) {
                        d.search.start = getStartDate();
                        d.search.end = getEndDate();
                    }

                    return JSON.stringify(d);
                }
            },
            columns: [
                { data: "createdAt", title: TextGetter.get("date") },
                { data: "user_name", title: TextGetter.get("employee") },
                { data: "customer_name", title: TextGetter.get("customer"),
                    render: function ( data, type, row ) {
                        if (data == null || typeof data == "undefined") {
                            return TextGetter.get("new_customer");
                        } else {
                            return data;
                        }
                    }
                },
                // { data: "user_id", title: TextGetter.get("employee_id") },
                { data: "active", title: TextGetter.get("status"),
                    render: function ( data, type, row ) {
                        if (data == true) {
                            return `<span class="badge badge-success">`+TextGetter.get("completed")+`</span>`;
                        } else {
                            return `<span class="badge badge-warning">`+TextGetter.get("discarded")+`</span>`;
                        }
                    }
                },
                { data: "sell_items", title: TextGetter.get("number_of_products"),
                    render: function ( data, type, row ) {
                        return data.length;
                    }
                },
                { data: "total", title: TextGetter.get("total")
                },
                { title: TextGetter.get("manipulation") }
            ],
            "order": [[ 0, "asc" ]],
            "columnDefs": [ {
                "targets": -1,
                "data": null,
                "defaultContent": `
                    <button type="button" class="view btn btn-sm btn-primary">
                        `+TextGetter.get("view")+`
                    </button>
                    <button type="button" class="discard btn btn-sm btn-danger">
                        <i class="fa fa-trash-o" aria-hidden="true"></i>
                        `+TextGetter.get("discard")+`
                    </button>
                `
            },
            {"targets": 0, "type":"date-euro"}
            ],
            "responsive": true,
            "pageLength": 8,
            "lengthMenu": [[5, 8, 10, -1], [5, 8, 10, "All"]]
        });

        // ==== Handle events that cause table to update ====
        ipcRenderer.on(EventGetter.get("discard_sell_bill_success"), (event, data) => {
            dataTable.ajax.reload();
        });
        // Reload when clicking Update in Date Filter
        $("#btn-date-filter-update").click(() => {
            dataTable.ajax.reload();
        });
        // Reload when changing the usage of date filter
        $("#use-date-filter").change(() => {
            dataTable.ajax.reload();
        });

        // ==== Handle row buttons ====
        // Discard
        $('#realtime tbody').on( 'click', 'button.discard', function () {
            let data = dataTable.row( $(this).parents('tr') ).data();
            ipcRenderer.send(EventGetter.get("request_discard_sell_bill"), data);
        } );
        // View
        $('#realtime tbody').on( 'click', 'button.view', function () {
            let data = dataTable.row( $(this).parents('tr') ).data();
            ipcRenderer.send(EventGetter.get("request_view_sell_bill"), data);
        } );


    }
};

$(document).ready(() =>  {
    app.start();
});