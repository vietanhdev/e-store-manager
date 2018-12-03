import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import {View} from '../shared/View';
import {UserController} from '../../controllers/UserController';
import { isNull, isUndefined } from "util";
const {ipcMain} = require('electron');
const { dialog } = require('electron');
const settings = require('electron-settings');

export class PasswordInputView extends View {

    private windowPromptPassword: BrowserWindow;
    private passwordCarrier: any; /// information about password

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("password_input", window, parent, 500, 200);
        this.setMenu(null);
    }


    private static instance: PasswordInputView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!PasswordInputView.instance) {
            PasswordInputView.instance = new PasswordInputView(window, parent);
        }
        return PasswordInputView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {
        ipcMain.on(EventGetter.get('user_enter_password'), (event:any, password:any) => {
            this.hide();
            if (isUndefined(this.passwordCarrier)) this.passwordCarrier = {password: ""};
            this.passwordCarrier.password = password;
            this.windowPromptPassword.webContents.send(EventGetter.get("set_password"), this.passwordCarrier);
        });
        ipcMain.on(EventGetter.get('prompt_password'), (event:any, passwordCarrier:any) => {
            this.hide();
            this.windowPromptPassword = event.sender.getOwnerBrowserWindow();
            this.setOriginParent(this.windowPromptPassword);
            this.passwordCarrier = passwordCarrier;
            this.show();
        });
    }

}