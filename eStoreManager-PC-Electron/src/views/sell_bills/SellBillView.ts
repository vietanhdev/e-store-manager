import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {SellController} from '../../controllers/SellController';
import { TextGetter } from "../../services/TextGetter";
const settings = require('electron-settings');
const {ipcMain} = require('electron');
const { dialog } = require('electron');



export class SellBillView extends View {

    private sellController:SellController;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("sell_bills", window, parent);
        this.sellController = new SellController();
    }
    
    private static instance: SellBillView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!SellBillView.instance) {
            SellBillView.instance = new SellBillView(window, parent);
        }
        return SellBillView.instance;
    }


    // Handle all logic of this view
    logicHandle():void {

        
        const textGetter = TextGetter.getInstance();

        // ======= Handle requests from renderer process ========

        // Request discard bill
        ipcMain.on(EventGetter.get('request_discard_sell_bill'), (event:any, data:any) => {

            dialog.showMessageBox(this.getWindow(), Object({
                type: "error",
                title: "Prompt",
                message: TextGetter.get("are_you_sure_discard_sell_bill"),
                buttons: ["OK", TextGetter.get("cancel")]
            }), (result) => {
                if (result === 0) { // User click OK
                    this.sellController.discardBill(data, (respond:any) => {
                        Dialog.showDialogFromRespond("info", respond, this.getWindow());
                        this.getWindow().webContents.send(EventGetter.get("discard_sell_bill_success"));
                    }, (respond:any) => {
                        Dialog.showDialogFromRespond("error", respond, this.getWindow());
                    });
                }
            });

        });

        
    }

}