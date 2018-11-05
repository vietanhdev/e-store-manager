import { app, BrowserWindow, Menu} from "electron";
const {ipcMain} = require('electron');
import {View} from './View';
const { dialog } = require('electron');

export class AboutView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("about", window, parent, 500, 400);
        this.getWindow().setMenu(null);
    }

    // Handle all logic of this view
    logicHandle():void {
        
    }

};