import { app, BrowserWindow, Menu} from "electron";
import {View} from '../View/View';
const { dialog } = require('electron');

export class WelcomeView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("WelcomeView/welcome", window, parent);
    }

    // Handle all logic of this view
    logicHandle():void {
        
    }

}