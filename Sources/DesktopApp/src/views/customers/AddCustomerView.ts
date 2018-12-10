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


export class AddCustomerView extends View {


    // Browser Window that request to add customer
    private requestedBrowserWindow:BrowserWindow;
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("add_customer", window, parent, 600, 400);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: AddCustomerView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!AddCustomerView.instance) {
            AddCustomerView.instance = new AddCustomerView(window, parent);
        }
        return AddCustomerView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var customerController = new CustomerController();
        
        // ======= Handle requests from renderer process ========

        ipcMain.on(EventGetter.get('request_add_customer'), (event:any, data:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.show();
        });

        ipcMain.on(EventGetter.get('add_customer'), (event:any, data:any) => {
            customerController.addCustomer(data, (respond:any) => {
                let newCustomer = data;
                newCustomer.id = respond.id;
                this.requestedBrowserWindow.webContents.send(EventGetter.get("add_customer_success"), newCustomer);
                Dialog.showDialog("info", null, TextGetter.get("created_customer_successfully") + respond.id, this.getWindow(), () => {
                    this.hide();
                });
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            })
        });



    }

}