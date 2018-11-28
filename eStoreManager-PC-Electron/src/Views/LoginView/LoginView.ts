import { app, BrowserWindow, Menu} from "electron";
const {ipcMain} = require('electron');
import {View} from '../View/View';
import {UserController} from '../../Controllers/Users/UserController';
const { dialog } = require('electron')
const settings = require('electron-settings');

export class LoginView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("LoginView/login", window, parent);
    }

    // Handle all logic of this view
    logicHandle():void {
        ipcMain.on('login-command', (event:any, arg:any) => {
            event.preventDefault();
            
            this.showLoadingModal();

            let userController = new UserController();
            
            userController.login(arg.username, arg.password, (result:any) => {
                if (!result.success) {
                    dialog.showMessageBox(this.getWindow(), Object({
                        type: "error",
                        title: "Login error ("+result.errorCode+")",
                        message: result.message,
                        buttons: ["OK"]
                    }));
                } else {
                    settings.set("account_info.userid", result.id);
                    settings.set("account_info.fullname", result.name);
                    settings.set("account_info.token", result.accessToken);
                    this.requestChangeView("WelcomeView/welcome");
                }
                this.hideLoadingModal();
            }); 

        });
    }

}