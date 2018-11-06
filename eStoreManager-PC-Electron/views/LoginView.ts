import { app, BrowserWindow, Menu} from "electron";
const {ipcMain} = require('electron');
import {View} from './View';
import {UserController} from '../controllers/UserController';
const { dialog } = require('electron')

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
                if (!result.success) {
                    dialog.showMessageBox(this.getWindow(), Object({
                        type: "error",
                        title: "Login error ("+result.errorCode+")",
                        message: result.message,
                        buttons: ["OK"]
                    }));
                } else {
                    dialog.showMessageBox(this.getWindow(), Object({
                        type: "info",
                        title: "Logged",
                        message: "Logged in",
                        buttons: ["OK"]
                    }));
                    this.requestChangeView("welcome");
                }
                this.hideLoadingModal();
            }); 

        });
    }

}