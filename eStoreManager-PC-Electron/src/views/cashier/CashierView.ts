import { app, BrowserWindow, Menu} from "electron";
import * as path from "path";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import {View} from '../shared/View';
const {ipcMain} = require('electron');
const { dialog } = require('electron');

let express = require('express');
let bodyParser = require('body-parser');
let expressApp = express();

export class CashierView extends View {

    private orderWindow:BrowserWindow;
    private orderData: any;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("cashier",  window, parent);
    }

    private static instance: CashierView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!CashierView.instance) {
            CashierView.instance = new CashierView(window, parent);

            // === Setup server to receive barcode ===

            expressApp.use(bodyParser.urlencoded({
                extended: true
            }));
            expressApp.use(bodyParser.json());

            // Setup server port
            var expressAppPort = ConfigGetter.get().barcode_server.port;

            // Barcode service
            expressApp.get(ConfigGetter.get().barcode_server.path, (req:any, res:any) => {
                let product_code = req.query.code;
                console.log(product_code);
                if (CashierView.instance.isVisible()) {
                    CashierView.instance.getWindow().webContents.send(EventGetter.get('new_barcode_from_server'), product_code);
                }
                res.send('ok');
            });            

            // Launch app to listen to specified port
            expressApp.listen(expressAppPort, function () {
                console.log("Running barocode server on port " + expressAppPort);
            });


        }
        return CashierView.instance;
    }

    private getOrderWindow(): BrowserWindow {
        if (this.orderWindow == null) {
            this.orderWindow = new BrowserWindow({show: true});
            // Emitted when the window is closed.
            this.orderWindow.on("closed", (event: any) => {
                // Dereference the window object, usually you would store windows
                // in an array if your app supports multi windows, this is the time
                // when you should delete the corresponding element.
                this.orderWindow = null;
            });

            //this.orderWindow.webContents.openDevTools();

            return this.orderWindow;
        } else {
            return this.orderWindow;
        }
    }

    // Handle all logic of this view
    logicHandle():void {

        // Handle request discard order
        ipcMain.on(EventGetter.get('request_discard_order'), () => {
            // Ask user the new value of quantity
            dialog.showMessageBox(this.getWindow(), Object({
                type: "info",
                title: TextGetter.get("discard_order"),
                message: TextGetter.get("are_you_sure_discard_this_order"),
                buttons: [TextGetter.get("ok"), TextGetter.get("cancel")]
            }), (r) => {
                if (r == 0) {
                    this.getWindow().reload();
                }
            });
        });


        // Handle order printing
        ipcMain.on(EventGetter.get('request_print_order'), (e:any, data:any) => {

            // Save order data
            this.orderData = data;

            // Load order template
            this.getOrderWindow().loadFile(path.join(__dirname, "./ejs/order_template.ejs"));
        
        });

        // When the order window request the data, send it
        ipcMain.on(EventGetter.get('request_order_data'), (e:any, data:any) => {

            this.getOrderWindow().webContents.send(EventGetter.get('order_data'), this.orderData);

        });


        
    }

}