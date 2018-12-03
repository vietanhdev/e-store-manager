import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {AddEmployeeView} from './AddEmployeeView';
import {EditEmployeeView} from './EditEmployeeView';
import {UserController} from '../../controllers/UserController';
import { TextGetter } from "../../services/TextGetter";
const settings = require('electron-settings');
const {ipcMain} = require('electron');
const { dialog } = require('electron');



export class EmployeeView extends View {

    private userController:UserController;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("employees", window, parent);
        this.userController = new UserController();
    }
    
    private static instance: EmployeeView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!EmployeeView.instance) {
            EmployeeView.instance = new EmployeeView(window, parent);
        }
        return EmployeeView.instance;
    }

    // Update Employee list
    updateEmployeeList():void {
        this.userController.getAllUsers((data:any) => {
            this.getWindow().webContents.send(EventGetter.get("update_employee_list"), data);
        }, () => {
            Dialog.showDialog("error", "error_retriving_employee_list", null, this.getWindow());
        });
    }

    // Handle all logic of this view
    logicHandle():void {

        
        const textGetter = TextGetter.getInstance();

        // ======= Handle requests from renderer process ========

        // Request employee list
        ipcMain.on(EventGetter.get('request_update_employee_list'), (event:any) => {
            this.updateEmployeeList();
        });

        // Request delete employee
        ipcMain.on(EventGetter.get('request_delete_employee'), (event:any, data:any) => {

            // Check and prevent user from deleting their own account
            if (data.id == settings.get("account_info.userid")) {
                Dialog.showDialog("error", "delete_own_account_denied", null, this.getWindow());
            } else {
                dialog.showMessageBox(this.getWindow(), Object({
                    type: "error",
                    title: "Prompt",
                    message: TextGetter.get("are_you_sure_delete_employee") + data.username,
                    buttons: ["OK", TextGetter.get("cancel")]
                }), (result) => {
                    if (result === 0) { // User click OK
                        this.userController.deleteUser(data.id, (respond:any) => {
                            Dialog.showDialogFromRespond("info", respond, this.getWindow());
                            // Update employee list
                            this.getWindow().webContents.send(EventGetter.get("delete_row_from_employee_table"), data.id);
                        }, (respond:any) => {
                            Dialog.showDialogFromRespond("error", respond, this.getWindow());
                        });
                    }
                });
            }

        });


        // Request change employee password
        ipcMain.on(EventGetter.get('request_change_user_password'), (event:any, data:any) => {

            this.userController.updateUserInfo(data, (respond:any) => {
                Dialog.showDialogFromRespond("info", respond, this.getWindow());
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            });

        });


        // Request update employee info
        ipcMain.on(EventGetter.get('request_update_user_info'), (event:any, user:any) => {

            let editEmployeeView = EditEmployeeView.getInstance(null, this.getWindow(), user);
            editEmployeeView.show();

        });


    }

}