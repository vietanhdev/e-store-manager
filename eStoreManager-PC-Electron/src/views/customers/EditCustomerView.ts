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


export class EditCustomerView extends View {

    private userInfo: any
    
    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("edit_customer", window, parent, 600, 600);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);
    }

    private static instance: EditCustomerView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow, user:any) {
        if (!EditCustomerView.instance) {
            EditCustomerView.instance = new EditCustomerView(window, parent);
        }
        EditCustomerView.instance.userInfo = user;
        return EditCustomerView.instance;
    }

    // Handle all logic of this view
    logicHandle():void {

        var userController = new UserController();

        // ======= Handle requests from renderer process ========
        ipcMain.on(EventGetter.get('update_customer'), (event:any, data:any) => {
            userController.updateUserInfo(data, (respond:any) => {
                this.getWindow().webContents.send(EventGetter.get("update_customer_success"));
                Dialog.showDialog("info", TextGetter.get("updated_user_successfully"), null, this.getWindow(), () => {
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