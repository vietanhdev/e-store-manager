import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
// import {AddImportProductView} from './AddImportProductView';
// import {EditImportProductView} from './EditImportProductView';
import {BuyController} from '../../controllers/BuyController';
import { TextGetter } from "../../services/TextGetter";
const settings = require('electron-settings');
const {ipcMain} = require('electron');
const { dialog } = require('electron');



export class ImportProductView extends View {

    private buyController:BuyController;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("import_products", window, parent);
        this.buyController = new BuyController();
    }
    
    private static instance: ImportProductView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!ImportProductView.instance) {
            ImportProductView.instance = new ImportProductView(window, parent);
        }
        return ImportProductView.instance;
    }


    // Handle all logic of this view
    logicHandle():void {

        
        const textGetter = TextGetter.getInstance();

        // ======= Handle requests from renderer process ========

        // Request discard bill
        ipcMain.on(EventGetter.get('request_discard_import_bill'), (event:any, data:any) => {

            dialog.showMessageBox(this.getWindow(), Object({
                type: "error",
                title: "Prompt",
                message: TextGetter.get("are_you_sure_discard_import_bill"),
                buttons: ["OK", TextGetter.get("cancel")]
            }), (result) => {
                if (result === 0) { // User click OK
                    this.buyController.discardBill(data, (respond:any) => {
                        Dialog.showDialogFromRespond("info", respond, this.getWindow());
                        this.getWindow().webContents.send(EventGetter.get("discard_import_bill_success"));
                    }, (respond:any) => {
                        Dialog.showDialogFromRespond("error", respond, this.getWindow());
                    });
                }
            });

        });

        
    }

}