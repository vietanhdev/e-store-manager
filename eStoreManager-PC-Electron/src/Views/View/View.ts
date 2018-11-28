import { app, BrowserWindow, Menu } from "electron";
import * as path from "path";
import { EventEmitter } from "events";
const ejse = require('ejs-electron');
var events = require('events').EventEmitter;
const { dialog } = require('electron')
const {ipcMain} = require('electron');

export class View {
    
    private view: string;
    private viewFile: string;
    private window: BrowserWindow;
    private eventEmitter: EventEmitter;
    

    constructor (view: string, window: BrowserWindow, parent: BrowserWindow, width: number = 800, height: number = 600) {

        this.view = view;
        this.eventEmitter =  new events.EventEmitter();
        
        // If the window was not passed, create another window for this view
        if (window == null) {

            // Create the browser window.
            this.window = new BrowserWindow({
                height: height,
                width: width,
                parent: parent,
                webPreferences: {
                    nodeIntegrationInWorker: true
                }
            });

            // Emitted when the window is closed.
            this.window.on("closed", () => {
                // Dereference the window object, usually you would store windows
                // in an array if your app supports multi windows, this is the time
                // when you should delete the corresponding element.
                this.window = null;
            });
            
        } else {
            this.window = window;
        }

        // Calculate view file path and write to this.viewFile
        this.setViewFile(view);

        // Handle request_change_view event from browser thread
        ipcMain.on('request_change_view', (event:any, data:any) => {
            this.requestChangeView(data);
        });

        // Call logic handler
        this.logicHandle();

    }

    

    // Handle all logic of this view
    logicHandle():void {}

    getView():string {
        return this.view;
    }

    getEventEmitter():EventEmitter {
        return this.eventEmitter;
    }

    setViewFile(view: string):void {
        this.viewFile = path.join(__dirname, "../"+view+".ejs");
    }

    setMenu(menu: Menu): void {
        this.window.setMenu(menu);
    }

    public getWindow():BrowserWindow {
        return this.window;
    }

    // Load view file to view window
    public show() {
        this.window.loadFile(this.viewFile); // Load view file to view window
    }

    public showLoadingModal() {
        this.window.webContents.send('loading-modal' , {status:'show'});
    }
    public hideLoadingModal() {
        this.window.webContents.send('loading-modal' , {status:'hide'});
    }


    requestChangeView(view: string) {
        this.eventEmitter.emit("request_change_view", view);
    }

}