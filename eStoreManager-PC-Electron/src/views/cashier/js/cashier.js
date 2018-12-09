$(document).ready(() => {

    // Import controllers
    const ProductController = require("../../../controllers/ProductController.js").ProductController;
    let productController = new ProductController();
    const UserController = require("../../../controllers/UserController.js").UserController;
    let userController = new UserController();
    const CustomerController = require("../../../controllers/CustomerController.js").CustomerController;
    let customerController = new CustomerController();
    const SellController = require("../../../controllers/SellController.js").SellController;
    let sellController = new SellController();

    // Load library
    const prompt = require('electron-prompt');
    const settings = require('electron-settings');
    const EventGetter = require("../../../services/EventGetter").EventGetter;
    const TextGetter = require("../../../services/TextGetter").TextGetter;
    const {ipcRenderer} = require('electron');
    var dt = require( 'datatables.net-dt' )( window, $ );
    require( 'datatables.net-responsive-dt' )( window, $ );

	// Register sum() api for datatable
    jQuery.fn.dataTable.Api.register( 'sum()', function ( ) {
        return this.flatten().reduce( function ( a, b ) {

            if ( typeof a === 'string' ) {
                a = a.replace(/[^\d.-]/g, '') * 1;
            }
            if ( typeof b === 'string' ) {
                b = b.replace(/[^\d.-]/g, '') * 1;
            }

            return a + b;
        }, 0 );
    } );


    // TODO: Change this
    var tax = 0.08; // 8 percent
    $("#tax").val("" + (tax * 100) + " %");

    // === Calculate charge for customer automatically ===
    function updateCustomerCharge() {
        let grandTotal = $("#grandtotal").val();
        let customerGive  = $("#customer-give").val();
        let customerTakeBack = 0;

        if (Number(customerGive) < Number(grandTotal)) {
            customerTakeBack = TextGetter.get("insufficient_payment");
        } else {
            customerTakeBack = Number(customerGive) - Number(grandTotal);
        }

        $("#customer-take-back").val(customerTakeBack);
    }
    $("#customer-give").on("keyup", () => {
        updateCustomerCharge();
    });
    
    // === Create product table ===
    var PID_COLUMN = 1;
    dataTable = $('#product-table').DataTable({
        searching: false,
        columns: [
            {
                title: TextGetter.get("id"),
                data: "row_id",
                "visible": false,
            },
            { 
                title: "PID",
                data: "id"
            },
            { 
                title: TextGetter.get("product_name"),
                data: "name"
            },
            { 
                title: TextGetter.get("price"),
                data: "price"
            },
            { 
                title: TextGetter.get("quantity"),
                data: "quantities"
            },
            { 
                title: TextGetter.get("quantity_manipulation"),
                data: null
            },
            { 
                title: TextGetter.get("unit"),
                data: "unit"
            },
            { 
                title: TextGetter.get("total"),
                data: "total"
            },
            { 
                title: TextGetter.get("delete_question")
            }
        ],
        "columnDefs": [ {
            "targets": -1,
            "data": null,
            "defaultContent": `
                <button type="button" class="delete-product btn btn-sm btn-danger">
                    <i class="fa fa-trash-o" onclick="$(this).parent().parent().parent().remove();" aria-hidden="true"></i>
                </button>
                `
        },
        {
            "targets": -2,
            "data": null,
            render: function(data,type,row) { 
                this.data = Number(row["price"]) * Number(row["quantities"]);
                return (Number(row["price"]) * Number(row["quantities"]));
            }
        },
        {
            "targets": 5,
            "data": null,
            "defaultContent": `
                <button type="button" class="edit-quantity btn btn-sm btn-default">
                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                </button>
                <button type="button" class="increase-quantity btn btn-sm btn-success">
                    <i class="fa fa-plus" aria-hidden="true"></i>
                </button>
                <button type="button" class="decrease-quantity btn btn-sm btn-warning">
                    <i class="fa fa-minus" aria-hidden="true"></i>
                </button>
                `
        }
        ],
        "order": [[ 0, "desc" ]],
        "responsive": true,
        "pageLength": 5,
        "lengthMenu": [[5, 8, 10, -1], [5, 8, 10, "All"]],
        "drawCallback": function () {
            let subtotal = Number(this.api().cells( null, 7 ).render('display').sum()).toFixed(2);
            let grandtotal = Number(subtotal * (1+tax)).toFixed(2);
            $("#subtotal").val(subtotal);
            $("#grandtotal").val(grandtotal);
            updateCustomerCharge();
        }
    });


    // === Handle row buttons ===

    // Delete product
    $('#product-table').on("click", ".delete-product", function(){
        dataTable.row($(this).parents('tr')).remove().draw(false);
    });
    // Edit quantity
    $('#product-table').on("click", ".edit-quantity", function(){
        let product = dataTable.row($(this).parents('tr')).data();

        // Ask user the new value of quantity
        prompt({
            title: TextGetter.get("input_quantity_for") + product.name,
            label: null,
            value: product.quantities,
            inputAttrs: {
                type: 'text'
            },
            type: 'input'
        })
        .then((r) => {
            if (r != null) {
                product.quantities = Number(r);

                // Redraw the modified row
                dataTable
                .row( $(this).parents('tr') )
                .data( product )
                .draw();
            }
        })
        .catch(console.error);
        
    });
    // Increase quantity
    $('#product-table').on("click", ".increase-quantity", function(){
        let product = dataTable.row($(this).parents('tr')).data();
        product.quantities = Number(product.quantities) + 1;
        // Redraw the modified row
        dataTable
        .row( $(this).parents('tr') )
        .data( product )
        .draw();
    });
    // Decrease quantity
    $('#product-table').on("click", ".decrease-quantity", function(){
        let product = dataTable.row($(this).parents('tr')).data();
        product.quantities = Number(product.quantities) - 1;

        if (product.quantities > 0) {
            // Redraw the modified row
            dataTable
            .row( $(this).parents('tr') )
            .data( product )
            .draw();
        } else { // Remove product if quantity <= 0
            dataTable
            .row( $(this).parents('tr') )
            .remove()
            .draw(false);
        }
        
    });
    
    // === Function to add product to table ===
    var productCount = 0;
    function addProduct(productIdOrBarcode) {

        // Get product information
        let productData = productController.getProductData(productIdOrBarcode,
            (response) => { // Success

                // === Check if product already in the table ===
                // Find indexes of rows which have `Yes` in the second column
                let indexes = dataTable.rows().eq( 0 ).filter( function (rowIdx) {
                    return dataTable.cell( rowIdx, PID_COLUMN ).data() == response.id ? true : false;
                } );

                // If there is no similar product in the table, add product to table
                if (indexes.length == 0) {
                    response.row_id = ++productCount;
                    response.quantities = 1;
                    response.total = 0;
                    dataTable.row.add(response).draw(false);
                } else { // Otherwise increase the quantity by 1
                    let index = indexes[0];
                    let product = dataTable.row(index).data();
                    product.row_id = ++productCount;
                    product.quantities = Number(product.quantities) + 1;
                    dataTable
                    .row( index )
                    .data( product )
                    .draw();
                }

            },
            (response) => { //Fail
                alert(TextGetter.get("product_not_found") + productIdOrBarcode);
            }
        );
    }
    // Add product using id / barcode
    $("#btn-add-product").click(() => {
        addProduct($("#inp-id-or-barcode").val());
        $("#inp-id-or-barcode").val("");
    });
    // Enter on id / barcode field to input product
    $('#inp-id-or-barcode').keypress(function (e) {
        if (e.which == 13) {
            addProduct($("#inp-id-or-barcode").val());
            $("#inp-id-or-barcode").val("");
            return false;    //<---- Add this line to prevent default
        }
    });

    // Add barcode via server
    ipcRenderer.on(EventGetter.get("new_barcode_from_server"), (event, barcode) => {
        addProduct(barcode);
    });


    // === Setup Clock ====
    function startTimer() {
        var today = new Date();
        var h = today.getHours();
        var m = today.getMinutes();
        var s = today.getSeconds();
        var day = today.getDate();
        var month = today.getMonth() + 1;
        m = checkTime(m);
        s = checkTime(s);
        $("#clock").html(h + ":" + m + ":" + s + "&nbsp;&nbsp;" + day + "/" + month);
        var t = setTimeout(startTimer, 500);
    }
    function checkTime(i) {
        if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
        return i;
    }
    startTimer();

    // === Show Employee ID ===
    $("#logged-in-user").html(userController.getLoggedInUserDisplayName());

    // === Ajax Search for product ===
    // Autocomplete searching for products
    $('#search_form_product').autocomplete({
        lookup: function (query, done) {
            
            let headers = Object({'Content-Type': 'application/json'});
            headers['Authorization'] = "Bearer " + settings.get('account_info.token');

            let postData = {};
            postData.draw = 1;
            postData.length = 8;
            postData.search = {};
            postData.search.value = query;
            postData.start = 0;
            postData = JSON.stringify(postData);

            $.ajax({
                "url": productController.getAjaxAPIUrl(),
                "type": 'POST',
                "headers": headers,
                "processData": true,
                "contentType": 'application/json',
                "data": postData
            })
            .done(function( respond ) {
                let result = {
                    suggestions: []
                };
                let data = respond.data;
                for (let i = 0 ; i < data.length; ++i) {
                    let value = data[i].name + " : " + data[i].barcode;
                    result.suggestions.push({"value": value
                        , "data": data[i]});
                }
                done(result);
            });
            
        },
        onSelect: function (suggestion) {
            // alert('You selected: ' + suggestion.value + ', ' + suggestion.data);
            $("#search_form_product").val(""); // Prevent searching loop
            addProduct(suggestion.data.id);
        }
    });


    // === Handle Customer Area ===
    // Add customer
    function addCustomerById(id) {
        customerController.getCustomerDataById(id, 
            (customer) => { // Success
                $("#customer-name").html(customer.name);
                $("#customer-id").html(customer.id);
                $("#customer-id").attr("customerid", customer.id);
            },
            (respond) => {
                alert(TextGetter.get("customer_not_found"));
            });
    }
    $("#btn-add-customer").click(() => {
        let customerId = $("#inp-customer-id").val();
        addCustomerById(customerId);
    });
    // New customer
    ipcRenderer.on(EventGetter.get("add_customer_success"), (event, customer) => {
        $("#customer-name").html(customer.name);
        $("#customer-id").html(customer.id);
        $("#customer-id").attr("customerid", customer.id);
    });
    $("#btn-new-customer").click(() => {
        ipcRenderer.send(EventGetter.get('request_add_customer'));
    });


    // === Handle Main Buttons Area ===
    // Discard button
    $("#discard").click(() => {

        ipcRenderer.send(EventGetter.get('request_discard_order'));

    });
    // Printing Button
    $("#print-order").click(() => {

        let orderData = {};

        ipcRenderer.send(EventGetter.get('request_print_order'), orderData);

    });
    // Complete Button
    $("#complete-order").click(() => {

        let order_data = {};
        order_data.customer_id = $("#customer-id").attr("customerid");
        order_data.customer_name = $("#customer-name").html();
        order_data.tax = tax;
        order_data.total = $("#grandtotal").val();
        order_data.subtotal = $("#subtotal").val();
        order_data.employee_name = $("#logged-in-user").html();
        order_data.sell_items = dataTable.rows().data().toArray();
        for (let i = 0; i < order_data.sell_items.length; ++i) {
            order_data.sell_items[i].product_id = order_data.sell_items[i].id;
        }
        
        ipcRenderer.send(EventGetter.get("request_add_sell"), order_data);

    });


    // Show barcode scanner
    $("#btn-show-barcode-scanner").click(() => {
        ipcRenderer.send(EventGetter.get("request_open_barcode_scanner"));
    });

});