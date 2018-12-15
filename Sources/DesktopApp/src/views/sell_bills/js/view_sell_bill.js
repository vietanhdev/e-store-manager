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

        var row_id = 0;
      
        // Create table 
        dataTable = $('#realtime').DataTable({
            columns: [
                { data: "product_id", title: TextGetter.get("product_id"), visible: false  },
                { data: "product_name", title: TextGetter.get("product_name") },
                { data: "price", title: TextGetter.get("price") },
                { data: "quantities", title: TextGetter.get("quantity") },
                { data: "unit", title: TextGetter.get("unit") },
                { data: "total", title: TextGetter.get("total"),
                    render: function(data,type,row) { 
                        this.data = Number(row["price"]) * Number(row["quantities"]);
                        return (Number(row["price"]) * Number(row["quantities"]));
                    }
                }
            ],
            "order": [[ 0, "desc" ]],
            "responsive": true,
            "pageLength": 8,
            "lengthMenu": [[5, 8, 10, -1], [5, 8, 10, "All"]]
        });


        ipcRenderer.send(EventGetter.get("request_sell_view_data"));
        ipcRenderer.on(EventGetter.get("respond_request_sell_view_data"), (event, data) => {
            // console.log(data);
            $("#created_at").val(data.createdAt);
            $("#created_by").val(data.user_name);
            let customer = data.customer_name;

            if (customer == null || typeof customer == "undefined") {
                customer = TextGetter.get("new_customer");
            }
            $("#customer").val(customer);
            $("#grandtotal").val(data.total);

            if (data.active == true) {
                $("#status-completed").show();
            } else {
                $("#status-discarded").show();
            }
            dataTable.rows.add(data.sell_items).draw();
        });

        // Handle close command
        $("#btn-close").click(() => {
            require('electron').remote.getCurrentWindow().close();
        });

    }
};

$(document).ready(() =>  {
    app.start();
});