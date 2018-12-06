import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import {View} from '../shared/View';
import {UserController} from '../../controllers/UserController';
const {ipcMain} = require('electron');
const { dialog } = require('electron');
const settings = require('electron-settings');

export class PreferenceView extends View {

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("preferences", window, parent, 850, 600);
        this.getWindow().setMenu(null);
        // this.getWindow().webContents.openDevTools();
    }

    
    private static instance: PreferenceView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!PreferenceView.instance) {
            PreferenceView.instance = new PreferenceView(window, parent);
        }
        return PreferenceView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {
        
    }

};