import { BrowserWindow} from "electron";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {SupplierController} from '../../controllers/SupplierController';
const {ipcMain} = require('electron');


export class EditSupplierView extends View {

    private supplierInfo: any
    // Browser Window that request to add supplier
    private requestedBrowserWindow:BrowserWindow;
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("edit_supplier", window, parent, 600, 600);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: EditSupplierView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!EditSupplierView.instance) {
            EditSupplierView.instance = new EditSupplierView(window, parent);
        }
        return EditSupplierView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var supplierController = new SupplierController();

        // ======= Handle requests from renderer process ========
        
        // Request update supplier info
        ipcMain.on(EventGetter.get('request_update_supplier_info'), (event:any, supplier:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.supplierInfo = supplier;
            this.show();
        });

        ipcMain.on(EventGetter.get('update_supplier'), (event:any, data:any) => {
            supplierController.updateSupplierInfo(data, (respond:any) => {
                this.requestedBrowserWindow.webContents.send(EventGetter.get("update_supplier_success"));
                Dialog.showDialog("info", "updated_supplier_successfully", null, this.getWindow(), () => {
                    this.hide();
                });
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            })
        });


        ipcMain.on(EventGetter.get('request_supplier_info'), (event:any) => {
            // console.log(this.supplierInfo);
            event.sender.getOwnerBrowserWindow().webContents.send(EventGetter.get("respond_request_supplier_info"), this.supplierInfo);
        });



    }

}