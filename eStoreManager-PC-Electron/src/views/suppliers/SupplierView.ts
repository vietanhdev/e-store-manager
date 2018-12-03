import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {AddSupplierView} from './AddSupplierView';
import {EditSupplierView} from './EditSupplierView';
import {SupplierController} from '../../controllers/SupplierController';
import { TextGetter } from "../../services/TextGetter";
const settings = require('electron-settings');
const {ipcMain} = require('electron');
const { dialog } = require('electron');



export class SupplierView extends View {

    private supplierController:SupplierController;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("suppliers", window, parent);
        this.supplierController = new SupplierController();
    }
    
    private static instance: SupplierView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!SupplierView.instance) {
            SupplierView.instance = new SupplierView(window, parent);
        }
        return SupplierView.instance;
    }


    // Handle all logic of this view
    logicHandle():void {

        
        const textGetter = TextGetter.getInstance();

        // ======= Handle requests from renderer process ========

        // Request delete supplier
        ipcMain.on(EventGetter.get('request_delete_supplier'), (event:any, supplier:any) => {

            dialog.showMessageBox(this.getWindow(), Object({
                type: "error",
                title: "Prompt",
                message: TextGetter.get("are_you_sure_delete_supplier") + supplier.name,
                buttons: ["OK", TextGetter.get("cancel")]
            }), (result) => {
                if (result === 0) { // User click OK
                    this.supplierController.deleteSupplier(supplier.id, (respond:any) => {
                        Dialog.showDialogFromRespond("info", respond, this.getWindow());
                        // Update supplier list
                        this.getWindow().webContents.send(EventGetter.get("delete_row_from_supplier_table"), supplier.id);
                    }, (respond:any) => {
                        Dialog.showDialogFromRespond("error", respond, this.getWindow());
                    });
                }
            });

        });

    }

}