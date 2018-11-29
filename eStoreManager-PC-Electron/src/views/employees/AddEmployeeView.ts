import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import {View} from '../shared/View';
import {UserController} from '../../controllers/UserController';
const {ipcMain} = require('electron');
const { dialog } = require('electron');


export class AddEmployeeView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("add_employee", window, parent, 600, 600);
        this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    // Handle all logic of this view
    logicHandle():void {

        var userController = new UserController();

        // ======= Handle requests from renderer process ========

        ipcMain.on(EventGetter.get('add_employee'), (event:any, data:any) => {
            userController.addUser(data, (respond:any) => {
                this.getWindow().webContents.send(EventGetter.get("add_employee_success"));
                dialog.showMessageBox(this.getWindow(), Object({
                    type: "info",
                    title: "Success",
                    message: "Created user successfully. UserId: " + respond.id,
                    buttons: ["OK"]
                }));
                console.log(respond);
            }, (respond:any) => {
                dialog.showMessageBox(this.getWindow(), Object({
                    type: "error",
                    title: "Error: " + respond.code,
                    message: respond.message,
                    buttons: ["OK"]
                }));
            })
        });


    }

}