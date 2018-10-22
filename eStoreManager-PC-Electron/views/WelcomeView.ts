import { app, BrowserWindow } from "electron";
const {ipcMain} = require('electron');
import {View} from './View';
const { dialog } = require('electron');

export class WelcomeView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("welcome", window, parent);
    }

    // Handle all logic of this view
    logicHandle():void {
        ipcMain.on('menu-click', (event:any, data:any) => {

            console.log(data);
            
            if (data == "menu-cashier-ui") {
                this.requestChangeView('cashier_ui');
            }

        });
    }

}