import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {AddEmployeeView} from './AddEmployeeView';
import {UserController} from '../../controllers/UserController';
const {ipcMain} = require('electron');


export class EmployeeView extends View {

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("employees", window, parent);
    }
    
    private static instance: EmployeeView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!EmployeeView.instance) {
            EmployeeView.instance = new EmployeeView(window, parent);
        }
        return EmployeeView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var userController = new UserController();

        // ======= Handle requests from renderer process ========

        // Request employee list
        ipcMain.on(EventGetter.get('request_update_employee_list'), (event:any) => {

            userController.getAllUsers((data:any) => {
                this.getWindow().webContents.send(EventGetter.get("update_employee_list"), data);
            }, () => {
                Dialog.showDialog("error", "error_retriving_employee_list", null, this.getWindow());
            });

            
        });


        // Request add employee
        ipcMain.on('request_add_employee', (event:any) => {

            let addEmployeeView = AddEmployeeView.getInstance(null, this.getWindow());
            addEmployeeView.show();

        });


    }

}