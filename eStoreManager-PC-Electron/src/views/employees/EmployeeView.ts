import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import {View} from '../shared/View';
import {AddEmployeeView} from './AddEmployeeView';
import {UserController} from '../../controllers/UserController';
const {ipcMain} = require('electron');
const { dialog } = require('electron');

export class EmployeeView extends View {

    constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("employees", window, parent);
    }

    // Handle all logic of this view
    logicHandle():void {

        var userController = new UserController();

        // ======= Handle requests from renderer process ========

        // Request employee list
        ipcMain.on('request_update_employee_list', (event:any) => {

            userController.getAllUsers((data:any) => {
                this.getWindow().webContents.send("update_employee_list", data);
            }, () => {
                dialog.showMessageBox(this.getWindow(), Object({
                    type: "error",
                    title: "Error",
                    message: "Error on retriving employee list",
                    buttons: ["OK"]
                }));
            });

            
        });


        // Request add employee
        ipcMain.on('request_add_employee', (event:any) => {

            let addEmployee = new AddEmployeeView(null, this.getWindow());
            addEmployee.show();

        });


    }

}