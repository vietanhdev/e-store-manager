import { app, BrowserWindow } from "electron";
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
            let result = userController.login(arg.username, arg.password);

            if (result.success === false) {
                dialog.showMessageBox(this.getWindow(), Object({
                    type: "error",
                    title: "Login error ("+result.errorCode+")",
                    message: result.message,
                    buttons: ["OK"]
                }));
                this.hideLoadingModal();
            }
            

        });
    }

}