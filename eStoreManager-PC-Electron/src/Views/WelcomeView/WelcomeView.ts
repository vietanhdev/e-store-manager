import { app, BrowserWindow, Menu} from "electron";
const {ipcMain} = require('electron');
import {View} from '../View/View';
const { dialog } = require('electron');

export class WelcomeView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("WelcomeView/welcome", window, parent);
    }

    // Handle all logic of this view
    logicHandle():void {
        ipcMain.on('menu-click', (event:any, data:any) => {

            console.log(data);
            
            if (data == "menu-cashier-ui") {
                this.requestChangeView('CashierView/cashier_ui');
            }

        });
    }

}