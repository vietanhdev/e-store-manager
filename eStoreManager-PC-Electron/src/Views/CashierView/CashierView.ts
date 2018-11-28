import { app, BrowserWindow, Menu } from "electron";
const {ipcMain} = require('electron');
import {View} from '../View/View';
import {UserController} from '../../Controllers/Users/UserController';
const { dialog } = require('electron')

export class CashierView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("CashierView/cashier_ui", window, parent);
    }

    // Handle all logic of this view
    logicHandle():void {
        
    }

}