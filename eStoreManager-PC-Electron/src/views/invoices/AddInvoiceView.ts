import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {InvoiceController} from '../../controllers/InvoiceController';
import { isNull } from "util";
const {ipcMain} = require('electron');
const { dialog } = require('electron');


export class AddInvoiceView extends View {


    // Browser Window that request to add invoice
    private requestedBrowserWindow:BrowserWindow;
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("add_invoice", window, parent, 600, 400);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: AddInvoiceView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!AddInvoiceView.instance) {
            AddInvoiceView.instance = new AddInvoiceView(window, parent);
        }
        return AddInvoiceView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var invoiceController = new InvoiceController();
        
        // ======= Handle requests from renderer process ========

        ipcMain.on(EventGetter.get('request_add_invoice'), (event:any, data:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.show();
        });

        ipcMain.on(EventGetter.get('add_invoice'), (event:any, data:any) => {
            invoiceController.addInvoice(data, (respond:any) => {
                let newInvoice = data;
                newInvoice.id = respond.id;
                this.requestedBrowserWindow.webContents.send(EventGetter.get("add_invoice_success"), newInvoice);
                Dialog.showDialog("info", null, TextGetter.get("created_invoice_successfully") + respond.id, this.getWindow(), () => {
                    this.hide();
                });
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            })
        });



    }

}