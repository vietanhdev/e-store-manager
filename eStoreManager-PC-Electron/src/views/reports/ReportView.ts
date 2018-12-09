import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import { TextGetter } from "../../services/TextGetter";
const settings = require('electron-settings');
const {ipcMain} = require('electron');
const { dialog } = require('electron');
import {ReportController} from '../../controllers/ReportController';



export class ReportView extends View {

    private reportController:ReportController;
    private requestedBrowserWindow: BrowserWindow;

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super("reports", window, parent);
        this.reportController = new ReportController();
    }
    
    private static instance: ReportView;
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!ReportView.instance) {
            ReportView.instance = new ReportView(window, parent);
        }
        return ReportView.instance;
    }


    // Handle all logic of this view
    logicHandle():void {

        ipcMain.on(EventGetter.get("request_report_data"), (event:any, data:any) => {
            
            // Save requested window
            this.requestedBrowserWindow = event.sender.getOwnerBrowserWindow();

            this.reportController.getReport(data.dateFrom, data.dateTo, (respond:any) => { // Success
                
                // Send back the result to requested window
                this.requestedBrowserWindow.webContents.send(EventGetter.get("report_data"), respond);

            }, (respond:any) => { // Fail
                Dialog.showDialogFromRespond("error", respond, this.getWindow());
            });

        })
        
        
    }

}