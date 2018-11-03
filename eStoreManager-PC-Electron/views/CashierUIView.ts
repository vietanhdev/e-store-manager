import { app, BrowserWindow } from "electron";
const {ipcMain} = require('electron');
import {View} from './View';
import {UserController} from '../controllers/UserController';
const { dialog } = require('electron')

export class CashierUIView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("cashier_ui", window, parent);
    }

    // Handle all logic of this view
    logicHandle():void {
        
    }

}