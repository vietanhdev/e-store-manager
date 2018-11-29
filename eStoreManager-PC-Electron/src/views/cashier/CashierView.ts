import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import {View} from '../shared/View';
const {ipcMain} = require('electron');
const { dialog } = require('electron');

export class CashierView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("cashier",  window, parent);
    }

    // Handle all logic of this view
    logicHandle():void {
        
    }

}