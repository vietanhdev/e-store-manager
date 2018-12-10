import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {ProductController} from '../../controllers/ProductController';
import {BuyController} from '../../controllers/BuyController';
import { TextGetter } from "../../services/TextGetter";
const settings = require('electron-settings');
const {ipcMain} = require('electron');
const { dialog } = require('electron');



export class AddImportBillView extends View {

    private productController:ProductController;

    // Browser Window that request
    private requestedBrowserWindow:BrowserWindow;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("add_import_bill", window, parent);
        // this.getWindow().webContents.openDevTools();
        this.productController = new ProductController();
    }
    
    private static instance: AddImportBillView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!AddImportBillView.instance) {
            AddImportBillView.instance = new AddImportBillView(window, parent);
        }
        return AddImportBillView.instance;
    }


    // Handle all logic of this view
    logicHandle():void {

        
        const textGetter = TextGetter.getInstance();


        // ======= Handle requests from renderer process ========
        ipcMain.on(EventGetter.get('request_add_import_bill'), (event:any, data:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.show();
        });

        let buyController = new BuyController();

        ipcMain.on(EventGetter.get('add_import_bill'), (event:any, data:any) => {
            let postData = {"buy_items": data}
            buyController.addBuy(postData, (respond:any) => {
                let newData = data;
                newData.id = respond.id;
                this.requestedBrowserWindow.webContents.send(EventGetter.get("add_import_bill_success"), newData);
                this.hide();
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            });
        });

    }

}