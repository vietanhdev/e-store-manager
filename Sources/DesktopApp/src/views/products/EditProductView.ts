import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {ProductController} from '../../controllers/ProductController';
import { isNull } from "util";
const {ipcMain} = require('electron');
const { dialog } = require('electron');


export class EditProductView extends View {

    private productInfo: any
    // Browser Window that request to add product
    private requestedBrowserWindow:BrowserWindow;
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("edit_product", window, parent, 600, 600);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: EditProductView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!EditProductView.instance) {
            EditProductView.instance = new EditProductView(window, parent);
        }
        return EditProductView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var productController = new ProductController();

        // ======= Handle requests from renderer process ========
        
        // Request update product info
        ipcMain.on(EventGetter.get('request_update_product_info'), (event:any, product:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.productInfo = product;
            this.show();
        });

        ipcMain.on(EventGetter.get('update_product'), (event:any, data:any) => {
            productController.updateProductInfo(data, (respond:any) => {
                this.requestedBrowserWindow.webContents.send(EventGetter.get("update_product_success"));
                Dialog.showDialog("info", "updated_product_successfully", null, this.getWindow(), () => {
                    this.hide();
                });
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            })
        });


        ipcMain.on(EventGetter.get('request_product_info'), (event:any) => {
            // console.log(this.productInfo);
            event.sender.getOwnerBrowserWindow().webContents.send(EventGetter.get("respond_request_product_info"), this.productInfo);
        });



    }

}