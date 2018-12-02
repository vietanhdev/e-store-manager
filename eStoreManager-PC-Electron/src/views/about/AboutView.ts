import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import {View} from '../shared/View';
const {ipcMain} = require('electron');
const { dialog } = require('electron');


export class AboutView extends View {

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("about_us",  window, parent, 500, 400);
    }

    private static instance: AboutView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!AboutView.instance) {
            AboutView.instance = new AboutView(window, parent);
        }
        return AboutView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {
        
    }


};