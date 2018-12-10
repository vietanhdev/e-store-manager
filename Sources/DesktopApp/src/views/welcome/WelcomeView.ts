import { app, BrowserWindow, Menu} from "electron";
import {View} from '../shared/View';
const { dialog } = require('electron');

export class WelcomeView extends View {

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("welcome", window, parent);
    }

    
    private static instance: WelcomeView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!WelcomeView.instance) {
            WelcomeView.instance = new WelcomeView(window, parent);
        }
        return WelcomeView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {
        
    }

}