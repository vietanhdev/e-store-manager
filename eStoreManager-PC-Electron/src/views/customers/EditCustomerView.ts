import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {CustomerController} from '../../controllers/CustomerController';
import { isNull } from "util";
const {ipcMain} = require('electron');
const { dialog } = require('electron');


export class EditCustomerView extends View {

    private customerInfo: any
    // Browser Window that request to add customer
    private requestedBrowserWindow:BrowserWindow;
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("edit_customer", window, parent, 600, 600);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: EditCustomerView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!EditCustomerView.instance) {
            EditCustomerView.instance = new EditCustomerView(window, parent);
        }
        return EditCustomerView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var customerController = new CustomerController();

        // ======= Handle requests from renderer process ========
        
        // Request update customer info
        ipcMain.on(EventGetter.get('request_update_customer_info'), (event:any, customer:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.customerInfo = customer;
            this.show();
        });

        ipcMain.on(EventGetter.get('update_customer'), (event:any, data:any) => {
            customerController.updateCustomerInfo(data, (respond:any) => {
                this.requestedBrowserWindow.webContents.send(EventGetter.get("update_customer_success"));
                Dialog.showDialog("info", "updated_customer_successfully", null, this.getWindow(), () => {
                    this.hide();
                });
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            })
        });


        ipcMain.on(EventGetter.get('request_customer_info'), (event:any) => {
            // console.log(this.customerInfo);
            event.sender.getOwnerBrowserWindow().webContents.send(EventGetter.get("respond_request_customer_info"), this.customerInfo);
        });



    }

}