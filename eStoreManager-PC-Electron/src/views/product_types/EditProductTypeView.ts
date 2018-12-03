import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {ProductTypeController} from '../../controllers/ProductTypeController';
import { isNull } from "util";
const {ipcMain} = require('electron');
const { dialog } = require('electron');


export class EditProductTypeView extends View {

    private product_typeInfo: any
    // Browser Window that request to add product_type
    private requestedBrowserWindow:BrowserWindow;
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("edit_product_type", window, parent, 600, 600);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: EditProductTypeView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!EditProductTypeView.instance) {
            EditProductTypeView.instance = new EditProductTypeView(window, parent);
        }
        return EditProductTypeView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var productTypeController = new ProductTypeController();

        // ======= Handle requests from renderer process ========
        
        // Request update product_type info
        ipcMain.on(EventGetter.get('request_update_product_type_info'), (event:any, product_type:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.product_typeInfo = product_type;
            this.show();
        });

        ipcMain.on(EventGetter.get('update_product_type'), (event:any, data:any) => {
            productTypeController.updateProductTypeInfo(data, (respond:any) => {
                this.requestedBrowserWindow.webContents.send(EventGetter.get("update_product_type_success"));
                Dialog.showDialog("info", TextGetter.get("updated_product_type_successfully"), null, this.getWindow(), () => {
                    this.hide();
                });
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            })
        });


        ipcMain.on(EventGetter.get('request_product_type_info'), (event:any) => {
            // console.log(this.product_typeInfo);
            event.sender.getOwnerBrowserWindow().webContents.send(EventGetter.get("respond_request_product_type_info"), this.product_typeInfo);
        });



    }

}