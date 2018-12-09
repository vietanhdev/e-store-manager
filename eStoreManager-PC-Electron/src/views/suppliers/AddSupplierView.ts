import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {SupplierController} from '../../controllers/SupplierController';
import { isNull } from "util";
const {ipcMain} = require('electron');
const { dialog } = require('electron');


export class AddSupplierView extends View {


    // Browser Window that request to add supplier
    private requestedBrowserWindow:BrowserWindow;
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("add_supplier", window, parent, 600, 400);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: AddSupplierView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!AddSupplierView.instance) {
            AddSupplierView.instance = new AddSupplierView(window, parent);
        }
        return AddSupplierView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var supplierController = new SupplierController();
        
        // ======= Handle requests from renderer process ========

        ipcMain.on(EventGetter.get('request_add_supplier'), (event:any, data:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.show();
        });

        ipcMain.on(EventGetter.get('add_supplier'), (event:any, data:any) => {
            supplierController.addSupplier(data, (respond:any) => {
                let newSupplier = data;
                newSupplier.id = respond.id;
                this.requestedBrowserWindow.webContents.send(EventGetter.get("add_supplier_success"), newSupplier);
                Dialog.showDialog("info", null, TextGetter.get("created_supplier_successfully") + respond.id, this.getWindow(), () => {
                    this.hide();
                });
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            })
        });



    }

}