import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import {View} from '../shared/View';
const {ipcMain} = require('electron');
const { dialog } = require('electron');

export class CashierView extends View {

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("cashier",  window, parent);
    }

    private static instance: CashierView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!CashierView.instance) {
            CashierView.instance = new CashierView(window, parent);
        }
        return CashierView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        // Handle request discard order
        ipcMain.on(EventGetter.get('request_discard_order'), () => {
            // Ask user the new value of quantity
            dialog.showMessageBox(this.getWindow(), Object({
                type: "info",
                title: TextGetter.get("discard_order"),
                message: TextGetter.get("are_you_sure_discard_this_order"),
                buttons: [TextGetter.get("ok"), TextGetter.get("cancel")]
            }), (r) => {
                if (r == 0) {
                    this.getWindow().reload();
                }
            });
        });
        
    }

}