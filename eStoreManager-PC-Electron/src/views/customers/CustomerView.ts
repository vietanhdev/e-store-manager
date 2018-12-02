import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {AddCustomerView} from './AddCustomerView';
import {EditCustomerView} from './EditCustomerView';
import {UserController} from '../../controllers/UserController';
import { TextGetter } from "../../services/TextGetter";
const settings = require('electron-settings');
const {ipcMain} = require('electron');
const { dialog } = require('electron');



export class CustomerView extends View {

    private userController:UserController;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("customers", window, parent);
        this.userController = new UserController();
    }
    
    private static instance: CustomerView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!CustomerView.instance) {
            CustomerView.instance = new CustomerView(window, parent);
        }
        return CustomerView.instance;
    }

    // Update Customer list
    updateCustomerList():void {
        this.userController.getAllUsers((data:any) => {
            this.getWindow().webContents.send(EventGetter.get("update_customer_list"), data);
        }, () => {
            Dialog.showDialog("error", "error_retriving_customer_list", null, this.getWindow());
        });
    }

    // Handle all logic of this view
    logicHandle():void {

        
        const textGetter = TextGetter.getInstance();

        // ======= Handle requests from renderer process ========

        // Request customer list
        ipcMain.on(EventGetter.get('request_update_customer_list'), (event:any) => {

            this.updateCustomerList();

        });


        // Request add customer
        ipcMain.on(EventGetter.get('request_add_customer'), (event:any) => {

            let addCustomerView = AddCustomerView.getInstance(null, this.getWindow());
            addCustomerView.show();

        });

        // // Request delete customer
        // ipcMain.on(EventGetter.get('request_delete_customer'), (event:any, data:any) => {

        //     // Check and prevent user from deleting their own account
        //     if (data.id == settings.get("account_info.userid")) {
        //         Dialog.showDialog("error", "delete_own_account_denied", null, this.getWindow());
        //     } else {
        //         dialog.showMessageBox(this.getWindow(), Object({
        //             type: "error",
        //             title: "Prompt",
        //             message: TextGetter.get("are_you_sure_delete_customer") + data.username,
        //             buttons: ["OK", TextGetter.get("cancel")]
        //         }), (result) => {
        //             if (result === 0) { // User click OK
        //                 this.userController.deleteUser(data.id, (respond:any) => {
        //                     Dialog.showDialogFromRespond("info", respond, this.getWindow());
        //                     // Update customer list
        //                     this.getWindow().webContents.send(EventGetter.get("delete_row_from_customer_table"), data.id);
        //                 }, (respond:any) => {
        //                     Dialog.showDialogFromRespond("error", respond, this.getWindow());
        //                 });
        //             }
        //         });
        //     }

        // });


        // // Request update customer info
        // ipcMain.on(EventGetter.get('request_update_user_info'), (event:any, user:any) => {

        //     let editCustomerView = EditCustomerView.getInstance(null, this.getWindow(), user);
        //     editCustomerView.show();

        // });


    }

}