import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
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
        
    }

}