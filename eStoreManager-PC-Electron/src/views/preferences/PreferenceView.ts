import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import {View} from '../shared/View';
import {UserController} from '../../controllers/UserController';
const {ipcMain} = require('electron');
const { dialog } = require('electron');
const settings = require('electron-settings');

export class PreferenceView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("preferences", window, parent, 750, 700);
        this.getWindow().setMenu(null);
        // this.getWindow().webContents.openDevTools();
    }

    // Handle all logic of this view
    logicHandle():void {
        
    }

};