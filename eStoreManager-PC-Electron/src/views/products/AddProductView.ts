import { BrowserWindow} from "electron";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {ProductController} from '../../controllers/ProductController';
const {ipcMain} = require('electron');


export class AddProductView extends View {


    // Browser Window that request to add product
    private requestedBrowserWindow:BrowserWindow;
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("add_product", window, parent, 650, 400);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: AddProductView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!AddProductView.instance) {
            AddProductView.instance = new AddProductView(window, parent);
        }
        return AddProductView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var productController = new ProductController();
        
        // ======= Handle requests from renderer process ========

        ipcMain.on(EventGetter.get('request_add_product'), (event:any, data:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.show();
        });

        ipcMain.on(EventGetter.get('add_product'), (event:any, data:any) => {
            productController.addProduct(data, (respond:any) => {
                let newProduct = data;
                newProduct.id = respond.id;
                this.requestedBrowserWindow.webContents.send(EventGetter.get("add_product_success"), newProduct);
                Dialog.showDialog("info", null, TextGetter.get("created_product_successfully") + respond.id, this.getWindow(), () => {
                    this.hide();
                });
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            })
        });



    }

}