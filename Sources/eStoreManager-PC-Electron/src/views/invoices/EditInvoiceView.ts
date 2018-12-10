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


export class EditInvoiceView extends View {

    private invoiceInfo: any
    // Browser Window that request to add invoice
    private requestedBrowserWindow:BrowserWindow;
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("edit_invoice", window, parent, 600, 600);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: EditInvoiceView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!EditInvoiceView.instance) {
            EditInvoiceView.instance = new EditInvoiceView(window, parent);
        }
        return EditInvoiceView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var invoiceController = new InvoiceController();

        // ======= Handle requests from renderer process ========
        
        // Request update invoice info
        ipcMain.on(EventGetter.get('request_update_invoice_info'), (event:any, invoice:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.invoiceInfo = invoice;
            this.show();
        });

        ipcMain.on(EventGetter.get('update_invoice'), (event:any, data:any) => {
            invoiceController.updateInvoiceInfo(data, (respond:any) => {
                this.requestedBrowserWindow.webContents.send(EventGetter.get("update_invoice_success"));
                Dialog.showDialog("info", "updated_invoice_successfully", null, this.getWindow(), () => {
                    this.hide();
                });
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            })
        });


        ipcMain.on(EventGetter.get('request_invoice_info'), (event:any) => {
            // console.log(this.invoiceInfo);
            event.sender.getOwnerBrowserWindow().webContents.send(EventGetter.get("respond_request_invoice_info"), this.invoiceInfo);
        });



    }

}