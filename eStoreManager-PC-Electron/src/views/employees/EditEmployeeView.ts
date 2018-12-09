import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {UserController} from '../../controllers/UserController';
import { isNull } from "util";
const {ipcMain} = require('electron');
const { dialog } = require('electron');


export class EditEmployeeView extends View {

    private userInfo: any
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("edit_employee", window, parent, 600, 600);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: EditEmployeeView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow, user:any) {
        if (!EditEmployeeView.instance) {
            EditEmployeeView.instance = new EditEmployeeView(window, parent);
        }
        EditEmployeeView.instance.userInfo = user;
        return EditEmployeeView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var userController = new UserController();

        // ======= Handle requests from renderer process ========
        ipcMain.on(EventGetter.get('update_employee'), (event:any, data:any) => {
            userController.updateUserInfo(data, (respond:any) => {
                this.getWindow().webContents.send(EventGetter.get("update_employee_success"));
                Dialog.showDialog("info", "updated_user_successfully", null, this.getWindow(), () => {
                    this.hide();
                });
            }, (respond:any) => {
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            })
        });


        ipcMain.on(EventGetter.get('request_user_info'), (event:any) => {
            event.sender.getOwnerBrowserWindow().webContents.send("respond_request_user_info", this.userInfo);
        });

    }

}