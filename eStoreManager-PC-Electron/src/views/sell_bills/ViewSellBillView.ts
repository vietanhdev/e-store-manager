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



export class ViewSellBillView extends View {

    private productController:ProductController;
    private data: any;

    // Browser Window that request
    private requestedBrowserWindow:BrowserWindow;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("view_sell_bill", window, parent);
        // this.getWindow().webContents.openDevTools();
        this.productController = new ProductController();
    }
    
    private static instance: ViewSellBillView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!ViewSellBillView.instance) {
            ViewSellBillView.instance = new ViewSellBillView(window, parent);
        }
        return ViewSellBillView.instance;
    }


    // Handle all logic of this view
    logicHandle():void {

        const textGetter = TextGetter.getInstance();

        // ======= Handle requests from renderer process ========
        ipcMain.on(EventGetter.get('request_view_sell_bill'), (event:any, data:any) => {
            this.data = data;
            // console.log(data);
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.show();
        });

        ipcMain.on(EventGetter.get('request_sell_view_data'), (event:any, data:any) => {
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.requestedBrowserWindow.webContents.send(EventGetter.get('respond_request_sell_view_data'), this.data);
        });

    }

}