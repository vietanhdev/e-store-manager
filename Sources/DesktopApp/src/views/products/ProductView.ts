import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {AddProductView} from './AddProductView';
import {EditProductView} from './EditProductView';
import {ProductController} from '../../controllers/ProductController';
import { TextGetter } from "../../services/TextGetter";
const settings = require('electron-settings');
const {ipcMain} = require('electron');
const { dialog } = require('electron');



export class ProductView extends View {

    private productController:ProductController;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("products", window, parent);
        this.productController = new ProductController();
    }
    
    private static instance: ProductView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!ProductView.instance) {
            ProductView.instance = new ProductView(window, parent);
        }
        return ProductView.instance;
    }


    // Handle all logic of this view
    logicHandle():void {

        
        const textGetter = TextGetter.getInstance();

        // ======= Handle requests from renderer process ========

        // Request delete product
        ipcMain.on(EventGetter.get('request_delete_product'), (event:any, product:any) => {

            dialog.showMessageBox(this.getWindow(), Object({
                type: "error",
                title: "Prompt",
                message: TextGetter.get("are_you_sure_delete_product") + product.name,
                buttons: ["OK", TextGetter.get("cancel")]
            }), (result) => {
                if (result === 0) { // User click OK
                    this.productController.deleteProduct(product.id, (respond:any) => {
                        Dialog.showDialogFromRespond("info", respond, this.getWindow());
                        // Update product list
                        this.getWindow().webContents.send(EventGetter.get("delete_row_from_product_table"), product.id);
                    }, (respond:any) => {
                        Dialog.showDialogFromRespond("error", respond, this.getWindow());
                    });
                }
            });

        });

    }

}