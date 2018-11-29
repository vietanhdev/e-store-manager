import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import {View} from '../shared/View';
import {UserController} from '../../controllers/UserController';
const {ipcMain} = require('electron');
const { dialog } = require('electron');

export class EmployeeView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("edit_employee", window, parent);
    }

    // Handle all logic of this view
    logicHandle():void {

        var userController = new UserController();

        // ======= Handle requests from renderer process ========

        // Request employee list
        ipcMain.on('request_employee_list', (event:any) => {

            userController.getAllUsers((data:any) => {
                this.getWindow().webContents.send("update_employee_list", data);
            }, (respond:any) => {
                dialog.showMessageBox(this.getWindow(), Object({
                    type: "error",
                    title: "Error: " + respond.code,
                    message: respond.message,
                    buttons: ["OK"]
                }));
            });

            
        });


    }

}