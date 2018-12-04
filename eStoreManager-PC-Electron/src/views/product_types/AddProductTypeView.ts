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


export class AddProductTypeView extends View {


    // Browser Window that request to add product_type
    private requestedBrowserWindow:BrowserWindow;
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("add_product_type", window, parent, 650, 400);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: AddProductTypeView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!AddProductTypeView.instance) {
            AddProductTypeView.instance = new AddProductTypeView(window, parent);
        }
        return AddProductTypeView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var product_typeController = new ProductTypeController();
        
        // ======= Handle requests from renderer process ========

        ipcMain.on(EventGetter.get('request_add_product_type'), (event:any, data:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.show();
        });

        ipcMain.on(EventGetter.get('add_product_type'), (event:any, data:any) => {
            product_typeController.addProductType(data, (respond:any) => {
                let newProductType = data;
                newProductType.id = respond.id;
                this.requestedBrowserWindow.webContents.send(EventGetter.get("add_product_type_success"), newProductType);
                Dialog.showDialog("info", TextGetter.get("created_product_type_successfully") + respond.id, null, this.getWindow(), () => {
                    this.hide();
                });
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            })
        });



    }

}