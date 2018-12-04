import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {AddProductTypeView} from './AddProductTypeView';
import {EditProductTypeView} from './EditProductTypeView';
import {ProductTypeController} from '../../controllers/ProductTypeController';
import { TextGetter } from "../../services/TextGetter";
const settings = require('electron-settings');
const {ipcMain} = require('electron');
const { dialog } = require('electron');



export class ProductTypeView extends View {

    private product_typeController:ProductTypeController;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("product_types", window, parent);
        this.product_typeController = new ProductTypeController();
    }
    
    private static instance: ProductTypeView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!ProductTypeView.instance) {
            ProductTypeView.instance = new ProductTypeView(window, parent);
        }
        return ProductTypeView.instance;
    }


    // Handle all logic of this view
    logicHandle():void {

        
        const textGetter = TextGetter.getInstance();

        // ======= Handle requests from renderer process ========

        // Request delete product_type
        ipcMain.on(EventGetter.get('request_delete_product_type'), (event:any, product_type:any) => {

            dialog.showMessageBox(this.getWindow(), Object({
                type: "error",
                title: "Prompt",
                message: TextGetter.get("are_you_sure_delete_product_type") + product_type.name,
                buttons: ["OK", TextGetter.get("cancel")]
            }), (result) => {
                if (result === 0) { // User click OK
                    this.product_typeController.deleteProductType(product_type.id, (respond:any) => {
                        Dialog.showDialogFromRespond("info", respond, this.getWindow());
                        // Update product_type list
                        this.getWindow().webContents.send(EventGetter.get("delete_row_from_product_type_table"), product_type.id);
                    }, (respond:any) => {
                        Dialog.showDialogFromRespond("error", respond, this.getWindow());
                    });
                }
            });

        });

    }

}