import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import {View} from '../shared/View';
import {UserController} from '../../controllers/UserController';
const {ipcMain} = require('electron');
const { dialog } = require('electron');
const settings = require('electron-settings');

export class LoginView extends View {

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("login", window, parent);
    }


    private static instance: LoginView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!LoginView.instance) {
            LoginView.instance = new LoginView(window, parent);
        }
        return LoginView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {
        ipcMain.on(EventGetter.get('login_command'), (event:any, arg:any) => {
            event.preventDefault();

            let userController = new UserController();
            
            userController.login(arg.username, arg.password, (result:any) => {
                settings.set("account_info.userid", result.id);
                settings.set("account_info.fullname", result.name);
                settings.set("account_info.token", result.accessToken);
                settings.set("account_info.roles", result.role);
                settings.set('verified_logged_in', true);
                this.requestChangeView("welcome");
            }, (result:any) => {
                dialog.showMessageBox(this.getWindow(), Object({
                    type: "error",
                    title: "Login Error",
                    message: result.message,
                    buttons: ["OK"]
                }));
            }); 

        });
    }

}