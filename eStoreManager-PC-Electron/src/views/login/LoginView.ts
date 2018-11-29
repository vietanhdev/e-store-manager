import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import {View} from '../shared/View';
import {UserController} from '../../controllers/UserController';
const {ipcMain} = require('electron');
const { dialog } = require('electron');
const settings = require('electron-settings');

export class LoginView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("login", window, parent);
    }

    // Handle all logic of this view
    logicHandle():void {
        ipcMain.on('login-command', (event:any, arg:any) => {
            event.preventDefault();
            
            this.showLoadingModal();

            let userController = new UserController();
            
            userController.login(arg.username, arg.password, (result:any) => {
                settings.set("account_info.userid", result.id);
                settings.set("account_info.fullname", result.name);
                settings.set("account_info.token", result.accessToken);
                this.requestChangeView("welcome");
                this.hideLoadingModal();
            }, (result:any) => {
                dialog.showMessageBox(this.getWindow(), Object({
                    type: "error",
                    title: "Login error ("+result.code+")",
                    message: result.message,
                    buttons: ["OK"]
                }));
            }); 

        });
    }

}