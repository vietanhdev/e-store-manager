import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {AddCustomerView} from './AddCustomerView';
import {EditCustomerView} from './EditCustomerView';
import {CustomerController} from '../../controllers/CustomerController';
const settings = require('electron-settings');
const {ipcMain} = require('electron');
const { dialog } = require('electron');



export class CustomerView extends View {

    private customerController:CustomerController;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("customers", window, parent);
        this.customerController = new CustomerController();
    }
    
    private static instance: CustomerView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!CustomerView.instance) {
            CustomerView.instance = new CustomerView(window, parent);
        }
        return CustomerView.instance;
    }


    // Handle all logic of this view
    logicHandle():void {

        
        const textGetter = TextGetter.getInstance();

        // ======= Handle requests from renderer process ========

        // Request delete customer
        ipcMain.on(EventGetter.get('request_delete_customer'), (event:any, customer:any) => {

            dialog.showMessageBox(this.getWindow(), Object({
                type: "error",
                title: "Prompt",
                message: TextGetter.get("are_you_sure_delete_customer") + customer.name,
                buttons: ["OK", TextGetter.get("cancel")]
            }), (result) => {
                if (result === 0) { // User click OK
                    this.customerController.deleteCustomer(customer.id, (respond:any) => {
                        Dialog.showDialogFromRespond("info", respond, this.getWindow());
                        // Update customer list
                        this.getWindow().webContents.send(EventGetter.get("delete_row_from_customer_table"), customer.id);
                    }, (respond:any) => {
                        Dialog.showDialogFromRespond("error", respond, this.getWindow());
                    });
                }
            });

        });

    }

}