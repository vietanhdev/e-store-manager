import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import { isNull } from "util";
const {ipcMain} = require('electron');
const { dialog } = require('electron');


export class AddImportItemView extends View {


    // Browser Window that request
    private requestedBrowserWindow:BrowserWindow;
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("add_import_item", window, parent, 800, 500);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: AddImportItemView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!AddImportItemView.instance) {
            AddImportItemView.instance = new AddImportItemView(window, parent);
        }
        return AddImportItemView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {
        
        // ======= Handle requests from renderer process ========
        ipcMain.on(EventGetter.get('request_add_import_item'), (event:any, data:any) => {
            this.setOriginWindow(null);
            this.setOriginParent(event.sender.getOwnerBrowserWindow());
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();
            this.show();
        });

        ipcMain.on(EventGetter.get('add_import_item'), (event:any, data:any) => {
            this.requestedBrowserWindow.webContents.send(EventGetter.get("add_import_item_success"), data);
            this.hide();
        });

    }

}