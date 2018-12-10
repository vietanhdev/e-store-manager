import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {AddInvoiceView} from './AddInvoiceView';
import {EditInvoiceView} from './EditInvoiceView';
import {InvoiceController} from '../../controllers/InvoiceController';
import { TextGetter } from "../../services/TextGetter";
const settings = require('electron-settings');
const {ipcMain} = require('electron');
const { dialog } = require('electron');



export class InvoiceView extends View {

    private invoiceController:InvoiceController;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("invoices", window, parent);
        this.invoiceController = new InvoiceController();
    }
    
    private static instance: InvoiceView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!InvoiceView.instance) {
            InvoiceView.instance = new InvoiceView(window, parent);
        }
        return InvoiceView.instance;
    }


    // Handle all logic of this view
    logicHandle():void {

        
        const textGetter = TextGetter.getInstance();

        // ======= Handle requests from renderer process ========

        // Request delete invoice
        ipcMain.on(EventGetter.get('request_delete_invoice'), (event:any, invoice:any) => {

            dialog.showMessageBox(this.getWindow(), Object({
                type: "error",
                title: "Prompt",
                message: TextGetter.get("are_you_sure_delete_invoice") + invoice.name,
                buttons: ["OK", TextGetter.get("cancel")]
            }), (result) => {
                if (result === 0) { // User click OK
                    this.invoiceController.deleteInvoice(invoice.id, (respond:any) => {
                        Dialog.showDialogFromRespond("info", respond, this.getWindow());
                        // Update invoice list
                        this.getWindow().webContents.send(EventGetter.get("delete_row_from_invoice_table"));
                    }, (respond:any) => {
                        Dialog.showDialogFromRespond("error", respond, this.getWindow());
                    });
                }
            });

        });

    }

}