import { app, BrowserWindow, Menu} from "electron";
const {ipcMain} = require('electron');
import {View} from '../View/View';
const { dialog } = require('electron');

export class PreferenceView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("PreferenceView/preference", window, parent, 800, 700);
        this.getWindow().setMenu(null);
        this.getWindow().webContents.openDevTools();
    }

    // Handle all logic of this view
    logicHandle():void {
        
    }

};