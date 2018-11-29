import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import {View} from '../shared/View';
const {ipcMain} = require('electron');
const { dialog } = require('electron');


export class AboutView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("about_us", window, parent, 500, 400);
        this.getWindow().setMenu(null);
    }

    // Handle all logic of this view
    logicHandle():void {
        
    }

};